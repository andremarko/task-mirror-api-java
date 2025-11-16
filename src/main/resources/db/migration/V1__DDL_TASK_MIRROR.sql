-- Tabela de usuários (com self join para representar líderes e subordinados)
CREATE TABLE tbl_usuarios (
id_usuario INT IDENTITY(1,1) PRIMARY KEY,
username VARCHAR(100) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
role_usuario VARCHAR(100) NOT NULL,
funcao VARCHAR(100) NOT NULL,
ativo BIT DEFAULT 1,
cargo VARCHAR(100) NULL,
setor VARCHAR(100) NULL,
id_lider INT NULL,
CONSTRAINT fk_usuarios_lider FOREIGN KEY (id_lider) REFERENCES tbl_usuarios(id_usuario) ON DELETE SET NULL
);

-- Tabela de tipos de tarefa
CREATE TABLE tbl_tipo_tarefa (
id_tipo_tarefa INT IDENTITY(1,1) PRIMARY KEY,
nome VARCHAR(100) NOT NULL
);

-- Tabela de status da tarefa
CREATE TABLE tbl_status_tarefa (
id_status_tarefa INT IDENTITY(1,1) PRIMARY KEY,
nome VARCHAR(100) NOT NULL
);

-- Tabela de tarefas
CREATE TABLE tbl_tarefas (
id_tarefa INT IDENTITY(1,1) PRIMARY KEY,
id_usuario INT NOT NULL,
id_lider INT NOT NULL,
id_tipo_tarefa INT NOT NULL,
id_status_tarefa INT NOT NULL,
descricao VARCHAR(500),
tempo_estimado DECIMAL(5,2),
tempo_real DECIMAL(5,2),
data_inicio DATETIMEOFFSET,
data_fim DATETIMEOFFSET NULL,
CONSTRAINT fk_tarefas_usuario FOREIGN KEY (id_usuario) REFERENCES tbl_usuarios(id_usuario),
CONSTRAINT fk_tarefas_lider FOREIGN KEY (id_lider) REFERENCES tbl_usuarios(id_usuario),
CONSTRAINT fk_tarefas_tipo FOREIGN KEY (id_tipo_tarefa) REFERENCES tbl_tipo_tarefa(id_tipo_tarefa),
CONSTRAINT fk_tarefas_status FOREIGN KEY (id_status_tarefa) REFERENCES tbl_status_tarefa(id_status_tarefa)
);

-- Tabela de feedbacks (1:1 com tarefa)
CREATE TABLE tbl_feedbacks (
id_feedback INT IDENTITY(1,1) PRIMARY KEY,
id_tarefa INT UNIQUE NOT NULL,
conteudo NVARCHAR(MAX) NOT NULL,
data_gerado DATETIMEOFFSET DEFAULT SYSDATETIMEOFFSET(),
CONSTRAINT fk_feedback_tarefa FOREIGN KEY (id_tarefa) REFERENCES tbl_tarefas(id_tarefa) ON DELETE CASCADE
);
