-- senha123 (BCrypt) para todos

---------------------------------------
-- 1 LÍDER
---------------------------------------
INSERT INTO tbl_usuarios (
    username, password, role_usuario, funcao, ativo, cargo, setor, id_lider
) VALUES (
             'lider01',
             '$2y$12$BivcwEEHteyl8lo1GdVWqe3vgrsqNGljQkjWch4DQZlBKqY7TBxrG',
             'ROLE_SUPERIOR',
             'Gestão',
             1,
             'Coordenador',
             'TI',
             NULL
         );

---------------------------------------
-- 1 ADMIN
---------------------------------------
INSERT INTO tbl_usuarios (
    username, password, role_usuario, funcao, ativo, cargo, setor, id_lider
) VALUES (
             'admin01',
             '$2y$12$BivcwEEHteyl8lo1GdVWqe3vgrsqNGljQkjWch4DQZlBKqY7TBxrG',
             'ROLE_ADMIN',
             'Administração do sistema',
             1,
             'Administrador',
             'TI',
             NULL
         );

---------------------------------------
-- 5 SUBORDINADOS
---------------------------------------

INSERT INTO tbl_usuarios (
    username, password, role_usuario, funcao, ativo, cargo, setor, id_lider
) VALUES (
             'sub01',
             '$2y$12$BivcwEEHteyl8lo1GdVWqe3vgrsqNGljQkjWch4DQZlBKqY7TBxrG',
             'ROLE_SUBORDINADO',
             'Desenvolvimento',
             1,
             'Analista',
             'TI',
             1
         );

INSERT INTO tbl_usuarios (
    username, password, role_usuario, funcao, ativo, cargo, setor, id_lider
) VALUES (
             'sub02',
             '$2y$12$BivcwEEHteyl8lo1GdVWqe3vgrsqNGljQkjWch4DQZlBKqY7TBxrG',
             'ROLE_SUBORDINADO',
             'Desenvolvimento',
             1,
             'Analista',
             'TI',
             1
         );

INSERT INTO tbl_usuarios (
    username, password, role_usuario, funcao, ativo, cargo, setor, id_lider
) VALUES (
             'sub03',
             '$2y$12$BivcwEEHteyl8lo1GdVWqe3vgrsqNGljQkjWch4DQZlBKqY7TBxrG',
             'ROLE_SUBORDINADO',
             'Desenvolvimento',
             1,
             'Analista',
             'TI',
             1
         );

INSERT INTO tbl_usuarios (
    username, password, role_usuario, funcao, ativo, cargo, setor, id_lider
) VALUES (
             'sub04',
             '$2y$12$BivcwEEHteyl8lo1GdVWqe3vgrsqNGljQkjWch4DQZlBKqY7TBxrG',
             'ROLE_SUBORDINADO',
             'Desenvolvimento',
             1,
             'Analista',
             'TI',
             1
         );

INSERT INTO tbl_usuarios (
    username, password, role_usuario, funcao, ativo, cargo, setor, id_lider
) VALUES (
             'sub05',
             '$2y$12$BivcwEEHteyl8lo1GdVWqe3vgrsqNGljQkjWch4DQZlBKqY7TBxrG',
             'ROLE_SUBORDINADO',
             'Desenvolvimento',
             1,
             'Analista',
             'TI',
             1
         );
