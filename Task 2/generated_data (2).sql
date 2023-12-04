INSERT INTO instrument (type, brand, renting_fee)
VALUES 
('Guitar', 'Yamaha', 100),
('Piano', 'Steinway', 200),
('Violin', 'Stradivarius', 150),
('Drum', 'Pearl', 120),
('Flute', 'Yamaha', 80),
('Saxophone', 'Selmer', 110),
('Cello', 'Dvorak', 170),
('Trumpet', 'Bach', 90),
('Harp', 'Lyon & Healy', 210),
('Clarinet', 'Buffet Crampon', 100);

INSERT INTO lesson_instrument (value)
VALUES 
('Guitar'),
('Piano'),
('Violin'),
('Drum'),
('Flute'),
('Saxophone'),
('Cello'),
('Trumpet'),
('Harp'),
('Clarinet');

INSERT INTO skill_level (value)
VALUES 
('Beginner'),
('Intermediate'),
('Advanced');

INSERT INTO type_of_lesson (value)
VALUES 
('Individual'),
('Group'),
('Ensemble');

INSERT INTO person (person_number, first_name, last_name, address, city, zip, email, phone_number)
VALUES 
('198001231234', 'Erik', 'Svensson', 'Kungsgatan 1', 'Stockholm', '111 43', 'erik.svensson@email.se', '0701234567'),
('199205105678', 'Anna', 'Johansson', 'Storgatan 5', 'Goteborg', '411 38', 'anna.johansson@email.se', '0702345678'),
('198502161357', 'Karl', 'Larsson', 'Drottninggatan 10', 'Malmo', '211 49', 'karl.larsson@email.se', '0703456789'),
('197808042468', 'Sara', 'Karlsson', 'Vastra Tradgardsgatan 15', 'Uppsala', '753 09', 'sara.karlsson@email.se', '0704567890'),
('199011221245', 'Oskar', 'Andersso', 'Norr Malarstrand 20', 'Vasteras', '722 11', 'oskar.andersson@email.se', '0705678901'),
('198307302356', 'Lina', 'Oberg', 'Ostra Agatan 25', 'Orebro', '701 42', 'lina.oberg@email.se', '0706789012'),
('197503183478', 'Nils', 'Gustafss', 'Halsingegatan 30', 'Linkoping', '581 83', 'nils.gustafsson@email.se', '0707890123'),
('199509054590', 'Emma', 'Sjoberg', 'Fiskaregatan 35', 'Helsingborg', '252 67', 'emma.sjoberg@email.se', '0708901234'),
('198712135601', 'Lukas', 'Berg', 'Ringvagen 40', 'Jonkoping', '554 66', 'lukas.berg@email.se', '0709012345'),
('198204276782', 'Ida', 'Lind', 'Sbackvagen 45', 'Norrkoping', '602 28', 'ida.lind@email.se', '0700123456'),
('199306197893', 'Filip', 'Holm', 'Bergsgatan 50', 'Lund', '222 22', 'filip.holm@email.se', '0701234568'),
('197610088904', 'Clara', 'Strom', 'Avenyn 55', 'Boras', '503 42', 'clara.strom@email.se', '0702345679'),
('198403259015', 'Jakob', 'Ek', 'Sodra Vagen 60', 'Eskilstuna', '632 20', 'jakob.ek@email.se', '0703456780'),
('198807140126', 'Frida', 'Nystrom', 'Klostergatan 65', 'Sodertalje', '151 72', 'frida.nystrom@email.se', '0704567891'),
('199101021237', 'Magnus', 'Lundqvis', 'Bruksgatan 70', 'Vaxjo', '351 12', 'magnus.lundqvist@email.se', '0705678902'),
('197911212348', 'Sofia', 'Fredriks', 'Esplanaden 75', 'Karlstad', '651 09', 'sofia.fredriksson@email.se', '0706789013'),
('198605313459', 'Henrik', 'Arvidss', 'Skolgatan 80', 'Kristianstad', '291 53', 'henrik.arvidsson@email.se', '0707890124'),
('199409184560', 'Lisa', 'Bjork', 'Kanalgatan 85', 'Kalmar', '392 32', 'lisa.bjork@email.se', '0708901235'),
('197702075671', 'Anders', 'Viklund', 'Strandgatan 90', 'Falun', '791 60', 'anders.viklund@email.se', '0709012346'),
('199612266782', 'Elin', 'Astrom', 'Stationsgatan 95', 'Skelleftea', '931 42', 'elin.astrom@email.se', '0700123457');

INSERT INTO student (student_person_id, contact_person_id)
VALUES 
(1, 9),
(2, 10),
(3, 11),
(4, 12),
(5, 13),
(6, 14),
(7, 15),
(8, 16);

INSERT INTO instructor (instructor_id, instructor_person_id)
VALUES 
(1, 17),
(2, 18),
(3, 19),
(4, 20);

INSERT INTO instructor_instrument (instructor_id, instrument_id)
VALUES 
(17, 1), (17, 2), (17, 3),      
(18, 4), (18, 5),               
(19, 6), (19, 7), (19, 8),       
(20, 9), (20, 10), (20, 11);    

INSERT INTO music_lesson (instructor_id, instrument_id, skill_level_id, type_of_lesson_id, min_students, max_students, genre, time)
VALUES 
(17, 1, 1, 1, 5, 10, 'Classical', '2023-01-10 10:00:00'),
(17, 2, 2, 2, 6, 10, 'Jazz', '2023-01-12 11:00:00'),
(18, 4, 1, 3, 5, 9, 'Blues', '2023-01-15 14:00:00'),
(18, 5, 3, 1, 7, 10, 'Rock', '2023-01-17 09:00:00'),
(19, 6, 2, 1, 5, 8, 'Pop', '2023-01-20 13:00:00'),
(19, 7, 1, 2, 6, 10, 'Folk', '2023-01-22 15:00:00'),
(19, 8, 3, 3, 4, 8, 'Electronic', '2023-01-24 17:00:00'),
(20, 9, 2, 1, 5, 7, 'Country', '2023-01-27 10:00:00'),
(20, 10, 1, 2, 6, 9, 'Reggae', '2023-01-29 12:00:00'),
(20, 11, 3, 3, 5, 10, 'Hip-hop', '2023-02-01 14:00:00'),
(17, 3, 2, 1, 7, 10, 'Jazz', '2023-02-03 16:00:00'),
(18, 4, 1, 2, 4, 8, 'Classical', '2023-02-05 18:00:00'),
(19, 6, 3, 3, 5, 7, 'Blues', '2023-02-08 09:00:00'),
(20, 9, 2, 1, 6, 9, 'Rock', '2023-02-10 11:00:00'),
(17, 1, 1, 2, 5, 10, 'Pop', '2023-02-12 13:00:00'),
(18, 5, 3, 3, 4, 8, 'Folk', '2023-02-14 15:00:00'),
(19, 7, 2, 1, 7, 10, 'Electronic', '2023-02-16 17:00:00'),
(20, 10, 1, 2, 5, 7, 'Country', '2023-02-18 10:00:00'),
(17, 2, 3, 3, 6, 9, 'Reggae', '2023-02-20 12:00:00'),
(18, 4, 2, 1, 5, 10, 'Hip-hop', '2023-02-22 14:00:00'),
(17, 1, 1, 1, 1, 1, NULL, '2023-03-05 10:00:00'),
(17, 2, 2, 1, 1, 1, NULL, '2023-03-06 10:00:00'),
(18, 4, 3, 1, 1, 1, NULL, '2023-03-07 10:00:00'),
(18, 5, 1, 1, 1, 1, NULL, '2023-03-08 10:00:00'),
(19, 6, 2, 1, 1, 1, NULL, '2023-03-09 10:00:00'),
(19, 7, 3, 1, 1, 1, NULL, '2023-03-10 10:00:00'),
(20, 9, 1, 1, 1, 1, NULL, '2023-03-11 10:00:00'),
(20, 10, 2, 1, 1, 1, NULL, '2023-03-12 10:00:00'),
(17, 3, 3, 1, 1, 1, NULL, '2023-03-13 10:00:00'),
(18, 4, 1, 1, 1, 1, NULL, '2023-03-14 10:00:00');

INSERT INTO rental (instrument_id, student_id, start_date, end_date)
VALUES 
(1, 1, '2023-01-01', '2023-06-01'), 
(2, 1, '2023-07-01', '2023-12-31'), 
(3, 2, '2023-02-01', '2023-08-01'), 
(4, 3, '2023-03-01', '2023-09-01'), 
(5, 4, '2023-04-01', '2023-10-01'), 
(6, 5, '2023-05-01', '2023-11-01'), 
(7, 5, '2023-06-01', '2023-12-01'); 


INSERT INTO sibling (student_id, person_id)
VALUES 
(1, 2),  
(2, 1),  
(3, 4),  
(4, 3);  

INSERT INTO lesson_participant (student_id, lesson_id)
VALUES 
(1, 21),
(2, 22),
(3, 23),
(4, 24),
(5, 25),
(6, 26),
(7, 27),
(8, 28),
(1, 29),
(2, 30),
(3, 1), (4, 1), 
(5, 2), (6, 2), 
(7, 3),         
(8, 4),         
(1, 5), (2, 5), 
(3, 6), (4, 6), 
(5, 7), (6, 7), 
(7, 8), (8, 8), 
(1, 9), (2, 9), 
(3, 10),        
(4, 11), (5, 11),
(6, 12),        
(7, 13), (8, 13), 
(1, 14), (2, 14), 
(3, 15), (4, 15), 
(5, 16),        
(6, 17), (7, 17), 
(8, 18),        
(1, 19), (2, 19), 
(3, 20), (4, 20); 