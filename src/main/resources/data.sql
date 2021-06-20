USE `walton_public_defender` ;

# Insert investigators (id, name, workingStatus)
INSERT INTO investigators
	VALUES
    (1, 'Tim Loodle', 0),
    (2, 'Sandra Sanderson', 0),
    (3, 'Debbie Downer', 1);

# Insert attorneys (id, name, workingStatus, investigator)
INSERT INTO attorneys
	VALUES
    (1, 'Matt Quillian', 0, 1),
    (2, 'John Doe', 0, 2),
    (3, 'Jane Smith', 1, NULL),
    (4, 'Matt Schneider', 1, NULL);

# Insert clients (id, name, custodyStatus, incarcerationDate, releaseDate)
INSERT INTO clients
	VALUES
    (1, 'Eric Hoefle', 0, '2018-07-16', NULL),
    (2, 'Jason Baddorf', 0, '2018-05-14', NULL),
    (3, 'Jamie Jameson', 1, '2018-08-23', '2018-08-25'),
    (4, 'Marky Mark', 1, '2017-10-17', '2017-11-17'),
    (5, 'Phteven McButton', 0, '2017-08-16', NULL),
    (6, 'Atticus Finch', 1, '2018-06-07', '2018-06-09'),
    (7, 'Erica Erickson', 0, '2018-07-16', NULL),
    (8, 'Fblthp NLN', 0, '2018-03-29', NULL),
    (9, 'Jimmy Fallon', 0, '2017-07-20', NULL),
    (10,'Hamilton Holmes', 1, '2018-11-05', '2018-11-20');

# Insert judges (id, name, workingStatus)
INSERT INTO judges
	VALUES
    (1, 'Horace Johnson', 0),
    (2, 'John Mott', 0);
    
# Insert cases (id, caseNumber, client, caseStatus, dateOpened, dateClosed, judge, attorney)
INSERT INTO cases
	VALUES
    (1, '18J1614', 1, '2018-7-16', NULL, 1, 1),
    (2, '18J1417', 2, '2018-5-14', NULL, 2, 2),
    (3, '18J1714', 3, '2018-8-23', NULL, 1, 1),
    (4, '17J2837', 4, '2017-10-17', NULL, 2, 2),
    (5, '17J1723', 5, '2017-8-16', NULL, 2, 2),
    (6, '18J1792', 6, '2018-8-20', '2018-9-17', 1, 1),
    (7, '18J1729', 6, '2018-6-7', '2018-10-20', 1, 1),
    (8, '17J2817', 7, '2018-7-16', '2018-1-8', 2, 2),
    (9, '18J2184', 7, '2018-7-16', '2019-2-5', 2, 1),
    (10, '18J4761', 8, '2018-3-29', NULL, 1, 2),
    (11, '17J7424', 9, '2017-7-20', NULL, 2, 2),
    (12, '18J8516', 10, '2018-11-5', NULL, 1, 1);

# Insert charges (id, name, statute)
INSERT INTO charges
	VALUES
	(1, 'Simple battery', '16-5-23'),
	(2, 'Battery', '16-5-23.1'),
    (3, 'Theft by taking', '16-8-2'),
	(4, 'Driving while license suspended', '40-5-21'),
	(5, 'Possession of controlled substance', '16-13-30');

# Insert charged_counts (id, countNumber, charge, courtCase)
INSERT INTO charged_counts
	VALUES
    (1, 1, 1),
    (1, 3, 2),
    (1, 4, 3),
    (2, 5, 3),
	(1, 3, 4),
	(1, 2, 5),
	(1, 4, 6),
	(1, 1, 7),
	(1, 5, 8),
	(1, 2, 9),
	(1, 1, 10),
	(1, 3, 11),
	(1, 5, 12);
