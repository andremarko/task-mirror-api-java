INSERT INTO tbl_tarefas (id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, data_inicio)
VALUES
    (3, 1, 1, 1, 'Planejamento da sprint inicial', 2.5, SYSDATETIMEOFFSET()),
    (4, 1, 2, 1, 'Execução de testes unitários', 3.0, SYSDATETIMEOFFSET()),
    (5, 1, 3, 1, 'Análise de logs de produção', 1.5, SYSDATETIMEOFFSET()),
    (6, 1, 4, 1, 'Suporte a chamados críticos', 2.0, SYSDATETIMEOFFSET()),
    (7, 1, 5, 1, 'Revisão de documentação técnica', 2.5, SYSDATETIMEOFFSET());
