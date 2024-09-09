package dbproject.DBClasses;

import dbproject.*;

import java.util.ArrayList;
import java.util.Random;

public class DBAutomaticExam extends DBExam {

    private final static Random rnd = new Random();

    public DBAutomaticExam(int maxNumQue, DBWrapper db) {
        super(maxNumQue, db);
        this.questionIDs = new ArrayList<>();
    }

    @Override
    public void createExam() {
        super.createExam();
        while (currNumQue < this.maxNumQue) {
            DBQuestion questionSelected = (DBQuestion) db.getRandomQuestionOfSubject(db.getSelectedSubject());
            //check if question is already in the exam
            if (!questionIDs.isEmpty() && questionIDs.contains(questionSelected.getId())) {
                continue;
            }
            questionIDs.add(questionSelected.getId());
            addQuestionToList(questionSelected, eid);
            //System.out.println("The question was successfully added!");
        }
    }
}
