INSERT INTO USER (email, password, login, active) VALUES ( 'thomas@gmail.com', '$2a$12$8K4uC9YPl659Qnz6NUqy9e35xsoQ/OIsaVhIWRJP913VpsulQGZNy', 'thomas', 1);
INSERT INTO USER (email, password, login, active) VALUES ( 'anja@gmail.com', '$2a$12$8K4uC9YPl659Qnz6NUqy9e35xsoQ/OIsaVhIWRJP913VpsulQGZNy', 'anja', 1);

INSERT INTO AUTHORITY (description) VALUES ( 'ADMIN');
INSERT INTO AUTHORITY (description) VALUES ( 'STUDENT');

INSERT INTO USERAUTHORITY (IDUSER, IDAUTHORITY) VALUES ( 1, 1);
INSERT INTO USERAUTHORITY (IDUSER, IDAUTHORITY) VALUES ( 2, 2);
 
