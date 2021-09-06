package utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import retrofit2.Response;

import java.io.InputStream;

public final class JsonSchemaHelper {

    public static boolean isMatchingSchema(String path, Response<?> response) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            InputStream inputStream = JsonSchemaHelper.class.getResourceAsStream(path);
            JSONObject rawSchema;
            if (inputStream != null) {
                rawSchema = new JSONObject(new JSONTokener(inputStream));
                Schema schema = SchemaLoader.load(rawSchema);
                schema.validate(new JSONObject(gson.toJson(response.body())));
                return true;
            } else {
                return false;
            }
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}