DELETE FROM book;

INSERT INTO book (isbn, title, author, description) VALUES ('978-0201633610', 'Design Patterns', 'Erich Gamma', 'Mit Design Patterns lassen sich wiederkehrende Aufgaben in der objektorientierten Softwareentwicklung effektiv lösen.');
INSERT INTO book (isbn, title, author, description) VALUES ('978-3826655487', 'Clean Code', 'Robert C. Martin', 'Das einzige praxisnahe Buch, mit dem Sie lernen, guten Code zu schreiben!');
INSERT INTO book (isbn, title, author, description) VALUES ('978-3836211161', 'Coding for Fun', 'Gottfried Wolmeringer', 'Dieses unterhaltsam geschriebene Buch führt Sie spielerisch durch die spektakuläre Geschichte unserer Blechkollegen.');

delete from bookshelf_user;
insert into bookshelf_user (id, username, password, role) VALUES (1, 'user', '$2a$10$A1u.ErUPeKZEr8mCsqXth.T2AV44HXYLkD3LMrgX/ggj9NfoqDcbu', 'ROLE_USER');
insert into bookshelf_user (id, username, password, role) VALUES (2, 'admin', '$2a$10$HyN7ZAjQOec5AyrowiJmLOjgfOg6wILi/m5/11zlndRLExs54D29m', 'ROLE_ADMIN');
