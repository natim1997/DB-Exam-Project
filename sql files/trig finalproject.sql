create or replace function set_creation_date()
   returns TRIGGER 
   language 'plpgsql'
  as
$set_creation_date$
	
begin
	update exam
	set creationDate = CURRENT_DATE
	where eid = NEW.eid;
	return NEW;
end;
$set_creation_date$;

create trigger creationdate_insert_trig
after insert 
on exam
for each row 
execute procedure set_creation_date();

create or replace function limit_answers_on_open_ended()
   returns TRIGGER 
   language 'plpgsql'
  as
$limit_questions_on_open_ended$
	
begin
	if EXISTS(SELECT qid FROM question natural join type WHERE qid = NEW.qid AND type = 'Open Ended') then
		if(select count(*) from question_answer where qid = new.qid) > 0 then 
			raise exception 'Can not add another answer to an open ended question!';
		end if;
	end if;
	return NEW;
end;
$limit_questions_on_open_ended$;

create or replace function check_for_matching_answer_and_question()
   returns TRIGGER 
   language 'plpgsql'
  as
$check_for_matching_answer_and_question$
declare
	question_type text;
	answer_type text;
begin
	select distinct type into question_type from (question natural join question_answer) natural join type where qid = new.qid; 
	select distinct type into answer_type from (answer natural join question_answer) natural join type  where aid = new.aid;
	if answer_type != question_type then
		raise exception 'Question and Answer should have the same type!';
	end if;
	return NEW;
end;
$check_for_matching_answer_and_question$;

create or replace trigger check_for_matching_types
before insert 
on question_answer
for each row 
execute procedure check_for_matching_answer_and_question();

create or replace function update_question_num_and_correct()
   returns TRIGGER 
   language 'plpgsql'
  as
$update_question_num_and_correct$
declare
num_of_correct_answers int;
num_of_answers int;
	
begin
	select count(*) into num_of_answers from question_answer where qid = new.qid;
	select count(*) into num_of_correct_answers from question_answer where qid = new.qid and iscorrect = true;
	update question
	set numAnswers = num_of_answers,
		numCorrectAnswers = num_of_correct_answers
	where qid = new.qid;
	return NEW;
end;
$update_question_num_and_correct$;

create or replace trigger update_question_after_insert_trig
after insert or delete or update
on question_answer
for each row 
execute procedure update_question_num_and_correct();
