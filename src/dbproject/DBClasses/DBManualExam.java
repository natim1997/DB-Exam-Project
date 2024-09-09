package dbproject.DBClasses;

import dbproject.*;

import java.util.List;
import java.util.Scanner;

public class DBManualExam extends DBExam {
    private final Scanner input;

    public DBManualExam(int maxNumQue, Scanner input, DBWrapper dbWrapper) {
        super(maxNumQue, dbWrapper);
        this.input = input;
    }

    @Override
    public void createExam() {
        super.createExam();
        while (currNumQue < this.maxNumQue) {
            try {
                DBQuestion question = selectQuestionFromDatabase();
                modifyAnswersIfNeeded(question);
                addQuestionToList(question, eid);
            } catch (RuntimeException e) {
                System.err.println("Error! " + e.getMessage());
            }
        }
    }

    private DBQuestion selectQuestionFromDatabase() {
        Main.printAllQuestionsFromSubject(db.getSelectedSubject());
        System.out.println("Select a question ID from the list:");
        int questionId = input.nextInt();
        input.nextLine();
        return (DBQuestion) db.getQuestionBylD(questionId);
    }

    private void modifyAnswersIfNeeded(DBQuestion question) {
        int choice = -1;
        do {
            System.out.println("Modify the question? (or -1 to stop)");
            System.out.println("1. Add answer");
            System.out.println("2. Delete answer");
            System.out.println("Select an option:");
            choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1:
                    addAnswersToQuestion(question);
                    break;
                case 2:
                    deleteAnswerFromAQuestion(question);
                    break;
                case -1:
                    break;
                default:
                    System.err.println("Invalid choice!");
            }
        } while (choice != -1);
    }

    private void addAnswersToQuestion(DBQuestion question) {
        List<Answer> answers = db.getAllAnswers();
        System.out.println("Available answers:");
        for (Answer value : answers) {
            DBAnswer a = (DBAnswer) value;
            a.setShowFullDetails(true);
            System.out.println(a);
        }

        System.out.println("Enter the ID of the answer you want to add (or -1 to stop):");
        int answerId = input.nextInt();
        input.nextLine();
        if(answerId == -1) {
            return;
        }

        System.out.println("Is this the correct answer? (yes/no)");
        boolean isCorrect = input.nextLine().equalsIgnoreCase("yes");

        db.addAnswerToQuestion(question.getId(), answerId, isCorrect);
    }

    public void deleteAnswerFromAQuestion(DBQuestion question)  {
        List<Answer> answers = db.getAnswersFromQuestion(question);
        System.out.println("Available answers:");
        for (Answer value : answers) {
            DBAnswer a = (DBAnswer) value;
            a.setShowFullDetails(true);
            System.out.println(a);
        }

        System.out.println("Enter the ID of the answer you want to delete (or -1 to stop):");
        int answerId = input.nextInt();
        input.nextLine();

        if(answerId == -1) {
            return;
        }
        db.deleteAnswerFromQuestion(question.getId(), answerId);
    }

}
