-- ----------------------------
-- Senha padrão: senha123 (BCrypt)
-- ----------------------------

-- 1 LÍDER
INSERT INTO tbl_usuarios (username, password, role_usuario, funcao, cargo, setor)
VALUES
    ('lider', '$2y$12$BivcwEEHteyl8lo1GdVWqe3vgrsqNGljQkjWch4DQZlBKqY7TBxrG', 'ROLE_SUPERIOR', 'Gestão de Projetos', 'Gerente', 'Desenvolvimento');

-- 1 ADMIN
INSERT INTO tbl_usuarios (username, password, role_usuario, funcao)
VALUES
    ('admin', '$2y$12$BivcwEEHteyl8lo1GdVWqe3vgrsqNGljQkjWch4DQZlBKqY7TBxrG', 'ROLE_ADMIN', 'Administração do Task Mirror');

-- 5 SUBORDINADOS
INSERT INTO tbl_usuarios (username, password, role_usuario, funcao, cargo, setor, id_lider)
VALUES
    ('usuario-subordinado1','$2y$12$BivcwEEHteyl8lo1GdVWqe3vgrsqNGljQkjWch4DQZlBKqY7TBxrG','ROLE_SUBORDINADO','Desenvolvedor Full Stack','Analista Desenvolvedor','Desenvolvimento',1),
    ('usuario-subordinado2','$2y$12$BivcwEEHteyl8lo1GdVWqe3vgrsqNGljQkjWch4DQZlBKqY7TBxrG','ROLE_SUBORDINADO','Desenvolvedor Full Stack','Analista Desenvolvedor','Desenvolvimento',1),
    ('usuario-subordinado3','$2y$12$BivcwEEHteyl8lo1GdVWqe3vgrsqNGljQkjWch4DQZlBKqY7TBxrG','ROLE_SUBORDINADO','Desenvolvedor Full Stack','Analista Desenvolvedor','Desenvolvimento',1),
    ('usuario-subordinado4','$2y$12$BivcwEEHteyl8lo1GdVWqe3vgrsqNGljQkjWch4DQZlBKqY7TBxrG','ROLE_SUBORDINADO','Desenvolvedor Full Stack','Analista Desenvolvedor','Desenvolvimento',1),
    ('usuario-subordinado5','$2y$12$BivcwEEHteyl8lo1GdVWqe3vgrsqNGljQkjWch4DQZlBKqY7TBxrG','ROLE_SUBORDINADO','Desenvolvedor Full Stack','Analista Desenvolvedor','Desenvolvimento',1);
