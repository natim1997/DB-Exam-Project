package dbproject.DBClasses;

import dbproject.*;

import java.util.List;

public interface Wrapper {
    public int addAnswer(String answer, QuestionType type);
    public String getAnswerBylD(int ID);
    public boolean deleteAnswerBylD(int ID);
    public List<Answer> getAllAnswers();
    public int getNumAnswers();
    public Question getQuestionBylD(int ID);
    public Question getRandomQuestionOfSubject(Subject subject);
    public int getNumQuestions();
    public int addQuestion(Question question);
    public boolean deleteQuestionBylD(int ID);
    public List<Question> getAllQuestionsFromSubject(Subject subject);
    public boolean addAnswerToQuestion(int qid, int aid, boolean isCorrect);
    public boolean deleteAnswerFromQuestion(int qid, int aid);
    public List<Answer> getAnswersFromQuestion(Question question);
    public void addSubjectToTeacher(int tid, Subject subject);
    public int addTeacher(Teacher teacher);
    public List<Teacher> getAllTeachers();
    public Teacher getTeacherByID(int ID);
    public List<Subject> getSubjectsFromTeacher(int tid);
    public boolean deleteTeacherByID(int ID);
    public int addExam(String creationDate);
    public boolean addQuestionToExam(int qid, int eid);
    public List<Question> getQuestionsFromExam(int eid);
    public Exam getExamByID(int eid);
    public void close();
    public Subject getSelectedSubject();
    public void setSelectedSubject(Subject selectedSubject);
    public boolean updateQuestionDifficulty(Question question, Difficulty newDifficulty);

}
