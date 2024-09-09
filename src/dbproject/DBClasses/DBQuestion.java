package dbproject.DBClasses;
import dbproject.Difficulty;
import dbproject.Question;
import java.util.List;

public class DBQuestion extends Question {

    private int numAnswers;
    private int numCorrectAnswers;
    private QuestionType type;
    private List<Integer> answerIDs;


    public DBQuestion(int ID, String question, Difficulty difficulty, QuestionType type) {
        super(ID, question, difficulty);
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.toString());
        sb.append("Type: ");
        sb.append(type.getType());
        sb.append("\n");
        return sb.toString();
    }

    public int getNumAnswers() {
        return numAnswers;
    }

    public void setNumAnswers(int numAnswers) {
        this.numAnswers = numAnswers;
    }

    public int getNumCorrectAnswers() {
        return numCorrectAnswers;
    }

    public void setNumCorrectAnswers(int numCorrectAnswers) {
        this.numCorrectAnswers = numCorrectAnswers;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }
}
