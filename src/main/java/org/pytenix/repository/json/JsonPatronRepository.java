package org.pytenix.repository.json;

import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.pytenix.models.Patron;
import org.pytenix.repository.PatronRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

/**
 * A concrete, JSON-based implementation of the {@link PatronRepository} interface.
 * Initializes the generic repository with the specific file path and type tokens
 * needed by Gson to deserialize a collection of Patrons.
 */
public class JsonPatronRepository extends JsonRepository<Patron> implements PatronRepository {


    public JsonPatronRepository(String filePath) {
        super(filePath, new TypeToken<HashMap<Integer, Patron>>() {
        }.getType());
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
