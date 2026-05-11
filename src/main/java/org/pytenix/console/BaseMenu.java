package org.pytenix.console;

import org.jetbrains.annotations.Nullable;

import java.util.Scanner;

/**
 * An abstract base class for menus that provides reusable, error-proof
 * methods for reading user input from the console.
 */
public abstract class BaseMenu implements ConsoleMenu {
    protected final Scanner scanner;

    public BaseMenu(Scanner scanner) {
        this.scanner = scanner;
    }


    /**
     * Reads a integer input from the user.
     *
     * @param prompt The text to display to the user.
     * @return The entered number, only returns if the user inputs an valid number.
     */
    protected int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
    }

    /**
     * Reads a string input from the user.
     *
     * @param prompt     The text to display to the user.
     * @param canBeBlank If false, the user is forced to enter a non-empty string.
     * @return The entered string, or null if the user typed "cancel".
     */
    protected @Nullable String readString(String prompt, boolean canBeBlank) {
        System.out.print(prompt);

        if (canBeBlank) {
            return scanner.nextLine().trim();
        }

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("cancel"))
                return null;

            if (!input.isBlank()) {
                return input;
            } else
                System.out.println("Please provide a valid input. Type 'cancel' to cancel");

        }

    }
}


