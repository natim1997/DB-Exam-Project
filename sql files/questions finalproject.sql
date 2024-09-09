--Types
--Open Ended
--Single Selection
--Multiple choice

--Subjects
--Math
--History
--Science
--Geography

--Difficuly
--Easy
--Moderate
--Hard

--TRUNCATE TABLE type, question, answer, difficulty,question_exam, question_answer, type, subject, teacher_subject, exam, question_exam RESTART IDENTITY;
insert into Type (type) values ('Open Ended'), ('Single Selection'), ('Multiple choice');
select * from type;

insert into Subject (subject) values ('Math'), ('History'), ('Science'), ('Geography');
select * from subject;

--Geography
--What is the name of the biggest technology company in South Korea? Difficulty.Easy
-- What is the name of the largest desert in the world? Difficulty.Easy
--What is the name of the current president of the United States? Difficulty.Easy

-- What is the capital of France? Paris Difficulty.Easy
-- What is the name of the largest ocean in the world? Pacific Ocean Difficulty.Easy
-- What is the name of the highest mountain in the world? Mount Everest, Nepal Difficulty.Moderate
-- What is the name of the largest country in the world by area? Russia Difficulty.Moderate
-- What is the name of the largest country in the world by population? India Difficulty.Moderate

-- select * from (question natural join subject) natural join type
-- where subject = 'Geography'

insert into Question (text, typeid, sid) values 
('What is the name of the biggest technology company in South Korea?', 3, 4)
,('What is the name of the largest desert in the world?', 3, 4)
,('What is the name of the current president of the United States?', 1, 4);

insert into Question (text, typeid, sid) values 
('what is the capital of France?', 1, 4)
,(' What is the name of the largest ocean in the world?', 1, 4)
,('What is the name of the highest mountain in the world?', 1, 4)
,('What is the name of the largest country in the world by area?', 1, 4)
,('What is the name of the largest country in the world by population?', 1, 4);

--History
insert into question(text,typeid,sid) values ('What year did the Titanic sink in the Atlantic Ocean on 15 April, on its maiden voyage from Southampton?',3,2);
-- diffuclty - M
-- Answer 1 - "1908",false
-- Answer 2 - "1912",true
-- Answer 3 - "1914",false
-- Answer 4 - "1920",false
insert into question(text,typeid,sid) values ('Who was Prime Minister of Great Britain from 1841 to 1846?',3,2);
-- diffuclty - M
-- Answer 1 - "William Gladstone",false
-- Answer 2 - "Benjamin Disraeli",false
-- Answer 3 - "Robert Peel",true
-- Answer 4 - "Lord Palmerston",false
insert into question(text,typeid,sid) values ('Who was the first person in space',3,2);
-- diffuclty - E
-- Answer 1 - "Yuri Gagarin",false
-- Answer 2 - "Neil Armstrong",false
-- Answer 3 - "John Glenn",false
-- Answer 4 - "Alan Shepard",false
insert into question(text,typeid,sid) values ('What is the name of the first human to fly',1,2);
-- diffuclty - E
-- Answer 1 - "Orville Wright, United States" - Easy
insert into question(text,typeid,sid) values ('What is the name of the first man on the moon?',1,2);
-- diffuclty - E
-- Answer 1 - "Neil Armstrong, United States" - Easy
insert into question(text,typeid,sid) values ('Which metal was discovered by Hans Christian Oersted in 1825?',3,2);
-- diffuclty - H
-- Answer 1 - "Copper",false
-- Answer 2 - "Zinc",false
-- Answer 3 - "Nickel",false
-- Answer 4 - "Aluminium",true

--Math
insert into question(text,typeid,sid) values ('What is the equation for the area of a circle?',1,1);
-- diffuclty - M
-- Answer 1 - "A = πr²" - Moderate
insert into question(text,typeid,sid) values ('What is the equation for the volume of a sphere?',1,1);
-- diffuclty - M
-- Answer 1 - "V = 4/3πr³" - Moderate
insert into question(text,typeid,sid) values ('What is the equation for the slope of a line?',1,1);
-- diffuclty - M
-- Answer 1 - "m = (y₂ - y₁)/(x₂ - x₁)" - Moderate
insert into question(text,typeid,sid) values ('What is the equation for the Pythagorean Theorem?',1,1);
-- diffuclty - M
-- Answer 1 - "a² + b² = c²" - Moderate
insert into question(text,typeid,sid) values ('Which of the following are prime numbers?',3,1);
-- diffuclty - E
-- Answer 1 - "2",true
-- Answer 2 - "1",false
-- Answer 3 - "21",false
-- Answer 4 - "4",false
-- Answer 5 - "1",false

insert into question (text, typeid, sid) values 
('What is the name of the largest bone in the human body?',3,3),
('How many breaths does the human body take daily?',3,3),
('What is the chemical symbol of Tungsten?',3,3),
('What do you use to measure rainfall?',3,3),
('Which is the smallest planet in our solar system?',3,3),
('What is the name of the element with the atomic number 1?',1,3),
('What is the name of the force that keeps the planets in orbit around the sun?',1,3),
('What is the name of the process by which plants make food from sunlight?',1,3);

-- diffuclty
insert into difficulty (qid, difficulty) values
(1, 'Easy'),
(2, 'Easy'),
(3, 'Easy'),
(4, 'Easy'),
(5, 'Easy'),
(6, 'Moderate'),
(7, 'Moderate'),
(8, 'Moderate');
insert into difficulty (qid, difficulty) values
(9, 'Moderate'),
(10, 'Moderate'),
(11, 'Easy'),
(12, 'Easy'),
(13, 'Easy'),
(14, 'Hard');
insert into difficulty (qid, difficulty) values
(15, 'Moderate'),
(16, 'Moderate'),
(17, 'Moderate'),
(18, 'Moderate'),
(19, 'Easy');
insert into difficulty (qid, difficulty) values
(20, 'Hard'),
(21, 'Hard'),
(22, 'Easy'),
(23, 'Moderate'),
(24, 'Easy'),
(25, 'Easy'),
(26, 'Moderate'),
(27, 'Moderate');

--Answers
--Geography
insert into answer (typeid, text) values 
(3,'Samsung'),
(3,'LG'),
(3,'Hyundai'),
(3,'SK'),
(3,'Sahara'),
(3,'Gobi'),
(3,'Kalahari'),
(3,'Antarctic'),
(1,'Joe Biden'),
(1,'Paris'),
(1,'Pacific Ocean'),
(1,'Mount Everest, Nepal'),
(1, 'Russia'),
(1, 'India');

--History
insert into answer (typeid, text) values 
(3,'1908'),
(3,'1912'),
(3,'1914'),
(3,'1920'),
(3,'William Gladstone'),
(3,'Benjamin Disraeli'),
(3,'Robert Peel'),
(3,'Lord Palmerston'),
(3,'Yuri Gagarin'),
(3,'Neil Armstrong'),
(3,'John Glenn'),
(3,'Alan Shepard'),
(1, 'Orville Wright'),
(1, 'Neil Armstrong'),
(3, 'Copper'),
(3, 'Zinc'),
(3, 'Nickel'),
(3, 'Aluminium');

--Science
insert into answer (typeid, text) values 
(3,'Femur'),
(3,'About 20,000'),
(3,'W'),
(3,'Pluviometer'),
(3,'Mercury'),
(1,'Hydrogen'),
(1,'Gravity'),
(1,'Photosynthesis');

--Math
insert into answer (typeid, text) values 
(1,'A = πr²'),
(1,'V = 4/3πr³'),
(1,'m = (y₂ - y₁)/(x₂ - x₁)'),
(1,'a² + b² = c²'),
(3,'2');

--fixes
insert into answer (typeid, text) values 
(3, '1'),
(3,'21'),
(3,'4'),
(3,'11'),
(3,'Humerus'),
(3, 'Tibia'),
(3, 'Pelvis'),
(3, 'about 10,000'),
(3, 'about 30,000'),
(3, 'about 50,000'),
(3, 'Tn'),
(3, 'Tu'),
(3, 'Tg'),
(3, 'Barometer'),
(3, 'Hygrometer'),
(3, 'Thermometer'),
(3,'Venus'),
(3,'Mars'),	
(3,'Pluto');

--fixes
update question
set typeid = 2
where typeid = 3;

update answer
set typeid = 2
where typeid = 3;

delete FROM type
where type = 'Multiple choice';

--to check
--select * from ((question natural join subject) natural join type) natural join difficulty;





