package org.pytenix.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.pytenix.exceptions.FileNotLoadableException;
import org.pytenix.exceptions.FileNotSaveableException;
import org.pytenix.models.Book;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonBookRepository implements BookRepository {

    private final HashMap<Integer, Book> bookCache = new HashMap<>();
    private final String filePath;

    private final Gson gson;
    private static final Type BOOK_TYPE = new TypeToken<HashMap<Integer,Book>>() {}.getType();;

    public JsonBookRepository(String filePath) {
        this.filePath = filePath;
        this.gson = new Gson();

        loadFromFile();
    }

    private void loadFromFile() throws FileNotLoadableException {

        bookCache.clear();

        try {
            if (!new File(this.filePath).exists())
                new File(this.filePath).createNewFile();

            JsonReader reader = new JsonReader(new FileReader(this.filePath));
            bookCache.putAll(gson.fromJson(reader, BOOK_TYPE));
            reader.close();

        }catch (Exception e)
        {
            throw new FileNotLoadableException(filePath, e);
        }
    }

    private void saveToFile() {

        try {
            if (!new File(this.filePath).exists())
                new File(this.filePath).createNewFile();

            Writer writer = new FileWriter(this.filePath);
            gson.toJson(bookCache,writer);
            writer.close();

        }catch (Exception e)
        {
            throw new FileNotSaveableException(filePath, e);
        }
    }


    @Override
    public @Nullable Book findById(int id) {
        return bookCache.get(id);
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(bookCache.values());
    }

    @Override
    public List<Book> searchByTitle(String title) {
        return findAll().stream().filter(book -> book.getTitle().equalsIgnoreCase(title)).toList();
    }

    @Override
    public void save(@NotNull Book book) {
        bookCache.put(book.getId(), book);
        saveToFile();
    }

    @Override
    public void delete(int id) {
        if(bookCache.containsKey(id))
        {
            bookCache.remove(id);
            saveToFile();
        }
    }
}
