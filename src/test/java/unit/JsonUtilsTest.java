package unit;

import common.utils.JsonUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JsonUtilsTest {

    @Test
    public void convertFileToJsonObject_returnsCorrectValues() {
        JSONObject result = JsonUtils.convertFileToJsonObject("src/test/java/data/qa/users.json");

        Assert.assertNotNull(result, "Result should not be null for a valid file");
        Assert.assertEquals(result.get("name"), "assessmentUserName");
        Assert.assertEquals(result.get("job"), "qaAuto");
    }

    @Test
    public void convertFileToJsonObject_returnsNullForMissingFile() {
        JSONObject result = JsonUtils.convertFileToJsonObject("nonexistent/path/file.json");

        Assert.assertNull(result, "Result should be null when file does not exist");
    }
}
