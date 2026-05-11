package org.pytenix.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.pytenix.models.Patron;

import java.util.List;
import java.util.function.Predicate;


/**
 * Defines the standard operations for managing Patron data.
 */
public interface PatronRepository {

    /**
     * Retrieves a patron by their unique ID.
     *
     * @param id The ID to look for.
     * @return The Patron object, or null if no patron with this ID exists.
     */
    @Nullable
    Patron findById(int id);

    /**
     * Retrieves a list of all registered patrons.
     *
     * @return A list containing all patrons.
     */
    List<Patron> findAll();

    /**
     * A highly flexible search method allowing custom filter logic.
     *
     * @param predicate A lambda expression defining the search condition.
     * @return A list of patrons that match the given condition.
     */
    List<Patron> searchBy(Predicate<Patron> predicate);

    /**
     * Saves a patron to the repository.
     * Overwrites an existing patron if the ID already exists.
     *
     * @param patron The patron to save.
     */
    void save(@NotNull Patron patron);

    /**
     * Removes a patron from the repository based on their ID.
     *
     * @param id The ID of the patron to delete.
     */
    void delete(int id);

    /**
     * Safely shuts down the repository and flushes data to disk.
     */
    void close();

}
