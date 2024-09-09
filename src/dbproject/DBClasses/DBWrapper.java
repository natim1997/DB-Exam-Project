package dbproject.DBClasses;

import dbproject.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBWrapper implements Wrapper{
    private Subject selectedSubject;
    private Teacher teacher;
    //PostgresSQL connection
    Connection conn = null;
    //Class.forName("org.postgresql.Driver");
    final String dbUrl = "jdbc:postgresql:finalproject";

    public DBWrapper(Subject subject, Teacher teacher, String user, String password) {
        this(user, password);
        this.selectedSubject = subject;
        this.teacher = teacher;
    }

    public DBWrapper(String user, String password) {
        try {
            this.conn = DriverManager.getConnection(dbUrl, user, password);
        }catch(SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
        }catch(Exception e){
            throw new RuntimeException("Failed to connect to the database!");
        }
    }

    @Override
    public void setSelectedSubject(Subject selectedSubject) {
        this.selectedSubject = selectedSubject;
    }

    @Override
    public boolean updateQuestionDifficulty(Question question, Difficulty newDifficulty) {
        try {
            PreparedStatement stmt = conn.prepareStatement("update difficulty set difficulty = ? where qid = ?");
            stmt.setString(1, newDifficulty.name());
            stmt.setInt(2, question.getId());
            stmt.executeUpdate();
        }catch(SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
            throw new RuntimeException(ex.getMessage());
        }
        return true;
    }

    @Override
    public int addAnswer(String answer, QuestionType type) {
        int aid = -1;
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into answer (text, typeid) values (?, ?) returning aid");
            stmt.setString(1, answer);
            stmt.setInt(2, type.getID());
            ResultSet res = stmt.executeQuery();
            if(res.next()) {
                aid = res.getInt("aid");
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Failed to add answer!");
        }
        return aid;
    }

    @Override
    public Question getQuestionBylD(int ID) {
        DBQuestion question = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("select qid,text,difficulty,typeID,numanswers,numcorrectanswers from ((select * from question where qid = ?) as question natural join difficulty) natural join type");
            stmt.setInt(1, ID);
            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                int id = res.getInt("qid");
                String text = res.getString("text");
                String difficulty = res.getString("difficulty");
                QuestionType type = QuestionType.toQuestionType(res.getInt("typeID"));
                int numAnswers = res.getInt("numanswers");
                int numCorrectAnswers = res.getInt("numcorrectanswers");
                question = new DBQuestion(id, text, Difficulty.valueOf(difficulty), type);
                question.setNumAnswers(numAnswers);
                question.setNumCorrectAnswers(numCorrectAnswers);
            }else{
                throw new RuntimeException("Question with an id of " + ID + " was not found!");
            }

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Question with an id of " + ID + " was not found!");
        }
        return question;
    }

    @Override
    public Question getRandomQuestionOfSubject(Subject subject) {
        //select qid,text,difficulty,typeID from ((select * from question where sid = 2) as question natural join difficulty) natural join type order by random() limit 1
        DBQuestion question = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("select qid,text,difficulty,typeID from ((select * from question where sid = ?) as question natural join difficulty) natural join type order by random() limit 1");
            stmt.setInt(1, subject.getID());
            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                int id = res.getInt("qid");
                String text = res.getString("text");
                String difficulty = res.getString("difficulty");
                QuestionType type = QuestionType.toQuestionType(res.getInt("typeID"));
                question = new DBQuestion(id, text, Difficulty.valueOf(difficulty), type);
            }else{
                throw new RuntimeException("No Question of subject " + subject.getSubject() + " was found!");
            }

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("No Question of subject " + subject.getSubject() + " was found!");
        }
        return question;
    }

    @Override
    public String getAnswerBylD(int ID) {
        String answer = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("select text from answer where aid = ?");
            stmt.setInt(1, ID);
            ResultSet res = stmt.executeQuery();
            if(res.next()) {
                answer = res.getString("text");
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Answer with an id of " + ID + " was not found!");
        }
        return answer;
    }

    @Override
    public int getNumQuestions() {
        int numOfQuestions = 0;
        try {
           Statement stm = conn.createStatement();
           ResultSet res = stm.executeQuery("select COUNT(*) AS num_of_questions from question");
           if(res.next()) {
               numOfQuestions = res.getInt("num_of_questions");
           }
        }catch (SQLException e) {
           System.err.println("SQLException: " + e.getMessage());
           System.err.println("SQLState: " + e.getSQLState());
           System.err.println("VendorError: " + e.getErrorCode());
           throw new RuntimeException("Error! no questions were found!");
        }
        return numOfQuestions;
    }

    @Override
    public int getNumAnswers() {
        int numOfAnswers = 0;
        try {
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery("select COUNT(*) AS num_of_answers from answer");
            if(res.next()) {
                numOfAnswers = res.getInt("num_of_answers");
            }
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Error! no answers were found!");
        }
        return numOfAnswers;
    }

    @Override
    public Subject getSelectedSubject() {
        return selectedSubject;
    }

    @Override
    public int addQuestion(Question question) {
        DBQuestion toAdd = (DBQuestion) question;
        int qid = -1;
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into question (text, sid, typeID) values (?, ?, ?) returning qid");
            stmt.setString(1, toAdd.getText());
            stmt.setInt(2, selectedSubject.getID());
            stmt.setInt(3, toAdd.getType().getID());
            ResultSet res = stmt.executeQuery();

            if(res.next()) {
                qid = res.getInt("qid");
            }

            stmt = conn.prepareStatement("insert into difficulty (qid, difficulty) values (?, ?)");
            stmt.setInt(1, getNumQuestions());
            stmt.setString(2, toAdd.getDifficulty().name());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Failed to add question!");
        }
        return qid;
    }

    @Override
    public boolean deleteQuestionBylD(int ID) {
        try {
            PreparedStatement stmt = conn.prepareStatement("delete from difficulty where qid = ?");
            stmt.setInt(1, ID);
            stmt.executeUpdate();

            stmt = conn.prepareStatement("delete from question where qid = ?");
            stmt.setInt(1, ID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Failed to remove question!");
        }
        return true;
    }

    @Override
    public boolean deleteAnswerBylD(int ID) {
        try {
            PreparedStatement stmt = conn.prepareStatement("delete from answer where aid = ?");
            stmt.setInt(1, ID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Failed to add answer!");
        }
        return true;
    }

    @Override
    public List<Question> getAllQuestionsFromSubject(Subject subject) {
        List<Question> questions = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("select qid,text,difficulty,typeID from ((select * from question where sid = ?) as question natural join difficulty) natural join type");
            stmt.setInt(1, subject.getID());
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                int id = res.getInt("qid");
                String text = res.getString("text");
                String difficulty = res.getString("difficulty");
                QuestionType type = QuestionType.toQuestionType(res.getInt("typeID"));
                questions.add(new DBQuestion(id, text, Difficulty.valueOf(difficulty), type));
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("No Question of subject " + subject.getSubject() + " were found!");
        }
        return questions;
    }

    @Override
    public List<Answer> getAllAnswers() {
        List<Answer> answers = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("select aid,text,typeID from answer");
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int id = res.getInt("aid");
                String text = res.getString("text");
                QuestionType type = QuestionType.toQuestionType(res.getInt("typeID"));
                answers.add(new DBAnswer(id, text, type));
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("No Answers were found!");
        }
        return answers;
    }

    @Override
    public boolean addAnswerToQuestion(int qid, int aid, boolean isCorrect) { //todo
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into question_answer (qid, aid, iscorrect) values (?, ? ,?)");
            stmt.setInt(1, qid);
            stmt.setInt(2, aid);
            stmt.setBoolean(3, isCorrect);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Failed to add answer to question!");
        }
        return true;
    }

    @Override
    public boolean deleteAnswerFromQuestion(int qid, int aid) {
        try {
            PreparedStatement stmt = conn.prepareStatement("delete from question_answer where qid = ? and aid = ?");
            stmt.setInt(1, qid);
            stmt.setInt(2, aid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Failed to remove answer from question!");
        }
        return true;
    }

    @Override
    public List<Answer> getAnswersFromQuestion(Question question) {
        DBQuestion q = (DBQuestion) question;
        List<Answer> answers = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("select aid, text, iscorrect from (select * from question_answer where qid = ?) natural join answer");
            stmt.setInt(1, question.getId());
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int id = res.getInt("aid");
                String text = res.getString("text");
                boolean isCorrect = res.getBoolean("iscorrect");
                answers.add(new DBAnswer(id, text, isCorrect, q.getType()));
            }
            if (answers.isEmpty()) {
                throw new IllegalArgumentException("No Answers were found!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return answers;
    }

    @Override
    public void addSubjectToTeacher(int tid, Subject subject) {
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into teacher_subject (tid, sid) values (?, ?)");
            stmt.setInt(1, tid);
            stmt.setInt(2, subject.getID());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new IllegalStateException("Failed to add subject to teacher!");
        }
    }

    @Override
    public int addTeacher(Teacher teacher) {
        int tid = -1;
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into teacher (firstname, lastname) values (?, ?) returning tid");
            stmt.setString(1, teacher.getFirstName());
            stmt.setString(2, teacher.getLastName());
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                tid = res.getInt("tid");
            }

            for(Subject subject : teacher.getSubjects()) {
                addSubjectToTeacher(tid, subject);
            }

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Failed to add answer to question!");
        }
        return tid;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("select tid,firstname,lastname from teacher");
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int tid = res.getInt("tid");
                String firstName = res.getString("firstname");
                String lastName = res.getString("lastname");
                Teacher teacher = new Teacher(tid, firstName, lastName);
                //teacher.addSubjects(getSubjectsFromTeacher(tid));
                teachers.add(teacher);
            }

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("No Teachers were found!");
        }
        return teachers;
    }
  
    public Teacher getTeacherByID(int ID) {
        return null;
    }

    @Override
    public List<Subject> getSubjectsFromTeacher(int tid) {
        List<Subject> subjects = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("select sid from teacher_subject where tid = ? order by sid");
            stmt.setInt(1, tid);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int sid = res.getInt("sid");
                subjects.add(Subject.toSubject(sid));
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("No Subjects were found!");
        }
        return subjects;
    }

    @Override
    public boolean deleteTeacherByID(int ID) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE from teacher where tid = ?");
            stmt.setInt(1, ID);
            stmt.executeUpdate();
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Error! failed to remove a teacher!");
        }
        return true;
    }
  
    public int addExam(String creationDate) {
        int eid = -1;
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into exam (sid, tid) values (?, ?) returning eid");
            stmt.setInt(1, selectedSubject.getID());
            stmt.setInt(2, teacher.getID());
            //stmt.setString(3, creationDate);
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                eid = res.getInt("eid");
            }
        }catch(SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Failed to add exam!");
        }
        return eid;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public boolean addQuestionToExam(int qid, int eid) {
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into question_exam (eid, qid) values (?, ?)");
            stmt.setInt(1, eid);
            stmt.setInt(2, qid);
            stmt.executeUpdate();
        }catch(SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Failed to add question to exam!");
        }
        return true;
    }

    @Override
    public List<Question> getQuestionsFromExam(int eid) {
        List<Question> questions = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("select qid,text,difficulty,typeID from ((select * from question_exam where eid = ?) natural join question as question natural join difficulty) natural join type");
            stmt.setInt(1, eid);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int id = res.getInt("qid");
                String text = res.getString("text");
                String difficulty = res.getString("difficulty");
                QuestionType type = QuestionType.toQuestionType(res.getInt("typeID"));
                questions.add(new DBQuestion(id, text, Difficulty.valueOf(difficulty), type));
            }
            if(questions.isEmpty()){
                throw new IllegalArgumentException("No Questions were added to exam yet!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return questions;
    }

    @Override
    public Exam getExamByID(int eid) {
        return null;
    }

    @Override
    public void close() {
        try {
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
        }
    }

}
