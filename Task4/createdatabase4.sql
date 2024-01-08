CREATE TABLE instrument (
 instrument_id INT GENERATED ALWAYS AS IDENTITY NOT NULL,
 type VARCHAR(50) NOT NULL,
 brand VARCHAR(50) NOT NULL,
 renting_fee INT NOT NULL
);

ALTER TABLE instrument ADD CONSTRAINT PK_instrument PRIMARY KEY (instrument_id);

CREATE TABLE lesson_instrument (
 instrument_id INT GENERATED ALWAYS AS IDENTITY NOT NULL,
 value VARCHAR(50)
);

ALTER TABLE lesson_instrument ADD CONSTRAINT PK_lesson_instrument PRIMARY KEY (instrument_id);


CREATE TABLE person (
 person_id INT GENERATED ALWAYS AS IDENTITY NOT NULL,
 person_number VARCHAR(12) NOT NULL UNIQUE,
 first_name VARCHAR(200),
 last_name VARCHAR(200),
 address VARCHAR(200),
 city VARCHAR(200),
 zip VARCHAR(100),
 email VARCHAR(100),
 phone_number VARCHAR(100)
);

ALTER TABLE person ADD CONSTRAINT PK_person PRIMARY KEY (person_id);


CREATE TABLE skill_level (
 skill_level_id INT GENERATED ALWAYS AS IDENTITY NOT NULL,
 value VARCHAR(50)
);

ALTER TABLE skill_level ADD CONSTRAINT PK_skill_level PRIMARY KEY (skill_level_id);


CREATE TABLE student (
 student_id INT GENERATED ALWAYS AS IDENTITY NOT NULL,
 student_person_id INT NOT NULL,
 contact_person_id INT NOT NULL
);

ALTER TABLE student ADD CONSTRAINT PK_student PRIMARY KEY (student_id);


CREATE TABLE type_of_lesson (
 type_of_lesson_id INT GENERATED ALWAYS AS IDENTITY NOT NULL,
 value VARCHAR(10)
);

ALTER TABLE type_of_lesson ADD CONSTRAINT PK_type_of_lesson PRIMARY KEY (type_of_lesson_id);


CREATE TABLE instructor (
 instructor_id INT GENERATED ALWAYS AS IDENTITY NOT NULL,
 instructor_person_id INT NOT NULL
);

ALTER TABLE instructor ADD CONSTRAINT PK_instructor PRIMARY KEY (instructor_id);


CREATE TABLE instructor_instrument (
 instructor_id INT NOT NULL,
 instrument_id INT NOT NULL,
 PRIMARY KEY (instructor_id, instrument_id)
);


CREATE TABLE music_lesson (
 lesson_id INT GENERATED ALWAYS AS IDENTITY NOT NULL,
 instructor_id INT,
 instrument_id INT,
 skill_level_id INT NOT NULL,
 type_of_lesson_id INT NOT NULL,
 min_students INT,
 max_students INT,
 genre VARCHAR(50),
 time TIMESTAMP(50)
);

ALTER TABLE music_lesson ADD CONSTRAINT PK_music_lesson PRIMARY KEY (lesson_id);




CREATE TABLE pricing_scheme (
 type_of_lesson_id INT NOT NULL,
 skill_level_id INT NOT NULL,
 price INT
);

ALTER TABLE pricing_scheme ADD CONSTRAINT PK_pricing_scheme PRIMARY KEY (type_of_lesson_id,skill_level_id);


CREATE TABLE rental (
 instrument_id INT NOT NULL,
 student_id INT NOT NULL,
 start_date TIMESTAMP(50),
 end_date TIMESTAMP(50)
);

ALTER TABLE rental ADD CONSTRAINT PK_rental PRIMARY KEY (instrument_id,student_id);

ALTER TABLE rental
ADD COLUMN terminated_rental TIMESTAMP;



CREATE TABLE sibling (
  student_id1 INT NOT NULL,
  student_id2 INT NOT NULL,
  PRIMARY KEY (student_id1, student_id2),
  FOREIGN KEY (student_id1) REFERENCES student(student_id),
  FOREIGN KEY (student_id2) REFERENCES student(student_id)
);


CREATE TABLE lesson_participant (
 student_id INT NOT NULL,
 lesson_id INT NOT NULL
);

ALTER TABLE lesson_participant ADD CONSTRAINT PK_lesson_participant PRIMARY KEY (student_id,lesson_id);


ALTER TABLE student ADD CONSTRAINT FK_student_0 FOREIGN KEY (student_person_id) REFERENCES person (person_id);


ALTER TABLE instructor ADD CONSTRAINT FK_instructor_0 FOREIGN KEY (instructor_person_id) REFERENCES person (person_id);


ALTER TABLE instructor_instrument ADD CONSTRAINT FK_instructor_instrument_0 FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id);
ALTER TABLE instructor_instrument ADD CONSTRAINT FK_instructor_instrument_1 FOREIGN KEY (instrument_id) REFERENCES lesson_instrument (instrument_id);


ALTER TABLE music_lesson ADD CONSTRAINT FK_music_lesson_0 FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id);
ALTER TABLE music_lesson ADD CONSTRAINT FK_music_lesson_1 FOREIGN KEY (instrument_id) REFERENCES lesson_instrument (instrument_id);
ALTER TABLE music_lesson ADD CONSTRAINT FK_music_lesson_2 FOREIGN KEY (skill_level_id) REFERENCES skill_level (skill_level_id);
ALTER TABLE music_lesson ADD CONSTRAINT FK_music_lesson_3 FOREIGN KEY (type_of_lesson_id) REFERENCES type_of_lesson (type_of_lesson_id);


ALTER TABLE pricing_scheme ADD CONSTRAINT FK_pricing_scheme_0 FOREIGN KEY (type_of_lesson_id) REFERENCES type_of_lesson (type_of_lesson_id);
ALTER TABLE pricing_scheme ADD CONSTRAINT FK_pricing_scheme_1 FOREIGN KEY (skill_level_id) REFERENCES skill_level (skill_level_id);


ALTER TABLE rental ADD CONSTRAINT FK_rental_0 FOREIGN KEY (instrument_id) REFERENCES instrument (instrument_id);
ALTER TABLE rental ADD CONSTRAINT FK_rental_1 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE lesson_participant ADD CONSTRAINT FK_lesson_participant_0 FOREIGN KEY (student_id) REFERENCES student (student_id);
ALTER TABLE lesson_participant ADD CONSTRAINT FK_lesson_participant_1 FOREIGN KEY (lesson_id) REFERENCES music_lesson (lesson_id);

