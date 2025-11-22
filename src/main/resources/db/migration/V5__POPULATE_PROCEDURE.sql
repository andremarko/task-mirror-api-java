---------------------------------------------
-- INSERT TBL_STATUS_TAREFA / TBL_TIPO_TAREFA
---------------------------------------------

BEGIN
-- STATUS
task_mirror_pkg.proc_populate_tables(
'tbl_status_tarefa',
'nome',
'''PENDENTE'''
);

task_mirror_pkg.proc_populate_tables(
'tbl_status_tarefa',
'nome',
'''CONCLUIDA'''
);

task_mirror_pkg.proc_populate_tables(
'tbl_status_tarefa',
'nome',
'''ATRASADA'''
);

-- TIPOS
task_mirror_pkg.proc_populate_tables(
'tbl_tipo_tarefa',
'nome',
'''PLANEJAMENTO / ORGANIZACAO'''
);

task_mirror_pkg.proc_populate_tables(
'tbl_tipo_tarefa',
'nome',
'''EXECUCAO / OPERACAO'''
);

task_mirror_pkg.proc_populate_tables(
'tbl_tipo_tarefa',
'nome',
'''ANALISE / PESQUISA'''
);

task_mirror_pkg.proc_populate_tables(
'tbl_tipo_tarefa',
'nome',
'''SUPORTE / ATENDIMENTO'''
);

task_mirror_pkg.proc_populate_tables(
'tbl_tipo_tarefa',
'nome',
'''REVISAO / VERIFICACAO'''
);

task_mirror_pkg.proc_populate_tables(
'tbl_tipo_tarefa',
'nome',
'''DOCUMENTACAO'''
);

task_mirror_pkg.proc_populate_tables(
'tbl_tipo_tarefa',
'nome',
'''MANUTENCAO / AJUSTE'''
);

task_mirror_pkg.proc_populate_tables(
'tbl_tipo_tarefa',
'nome',
'''COMUNICACAO / REUNIOES'''
);

task_mirror_pkg.proc_populate_tables(
'tbl_tipo_tarefa',
'nome',
'''AUDITORIA / CONTROLE'''
);
END;
/
------------------------------------------------
-- INSERT TBL_USUARIOS
------------------------------------------------

BEGIN
task_mirror_pkg.proc_populate_tables(
'tbl_usuarios',
'username, password, role_usuario, funcao, cargo, setor',
'''lider'', ''$2y$12$BivcwEEHteyl8lo1GdVWqe3vgrsqNGljQkjWch4DQZlBKqY7TBxrG'', ''ROLE_SUPERIOR'', ''Gestao de Projetos'', ''Gerente'', ''Desenvolvimento'''
);

task_mirror_pkg.proc_populate_tables(
'tbl_usuarios',
'username, password, role_usuario, funcao, cargo, setor',
'''admin'',
''$2y$12$BivcwEEHteyl8lo1GdVWqe3vgrsqNGljQkjWch4DQZlBKqY7TBxrG'',
''ROLE_ADMIN'',
''Administração do Task Mirror'',
''Administrador'',
''Gerência'''
);
task_mirror_pkg.proc_populate_tables(
'tbl_usuarios',
'username, password, role_usuario, funcao, cargo, setor, id_lider',
'''usuario-subordinado1'', ''$2y$12$BivcwEEHteyl8lo1GdVWqe3vgrsqNGljQkjWch4DQZlBKqY7TBxrG'', ''ROLE_SUBORDINADO'', ''Desenvolvedor Full Stack'', ''Analista Desenvolvedor'', ''Desenvolvimento'', 1'
);

task_mirror_pkg.proc_populate_tables(
'tbl_usuarios',
'username, password, role_usuario, funcao, cargo, setor, id_lider',
'''usuario-subordinado2'', ''$2y$12$BivcwEEHteyl8lo1GdVWqe3vgrsqNGljQkjWch4DQZlBKqY7TBxrG'', ''ROLE_SUBORDINADO'', ''Desenvolvedor Full Stack'', ''Analista Desenvolvedor'', ''Desenvolvimento'', 1'
);

task_mirror_pkg.proc_populate_tables(
'tbl_usuarios',
'username, password, role_usuario, funcao, cargo, setor, id_lider',
'''usuario-subordinado3'', ''$2y$12$BivcwEEHteyl8lo1GdVWqe3vgrsqNGljQkjWch4DQZlBKqY7TBxrG'', ''ROLE_SUBORDINADO'', ''Desenvolvedor Full Stack'', ''Analista Desenvolvedor'', ''Desenvolvimento'', 1'
);

task_mirror_pkg.proc_populate_tables(
'tbl_usuarios',
'username, password, role_usuario, funcao, cargo, setor, id_lider',
'''usuario-subordinado4'', ''$2y$12$BivcwEEHteyl8lo1GdVWqe3vgrsqNGljQkjWch4DQZlBKqY7TBxrG'', ''ROLE_SUBORDINADO'', ''Desenvolvedor Full Stack'', ''Analista Desenvolvedor'', ''Desenvolvimento'', 1'
);

task_mirror_pkg.proc_populate_tables(
'tbl_usuarios',
'username, password, role_usuario, funcao, cargo, setor, id_lider',
'''usuario-subordinado5'', ''$2y$12$BivcwEEHteyl8lo1GdVWqe3vgrsqNGljQkjWch4DQZlBKqY7TBxrG'', ''ROLE_SUBORDINADO'', ''Desenvolvedor Full Stack'', ''Analista Desenvolvedor'', ''Desenvolvimento'', 1'
);
END;
/
------------------------------------------------
-- INSERT TBL_TAREFAS
------------------------------------------------

BEGIN
task_mirror_pkg.proc_populate_tables(
'tbl_tarefas',
'id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, data_inicio',
'3, 1, 1, 1, ''Planejamento da sprint inicial'', 2.5, SYSTIMESTAMP'
);

task_mirror_pkg.proc_populate_tables(
'tbl_tarefas',
'id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, tempo_real, data_inicio, data_fim',
'3, 1, 2, 2, ''Implementacao endpoints - backend'', 2, 1, SYSTIMESTAMP, SYSTIMESTAMP + INTERVAL ''1'' HOUR'
);

task_mirror_pkg.proc_populate_tables(
'tbl_tarefas',
'id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, data_inicio',
'3, 1, 1, 1, ''Planejamento da sprint inicial'', 2.5, SYSTIMESTAMP'
);

task_mirror_pkg.proc_populate_tables(
'tbl_tarefas',
'id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, data_inicio',
'4, 1, 2, 1, ''Execucao de testes unitarios'', 3.0, SYSTIMESTAMP'
);

task_mirror_pkg.proc_populate_tables(
'tbl_tarefas',
'id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, data_inicio',
'5, 1, 3, 1, ''Analise de logs de producao'', 1.5, SYSTIMESTAMP'
);

task_mirror_pkg.proc_populate_tables(
'tbl_tarefas',
'id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, data_inicio',
'6, 1, 4, 1, ''Suporte a chamados criticos'', 2.0, SYSTIMESTAMP'
);

task_mirror_pkg.proc_populate_tables(
'tbl_tarefas',
'id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, data_inicio',
'7, 1, 5, 1, ''Revisao de documentacao tecnica'', 2.5, SYSTIMESTAMP'
);

task_mirror_pkg.proc_populate_tables(
'tbl_tarefas',
'id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, data_inicio',
'3, 1, 1, 1, ''Planejamento da sprint 2'', 2.5, SYSTIMESTAMP'
);

task_mirror_pkg.proc_populate_tables(
'tbl_tarefas',
'id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, tempo_real, data_inicio, data_fim',
'3, 1, 2, 2, ''Testes de integracao - backend'', 3.0, 3.5, SYSTIMESTAMP, SYSTIMESTAMP + INTERVAL ''3'' HOUR + INTERVAL ''30'' MINUTE'
);

task_mirror_pkg.proc_populate_tables(
'tbl_tarefas',
'id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, tempo_real, data_inicio, data_fim',
'4, 1, 2, 3, ''Implementacao de endpoints - frontend'', 4.0, 5.0, SYSTIMESTAMP, SYSTIMESTAMP + INTERVAL ''5'' HOUR'
);

task_mirror_pkg.proc_populate_tables(
'tbl_tarefas',
'id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, data_inicio',
'4, 1, 3, 1, ''Analise de performance'', 2.0, SYSTIMESTAMP'
);

task_mirror_pkg.proc_populate_tables(
'tbl_tarefas',
'id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, tempo_real, data_inicio, data_fim',
'5, 1, 5, 2, ''Revisao de codigo'', 2.0, 2.0, SYSTIMESTAMP, SYSTIMESTAMP + INTERVAL ''2'' HOUR'
);

task_mirror_pkg.proc_populate_tables(
'tbl_tarefas',
'id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, data_inicio',
'5, 1, 4, 1, ''Suporte tecnico a clientes'', 3.0, SYSTIMESTAMP'
);

task_mirror_pkg.proc_populate_tables(
'tbl_tarefas',
'id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, tempo_real, data_inicio, data_fim',
'6, 1, 3, 2, ''Analise de incidentes de producao'', 2.5, 2.0, SYSTIMESTAMP, SYSTIMESTAMP + INTERVAL ''2'' HOUR + INTERVAL ''30'' MINUTE'
);

task_mirror_pkg.proc_populate_tables(
'tbl_tarefas',
'id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, data_inicio',
'6, 1, 2, 1, ''Testes de usabilidade'', 2.0, SYSTIMESTAMP'
);

task_mirror_pkg.proc_populate_tables(
'tbl_tarefas',
'id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, tempo_real, data_inicio, data_fim',
'7, 1, 6, 2, ''Atualizacao de documentacao'', 1.5, 1.5, SYSTIMESTAMP, SYSTIMESTAMP + INTERVAL ''1'' HOUR + INTERVAL ''30'' MINUTE'
);

task_mirror_pkg.proc_populate_tables(
'tbl_tarefas',
'id_usuario, id_lider, id_tipo_tarefa, id_status_tarefa, descricao, tempo_estimado, data_inicio',
'7, 1, 8, 1, ''Reuniao de alinhamento de projeto'', 1.0, SYSTIMESTAMP'
);
END;
/

BEGIN
-----------------------------------------------------
-- FEEDBACK PARA TAREFA 2
-----------------------------------------------------
task_mirror_pkg.proc_populate_tables(
'tbl_feedbacks',
'id_tarefa, conteudo',
'2, ''A implementação dos endpoints backend foi finalizada com sucesso. O tempo real ficou dentro da margem prevista e a qualidade do código está consistente. Bom desempenho geral.'''
);

-----------------------------------------------------
-- FEEDBACK PARA TAREFA 9
-----------------------------------------------------
task_mirror_pkg.proc_populate_tables(
'tbl_feedbacks',
'id_tarefa, conteudo',
'9, ''Os testes de integração do backend mostraram boa cobertura e estabilidade. Pequenos ajustes foram necessários, mas o resultado final foi positivo. Excelente execução.'''
);

-----------------------------------------------------
-- FEEDBACK PARA TAREFA 12
-----------------------------------------------------
task_mirror_pkg.proc_populate_tables(
'tbl_feedbacks',
'id_tarefa, conteudo',
'12, ''A revisão de código foi concluída com eficiência. A análise foi clara, objetiva e contribuiu para melhorias importantes no padrão do projeto.'''
);

-----------------------------------------------------
-- FEEDBACK PARA TAREFA 14
-----------------------------------------------------
task_mirror_pkg.proc_populate_tables(
'tbl_feedbacks',
'id_tarefa, conteudo',
'14, ''A análise de incidentes foi bem executada. A identificação da causa raiz foi precisa e a documentação das correções está organizada. Ótimo trabalho.'''
);

-----------------------------------------------------
-- FEEDBACK PARA TAREFA 16
-----------------------------------------------------
task_mirror_pkg.proc_populate_tables(
'tbl_feedbacks',
'id_tarefa, conteudo',
'16, ''A atualização da documentação foi concluída de forma cuidadosa e alinhada às necessidades da equipe. O conteúdo ficou claro e bem estruturado.'''
);
END;
/


COMMIT;
