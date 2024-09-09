Create Table Type(
	TypeID int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	Type text
);

Create Table Subject(
	SID int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	Subject text
);

create table Question(
	QID int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	Text TEXT,
	SID int,
	TypeID int,
	numAnswers int DEFAULT 0,
	numCorrectAnswers int DEFAULT 0,
	CONSTRAINT fk_sid FOREIGN KEY (SID) REFERENCES Subject (SID),
	CONSTRAINT fk_typeId FOREIGN KEY (TypeID) REFERENCES Type (TypeID)
);

Create Table Difficulty(
	QID int,
	Difficulty text,
	PRIMARY key (QID, Difficulty),
	CONSTRAINT fk_qid FOREIGN KEY (QID) REFERENCES Question (QID)
);

Create Table Answer(
	AID int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	TypeID int,
	text text,
	CONSTRAINT fk_typeId FOREIGN KEY (TypeID) REFERENCES Type (TypeID)
);

Create Table Teacher(
	TID int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	firstName text,
	lastName text
);

Create Table Teacher_Subject(
	SID int,
	TID int,
	PRIMARY key (sid, tid),
	CONSTRAINT fk_sid FOREIGN KEY (SID) REFERENCES Subject (SID),
	CONSTRAINT fk_tid FOREIGN KEY (TID) REFERENCES Teacher (TID)
);

Create Table Exam(
	EID int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	SID int,
	TID int,
	CreationDate date,
	CONSTRAINT fk_sid FOREIGN KEY (SID) REFERENCES Subject (SID),
	CONSTRAINT fk_tid FOREIGN KEY (TID) REFERENCES Teacher (TID)
);

Create Table Question_Exam(
	QID int,
	EID int,
	PRIMARY key (qid, eid),
	CONSTRAINT fk_qid FOREIGN KEY (QID) REFERENCES Question (QID),
	CONSTRAINT fk_eid FOREIGN KEY (EID) REFERENCES Exam (EID)
);

Create Table Question_Answer(
	QID int,
	AID int,
	isCorrect boolean,
	PRIMARY key(QID,AID),
	CONSTRAINT fk_qid FOREIGN KEY (QID) REFERENCES Question (QID),
	CONSTRAINT fk_aid FOREIGN KEY (AID) REFERENCES Answer (AID)
);

--fix teacher
alter table teacher
add constraint teacher_unique unique(tid, firstname, lastname);




