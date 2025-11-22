CREATE OR REPLACE PACKAGE task_mirror_pkg AS

    PROCEDURE proc_populate_tables(p_tabela IN VARCHAR2, p_campos IN VARCHAR2, p_valores IN VARCHAR2);
    FUNCTION query_to_json(p_query IN VARCHAR2) RETURN CLOB;
    FUNCTION fn_tempo_medio_conclusao_total RETURN NUMBER;
    FUNCTION fn_produtividade_usuario(P_id_usuario_subordinado IN NUMBER) RETURN NUMBER;
    PROCEDURE proc_qtd_tarefas_por_status(p_json OUT CLOB);

END task_mirror_pkg;
/

CREATE OR REPLACE PACKAGE BODY task_mirror_pkg AS

    -- POPULAR TABELAS
    PROCEDURE proc_populate_tables(
        p_tabela IN VARCHAR2,
        p_campos IN VARCHAR2,
        p_valores IN VARCHAR2)
    AS
        v_dml VARCHAR2(500);
BEGIN
        v_dml := 'INSERT INTO ' || p_tabela || '(' || p_campos || ') VALUES ( ' || p_valores || ')';

EXECUTE IMMEDIATE v_dml;
COMMIT;
EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('ERRO AO INSERIR DADOS: ' || SQLERRM);
END;

    -- TRANSFORMA TABLE PARA JSON
    FUNCTION query_to_json(p_query IN VARCHAR2)
        RETURN CLOB IS
        v_json      CLOB := '[';
        v_first_row BOOLEAN := TRUE;

        v_cursor    INTEGER;
        v_col_cnt   INTEGER;
        v_desc_t    DBMS_SQL.DESC_TAB;
        v_col_val   VARCHAR2(4000);
        v_status    INTEGER;
BEGIN
        -- ABRE CURSOR DINÂMICO
        v_cursor := DBMS_SQL.OPEN_CURSOR;
        DBMS_SQL.PARSE(v_cursor, p_query, DBMS_SQL.NATIVE);

        -- DESCRIBE COLUNAS
        DBMS_SQL.DESCRIBE_COLUMNS(v_cursor, v_col_cnt, v_desc_t);
FOR i IN 1..v_col_cnt LOOP
            DBMS_SQL.DEFINE_COLUMN(v_cursor, i, v_col_val, 4000);
END LOOP;

        -- EXECUTA QUERY
        v_status := DBMS_SQL.EXECUTE(v_cursor);

        -- Lê TODAS AS LINHAS
        LOOP
EXIT WHEN DBMS_SQL.FETCH_ROWS(v_cursor) = 0;

            IF NOT v_first_row THEN
                v_json := v_json || ','; -- SEPARADOR
ELSE
                v_first_row := FALSE;
END IF;

            v_json := v_json || CHR(10) || ' {';
FOR i IN 1..v_col_cnt LOOP
                DBMS_SQL.COLUMN_VALUE(v_cursor, i, v_col_val);
                v_json := v_json || '"' || LOWER(v_desc_t(i).col_name) || '":' ||
                  CASE
                      -- Se for um número inteiro, exibe sem aspas
                      WHEN REGEXP_LIKE(v_col_val, '^\d+$') THEN v_col_val
                      -- Caso contrário, trata como string (mesmo que seja 'NULL' ou um valor qualquer)
                      ELSE '"' || NVL(v_col_val, 'NULL') || '"'
END;

                IF i < v_col_cnt THEN
                    v_json := v_json || ',';
END IF;
END LOOP;
            v_json := v_json || '}' || CHR(10);
END LOOP;

        -- FECHA O CURSOR
        DBMS_SQL.CLOSE_CURSOR(v_cursor);

        -- FECHAR O ARRAY
        v_json := v_json || ']';
RETURN v_json;

EXCEPTION
        WHEN INVALID_CURSOR THEN
            IF DBMS_SQL.IS_OPEN(v_cursor) THEN
                DBMS_SQL.CLOSE_CURSOR(v_cursor);
END IF;
RETURN '{"error":"INVALID CURSOR"}';
WHEN VALUE_ERROR THEN
            IF DBMS_SQL.IS_OPEN(v_cursor) THEN
                DBMS_SQL.CLOSE_CURSOR(v_cursor);
END IF;
RETURN '{"error":"VALUE ERROR"}';
WHEN OTHERS THEN
            IF DBMS_SQL.IS_OPEN(v_cursor) THEN
                DBMS_SQL.CLOSE_CURSOR(v_cursor);
END IF;
RETURN '{"error":"UNKNOWN ERROR: ' || SQLERRM || '"}';
END query_to_json;

    -- TEMPO MEDIO DE CONCLUSAO (TOTAL)
      FUNCTION fn_tempo_medio_conclusao_total
        RETURN NUMBER
    AS
        v_tempo_medio NUMBER;
        v_count NUMBER;
BEGIN

SELECT COUNT(*) INTO v_count
FROM tbl_tarefas tt
         JOIN tbl_status_tarefa tst ON tst.id_status_tarefa = tt.id_status_tarefa
WHERE tempo_real > 0;

IF v_count = 0 THEN
            RAISE_APPLICATION_ERROR(-20001, 'Não existem tarefas concluídas.');
END IF;

SELECT AVG(tempo_real) INTO v_tempo_medio
FROM tbl_tarefas tt
         JOIN tbl_status_tarefa tst ON tst.id_status_tarefa = tt.id_status_tarefa
WHERE tempo_real > 0;

RETURN ROUND(v_tempo_medio, 2);

EXCEPTION
        WHEN ZERO_DIVIDE THEN
            RAISE_APPLICATION_ERROR(-20002, 'Alguma tarefa possui tempo real igual a zero.');
WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20010, 'Erro ao calcular tempo médio: ' || SQLERRM);
END fn_tempo_medio_conclusao_total;

    -- PRODUTIVIDADE POR USUARIO (ROLE_SUBORDINADO)
    FUNCTION fn_produtividade_usuario (p_id_usuario_subordinado IN NUMBER)
    RETURN NUMBER
    AS
        v_produtividade_media NUMBER;
        v_count NUMBER;
BEGIN
SELECT COUNT(*) INTO v_count
FROM tbl_usuarios
WHERE id_usuario = p_id_usuario_subordinado AND role_usuario = 'ROLE_SUBORDINADO';

IF v_count = 0 THEN
            RAISE_APPLICATION_ERROR(-20001, 'Usuário não encontrado');
END IF;

SELECT AVG((tempo_estimado / tempo_real) * 100)
INTO v_produtividade_media
FROM tbl_tarefas tt
         JOIN tbl_status_tarefa tst ON tst.id_status_tarefa = tt.id_status_tarefa
WHERE tt.id_usuario = p_id_usuario_subordinado AND tst.nome = 'CONCLUIDA'
  AND tempo_estimado > 0
  AND tempo_real > 0
  AND data_fim IS NOT NULL;

IF v_produtividade_media IS NULL THEN
        RAISE_APPLICATION_ERROR(-20002, 'Usuário não possui tarefas concluídas para calcular produtividade.');
END IF;

RETURN ROUND(v_produtividade_media, 2);

EXCEPTION
          WHEN ZERO_DIVIDE THEN
            RAISE_APPLICATION_ERROR(-20003, 'Tempo real não pode ser zero em alguma tarefa.');
WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20010, 'Erro ao calcular produtividade do usuário: ' || SQLERRM);
END;

    -- QUANTIDADE DE TAREFAS POR STATUS EM JSON
    PROCEDURE proc_qtd_tarefas_por_status(p_json OUT CLOB)
IS
        v_query VARCHAR2(1000);
BEGIN
        v_query := 'SELECT tst.nome AS status_tarefa,
                    COUNT(tt.id_status_tarefa) AS quantidade
                    FROM tbl_status_tarefa tst
                    LEFT JOIN tbl_tarefas tt ON tt.id_status_tarefa = tst.id_status_tarefa
                    GROUP BY tst.nome
                    ORDER BY tst.nome';
        p_json := query_to_json(v_query);
EXCEPTION
        WHEN OTHERS THEN
            p_json :=  '{"error":"Erro ao gerar JSON: ' || SQLERRM || '"}';
END proc_qtd_tarefas_por_status;
END task_mirror_pkg;
/