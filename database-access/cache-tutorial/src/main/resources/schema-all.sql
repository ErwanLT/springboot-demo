--
-- Copyright (C) 2025 LE TUTOUR Erwan
--
-- This file is part of cache-tutorial.
--
-- cache-tutorial is free software: you can redistribute it and/or modify
-- it under the terms of the GNU General Public License as published by
-- the Free Software Foundation, either version 3 of the License, or
-- (at your option) any later version.
--
-- cache-tutorial is distributed in the hope that it will be useful,
-- but WITHOUT ANY WARRANTY; without even the implied warranty of
-- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
-- GNU General Public License for more details.
--
-- You should have received a copy of the GNU General Public License
-- along with cache-tutorial.  If not, see <http://www.gnu.org/licenses/>.
--

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