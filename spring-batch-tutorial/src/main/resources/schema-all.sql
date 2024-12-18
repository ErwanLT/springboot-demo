DROP TABLE users IF EXISTS;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    username VARCHAR(20),
    status VARCHAR(20),
    last_login_date DATE
);

INSERT INTO users (username, status, last_login_date) VALUES
('alice', 'active', '2024-09-15'),  -- Moins de 6 mois
('bob', 'inactive', '2024-03-01'), -- Plus de 6 mois
('charlie', 'active', '2024-08-20'),  -- Moins de 6 mois
('david', 'inactive', '2023-12-15'), -- Plus de 6 mois
('eve', 'active', '2024-02-25'),  -- Plus de 6 mois
('frank', 'inactive', '2024-07-10'),  -- Moins de 6 mois
('grace', 'active', '2023-11-30');  -- Plus de 6 mois