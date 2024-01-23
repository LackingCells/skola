/* Delete script */

/* 1. Deletes: Tables */
DELETE FROM writes;
DELETE FROM authors;
DELETE FROM journal_loans;
DELETE FROM journals;
DELETE FROM book_loans;
DELETE FROM books;
DELETE FROM artifact_indexes;
DELETE FROM artifact_topics;
DELETE FROM artifact_loans;
DELETE FROM artifacts;
DELETE FROM slide_indexes;
DELETE FROM slide_topics;
DELETE FROM slide_loans;
DELETE FROM slides;
DELETE FROM researchers;
DELETE FROM digsite_staff;
DELETE FROM possible_staff;
DELETE FROM digsites;


/* 2. Drops: Drops tables in order of foreign keys */
DROP TABLE IF EXISTS writes, artifact_indexes, slide_indexes, journal_loans, book_loans, artifact_loans, slide_loans, digsite_staff, possible_staff; -- Outer tables
DROP TABLE IF EXISTS researchers, slides, artifacts; -- Middle tables
DROP TABLE IF EXISTS books, journals, digsites, slide_topics, artifact_topics, authors; -- Core tables

/* Drops views */
GO
DROP VIEW IF EXISTS employee_view;
GO
DROP VIEW IF EXISTS slide_catalogue;
GO
DROP VIEW IF EXISTS artifact_report;

/* Drops procedures */
DROP PROCEDURE IF EXISTS artifacts_by_digsite;

/* Drops triggers */
DROP TRIGGER IF EXISTS staff_to_possible;