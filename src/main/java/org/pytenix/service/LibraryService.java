package org.pytenix.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.pytenix.models.Book;
import org.pytenix.models.BookStatus;
import org.pytenix.models.Patron;
import org.pytenix.repository.BookRepository;
import org.pytenix.repository.PatronRepository;


/**
 * Service class handling the core business logic of the library.
 * It acts as a bridge between the repositories and the user interface,
 * ensuring that all business rules (like checking availability) are met
 * before modifying data.
 */

public class LibraryService {


    final BookRepository bookRepository;
    final PatronRepository patronRepository;


    public LibraryService(BookRepository bookRepository, PatronRepository patronRepository) {
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;


    }

    /**
     * Deletes a book with his relations
     *
     * @param book The book which gets deleted
     * @return A Response boolean whether the deletion succeeded or failed.
     */
    public boolean deleteBook(Book book)
    {
        if(book == null)
            return false;

        final int id = book.getId();
        if (book.getBookStatus().equals(BookStatus.CHECKED_OUT) && book.getInheritedUser() != null) {
            //Removes relations
            Patron patron = patronRepository.findById(book.getInheritedUser());
            if (patron != null) {
                patron.getBooksCheckedOut().remove(id);
                patronRepository.save(patron);
            }

        }
        bookRepository.delete(id);
        return true;
    }

    /**
     * Deletes a patron with his relations
     *
     * @param patron The patron which gets deleted
     * @return A Response boolean whether the deletion succeeded or failed.
     */
    public boolean deletePatron(Patron patron)
    {
        if(patron == null)
            return false;

        if (!patron.getBooksCheckedOut().isEmpty()) {
            //Removes relations
            for (Integer bookId : patron.getBooksCheckedOut()) {
                Book book = bookRepository.findById(bookId);
                if (book != null) {
                    book.setBookStatus(BookStatus.AVAILABLE);
                    book.setInheritedUser(null);
                    bookRepository.save(book);
                }
            }
        }
        patronRepository.delete(patron.getId());
        return true;
    }


    /**
     * Attempts to check out a book for a specific patron.
     * Validates that both the book and patron exist, the book isn't already
     * checked out, and the patron doesn't already have it.
     *
     * @param patronId The ID of the patron borrowing the book.
     * @param bookId   The ID of the book to be borrowed.
     * @return A Response enum detailing whether the checkout succeeded or why it failed.
     */
    public Response borrowOutBook(int patronId, int bookId) {
        final Patron patron = patronRepository.findById(patronId);
        final Book book = bookRepository.findById(bookId);

        if (patron == null)
            return Response.PATRON_NOT_EXISTS;
        if (book == null)
            return Response.BOOK_NOT_EXISTS;

        if (patron.getBooksCheckedOut().contains(bookId))
            return Response.BOOK_DUPLICATE;

        if (book.getBookStatus().equals(BookStatus.CHECKED_OUT))
            return Response.BOOK_NOT_AVAILABLE;


        book.setBookStatus(BookStatus.CHECKED_OUT);
        book.setInheritedUser(patronId);
        patron.getBooksCheckedOut().add(bookId);

        patronRepository.save(patron);
        bookRepository.save(book);

        return Response.SUCCESS;


    }

    /**
     * Processes the return of a borrowed book.
     * Ensures the book and patron exist, and verifies that this specific patron
     * actually has the book checked out before returning it.
     *
     * @param patronId The ID of the patron returning the book.
     * @param bookId   The ID of the book being returned.
     * @return A Response enum detailing whether the return succeeded or why it failed.
     */
    public Response returnBook(int patronId, int bookId) {
        final Patron patron = patronRepository.findById(patronId);
        final Book book = bookRepository.findById(bookId);

        if (patron == null)
            return Response.PATRON_NOT_EXISTS;
        if (book == null)
            return Response.BOOK_NOT_EXISTS;

        if (!patron.getBooksCheckedOut().contains(bookId))
            return Response.BOOK_NOT_CONTAINS;

        if (book.getBookStatus().equals(BookStatus.AVAILABLE))
            return Response.BOOK_NOT_AVAILABLE;


        book.setBookStatus(BookStatus.AVAILABLE);
        book.setInheritedUser(null);
        patron.getBooksCheckedOut().remove(bookId);

        patronRepository.save(patron);
        bookRepository.save(book);

        return Response.SUCCESS;


    }

    /**
     * A wrapper for service responses.
     * Keeps the UI clean by passing back a success flag and a ready-to-print message
     * instead of throwing multiple custom exceptions.
     */
    @AllArgsConstructor
    @Getter
    public enum Response {
        SUCCESS(true, "The operation was successful"),
        PATRON_NOT_EXISTS(false, "The given Patron does not exists!"),
        BOOK_NOT_EXISTS(false, "The given Book does not exists!"),
        BOOK_DUPLICATE(false, "The patron already have this book!"),
        BOOK_NOT_AVAILABLE(false, "The given Book is currently not available!"),
        BOOK_NOT_CONTAINS(false, "The patron does not have this Book!");

        final boolean success;
        final String message;

    }

}
