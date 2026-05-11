package org.pytenix;

import org.pytenix.console.ConsoleMenu;
import org.pytenix.console.impl.BookManagementMenu;
import org.pytenix.console.impl.LibraryOperationsMenu;
import org.pytenix.console.impl.MainMenu;
import org.pytenix.console.impl.PatronManagementMenu;
import org.pytenix.repository.BookRepository;
import org.pytenix.repository.PatronRepository;
import org.pytenix.repository.json.JsonBookRepository;
import org.pytenix.repository.json.JsonPatronRepository;
import org.pytenix.service.LibraryService;

import java.util.Scanner;

/**
 * The main entry point of the Library Management Application.
 * It is responsible for wiring up the dependencies (repositories, services,
 * and menus) and starting the main console loop.
 * A shutdown hook is used to guarantee that data is saved safely upon exit.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookRepository bookRepo = new JsonBookRepository("books.json");
        PatronRepository patronRepo = new JsonPatronRepository("patrons.json");
        LibraryService libraryService = new LibraryService(bookRepo, patronRepo);

        // 2. Initialize sub-menus
        ConsoleMenu bookMenu = new BookManagementMenu(scanner, bookRepo, patronRepo);
        ConsoleMenu patronMenu = new PatronManagementMenu(scanner, patronRepo, bookRepo);
        ConsoleMenu libraryMenu = new LibraryOperationsMenu(scanner, libraryService);

        // 3. Initialize and start main menu
        ConsoleMenu mainMenu = new MainMenu(scanner, bookMenu, patronMenu, libraryMenu);
        mainMenu.display();

        // 4. Cleanup
        scanner.close();

        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {
            bookRepo.close();
            patronRepo.close();
        }));

    }

}