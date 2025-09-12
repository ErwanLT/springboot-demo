CREATE TABLE author (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    bio TEXT
);

-- Table pour les articles
CREATE TABLE article (
    id BIGINT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    author_id BIGINT,
    FOREIGN KEY (author_id) REFERENCES author(id)
);


INSERT INTO author (id, name, bio) VALUES
(1, 'J.K. Rowling', 'J.K. Rowling is the author of the much-loved series of seven Harry Potter novels.'),
(2, 'Stephen King', 'Stephen King is the author of more than sixty books, all of them worldwide bestsellers.'),
(3, 'Agatha Christie', 'Agatha Christie is known throughout the world as the Queen of Crime.');

INSERT INTO article (id, title, content, author_id) VALUES
(1, 'Harry Potter', 'Harry Potter is a series of seven fantasy novels written by British author J. K. Rowling.', 1),
(2, 'The Shining', 'The Shining is a horror novel by American author Stephen King.', 2);