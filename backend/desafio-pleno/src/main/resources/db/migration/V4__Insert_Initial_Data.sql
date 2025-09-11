
-- Inserindo usuários
INSERT INTO usuario (nome, email, bairro) VALUES
('João Silva', 'joao.silva@email.com', 'Centro'),
('Maria Souza', 'maria.souza@email.com', 'Jardim América');

-- Inserindo funcionários
INSERT INTO funcionario (nome, email) VALUES
('Carlos Lima', 'carlos.lima@email.com'),
('Ana Paula', 'ana.paula@email.com');

-- Inserindo solicitações
INSERT INTO solicitacao (titulo, descricao, bairro, usuario_id, funcionario_id)
VALUES
('Solicitação de Limpeza', 'Solicito limpeza da praça central.', 'Centro', 1, 1),
('Reparo de Iluminação', 'Lâmpada queimada na rua 10.', 'Jardim América', 2, 2);