--
-- Copyright (C) 2025 LE TUTOUR Erwan
--
-- This file is part of i18n-tutorial.
--
-- i18n-tutorial is free software: you can redistribute it and/or modify
-- it under the terms of the GNU General Public License as published by
-- the Free Software Foundation, either version 3 of the License, or
-- (at your option) any later version.
--
-- i18n-tutorial is distributed in the hope that it will be useful,
-- but WITHOUT ANY WARRANTY; without even the implied warranty of
-- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
-- GNU General Public License for more details.
--
-- You should have received a copy of the GNU General Public License
-- along with i18n-tutorial.  If not, see <http://www.gnu.org/licenses/>.
--

DROP TABLE users IF EXISTS;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    username VARCHAR(20),
    status VARCHAR(20),
    last_login_date TIMESTAMP
);

INSERT INTO users (username, status, last_login_date) VALUES
('alice', 'active', '2024-09-15 10:30:00'),
('bob', 'inactive', '2024-03-01 12:00:00'),
('charlie', 'active', '2024-08-20 14:00:00'),
('david', 'inactive', '2023-12-15 08:45:00'),
('eve', 'active', '2024-02-25 20:15:00'),
('frank', 'inactive', '2024-07-10 18:00:00'),
('grace', 'active', '2023-11-30 22:00:00');