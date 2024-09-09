INSERT INTO question_answer (qid, aid, iscorrect)
VALUES
    (1, 1, TRUE),  -- Correct answer
    (1, 2, FALSE), -- Incorrect answer
    (1, 3, FALSE), -- Incorrect answer
	(1, 4, FALSE);
INSERT INTO question_answer (qid, aid, iscorrect)
VALUES
    (2, 5, FALSE),  -- Incorrect answer
    (2, 6, FALSE), -- Incorrect answer
    (2, 7, FALSE), -- Incorrect answer
	(2, 8, TRUE); -- Correct answer
INSERT INTO question_answer (qid, aid)
VALUES
    (3,9);
INSERT INTO question_answer (qid, aid)
VALUES
    (4,10);   
INSERT INTO question_answer (qid, aid)
VALUES
    (5,11);
INSERT INTO question_answer (qid, aid)
VALUES
    (6,12);
INSERT INTO question_answer (qid, aid)
VALUES
    (7,13);
INSERT INTO question_answer (qid, aid)
VALUES
    (8,14);
INSERT INTO question_answer (qid, aid,iscorrect)
VALUES
    (9, 15, FALSE),  
    (9, 16, TRUE),   
    (9, 17, FALSE), 
	(9, 18, FALSE);
INSERT INTO question_answer (qid, aid,iscorrect)
VALUES
    (10, 19, FALSE),  
    (10, 20, FALSE),   
    (10, 21, TRUE), 
	(10, 22, FALSE);
INSERT INTO question_answer (qid, aid,iscorrect)
VALUES
    (11, 23, FALSE),  
    (11, 24, true),   
    (11, 25, FALSE), 
	(11, 26, FALSE);
INSERT INTO question_answer (qid, aid)
VALUES
    (12, 27);
INSERT INTO question_answer (qid, aid)
VALUES
    (13, 28);
INSERT INTO question_answer (qid, aid,iscorrect)
VALUES
    (14, 29, FALSE),  
    (14, 30, FALSE),   
    (14, 31, FALSE), 
	(14, 32, TRUE);
INSERT INTO question_answer (qid, aid)
VALUES
    (15, 41);
INSERT INTO question_answer (qid, aid)
VALUES
    (16, 42);
INSERT INTO question_answer (qid, aid)
VALUES
    (17, 43);
INSERT INTO question_answer (qid, aid)
VALUES
    (18, 44);
INSERT INTO question_answer (qid, aid,iscorrect)
VALUES
    (19, 45, TRUE),  
    (19, 46, FALSE),   
    (19, 47, FALSE), 
	(19, 48, FALSE),
	(19, 49, FALSE);
INSERT INTO question_answer (qid, aid,iscorrect)
VALUES
    (20, 33, TRUE),  
    (20, 50, FALSE),   
    (20, 51, FALSE), 
	(20, 52, FALSE);
INSERT INTO question_answer (qid, aid,iscorrect)
VALUES
    (21, 34, TRUE),  
    (21, 53, FALSE),   
    (21, 54, FALSE), 
	(21, 55, FALSE);
INSERT INTO question_answer (qid, aid,iscorrect)
VALUES
    (22, 35, TRUE),  
    (22, 56, FALSE),   
    (22, 57, FALSE), 
	(22, 58, FALSE);
INSERT INTO question_answer (qid, aid,iscorrect)
VALUES
    (23, 36, TRUE), 
    (23, 59, FALSE),   
    (23, 60, FALSE), 
	(23, 61, FALSE);
INSERT INTO question_answer (qid, aid,iscorrect)
VALUES
    (24, 37, TRUE),  
    (24, 62, FALSE),   
    (24, 63, FALSE), 
	(24, 64, FALSE);
INSERT INTO question_answer (qid, aid)
VALUES
    (25, 38);
INSERT INTO question_answer (qid, aid)
VALUES
    (26, 39);
INSERT INTO question_answer (qid, aid)
VALUES
	(27, 40);

--to check
--select * from question_answer natural join answer;

