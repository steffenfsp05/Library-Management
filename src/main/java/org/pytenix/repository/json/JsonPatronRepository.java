package org.pytenix.repository.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.pytenix.exceptions.FileNotLoadableException;
import org.pytenix.exceptions.FileNotSaveableException;
import org.pytenix.models.Book;
import org.pytenix.models.Patron;
import org.pytenix.repository.PatronRepository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

public class JsonPatronRepository extends JsonRepository<Patron> implements PatronRepository {


    public JsonPatronRepository(String filePath) {
        super(filePath);
    }

    @Override
    public @Nullable Patron findById(int id) {
        return getCache().get(id);
    }

    @Override
    public List<Patron> findAll() {
        return new ArrayList<>(getCache().values());
    }

    @Override
    public List<Patron> searchBy(Predicate<Patron> predicate) {
        return findAll().stream().filter(predicate).toList();
    }

    @Override
    public void save(@NotNull Patron patron) {
        getCache().put(patron.getId(), patron);
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
