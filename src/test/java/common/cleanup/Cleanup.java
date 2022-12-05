package common.cleanup;

import common.apis.UserAPIs;
import io.restassured.response.Response;
import org.testng.Assert;

public class Cleanup {

    private Cleanup() {
    }

    public static void cleanupSingleUser(int userId) {
        UserAPIs userAPIs = new UserAPIs();
        Response response = userAPIs.deleteUser(userId);
        Assert.assertEquals(response.statusCode(), 200);
    }
}
