package dbproject;

import dbproject.DBClasses.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {

    //put your own username and password here
    private static final String user = "postgres";
    private static final String password = "1234";

    public static Teacher findTeacher(String firstname, String lastname) {
        List<Teacher> teachers = db.getAllTeachers();
        for (Teacher teacher : teachers) {
            if (teacher.getFirstName().equals(firstname) && teacher.getLastName().equals(lastname)) {
                return teacher;
            }
        }
        throw new IllegalArgumentException("No teacher found with that name!");
    }

    public static void printAllQuestionsFromSubject(Subject subject) {
        for (Question question : db.getAllQuestionsFromSubject(subject)) {
            System.out.print(question);
            try {
                System.out.println("Answers:");
                int i = 1;
                for (Answer answer : db.getAnswersFromQuestion(question)) {
                    System.out.format("%d) %s\n",i, answer);
                    i++;
                }
                System.out.println();
            } catch (IllegalArgumentException e) {
                System.out.println("No answers were added yet!\n");
            }
        }
    }

    public static void printAllAnswers() {
        List<Answer> answers =  db.getAllAnswers();
        for (Answer value : answers) {
            DBAnswer answer = (DBAnswer) value;
            answer.setShowFullDetails(true);
            System.out.println(answer);
        }
    }

    public static void printAnswersToQuestion(Question question){
        List<Answer> answers = db.getAnswersFromQuestion(question);
        for (Answer value : answers) {
            DBAnswer answer = (DBAnswer) value;
            System.out.println(answer);
        }
    }

    public static Subject selectASubjectFromTeacher(Teacher teacher, Scanner input) {
        Subject subject = null;
        boolean wantToTeach = false;
        List<Subject> subjects = null;
        do {
            try {
                subjects = db.getSubjectsFromTeacher(teacher.getID());

                if (subjects != null && !subjects.isEmpty()) {
                    System.out.println("You are currently teaching the following subjects:");
                    for (Subject s : subjects) {
                        System.out.format("%d. %s\n", s.getID(), s);
                    }

                    System.out.println("Want to teach a new subject? (true/false)");
                    wantToTeach = input.nextBoolean();
                }else {
                    System.out.println("You are not currently teaching any subjects.");
                    wantToTeach = true;
                }

                if(wantToTeach) {
                    subject = Subject.getSubjectFromUser(Subject.values(), input);
                    db.addSubjectToTeacher(teacher.getID(), subject);
                }else{
                    subject = Subject.getSubjectFromUser(subjects.toArray(new Subject[0]), input);
                }
            } catch (Exception e) {
                System.err.println("Error! " + e.getMessage());
                subject = null;
            }
        } while (subject == null);
        return subject;
    }

    static final DBWrapper db = new DBWrapper(user, password);
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Teacher teacher = null;

        System.out.println("Enter your full name (first and last):");
        String firstname = input.next();
        String lastname = input.next();

        try {
            teacher = findTeacher(firstname, lastname);
            System.out.println("Welcome back " + firstname + " " + lastname);
        } catch (Exception e) {
            System.out.println("Welcome " + firstname + " " + lastname);
            teacher = new Teacher(firstname, lastname);
            int tid = db.addTeacher(teacher);
            teacher.setID(tid);
        }

        db.setTeacher(teacher);
        Subject subject = selectASubjectFromTeacher(teacher, input);
        db.setSelectedSubject(subject);

        final int EXIT = -1;
        int selection = 0;

        do {
            printMenu();
            selection = input.nextInt();
            input.nextLine();

            try {
                switch (selection) {
                    case 1: {
                        printAllQuestionsFromSubject(subject);
                        break;
                    }
                    case 2: {
                        System.out.println("Enter the answer:");
                        String answer = input.nextLine();
                        System.out.println("Enter the type of the question:");
                        QuestionType type = QuestionType.getQuestionTypeFromUser(input);
                        db.addAnswer(answer, type);
                        break;
                    }
                    case 3: {
                        printAllQuestionsFromSubject(subject);
                        System.out.println("Enter the question ID:");
                        int qid = input.nextInt();
                        printAllAnswers();
                        System.out.println("Enter the answer ID:");
                        int aid = input.nextInt();
                        System.out.println("Is the answer correct? (true/false)");
                        boolean isCorrect = input.nextBoolean();
                        db.addAnswerToQuestion(qid, aid, isCorrect);
                        break;
                    }
                    case 4: {
                        System.out.println("Enter the question:");
                        String question = input.nextLine();
                        System.out.println("Enter the type of the question:");
                        QuestionType type = QuestionType.getQuestionTypeFromUser(input);
                        Difficulty difficulty = Difficulty.getDifficultyFromUser(input);
                        db.addQuestion(new DBQuestion(-1, question, difficulty, type));
                        break;
                    }
                    case 5: {
                        printAllQuestionsFromSubject(subject);
                        System.out.println("Enter the question ID:");
                        int qid = input.nextInt();
                        printAnswersToQuestion(db.getQuestionBylD(qid));
                        System.out.println("Enter the answer ID:");
                        int aid = input.nextInt();
                        db.deleteAnswerFromQuestion(qid, aid);
                        break;
                    }
                    case 6: {
                        printAllQuestionsFromSubject(subject);
                        System.out.println("Enter the question ID:");
                        int qid = input.nextInt();
                        db.deleteQuestionBylD(qid);
                        break;
                    }
                    case 7: {
                        generateExam(input);
                        break;
                    }
                    case 8: {
                        subject = selectASubjectFromTeacher(teacher, input);
                        db.setSelectedSubject(subject);
                        break;
                    }
                    case 9:
                        printAllQuestionsFromSubject(subject);
                        System.out.println("Enter the question ID:");
                        int qid = input.nextInt();
                        Question question = db.getQuestionBylD(qid);
                        System.out.println("Enter the new difficulty:");
                        Difficulty newDifficulty = Difficulty.getDifficultyFromUser(input);
                        db.updateQuestionDifficulty(question, newDifficulty);
                        break;
                    case EXIT: {
                        System.out.println("Goodbye!");
                        break;
                    }
                    default:
                        System.out.println("Error! option dose not exist, Please try again!");
                }
            } catch (Exception e) {
                System.err.println("Error! " + e.getMessage());
            }
        } while (selection != EXIT);
        db.close();
    }

    private static void generateExam(Scanner input) throws FileNotFoundException {

        int numQuestions = 0;

        do {
            System.out.println("Enter the number of questions you want in the exam:");
            numQuestions = input.nextInt();
        } while (numQuestions <= 0 || numQuestions > db.getNumQuestions());

        System.out.println("Do you we want to randomize the questions? (true/false)");
        boolean randomize = input.nextBoolean();

        DBExam exam = null;
        if (randomize) {
            exam = new DBAutomaticExam(numQuestions, db);
        } else {
            exam = new DBManualExam(numQuestions, input, db);
        }

        exam.createExam();
        String path = exam.writeExam(false);
        exam.writeExam( false);
        System.out.println("Exam created successfully!\nYou can find it in: " + path);
        exam.writeExam( true);

    }

    public static void printMenu() {
        System.out.println("\nWelcome to my Exam creation tool!");
        System.out.println("Please select an option: (-1 to exit)");
        System.out.println("1. Display all of the question (and their answers)");
        System.out.println("2. Add a new answers");
        System.out.println("3. Append an answer to an existing question");
        System.out.println("4. Add a new question");
        System.out.println("5. Delete an answers to a question");
        System.out.println("6. Delete a question");
        System.out.println("7. Generate a new test");
        System.out.println("8. Change the subject");
        System.out.println("9. update question difficulty");
    }
}