package common.cleanup;

import common.apis.UsersApiClient;
import io.restassured.response.Response;
import org.testng.Assert;

public class Cleanup {

    private Cleanup() {}

    public static void cleanupUser(int userId) {
        Response response = new UsersApiClient().deleteUser(userId);
        Assert.assertEquals(response.statusCode(), 200);
    }
}
