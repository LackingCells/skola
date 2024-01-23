/* DDL, Creation script */

/* 1. Drop tables if they exist in order of foreign keys: */
DROP TABLE IF EXISTS writes, artifact_indexes, slide_indexes, journal_loans, book_loans, artifact_loans, slide_loans, digsite_staff, possible_staff; -- Outer tables
DROP TABLE IF EXISTS researchers, slides, artifacts; -- Middle tables
DROP TABLE IF EXISTS books, journals, digsites, slide_topics, artifact_topics, authors; -- Core tables

/* Drop views if they exist: */
DROP VIEW IF EXISTS employee_view;
DROP VIEW IF EXISTS slide_catalogue;
DROP VIEW IF EXISTS artifact_report;

/* Drop procedure if they exist: */
DROP PROCEDURE IF EXISTS artifacts_by_digsite;

/* Drop trigger if they exist: */
DROP TRIGGER IF EXISTS staff_to_possible;


/* 2. Create tables in order of foreign keys: */
CREATE TABLE digsites (
    digsite_id         INT PRIMARY KEY IDENTITY(1,1),
    [name]             VARCHAR(40) NOT NULL,
    [start_date]       DATE NOT NULL,
    end_date           DATE,
    country            VARCHAR(20) NOT NULL,
    county             VARCHAR(40) NOT NULL,
    CONSTRAINT CHK_Date CHECK (start_date <= end_date)
);

CREATE TABLE digsite_staff (
    employee_id        INT PRIMARY KEY IDENTITY(1,1),
    digsite_id         INT NOT NULL REFERENCES digsites(digsite_id),
    [name]             VARCHAR(40) NOT NULL,
    phone_number       VARCHAR(20) UNIQUE NOT NULL,
    paygrade           VARCHAR(30) NOT NULL
);

CREATE TABLE possible_staff (
    staff_id           INT PRIMARY KEY IDENTITY(1,1),
    [name]             VARCHAR(40) NOT NULL,
    phone_number       VARCHAR(20) UNIQUE NOT NULL,
    paygrade           VARCHAR(30) NOT NULL
);

CREATE TABLE artifacts (
    artifact_id        INT PRIMARY KEY IDENTITY(1,1),
    digsite_id         INT NOT NULL REFERENCES digsites(digsite_id),
    date_found         DATE NOT NULL,
    grid_placement     VARCHAR(20) NOT NULL,
    artifact_description VARCHAR(80),
    found_at_depth     FLOAT NOT NULL,
    shelf_no           VARCHAR(20)
);

CREATE TABLE artifact_topics (
    topic              VARCHAR(20) PRIMARY KEY
);

CREATE TABLE artifact_indexes (
    topic              VARCHAR(20) NOT NULL REFERENCES artifact_topics(topic),
    artifact_id        INT NOT NULL REFERENCES artifacts(artifact_id),
    PRIMARY KEY (topic, artifact_id)
);

CREATE TABLE slides (
    slide_no           INT NOT NULL,
    digsite_id         INT NOT NULL REFERENCES digsites(digsite_id),
    [description]      VARCHAR(100),
    PRIMARY KEY (slide_no, digsite_id)
);

CREATE TABLE slide_topics (
    topic              VARCHAR(20) PRIMARY KEY
);

CREATE TABLE slide_indexes (
    topic              VARCHAR(20) NOT NULL REFERENCES slide_topics(topic),
    slide_no           INT NOT NULL,
    digsite_id         INT NOT NULL,
    FOREIGN KEY (slide_no, digsite_id) REFERENCES slides(slide_no, digsite_id),
    PRIMARY KEY (topic, slide_no, digsite_id)
);

CREATE TABLE researchers (
    researcher_id      INT PRIMARY KEY IDENTITY(1,1),
    digsite_id         INT REFERENCES digsites(digsite_id),
    [name]             VARCHAR(40) NOT NULL,
    phone_number       VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE slide_loans (
    slide_loan_id      INT PRIMARY KEY IDENTITY(1,1),
    researcher_id      INT NOT NULL REFERENCES researchers(researcher_id),
    slide_no           INT NOT NULL,
    digsite_id         INT NOT NULL,
    date_of_loan       DATE NOT NULL,
    date_of_return     DATE,
    FOREIGN KEY (slide_no, digsite_id) REFERENCES slides(slide_no, digsite_id),
    CONSTRAINT CHK_Slide_Loan CHECK (date_of_loan <= date_of_return)
);

CREATE TABLE artifact_loans (
    artifact_loan_id   INT PRIMARY KEY IDENTITY(1,1),
    researcher_id      INT NOT NULL REFERENCES researchers(researcher_id),
    artifact_id        INT NOT NULL REFERENCES artifacts(artifact_id),
    date_of_loan       DATE NOT NULL,
    date_of_return     DATE,
    CONSTRAINT CHK_Artifact_Loan CHECK (date_of_loan <= date_of_return)
);

CREATE TABLE books (
    book_id            INT PRIMARY KEY IDENTITY(1,1),
    edition            INT NOT NULL,
    shelf_no           VARCHAR(20) NOT NULL,
    [name]             VARCHAR(40) NOT NULL,
    publication_date   DATE NOT NULL,
    publisher          VARCHAR(40) NOT NULL
);

CREATE TABLE journals (
    journal_id         INT PRIMARY KEY IDENTITY(1,1),
    shelf_no           VARCHAR(20) NOT NULL,
    [name]             VARCHAR(80) NOT NULL,
    publication_date   DATE NOT NULL,
    publisher          VARCHAR(80) NOT NULL
);

CREATE TABLE book_loans (
    book_loan_id       INT PRIMARY KEY IDENTITY(1,1),
    researcher_id      INT NOT NULL REFERENCES researchers(researcher_id),
    book_id            INT NOT NULL REFERENCES books(book_id),
    date_of_loan       DATE NOT NULL,
    date_of_return     DATE,
    CONSTRAINT CHK_Book_Loan CHECK (date_of_loan <= date_of_return)
);

CREATE TABLE journal_loans (
    journal_loan_id    INT PRIMARY KEY IDENTITY(1,1),
    researcher_id      INT NOT NULL REFERENCES researchers(researcher_id),
    journal_id         INT NOT NULL REFERENCES journals(journal_id),
    date_of_loan       DATE NOT NULL,
    date_of_return     DATE,
    CONSTRAINT CHK_Journal_Loan CHECK (date_of_loan <= date_of_return)
);

CREATE TABLE authors (
    author_id          INT PRIMARY KEY IDENTITY(1,1),
    given_name         VARCHAR(20) NOT NULL,
    surname            VARCHAR(20) NOT NULL
);

CREATE TABLE writes (
    author_id          INT NOT NULL REFERENCES authors(author_id),
    book_id            INT NOT NULL REFERENCES books(book_id),
    PRIMARY KEY (author_id, book_id)
);
  

/* 3. Data insert in order of foreign keys: */

INSERT INTO authors 
VALUES 
    ('Stephen', 'Hawking'), 
    ('Bill', 'Bryson'), 
    ('Richard', 'Dawkins'), 
    ('John', 'Rennie'), 
    ('Steven', 'Weinberg'), 
    ('Charles', 'Darwin'), 
    ('Thomas', 'Kuhn'), 
    ('James', 'Watson'),  
    ('Francis', 'Crick'), 
    ('H.G.', 'Wells');

INSERT INTO artifact_topics 
VALUES 
    ('Medieval'), 
    ('Roman'), 
    ('Gothic'), 
    ('Victorian'), 
    ('Art Nouveau'), 
    ('Modern'), 
    ('Contemporary'), 
    ('Postmodern'), 
    ('Post-Impressionist'), 
    ('Renaissance'), 
    ('Baroque');

INSERT INTO slide_topics 
VALUES 
    ('Picture'), 
    ('Map'), 
    ('Artifact record'), 
    ('Record'), 
    ('Section mapping'), 
    ('Analysis');

INSERT INTO books 
VALUES 
    (1, 'A1', 'The Theory of Everything', '2014-09-09', 'Hachette Book Group'), 
    (2, 'B2', 'A Short History of Nearly Everything', '2003-09-09', 'Bantam Books'), 
    (1, 'C3', 'The Selfish Gene', '1976-06-01', 'Oxford University Press'), 
    (4, 'D4', 'The Making of a Scientist', '2007-01-01', 'Harvard University Press'), 
    (7, 'E5', 'The Language of the Genes', '1999-01-01', 'W. W. Norton & Company'), 
    (2, 'F6', 'The Autobiography of Charles Darwin', '1887-04-01', 'D. Appleton and Company'), 
    (1, 'G7', 'The Origin of Species', '1859-11-24', 'John Murray'), 
    (8, 'H8', 'The Structure of Scientific Revolutions', '1962-02-01', 'University of Chicago Press'), 
    (3, 'I9', 'The Double Helix', '1968-01-01', 'Basic Books'), 
    (21, 'J10', 'The Invisible Man', '1897-05-01', 'Longmans, Green, and Co.');

INSERT INTO writes 
VALUES 
    (1, 1), 
    (2, 2), 
    (3, 3), 
    (4, 4), 
    (5, 5), 
    (6, 6), 
    (6, 7), 
    (7, 8), 
    (8, 9), 
    (9, 9), 
    (10, 10);

INSERT INTO journals 
VALUES 
    ('B1', 'Journal of Molecular Biology', '2000-01-01', 'Springer'), 
    ('B1', 'Nature', '2001-01-01', 'Nature Publishing Group'), 
    ('B3', 'Science', '2002-01-01', 'American Association for the Advancement of Science'), 
    ('F5', 'Proceedings of the National Academy of Sciences', '2003-01-01', 'National Academy of Sciences'), 
    ('F8', 'Cell', '2004-01-01', 'American Society for Cell Biology'), 
    ('G6', 'Journal of Neuroscience', '2005-01-01', 'Society for Neuroscience'), 
    ('H5', 'Journal of Experimental Medicine', '2006-01-01', 'The Rockefeller University Press'), 
    ('K7', 'Journal of Clinical Investigation', '2007-01-01', 'The Rockefeller University Press'), 
    ('B2', 'Journal of Immunology', '2008-01-01', 'The American Association of Immunologists'), 
    ('N4', 'Immunity', '2009-01-01', 'Cell Press');

INSERT INTO digsites  
VALUES 
    ('AncientRuinsExcavation', '2023-01-15', '2023-02-28', 'Greece', 'Athens'), 
    ('VikingSettlementDig', '2023-03-10', '2023-04-20', 'Norway', 'Hordaland'), 
    ('PyramidExploration', '2023-05-05', '2023-06-15', 'Egypt', 'Giza'), 
    ('RomanArtifactSearch', '2023-07-01', '2023-08-15', 'Italy', 'Rome'), 
    ('MayanDiscoveryMission', '2023-09-10', '2023-10-25', 'Mexico', 'Yucatan'), 
    ('LostCityUnearthed', '2023-11-01', '2023-12-15','Cambodia', 'Siem Reap'), 
    ('MedievalCastleExcavation', '2024-01-10', NULL, 'Germany', 'Bavaria'), 
    ('IncanCivilizationDiscovery', '2024-01-11', NULL, 'Peru', 'Cusco');

INSERT INTO digsite_staff  
VALUES 
    (7, 'Kalle Kjällvik', '0705437645', 'Senior'), 
    (8, 'Manne Markusson', '0796543210','Intermediate'), 
    (8, 'Ture Svensson', '0706667777', 'Junior'), 
    (7, 'John Doe', '0762223333', 'Senior'), 
    (8, 'Jane Doe', '0738887777', 'Managerial'), 
    (7, 'Martin Mutumba', '0788889999', 'Intermediate');

INSERT INTO researchers
VALUES 
    (1, 'Emma Johnson', '0734567890'), 
    (2, 'David Smith', '0776543210'), 
    (3, 'Maria Rodriguez', '0756667777'), 
    (4, 'James Anderson', '0712223333'), 
    (5, 'Laura White', '0798887777'), 
    (6, 'Michael Brown', '0778889999'), 
    (2, 'Sarah Taylor', '0754443333'), 
    (7, 'Emily Davis', '0767778888'), 
    (1, 'Christopher Lee', '0789990000'), 
    (8, 'Jennifer Clark', '0710009999');

INSERT INTO artifacts 
VALUES
    (1, '2023-02-01', '3A', 'Ancient Greek Amphora with Red Figures', 2.5, '1B'),
    (2, '2023-04-05', '1B', 'Viking Brooch with Intricate Designs', 4.0, '22C'),
    (3, '2023-06-01', '5C', 'Mayan Jade Mask', 3.2, '12A'),
    (4, '2023-08-01', '2D', 'Roman Mosaic Tile', 5.5, '41A'),
    (1, '2023-02-10', '1A', 'Egyptian Scarab Amulet', 1.8, '52B'),
    (7, '2024-01-15', '14B', 'Medieval Knights Sword', 3.7, '66F'),
    (8, '2024-01-14', '20C', 'Incan Pottery Vessel', 2.0, '72A'),
    (4, '2023-07-15', '12A', 'Greek Marble Statue Fragment', 4.8, '89C'),
    (1, '2023-02-20', '13A', 'Roman Glass Perfume Bottle', 2.2, '19B'),
    (2, '2023-03-20', '15B', 'Norse Rune-Inscribed Stone', 4.5, '10A'),
    (6, '2023-11-15', '16C', 'Byzantine Gold Coin', 2.1, '18F'),
    (3, '2023-06-10', '3C', 'Aztec Feathered Headdress', 3.5, '23F'),
    (4, '2023-08-01', '18C', 'Etruscan Bronze Figurine', 6.2, '32A'),
    (2, '2023-03-11', '4B', 'Celtic Torc Necklace', 2.8, '14C'),
    (5, '2023-09-20', '2A', 'Ming Dynasty Porcelain Vase', 3.0, '15F'),
    (3, '2023-05-05', '6C', 'Olmec Stone Head', 4.2, '16H'),
    (4, '2023-07-15', '7C', 'Phoenician Glass Amphoriskos', 5.8, '17A'),
    (2, '2023-04-15', '8C', 'Viking Rune Stone Tablet', 3.3, '18B'),
    (1, '2023-02-20', '9A', 'Sumerian Cuneiform Tablet', 2.4, '19A'),
    (3, '2023-06-01', '10B', 'Toltec Stone Calendar', 4.7, '20B'),
    (7, '2024-01-11', '10A', 'Gothic Illuminated Manuscript', 6.0, '21C'),
    (1, '2023-02-15', '12D', 'Minoan Bull-Leaping Fresco Fragment', 2.6, '22D'),
    (2, '2023-03-10', '12A', 'Angkor Wat Stone Relief Panel', 3.9, '23E'),
    (3, '2024-05-25', '16A', 'Shang Dynasty Bronze Ding', 2.3, '24F'),
    (4, '2023-07-10', '3E', 'Assyrian Lamassu Sculpture Fragment', 5.0, '25A'),
    (1, '2023-02-01', '6F', 'Hittite Clay Cuneiform Tablet', 1.9, '62A'),
    (2, '2023-03-15', '13E', 'Navajo Turquoise Bracelet', 4.4, '63B'),
    (3, '2023-05-30', '9A', 'Zhou Dynasty Jade Bi Disc', 3.1, '64C'),
    (4, '2023-07-15', '7F', 'Vedic Saraswati Figurine', 5.3, '65D'),
    (1, '2023-01-20', '11F', 'Mycenaean Linear B Tablet', 2.0, '71D'),
    (2, '2023-03-15', '12E', 'Maori Carved Pounamu Pendant', 3.6, '72E'),
    (3, '2023-05-20', '17A', 'Chavin Stone Lanzon', 2.2, '41F'),
    (4, '2023-07-05', '3D', 'Sassanian Silver Plate', 4.6, '33H'),
    (1, '2023-01-20', '15F', 'Göbekli Tepe Carved Pillar', 2.7, '51A'),
    (2, '2023-04-10', '17A', 'Iroquois Wampum Belt', 4.2, '52C'),
    (3, '2023-05-25', '3F', 'Lycian Rock-Cut Tomb Facade', 3.4, '53B'),
    (4, '2023-07-10', '14F', 'Silla Dynasty Golden Crown', 5.7, '54C'),
    (1, '2023-01-15', '12B', 'Teotihuacan Obsidian Mirror', 2.9, '55D'),
    (2, '2023-06-01', '16C', 'Pueblo Painted Pottery', 3.8, '55E'),
    (3, '2023-05-15', '12E', 'Mycenaean Gold Funerary Mask', 2.5, '56F');

INSERT INTO slides 
VALUES
    (1, 1, 'Excavation team uncovering ancient amphora.'),
    (2, 1, 'Close-up of the red figures on the Greek amphora.'),
    (1, 2, 'Viking settlement dig site overview.'),
    (2, 2, 'Brooch with intricate designs found in Zone B1.'),
    (1, 3, 'Mayan Jade Mask discovered in Area C5.'),
    (2, 3, 'Archaeologist examining Mayan artifact.'),
    (1, 4, 'Roman mosaic tile unearthed in Section D2.'),
    (2, 4, 'Overview of the excavation area in Rome.'),
    (1, 5, 'Site exploration underway.'),
    (2, 5, 'Egyptian scarab amulet found in Quadrant A1.'),
    (1, 6, 'Archaeological analysis in action.'),
    (2, 6, 'Section mapping.'),
    (1, 7, 'Archaeological tools used in Sector A2.'),
    (2, 7, 'Artifact analysis in the lab for Mayan site.'),
    (1, 8, 'Greek marble statue fragment discovered.'),
    (2, 8, 'Section D3 overview during excavation.');

INSERT INTO artifact_indexes
VALUES
    ('Medieval', 7),
    ('Roman', 4),
    ('Gothic', 9),
    ('Victorian', 15),
    ('Art Nouveau', 23),
    ('Modern', 33),
    ('Contemporary', 39),
    ('Postmodern', 37),
    ('Post-Impressionist', 15),
    ('Renaissance', 6),
    ('Baroque', 4),
    ('Gothic', 7),
    ('Renaissance', 12),
    ('Modern', 14),
    ('Baroque', 8),
    ('Postmodern', 9),
    ('Art Nouveau', 3),
    ('Post-Impressionist', 11),
    ('Contemporary', 5),
    ('Gothic', 13),
    ('Baroque', 10),
    ('Victorian', 16),
    ('Renaissance', 17),
    ('Modern', 18),
    ('Post-Impressionist', 19),
    ('Contemporary', 20);

INSERT INTO slide_indexes
VALUES
    ('Analysis', 2, 3),
    ('Analysis', 1, 6),
    ('Analysis', 2, 7),
    ('Section mapping', 2, 6),
    ('Section mapping', 1, 5),
    ('Section mapping', 1, 2),
    ('Record', 1, 5),
    ('Record', 1, 6),
    ('Record', 1, 7),
    ('Record', 2, 7),
    ('Artifact record', 2, 1),
    ('Artifact record', 1, 1),
    ('Artifact record', 2, 2),
    ('Artifact record', 1, 3),
    ('Artifact record', 2, 3),
    ('Artifact record', 2, 5),
    ('Artifact record', 2, 7),
    ('Artifact record', 1, 8),
    ('Map', 1, 2),
    ('Map', 2, 4),
    ('Map', 2, 6),
    ('Map', 2, 8),
    ('Picture', 1, 1),
    ('Picture', 2, 1),
    ('Picture', 2, 2),
    ('Picture', 1, 3),
    ('Picture', 2, 3),
    ('Picture', 1, 4),
    ('Picture', 2, 5),
    ('Picture', 1, 8);

INSERT INTO slide_loans
VALUES
    (1, 2, 1, '2023-12-11', NULL),
    (1, 1, 8, '2023-12-10', '2023-12-20'),
    (3, 1, 2, '2023-12-19', NULL),
    (4, 2, 6, '2024-01-10', NULL),
    (4, 2, 4, '2024-01-10', '2024-01-13'),
    (5, 1, 4, '2024-01-01', NULL),
    (2, 2, 3, '2023-12-12', '2024-01-02');

INSERT INTO journal_loans
VALUES
    (1, 10, '2023-12-10', NULL),
    (2, 9, '2024-01-01', '2024-01-10'),
    (3, 8, '2023-05-05', '2023-07-08'),
    (4, 7, '2024-01-05', NULL),
    (4, 6, '2024-01-05', NULL),
    (5, 5, '2023-10-12', '2023-12-12'),
    (6, 2, '2023-12-12', NULL);

INSERT INTO artifact_loans
VALUES
    (1, 1, '2023-03-15', '2023-04-15'),
    (2, 2, '2023-04-01', '2023-05-01'),
    (3, 3, '2023-05-10', NULL),
    (4, 4, '2023-06-15', '2023-07-15'),
    (5, 5, '2023-07-01', '2023-08-01'),
    (6, 6, '2023-08-15', NULL),
    (2, 7, '2023-09-01', '2023-10-01'),
    (7, 8, '2023-10-15', NULL),
    (1, 9, '2023-11-01', NULL),
    (8, 10, '2023-12-15', NULL);

INSERT INTO book_loans
VALUES
    (1, 1, '2023-03-15', '2023-04-15'),
    (2, 2, '2023-04-01', '2023-05-01'),
    (3, 3, '2023-05-10', '2023-06-10'),
    (4, 4, '2023-06-15', '2023-07-15'),
    (5, 5, '2023-07-01', '2023-08-01'),
    (6, 6, '2023-08-15', '2023-09-15'),
    (2, 7, '2023-09-01', '2023-10-01'),
    (7, 8, '2023-10-15', '2023-11-15'),
    (1, 9, '2023-11-01', '2023-12-01'),
    (8, 10, '2023-12-15', NULL);

/*4.
Create views, one for all employees, one for all slides and one for all artifacts, with their respective topics*/
GO
CREATE VIEW employee_view AS
SELECT 
    digsite_id, 
    [name], 
    phone_number
FROM 
    digsite_staff
UNION ALL
SELECT 
    digsite_id, 
    [name], 
    phone_number
FROM 
    researchers;

GO
CREATE VIEW slide_catalogue AS
SELECT 
    slides.digsite_id, 
    slides.[description], 
    STRING_AGG(slide_topics.topic, ', ') AS topics
FROM 
    slides
INNER JOIN 
    slide_indexes ON slides.slide_no = slide_indexes.slide_no 
    AND slides.digsite_id = slide_indexes.digsite_id
INNER JOIN 
    slide_topics ON slide_indexes.topic = slide_topics.topic
GROUP BY 
    slides.digsite_id, 
    slides.[description];

GO
CREATE VIEW artifact_report AS
SELECT 
    date_found AS [date found], 
    grid_placement AS [grid placement], 
    artifact_description AS [description], 
    found_at_depth AS depth, 
    shelf_no AS shelf, 
    STRING_AGG(ISNULL(artifact_topics.topic, 'N/A'), ', ') AS topics, 
    digsites.country
FROM 
    artifacts
LEFT JOIN 
    artifact_indexes ON artifacts.artifact_id = artifact_indexes.artifact_id
LEFT JOIN 
    artifact_topics ON artifact_indexes.topic = artifact_topics.topic
INNER JOIN 
    digsites ON artifacts.digsite_id = digsites.digsite_id
GROUP BY 
    date_found, 
    grid_placement, 
    artifact_description, 
    found_at_depth, 
    shelf_no, 
    digsites.country;


/*5.
Create procedure and trigger for artifacts by digsite and and handling of staff that doesnt belong to a digsite:*/
GO
CREATE PROCEDURE artifacts_by_digsite @digsite VARCHAR(40)
AS
BEGIN
    SELECT 
        artifact_description, 
        country, 
        county, 
        STRING_AGG(ISNULL(artifact_topics.topic, 'N/A'), ', ') AS topics
    FROM 
        digsites
    INNER JOIN 
        artifacts ON digsites.digsite_id = artifacts.digsite_id
    LEFT JOIN 
        artifact_indexes ON artifacts.artifact_id = artifact_indexes.artifact_id
    LEFT JOIN 
        artifact_topics ON artifact_indexes.topic = artifact_topics.topic
    WHERE 
        [name] = @digsite
    GROUP BY 
        artifact_description, 
        country, 
        county
    ORDER BY 
        topics;
END;

/*Trigger:*/
GO
CREATE TRIGGER staff_to_possible
ON digsite_staff
AFTER DELETE
AS
BEGIN
    INSERT INTO possible_staff ([name], phone_number, paygrade)
    SELECT 
        deleted.[name], 
        deleted.phone_number, 
        deleted.paygrade
    FROM 
        deleted;
END;