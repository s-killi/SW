
INSERT INTO USER (email, password, login, active) VALUES ( 'thomas@gmail.com', '123456', 'thomas', 1);
INSERT INTO USER (email, password, login, active) VALUES ( 'anja@gmail.com', '123456', 'anja', 1);

INSERT INTO AUTHORITY (description) VALUES ( 'ADMIN');
INSERT INTO AUTHORITY (description) VALUES ( 'STUDENT');

INSERT INTO USERAUTHORITY (IDUSER, IDAUTHORITY) VALUES ( 1, 1);
INSERT INTO USERAUTHORITY (IDUSER, IDAUTHORITY) VALUES ( 2, 2);


INSERT INTO MANAGER (name, iduser) values ('thomas', 1);
INSERT INTO STUDENT (email, name, iduser) values ('anja@gmail.com', 'anja', 2);