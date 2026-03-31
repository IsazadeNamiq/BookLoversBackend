-- sample books (do not insert users/roles here — DataInitializer handles those)
INSERT INTO books (title, author, description, isbn, pages, price, published_date, cover_image_path)
VALUES ('Clean Code', 'Robert C. Martin', 'A Handbook of Agile Software Craftsmanship', '9780132350884', 464, 39.99, '2008-08-01', '/covers/clean_code.jpg');

INSERT INTO books (title, author, description, isbn, pages, price, published_date, cover_image_path)
VALUES ('Effective Java', 'Joshua Bloch', 'Best practices for the Java platform', '9780134685991', 416, 45.00, '2018-01-06', '/covers/effective_java.jpg');

INSERT INTO books (title, author, description, isbn, pages, price, published_date, cover_image_path)
VALUES ('Domain-Driven Design', 'Eric Evans', 'Tackling Complexity in the Heart of Software', '9780321125217', 560, 49.99, '2003-08-30', '/covers/ddd.jpg');