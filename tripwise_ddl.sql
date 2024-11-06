DROP DATABASE IF EXISTS tripwise;
CREATE DATABASE tripwise;
USE tripwise;

-- SET SQL_SAFE_UPDATES = 0; --

CREATE TABLE Cliente (
    id_cliente INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(60) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefone VARCHAR(20),
    endereco VARCHAR(255)
);

CREATE TABLE Servico (
    id_servico INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    preco DECIMAL(10, 2) NOT NULL,
    data_oferta_inicio DATE NOT NULL,
    data_oferta_fim DATE NOT NULL,
    data_escolhida DATE,
    disponibilidade BOOLEAN DEFAULT TRUE,
    localizacao VARCHAR(100),
    duracao TIME,
    capacidade INT,
    categoria ENUM('aventura', 'cultural', 'familia', 'gastronômico'),
    CONSTRAINT fk_servicos_cliente FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente)
);

CREATE TABLE Pagamento (
    id_pagamento INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM('cartão', 'pix', 'boleto', 'paypal') NOT NULL,
    status_pagamento ENUM('confirmado', 'falha', 'pendente') DEFAULT 'pendente'
);

CREATE TABLE Compra (
    id_compra INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_servico INT NOT NULL,
    id_pagamento INT NOT NULL,
    total DECIMAL(10, 2),
    status_compra ENUM('confirmada', 'cancelada', 'pendente') DEFAULT 'pendente',
    CONSTRAINT fk_compra_servico FOREIGN KEY (id_servico) REFERENCES Servico(id_servico),
    CONSTRAINT fk_compra_pagamento FOREIGN KEY (id_pagamento) REFERENCES Pagamento(id_pagamento)
);

CREATE TABLE Movimentacoes (
    id_movimentacao INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_servico INT NOT NULL,
    tipo_movimentacao ENUM('reserva', 'cancelamento', 'atualizacao') NOT NULL,
    data_movimentacao DATETIME NOT NULL,
    notas TEXT,
    CONSTRAINT fk_movimentacoes_servico FOREIGN KEY (id_servico) REFERENCES Servico(id_servico)
);
