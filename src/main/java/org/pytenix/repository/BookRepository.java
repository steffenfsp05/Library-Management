package org.pytenix.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.pytenix.models.Book;

import java.util.List;

public interface BookRepository {

    @Nullable Book findById(int id);
    List<Book> findAll();
    List<Book> searchByTitle(String title);
    void save(@NotNull Book book);
    void delete(int id);

}
