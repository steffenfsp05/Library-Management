package org.pytenix.repository.json;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import lombok.Getter;
import org.pytenix.exceptions.FileNotLoadableException;
import org.pytenix.exceptions.FileNotSavableException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * A generic, file-based repository implementation using Gson.
 * * Design Decision: Instead of reading from and writing to the disk on every single
 * query, this class loads the entire JSON file into an in-memory HashMap (`cache`)
 * on startup. This drastically improves read performance. Disk writes only happen
 * when the data is actually modified (save/delete).
 *
 * @param <T> The model type this repository manages (e.g., Book or Patron).
 */
public abstract class JsonRepository<T> {


    @Getter
    private final HashMap<Integer, T> cache;
    private final String filePath;

    private final Gson gson;

    private final Type type;

    public JsonRepository(String filePath, Type type) {
        this.filePath = filePath;
        this.cache = new HashMap<>();
        this.type = type;
        this.gson = new Gson();
        loadFromFile();
    }


    /**
     * Loads the JSON file into the memory cache.
     * If the file doesn't exist yet, it creates a new, empty one to prevent crashes.
     *
     * @throws FileNotLoadableException if there are I/O errors or formatting issues.
     */
    private void loadFromFile() throws FileNotLoadableException {
        cache.clear();

        try {
            if (!new File(this.filePath).exists()) {
                new File(this.filePath).createNewFile();
                saveToFile();
            }

            JsonReader reader = new JsonReader(new FileReader(this.filePath));

            HashMap<Integer, T> loadedData = gson.fromJson(reader, type);
            if (loadedData != null) {
                cache.putAll(loadedData);
            }

            reader.close();

        } catch (Exception e) {
            throw new FileNotLoadableException(filePath, e);
        }
    }

    /**
     * Serializes the current state of the memory cache and writes it to the disk.
     * Called automatically after every state change (save/delete/shutdown).
     */
    public void saveToFile() {

        try {
            if (!new File(this.filePath).exists())
                new File(this.filePath).createNewFile();

            Writer writer = new FileWriter(this.filePath);
            gson.toJson(cache, writer);
            writer.close();

        } catch (Exception e) {
            throw new FileNotSavableException(filePath, e);
        }
    }

}
