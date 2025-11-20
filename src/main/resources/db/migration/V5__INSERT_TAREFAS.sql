-- Tarefa 1
INSERT INTO tbl_tarefas (
id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, data_inicio
) VALUES (
3, 1, 1, 1, 'Planejamento da sprint inicial', 2.5, SYSTIMESTAMP
);

-- Tarefa 2
INSERT INTO tbl_tarefas (
id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, tempo_real, data_inicio, data_fim
) VALUES (
3, 1, 2, 2, 'Implementação endpoints - backend', 2, 1, SYSTIMESTAMP, SYSTIMESTAMP + INTERVAL '1' HOUR
);

-- Tarefa 3
INSERT INTO tbl_tarefas (
id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, data_inicio
) VALUES (
3, 1, 1, 1, 'Planejamento da sprint inicial', 2.5, SYSTIMESTAMP
);

-- Tarefa 4
INSERT INTO tbl_tarefas (
id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, data_inicio
) VALUES (
4, 1, 2, 1, 'Execução de testes unitários', 3.0, SYSTIMESTAMP
);

-- Tarefa 5
INSERT INTO tbl_tarefas (
id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, data_inicio
) VALUES (
5, 1, 3, 1, 'Análise de logs de produção', 1.5, SYSTIMESTAMP
);

-- Tarefa 6
INSERT INTO tbl_tarefas (
id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, data_inicio
) VALUES (
6, 1, 4, 1, 'Suporte a chamados críticos', 2.0, SYSTIMESTAMP
);

-- Tarefa 7
INSERT INTO tbl_tarefas (
id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, data_inicio
) VALUES (
7, 1, 5, 1, 'Revisão de documentação técnica', 2.5, SYSTIMESTAMP
);

-- Tarefa 8
INSERT INTO tbl_tarefas (
id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, data_inicio
) VALUES (
3, 1, 1, 1, 'Planejamento da sprint 2', 2.5, SYSTIMESTAMP
);

-- Tarefa 9
INSERT INTO tbl_tarefas (
id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, tempo_real, data_inicio, data_fim
) VALUES (
3, 1, 2, 2, 'Testes de integração - backend', 3.0, 3.5, SYSTIMESTAMP, SYSTIMESTAMP + INTERVAL '3' HOUR + INTERVAL '30' MINUTE
);

-- Tarefa 10
INSERT INTO tbl_tarefas (
id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, tempo_real, data_inicio, data_fim
) VALUES (
4, 1, 2, 3, 'Implementação de endpoints - frontend', 4.0, 5.0, SYSTIMESTAMP, SYSTIMESTAMP + INTERVAL '5' HOUR
);

-- Tarefa 11
INSERT INTO tbl_tarefas (
id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, data_inicio
) VALUES (
4, 1, 3, 1, 'Análise de performance', 2.0, SYSTIMESTAMP
);

-- Tarefa 12
INSERT INTO tbl_tarefas (
id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, tempo_real, data_inicio, data_fim
) VALUES (
5, 1, 5, 2, 'Revisão de código', 2.0, 2.0, SYSTIMESTAMP, SYSTIMESTAMP + INTERVAL '2' HOUR
);

-- Tarefa 13
INSERT INTO tbl_tarefas (
id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, data_inicio
) VALUES (
5, 1, 4, 1, 'Suporte técnico a clientes', 3.0, SYSTIMESTAMP
);

-- Tarefa 14
INSERT INTO tbl_tarefas (
id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, tempo_real, data_inicio, data_fim
) VALUES (
6, 1, 3, 2, 'Análise de incidentes de produção', 2.5, 2.0, SYSTIMESTAMP, SYSTIMESTAMP + INTERVAL '2' HOUR + INTERVAL '30' MINUTE
);

-- Tarefa 15
INSERT INTO tbl_tarefas (
id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, data_inicio
) VALUES (
6, 1, 2, 1, 'Testes de usabilidade', 2.0, SYSTIMESTAMP
);

-- Tarefa 16
INSERT INTO tbl_tarefas (
id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, tempo_real, data_inicio, data_fim
) VALUES (
7, 1, 6, 2, 'Atualização de documentação', 1.5, 1.5, SYSTIMESTAMP, SYSTIMESTAMP + INTERVAL '1' HOUR + INTERVAL '30' MINUTE
);

-- Tarefa 17
INSERT INTO tbl_tarefas (
id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, data_inicio
) VALUES (
7, 1, 8, 1, 'Reunião de alinhamento de projeto', 1.0, SYSTIMESTAMP
);
