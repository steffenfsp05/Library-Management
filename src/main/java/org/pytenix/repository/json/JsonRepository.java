package org.pytenix.repository.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import lombok.Getter;
import org.pytenix.exceptions.FileNotLoadableException;
import org.pytenix.exceptions.FileNotSaveableException;
import org.pytenix.models.Book;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.HashMap;

public abstract class JsonRepository<T> {


    @Getter
    private final HashMap<Integer, T> cache = new HashMap<>();
    private final String filePath;

    private final Gson gson;
    private final Type BOOK_TYPE = new TypeToken<HashMap<Integer,T>>() {}.getType();;

    public JsonRepository(String filePath) {
        this.filePath = filePath;
        this.gson = new Gson();

        loadFromFile();
    }


    private void loadFromFile() throws FileNotLoadableException {

        cache.clear();

        try {
            if (!new File(this.filePath).exists())
                new File(this.filePath).createNewFile();

            JsonReader reader = new JsonReader(new FileReader(this.filePath));
            cache.putAll(gson.fromJson(reader, BOOK_TYPE));
            reader.close();

        }catch (Exception e)
        {
            throw new FileNotLoadableException(filePath, e);
        }
    }

    public void saveToFile() {

        try {
            if (!new File(this.filePath).exists())
                new File(this.filePath).createNewFile();

            Writer writer = new FileWriter(this.filePath);
            gson.toJson(cache,writer);
            writer.close();

        }catch (Exception e)
        {
            throw new FileNotSaveableException(filePath, e);
        }
    }

}
