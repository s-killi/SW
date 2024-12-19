INSERT INTO user (login, password, email, active) values ('thomas', '{bcrypt}$2a$12$69GBDheB9KxZ4p4Zl9BLueq.C3ONV1VMxvx/cyoIVmzkgRziB9uFa', 'adam@email', 1);
INSERT INTO user (login, password, email, active) values ('anja', '{bcrypt}$2a$12$69GBDheB9KxZ4p4Zl9BLueq.C3ONV1VMxvx/cyoIVmzkgRziB9uFa', 'anja@email', 1);

INSERT INTO role (description) VALUES ( 'ADMIN');
INSERT INTO role (description) VALUES ( 'STUDENT');

INSERT INTO authority (description) VALUES ( 'CREATE_STUDENT');
INSERT INTO authority (description) VALUES ( 'LIST_STUDENT');
INSERT INTO authority (description) VALUES ( 'REGISTRATION');


INSERT INTO userrole(iduser, idrole) VALUES (1,1);
INSERT INTO userrole(iduser, idrole) VALUES (1,2);
INSERT INTO userrole(iduser, idrole) VALUES (2,2);
 

INSERT INTO roleauthority(idrole, idauthority) VALUES (1,1);
INSERT INTO roleauthority(idrole, idauthority) VALUES (1,3);
INSERT INTO roleauthority(idrole, idauthority) VALUES (2,2);


INSERT INTO workshop (description) VALUES ( 'IoT');
INSERT INTO workshop (description) VALUES ( 'ChatGPT');
INSERT INTO workshop (description) VALUES ( 'Scientific Writing');

INSERT INTO course (description) VALUES ( 'Business Administration');
INSERT INTO course (description) VALUES ( 'Computer Science');
INSERT INTO course (description) VALUES ( 'Law');
INSERT INTO course (description) VALUES ( 'Mathematic');



INSERT INTO ADDRESS(HOUSE_NUMBER, STREET, ZPL) VALUES ('1', 'Markusplatz', '93047');
INSERT INTO student (id, name, gender, address_id, course_id) VALUES (2, 'Anja Fischer',  'FEMALE', 1, 2);

