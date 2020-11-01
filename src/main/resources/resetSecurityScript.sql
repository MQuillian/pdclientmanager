USE `pdcm_sec`;
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE users;
INSERT INTO users VALUES (1, 'user', 'user@default.com', '$2a$10$HP/6Rg9tOZnSeJbCCzQWB.gVH26j3FIotGB.oLT/03zGGfd8nYsP6', true, true, true, true), (2, 'admin', 'admin@default.com', '$2a$10$4tSo2zUWC0dQbXmDTzxYbO65lyWNqFkQf/yHvEm1Bbz0CGajjArxK', true, true, true, true);
TRUNCATE TABLE authorities;
INSERT INTO authorities VALUES (1, 1, 'ROLE_USER'), (2, 2, 'ROLE_ADMIN'), (3, 2, 'ROLE_USER');
SET FOREIGN_KEY_CHECKS = 1;
