package org.pytenix.console.impl;

import org.pytenix.console.BaseMenu;
import org.pytenix.service.LibraryService;

import java.util.Scanner;

/**
 * Handles the user interface for library operations, specifically borrowing
 * and returning books. Relies on the {@link LibraryService} to enforce
 * all business rules and process the transactions.
 */
public class LibraryOperationsMenu extends BaseMenu {
    private final LibraryService libraryService;

    public LibraryOperationsMenu(Scanner scanner, LibraryService libraryService) {
        super(scanner);
        this.libraryService = libraryService;
    }

    @Override
    public void display() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- LIBRARY OPERATIONS ---");
            System.out.println("1. Borrow Book (Checkout)");
            System.out.println("2. Return Book");
            System.out.println("3. Back to Main Menu");

            int choice = readInt("Choose action: ");
            switch (choice) {
                case 1 -> checkoutBook();
                case 2 -> returnBook();
                case 3 -> back = true;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void checkoutBook() {
        int patronId = readInt("Patron ID: ");
        int bookId = readInt("Book ID: ");
        LibraryService.Response res = libraryService.borrowOutBook(patronId, bookId);
        System.out.println(res.isSuccess() ? "Success: " + res.getMessage() : "Error: " + res.getMessage());
    }

    private void returnBook() {
        int patronId = readInt("Patron ID: ");
        int bookId = readInt("Book ID: ");
        LibraryService.Response res = libraryService.returnBook(patronId, bookId);
        System.out.println(res.isSuccess() ? "Success: " + res.getMessage() : "Error: " + res.getMessage());
    }
}
