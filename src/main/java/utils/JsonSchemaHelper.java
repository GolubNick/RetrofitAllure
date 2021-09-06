package utils;


import com.google.gson.GsonBuilder;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import retrofit2.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public final class JsonSchemaHelper {

    public static void matchSchema(String path, Response<?> response) throws IOException {
        String schema = Files.readString(Path.of("src/test/resources" + path));
        Schema validator = SchemaLoader.load(new JSONObject(schema));
        JSONObject json = new JSONObject(new GsonBuilder().create().toJson(response.body()));
        try {
            validator.validate(json);
        } catch (ValidationException e) {
            throw new AssertionError(
                    e.getMessage() +
                    ":\n" +
                    e.getCausingExceptions().stream().map(ValidationException::getMessage)
                            .collect(Collectors.joining("\n")));
        }
    }
}
