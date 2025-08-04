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

-- author books
INSERT IGNORE INTO author_books (author_entity_id, books_id) VALUES (1, 1);
INSERT IGNORE INTO author_books (author_entity_id, books_id) VALUES (1, 2);
INSERT IGNORE INTO author_books (author_entity_id, books_id) VALUES (1, 3);
INSERT IGNORE INTO author_books (author_entity_id, books_id) VALUES (1, 4);
INSERT IGNORE INTO author_books (author_entity_id, books_id) VALUES (2, 5);
INSERT IGNORE INTO author_books (author_entity_id, books_id) VALUES (2, 6);

-- genres
INSERT IGNORE INTO genres (id, genre) VALUES (1, 'FANTASY');
INSERT IGNORE INTO genres (id, genre) VALUES (1, 'FICTION');
INSERT IGNORE INTO genres (id, genre) VALUES (2, 'FANTASY');
INSERT IGNORE INTO genres (id, genre) VALUES (2, 'FICTION');
INSERT IGNORE INTO genres (id, genre) VALUES (3, 'FANTASY');
INSERT IGNORE INTO genres (id, genre) VALUES (3, 'FICTION');
INSERT IGNORE INTO genres (id, genre) VALUES (4, 'FANTASY');
INSERT IGNORE INTO genres (id, genre) VALUES (4, 'FICTION');
INSERT IGNORE INTO genres (id, genre) VALUES (5, 'FICTION');
INSERT IGNORE INTO genres (id, genre) VALUES (6, 'FICTION');

-- users
INSERT IGNORE INTO user (id, username) VALUES (1, 'user1');
INSERT IGNORE INTO user (id, username) VALUES (2, 'user2');

-- default collections
INSERT IGNORE INTO collection (id, description, is_default_collection, title, user_id) VALUES (1, null, 1, 'Favorites', 1);
INSERT IGNORE INTO collection (id, description, is_default_collection, title, user_id) VALUES (2, null, 1, 'Currently Reading', 1);
INSERT IGNORE INTO collection (id, description, is_default_collection, title, user_id) VALUES (3, null, 1, 'Finished', 1);
INSERT IGNORE INTO collection (id, description, is_default_collection, title, user_id) VALUES (4, null, 1, 'Read List', 1);

INSERT IGNORE INTO collection (id, description, is_default_collection, title, user_id) VALUES (5, null, 1, 'Favorites', 2);
INSERT IGNORE INTO collection (id, description, is_default_collection, title, user_id) VALUES (6, null, 1, 'Currently Reading', 2);
INSERT IGNORE INTO collection (id, description, is_default_collection, title, user_id) VALUES (7, null, 1, 'Finished', 2);
INSERT IGNORE INTO collection (id, description, is_default_collection, title, user_id) VALUES (8, null, 1, 'Read List', 2);

-- custom collections
INSERT IGNORE INTO collection (id, description, is_default_collection, title, user_id) VALUES (9, null, 0, 'Read in 2025', 1);
INSERT IGNORE INTO collection (id, description, is_default_collection, title, user_id) VALUES (10, 'Books I got from a library', 0, 'Library books', 1);
INSERT IGNORE INTO collection (id, description, is_default_collection, title, user_id) VALUES (11, null, 0, 'Didn\'t finish', 2);

-- books part of collections, I am not making a statement with these
INSERT IGNORE INTO collection_books (collection_entity_id, books_id) VALUES (1, 1); -- user1 favorites
INSERT IGNORE INTO collection_books (collection_entity_id, books_id) VALUES (1, 2);
INSERT IGNORE INTO collection_books (collection_entity_id, books_id) VALUES (2, 3); -- user1 currently reading
INSERT IGNORE INTO collection_books (collection_entity_id, books_id) VALUES (3, 1); -- user1 finished
INSERT IGNORE INTO collection_books (collection_entity_id, books_id) VALUES (3, 2);
INSERT IGNORE INTO collection_books (collection_entity_id, books_id) VALUES (4, 4); -- user1 read list
INSERT IGNORE INTO collection_books (collection_entity_id, books_id) VALUES (4, 5);
INSERT IGNORE INTO collection_books (collection_entity_id, books_id) VALUES (9, 1); -- user1 read in 2025
INSERT IGNORE INTO collection_books (collection_entity_id, books_id) VALUES (9, 2);
INSERT IGNORE INTO collection_books (collection_entity_id, books_id) VALUES (10, 4); -- user1 library books

INSERT IGNORE INTO collection_books (collection_entity_id, books_id) VALUES (5, 2); -- user2 favorites
INSERT IGNORE INTO collection_books (collection_entity_id, books_id) VALUES (5, 1); -- user2 favorites
INSERT IGNORE INTO collection_books (collection_entity_id, books_id) VALUES (6, 6); -- user2 currently reading
INSERT IGNORE INTO collection_books (collection_entity_id, books_id) VALUES (7, 2); -- user2 finished
INSERT IGNORE INTO collection_books (collection_entity_id, books_id) VALUES (7, 1); -- user2 finished
INSERT IGNORE INTO collection_books (collection_entity_id, books_id) VALUES (8, 5); -- user2 read list
INSERT IGNORE INTO collection_books (collection_entity_id, books_id) VALUES (11, 3); -- user2 didn't finish

-- entries
INSERT IGNORE INTO entry (id, book_id, user_id, creation_date, description) VALUES (1, 1, 1, STR_TO_DATE('2025-07-01', '%Y-%m-%d'), 'Read first 3 chapters');
INSERT IGNORE INTO entry (id, book_id, user_id, creation_date, description) VALUES (2, 1, 1, STR_TO_DATE('2025-07-02', '%Y-%m-%d'), '5 chapters left');
INSERT IGNORE INTO entry (id, book_id, user_id, creation_date, description) VALUES (3, 1, 1, STR_TO_DATE('2025-07-03', '%Y-%m-%d'), 'Incredible!');
INSERT IGNORE INTO entry (id, book_id, user_id, creation_date, description) VALUES (4, 2, 1, STR_TO_DATE('2025-07-03', '%Y-%m-%d'), '');

INSERT IGNORE INTO entry (id, book_id, user_id, creation_date, description) VALUES (5, 5, 2, STR_TO_DATE('2025-07-06', '%Y-%m-%d'), '');
INSERT IGNORE INTO entry (id, book_id, user_id, creation_date, description) VALUES (6, 5, 2, STR_TO_DATE('2025-07-07', '%Y-%m-%d'), '');

-- series
INSERT IGNORE INTO series (id, description, title, author_id) VALUES (1, '', 'Lord of the Rings', 1);

-- series books
INSERT IGNORE INTO series_books (series_entity_id, books_id) VALUES (1, 1);
INSERT IGNORE INTO series_books (series_entity_id, books_id) VALUES (1, 2);
INSERT IGNORE INTO series_books (series_entity_id, books_id) VALUES (1, 3);