CREATE OR REPLACE TRIGGER trg_auditoria_tbl_usuarios
AFTER INSERT OR UPDATE OR DELETE ON tbl_usuarios
    FOR EACH ROW
DECLARE
v_antigos CLOB;
    v_novos   CLOB;
    v_tipo_operacao VARCHAR2(10);
BEGIN
    IF INSERTING THEN
        v_tipo_operacao := 'INSERT';
    ELSIF UPDATING THEN
        v_tipo_operacao := 'UPDATE';
    ELSIF DELETING THEN
        v_tipo_operacao := 'DELETE';
END IF;

    IF DELETING OR UPDATING THEN
        v_antigos := 'id_usuario: ' || :OLD.id_usuario ||
                      ', username: ' || :OLD.username ||
                      ', senha: ' || :OLD.password ||
                      ', role_usuario: ' || :OLD.role_usuario ||
                      ', funcao: ' || :OLD.funcao ||
                      ', ativo: ' || :OLD.ativo ||
                      ', cargo: ' || :OLD.cargo ||
                      ', setor: ' || :OLD.setor ||
                      ', id_lider: ' || NVL(TO_CHAR(:OLD.id_lider), 'NULL');
END IF;

    IF INSERTING OR UPDATING THEN
        v_novos := 'id_usuario: ' || :NEW.id_usuario ||
                    ', username: ' || :NEW.username ||
                    ', senha: ' || :NEW.password ||
                    ', role_usuario: ' || :NEW.role_usuario ||
                    ', funcao: ' || :NEW.funcao ||
                    ', ativo: ' || :NEW.ativo ||
                    ', cargo: ' || :NEW.cargo ||
                    ', setor: ' || :NEW.setor ||
                    ', id_lider: ' || NVL(TO_CHAR(:NEW.id_lider), 'NULL');
END IF;

INSERT INTO tbl_auditoria (
    nome_tabela,
    tipo_operacao,
    usuario,
    data_operacao,
    valores_antigos,
    valores_novos
)
VALUES (
           'TBL_USUARIOS',
           v_tipo_operacao,
           USER,
           SYSTIMESTAMP,
           v_antigos,
           v_novos
       );

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('ERRO: ' || SQLERRM);
END;
/
