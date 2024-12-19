INSERT INTO workshop (description) VALUES ( 'IoT');
INSERT INTO workshop (description) VALUES ( 'ChatGPT');
INSERT INTO workshop (description) VALUES ( 'Scientific Writing');

INSERT INTO course (description) VALUES ( 'Business Administration');
INSERT INTO course (description) VALUES ( 'Computer Science');
INSERT INTO course (description) VALUES ( 'Law');
INSERT INTO course (description) VALUES ( 'Mathematic');

INSERT INTO ADDRESS(HOUSE_NUMBER, STREET, ZPL) VALUES ('335', 'Ludwingstrasse', '93047');
INSERT INTO student (name, email, gender, address_id, course_id) VALUES ( 'Jonas Smith', 'j.smith@gmail.com', 'MALE', 1, 1);


INSERT INTO ADDRESS(HOUSE_NUMBER, STREET, ZPL) VALUES ('2', 'Markusplatz', '93047');
INSERT INTO student (name, email, gender, address_id, course_id) VALUES ( 'Diana Fischer', 'd.fischer@gmail.com', 'FEMALE', 2, 2);


INSERT INTO REGISTRATION (REGISTERED_AT, STUDENT_ID, WORKSHOP_ID) VALUES ('2023-11-23', 1,1);  

  


