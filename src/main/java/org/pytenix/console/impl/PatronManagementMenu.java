package org.pytenix.console.impl;

import org.pytenix.console.BaseMenu;
import org.pytenix.models.Patron;
import org.pytenix.repository.PatronRepository;
import org.pytenix.service.LibraryService;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 * Handles the user interface for all patron-related CRUD operations.
 * Note: When deleting a patron, this class ensures that any books currently
 * checked out by them are returned and marked as available again to prevent orphan records.
 */
public class PatronManagementMenu extends BaseMenu {
    private final PatronRepository patronRepository;
    private final LibraryService libraryService;


    public PatronManagementMenu(Scanner scanner, LibraryService libraryService, PatronRepository patronRepository) {
        super(scanner);
        this.patronRepository = patronRepository;
        this.libraryService = libraryService;
    }

    @Override
    public void display() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- PATRON MANAGEMENT ---");
            System.out.println("1. Add Patron");
            System.out.println("2. Update Patron");
            System.out.println("3. Delete Patron");
            System.out.println("4. List All Patrons");
            System.out.println("5. Back to Main Menu");

            int choice = readInt("Choose action: ");
            switch (choice) {
                case 1 -> addPatron();
                case 2 -> updatePatron();
                case 3 -> deletePatron();
                case 4 -> listAllPatrons();
                case 5 -> back = true;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void addPatron() {
        int id = readInt("Patron ID: ");
        if (patronRepository.findById(id) != null) {
            System.out.println("A patron with this ID already exists!");
            return;
        }

        String name = readString("Name: ", false);
        if (name == null)
            return;

        Patron patron = new Patron(id, name, new HashSet<>());
        patronRepository.save(patron);
        System.out.println("Patron registered successfully!");
    }

    private void updatePatron() {
        int id = readInt("ID of the patron to update: ");
        Patron patron = patronRepository.findById(id);
        if (patron == null) {
            System.out.println("Patron not found!");
            return;
        }
        String newName = readString("New Name: ", false);
        if (newName == null)
            return;

        if (!newName.isEmpty()) {
            patron.setName(newName);
            patronRepository.save(patron);
            System.out.println("Patron updated!");
        }
    }

    private void deletePatron() {
        int id = readInt("ID of the patron to delete: ");
        final Patron patron = patronRepository.findById(id);
        if (patron == null) {
            System.out.println("Patron not found!");
            return;
        }

        if(libraryService.deletePatron(patron))
             System.out.println("Patron deleted!");
        else
            System.out.println("Error occurred while deleting the patron!");
    }

    private void listAllPatrons() {
        List<Patron> patrons = patronRepository.findAll();
        if (patrons.isEmpty()) {
            System.out.println("No patrons registered.");
            return;
        }
        System.out.println("\n--- PATRON LIST ---");
        for (Patron p : patrons) {
            System.out.printf("ID: %d | Name: %s | Borrowed Books (IDs): %s\n",
                    p.getId(), p.getName(), p.getBooksCheckedOut().toString());
        }
    }
}