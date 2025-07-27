-- roles
INSERT IGNORE INTO role (id, name) VALUES (1, 'ADMIN'), (2, 'USER');

-- authors
INSERT IGNORE INTO author (id, age, name) VALUES (1, 81, 'J. R. R. Tolkien');
INSERT IGNORE INTO author (id, age, name) VALUES (2, 58, 'Charles Dickens');

-- books
INSERT IGNORE INTO book (id, author, publish_date, publisher, title) VALUES (1, 'J. R. R. Tolkien', STR_TO_DATE('1954-07-29', '%Y-%m-%d'), 'Allen & Unwin', 'The Fellowship of the Ring');
INSERT IGNORE INTO book (id, author, publish_date, publisher, title) VALUES (2, 'J. R. R. Tolkien', STR_TO_DATE('1954-11-11', '%Y-%m-%d'), 'Allen & Unwin', 'The Two Towers');
INSERT IGNORE INTO book (id, author, publish_date, publisher, title) VALUES (3, 'J. R. R. Tolkien', STR_TO_DATE('1955-10-22', '%Y-%m-%d'), 'Allen & Unwin', 'The Return of the King');
INSERT IGNORE INTO book (id, author, publish_date, publisher, title) VALUES (4, 'J. R. R. Tolkien', STR_TO_DATE('1937-09-21', '%Y-%m-%d'), 'Allen & Unwin', 'The Hobbit');
INSERT IGNORE INTO book (id, author, publish_date, publisher, title) VALUES (5, 'Charles Dickens', STR_TO_DATE('1838-01-01', '%Y-%m-%d'), 'Richard Bentley', 'Oliver Twist'); -- no specific date for publishing so going with 1/1
INSERT IGNORE INTO book (id, author, publish_date, publisher, title) VALUES (6, 'Charles Dickens', STR_TO_DATE('1853-01-01', '%Y-%m-%d'), 'Chapman & Hall', 'A Tale of Two Cities');