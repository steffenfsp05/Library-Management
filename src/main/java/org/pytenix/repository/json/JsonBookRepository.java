package org.pytenix.repository.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.pytenix.exceptions.FileNotLoadableException;
import org.pytenix.exceptions.FileNotSaveableException;
import org.pytenix.models.Book;
import org.pytenix.repository.BookRepository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

public class JsonBookRepository extends JsonRepository<Book> implements BookRepository {

    public JsonBookRepository(String filePath) {
        super(filePath);
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
        if(getCache().containsKey(id))
        {
            getCache().remove(id);
            saveToFile();
        }
    }
}
