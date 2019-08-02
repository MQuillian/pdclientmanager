USE `walton_public_defender` ;

# Insert investigators
INSERT INTO investigators
	VALUES
    (1, 'Tim Loodle', 0),
    (2, 'Sandra Sanderson', 0),
    (3, 'Debbie Downer', 1);

# Insert attorneys
INSERT INTO attorneys
	VALUES
    (1, 'Matt Quillian', 0, 1),
    (2, 'John Doe', 0, 2),
    (3, 'Jane Smith', 1, 1),
    (4, 'Matt Schneider', 1, 2);

# Insert clients
INSERT INTO clients
	VALUES
    (1, 'Eric Hoefle', 0),
    (2, 'Jason Baddorf', 0),
    (3, 'Jamie Jameson', 1),
    (4, 'Marky Mark', 1),
    (5, 'Phteven McButton', 0),
    (6, 'Atticus Finch', 1),
    (7, 'Erica Erickson', 0),
    (8, 'Fblthp NLN', 0),
    (9, 'Jimmy Fallon', 0),
    (10,'Hamilton Holmes', 1);

# Insert judges
	
INSERT INTO judges
	VALUES
    (1, 'Horace Johnson'),
    (2, 'John Mott');
    
# Insert cases
INSERT INTO cases
	VALUES
    (1, '18J161450', 1, 1, '2018-7-16', null, 1, 1),
    (2, '18J141738', 2, 1, '2018-5-14', null, 2, 2),
    (3, '18J171429', 3, 1, '2018-8-23', null, 1, 1),
    (4, '17J283757', 4, 1, '2017-10-17', null, 2, 2),
    (5, '17J172365', 5, 1, '2017-8-16', null, 2, 2),
    (6, '18J179254', 6, 2, '2018-8-20', '2018-9-17', 1, 1),
    (7, '18J172979', 6, 2, '2018-6-7', '2018-10-20', 1, 1),
    (8, '17J281737', 7, 2, '2018-7-16', '2018-1-8', 2, 2),
    (9, '18J218436', 7, 2, '2018-7-16', '2019-2-5', 2, 1),
    (10, '18J476421', 8, 1, '2018-3-29', null, 1, 2),
    (11, '17J742874', 9, 1, '2017-7-20', null, 2, 2),
    (12, '18J851936', 10, 1, '2018-11-5', null, 1, 1);

# Insert charges
INSERT INTO charges
	VALUES
	(1, 'Simple battery', '16-5-23.1'),
	(2, 'Battery', '16-5-23.1'),
    (3, 'Theft by taking', '16-8-2'),
	(4, 'Driving while license suspended', '40-5-21'),
	(5, 'Possession of controlled substance', '16-13-30');

# Insert counts
INSERT INTO counts
	VALUES
    (1, 1, 1, 1),
    (2, 1, 2, 3),
    (3, 1, 3, 4),
    (4, 2, 3, 5),
	(5, 1, 4, 3),
	(6, 1, 5, 2),
	(7, 1, 6, 4),
	(8, 1, 7, 1),
	(9, 1, 8, 5),
	(10, 1, 9, 2),
	(11, 1, 10, 1),
	(12, 1, 11, 3),
	(13, 1, 12, 5);
