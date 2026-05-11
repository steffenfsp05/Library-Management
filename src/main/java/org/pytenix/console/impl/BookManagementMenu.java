package org.pytenix.console.impl;

import org.pytenix.console.BaseMenu;
import org.pytenix.models.Book;
import org.pytenix.models.BookStatus;
import org.pytenix.models.Patron;
import org.pytenix.repository.BookRepository;
import org.pytenix.repository.PatronRepository;

import java.util.List;
import java.util.Scanner;

/**
 * Handles the user interface for all book-related CRUD operations and searches.
 */
public class BookManagementMenu extends BaseMenu {
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;

    public BookManagementMenu(Scanner scanner, BookRepository bookRepository, PatronRepository patronRepository) {
        super(scanner);
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
    }

    @Override
    public void display() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- BOOK MANAGEMENT ---");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Delete Book");
            System.out.println("4. List All Books");
            System.out.println("5. Search Book by Title");
            System.out.println("6. Search Book by Author");
            System.out.println("7. Search Book by Status");
            System.out.println("8. Back to Main Menu");

            int choice = readInt("Choose action: ");
            switch (choice) {
                case 1 -> addBook();
                case 2 -> updateBook();
                case 3 -> deleteBook();
                case 4 -> listAllBooks();
                case 5 -> searchBookByTitle();
                case 6 -> searchBookByAuthor();
                case 7 -> searchBookByStatus();
                case 8 -> back = true;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void addBook() {
        int id = readInt("Book ID: ");
        if (bookRepository.findById(id) != null) {
            System.out.println("A book with this ID already exists!");
            return;
        }
        String title = readString("Title: ", false);
        if (title == null)
            return;

        String author = readString("Author: ", false);
        if (author == null)
            return;

        int year = readInt("Publication Year: ");

        Book newBook = new Book(id, title, author, year, BookStatus.AVAILABLE, null);
        bookRepository.save(newBook);
        System.out.println("Book added successfully!");
    }

    private void updateBook() {
        int id = readInt("ID of the book to update: ");
        Book book = bookRepository.findById(id);
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }
        String newTitle = readString("New Title (leave empty to skip): ", true);
        if (!newTitle.isEmpty()) book.setTitle(newTitle);

        String newAuthor = readString("New Author (leave empty to skip): ", true);
        if (!newAuthor.isEmpty()) book.setAuthor(newAuthor);

        int newYear = readInt("New Year (0 to skip): ");
        if (newYear != 0) book.setPublicationYear(newYear);

        bookRepository.save(book);
        System.out.println("Book updated successfully!");
    }

    private void deleteBook() {
        int id = readInt("ID of the book to delete: ");
        Book book = bookRepository.findById(id);
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }

        if (book.getBookStatus().equals(BookStatus.CHECKED_OUT) && book.getInheritedUser() != null) {
            //Removes relations
            Patron patron = patronRepository.findById(book.getInheritedUser());
            if (patron != null) {
                patron.getBooksCheckedOut().remove(id);
                patronRepository.save(patron);
            }


        }
        bookRepository.delete(id);
        System.out.println("Book deleted successfully!");
    }

    private void listAllBooks() {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            System.out.println("No books found in the library.");
            return;
        }
        printBooks(books);
    }

    private void searchBookByTitle() {
        String title = readString("Enter title: ", false);
        if (title == null)
            return;

        List<Book> books = bookRepository.searchByTitle(title);
        if (books.isEmpty()) {
            System.out.println("No books found with that title.");
        } else {
            printBooks(books);
        }
    }

    private void searchBookByAuthor() {
        String author = readString("Enter author: ", false);
        if (author == null)
            return;

        List<Book> books = bookRepository.searchBy(book -> book.getAuthor().equalsIgnoreCase(author));
        if (books.isEmpty()) {
            System.out.println("No books found with that author.");
        } else {
            printBooks(books);
        }
    }

    private void searchBookByStatus() {
        int operation = readInt("Enter Status (1 = Available, 2 = Checked Out): ");
        if (operation != 1 && operation != 2) {
            System.out.println("Invalid Input!");
        } else {

            BookStatus bookStatus = operation == 1 ? BookStatus.AVAILABLE : BookStatus.CHECKED_OUT;

            List<Book> books = bookRepository.searchBy(book -> book.getBookStatus().equals(bookStatus));
            if (books.isEmpty()) {
                System.out.println("No books found with that status.");
            } else {
                printBooks(books);
            }
        }


    }


    public void printBooks(List<Book> books) {
        System.out.println("\n--- FOUND BOOKS ---");
        for (Book b : books) {
            System.out.printf("ID: %d | Title: %s | Author: %s | Year: %d | Status: %s\n",
                    b.getId(), b.getTitle(), b.getAuthor(), b.getPublicationYear(), b.getBookStatus());
        }
    }
}
