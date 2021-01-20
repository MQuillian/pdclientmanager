USE `pdcm_sec`;
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE users;
INSERT INTO users VALUES (1, 'Tim Loodle', 'investigator', 'user@default.com', '$2a$10$W1gqalOZZ/MtPR9faFvJdebkXho2G1.dO/6r1bHKck9ukJceLb9ci', true, true, true, true), (2, 'Matt Quillian', 'MQuillian', 'admin@default.com', '$2a$10$Ivk0/PYY57ifhm63Q7jBmuMk5g489JFS5Sm0HwojLU/kAYAG/A55a', true, true, true, true), (3, 'Michelle Wright', 'admin', 'admin@default.com', '$2a$10$d8Z.WyaqSmiXhn5MXGGqM.7S8gkoEoli6OjA4iiCDpTuyRZ29lc2W', true, true, true, true);
TRUNCATE TABLE authorities;
INSERT INTO authorities VALUES (1, 1, 'ROLE_USER'), (2, 1, 'ROLE_INVESTIGATOR'), (3, 2, 'ROLE_ADMIN'), (4, 2, 'ROLE_ATTORNEY'), (5, 3, 'ROLE_ADMIN');
SET FOREIGN_KEY_CHECKS = 1;
