package org.pytenix.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.pytenix.models.Book;
import org.pytenix.models.Patron;

import java.util.List;
import java.util.function.Predicate;

public interface PatronRepository {

    @Nullable
    Patron findById(int id);
    List<Patron> findAll();
    List<Patron> searchBy(Predicate<Patron> predicate);
    void save(@NotNull Patron book);
    void delete(int id);

}
