package common.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Objects;

public class JsonUtils {

    private JsonUtils() {

    }

    public static JSONObject convertFileToJsonObject(String path) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(path)) {
            return (JSONObject) jsonParser.parse(reader);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
