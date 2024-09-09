package dbproject;

import java.util.Scanner;

public enum Subject {
    Math(1, "Math"),
    History(2, "History"),
    Science(3, "Science"),
    Geography(4, "Geography");

    int ID;
    String subject;

    Subject(int ID, String subject) {
        this.ID = ID;
        this.subject = subject;
    }

    public int getID() {
        return ID;
    }

    public String getSubject() {
        return subject;
    }

    public static Subject toSubject(int ID) {
        return switch (ID) {
            case 1 -> Math;
            case 2 -> History;
            case 3 -> Science;
            case 4 -> Geography;
            default -> null;
        };
    }

    public static Subject toSubject(String subject) {
        return switch (subject) {
            case "Math" -> Math;
            case "Science" -> Science;
            case "History" -> History;
            case "Geography" -> Geography;
            default -> null;
        };
    }

    public static Subject getSubjectFromUser(Subject[] subjects, Scanner input) {
        Subject subject = null;
        int selection = 0;
        do {
            System.out.println("Select a subject:");
            for (Subject s : subjects) {
                System.out.println(s.getID() + ". " + s.getSubject());
            }
            selection = input.nextInt();
            input.nextLine();
            subject = Subject.toSubject(selection);
        } while (subject == null);
        return subject;
    }
}
