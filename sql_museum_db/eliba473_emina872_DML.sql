/* DML, functionality script */

/* 1. Views */
SELECT *
  FROM employee_view
 ORDER BY digsite_id;

SELECT *
  FROM artifact_report
 ORDER BY country;

SELECT *
  FROM slide_catalogue
 ORDER BY digsite_id;


/* 2. Selects */

/* Lista på utlånade slides */
SELECT [description] AS slide,
       [name] AS borrower,
       date_of_loan AS [date of loan]
  FROM slide_loans
       INNER JOIN researchers
       ON slide_loans.researcher_id = researchers.researcher_id
       INNER JOIN slides
       ON slides.slide_no = slide_loans.slide_no
          AND slides.digsite_id = slide_loans.digsite_id
 WHERE date_of_return IS NULL
 ORDER BY date_of_loan;

/* Lista på digsites och antal artefakter funna på varje */
SELECT [name],
       [start_date] AS [start date],
       ISNULL(CAST(end_date AS VARCHAR), 'In progress') AS [end date],
       county,
       country,
       COUNT(artifact_id) AS [artifacts found]
  FROM digsites
       INNER JOIN artifacts
       ON digsites.digsite_id = artifacts.digsite_id
 GROUP BY [name], [start_date], end_date, country, county
 ORDER BY [artifacts found] DESC;

/* Lista på utlånade artefakter och vem som har den för tillfället */
SELECT artifact_description AS artifact,
       shelf_no AS [belongs at shelf:],
       date_of_loan AS [date of loan],
       [name] AS borrower,
       phone_number AS [phone number]
  FROM artifacts
       INNER JOIN artifact_loans
       ON artifacts.artifact_id = artifact_loans.artifact_id
       INNER JOIN researchers
       ON artifact_loans.researcher_id = researchers.researcher_id
 WHERE artifact_loans.date_of_return IS NULL
 ORDER BY date_of_loan;

/* Lista på researchers som för tillfället lånar en bok och hur många lån dem har just nu */
SELECT researchers.researcher_id,
       [name],
       COUNT(book_loans.researcher_id) AS [number of books]
  FROM researchers
       INNER JOIN book_loans
       ON researchers.researcher_id = book_loans.researcher_id
 WHERE date_of_return IS NULL
 GROUP BY researchers.researcher_id, [name];


/* 3. Procedure och trigger demonstration */

/* Artefakter funna i PyramidExploration och RomanArtifactSearch */
EXEC artifacts_by_digsite @digsite = 'PyramidExploration';
EXEC artifacts_by_digsite @digsite = 'RomanArtifactSearch';

/* Tar bort digsite staff och visar att de hamnat i possible_staff */
DELETE
  FROM digsite_staff
 WHERE digsite_id = 8;

SELECT *
  FROM digsite_staff;

SELECT *
  FROM possible_staff;