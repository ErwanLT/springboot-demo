CREATE TABLE book (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(20) NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL
);

-- Insertion de quelques livres
INSERT INTO book (isbn, title) VALUES ('9782253006329', 'Le Petit Prince');
INSERT INTO book (isbn, title) VALUES ('9782070371518', 'La Peste');
INSERT INTO book (isbn, title) VALUES ('9782070360539', 'Les Misérables');
INSERT INTO book (isbn, title) VALUES ('9782266290861', '1984');
