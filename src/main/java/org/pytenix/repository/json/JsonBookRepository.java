package org.pytenix.repository.json;

import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.pytenix.models.Book;
import org.pytenix.repository.BookRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

/**
 * A concrete, JSON-based implementation of the {@link BookRepository} interface.
 * Initializes the generic repository with the specific file path and type tokens
 * needed by Gson to deserialize a collection of Books.
 */
public class JsonBookRepository extends JsonRepository<Book> implements BookRepository {

    public JsonBookRepository(String filePath) {
        super(filePath, new TypeToken<HashMap<Integer, Book>>() {
        }.getType());
    }

    @Override
    public @Nullable Book findById(int id) {
        return getCache().get(id);
    }


    @Override
    public List<Book> findAll() {
        return new ArrayList<>(getCache().values());
    }


    @Override
    public List<Book> searchByTitle(String title) {
        return searchBy(book -> book.getTitle().equalsIgnoreCase(title));
    }

    @Override
    public List<Book> searchBy(Predicate<Book> predicate) {
        return findAll().stream().filter(predicate).toList();
    }


    @Override
    public void save(@NotNull Book book) {
        getCache().put(book.getId(), book);
        saveToFile();
    }

    @Override
    public void delete(int id) {
        if (getCache().containsKey(id)) {
            getCache().remove(id);
            saveToFile();
        }
    }

    @Override
    public void close() {
        saveToFile();
    }
}
