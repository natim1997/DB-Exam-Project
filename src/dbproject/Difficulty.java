package dbproject;

import java.util.Scanner;

public enum Difficulty {
    Easy, Moderate, Hard;

    public static Difficulty getDifficultyFromUser(Scanner input) {
        Difficulty difficulty = null;
        int selection = 0;
        do {
            System.out.println("Select a difficulty:");
            for (Difficulty d : Difficulty.values()) {
                System.out.println((d.ordinal() + 1) + ". " + d);
            }
            selection = input.nextInt();
            input.nextLine();
            difficulty = Difficulty.values()[selection - 1];
        } while (difficulty == null);
        return difficulty;
    }
}
