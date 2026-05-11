package org.pytenix.console.impl;

import org.pytenix.console.BaseMenu;
import org.pytenix.console.ConsoleMenu;

import java.util.Scanner;

/**
 * The primary navigation menu of the application.
 * Routes the user's input to the specific sub-menus (Books, Patrons, Operations)
 * and controls the main application loop.
 */
public class MainMenu extends BaseMenu {
    private final ConsoleMenu bookMenu;
    private final ConsoleMenu patronMenu;
    private final ConsoleMenu libraryMenu;

    public MainMenu(Scanner scanner, ConsoleMenu bookMenu, ConsoleMenu patronMenu, ConsoleMenu libraryMenu) {
        super(scanner);
        this.bookMenu = bookMenu;
        this.patronMenu = patronMenu;
        this.libraryMenu = libraryMenu;
    }

    @Override
    public void display() {
        boolean running = true;
        System.out.println("Welcome to the Library Management System!");

        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Book Management");
            System.out.println("2. Patron Management");
            System.out.println("3. Library Operations (Borrow/Return)");
            System.out.println("4. Exit");

            int choice = readInt("Select an option: ");

            switch (choice) {
                case 1 -> bookMenu.display();
                case 2 -> patronMenu.display();
                case 3 -> libraryMenu.display();
                case 4 -> {
                    running = false;
                    System.out.println("Exiting program. Goodbye!");
                }
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }
}
