package org.pytenix.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.pytenix.models.Book;

import java.util.List;
import java.util.function.Predicate;

/**
 * Defines the standard operations for managing Book data.
 */
public interface BookRepository {

    /**
     * Retrieves a book by their unique ID.
     *
     * @param id The ID to look for.
     * @return The Book object, or null if no book with this ID exists.
     */
    @Nullable
    Book findById(int id);

    /**
     * Retrieves a list of all registered books.
     *
     * @return A list containing all books.
     */
    List<Book> findAll();

    /**
     * Retrieves books by their title.
     *
     * @param title The title to look for.
     * @return A list of patrons that match the given title.
     */
    List<Book> searchByTitle(String title);

    /**
     * A highly flexible search method allowing custom filter logic.
     *
     * @param predicate A lambda expression defining the search condition.
     * @return A list of patrons that match the given condition.
     */
    List<Book> searchBy(Predicate<Book> predicate);


    /**
     * Saves a book to the repository.
     * Overwrites an existing book if the ID already exists.
     *
     * @param book The book to save.
     */
    void save(@NotNull Book book);

    /**
     * Removes a book from the repository based on their ID.
     *
     * @param id The ID of the book to delete.
     */
    void delete(int id);


    /**
     * Safely shuts down the repository and flushes data to disk.
     */
    void close();

}
