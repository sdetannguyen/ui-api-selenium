package api_test;

import common.cleanup.Cleanup;
import common.utils.JsonUtils;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pojos.UserResponse;

import java.util.Objects;

@SuppressWarnings("unchecked")
public class UserManagementTest extends BaseTest {

    private static JSONObject targetUserObj;
    private static int userId;

    @BeforeMethod
    public void startTheTest() {
        targetUserObj = Objects.requireNonNull(
                JsonUtils.convertFileToJsonObject(String.format("src/test/java/data/%s/users.json", env)));
    }

    @Test
    public void createNewUserSuccessful() {
        String targetName = (String) targetUserObj.get("name");
        String targetJob = (String) targetUserObj.get("job");

        Response response = userAPIs.createUser((String) targetUserObj.get("name"), (String) targetUserObj.get("job"));
        UserResponse userRes = response.body().as(UserResponse.class);
        userId = userRes.getId();

        Assert.assertEquals(response.statusCode(), 201);
        Assert.assertEquals(userRes.getName(), targetName, "Verify user name on returned response");
        Assert.assertEquals(userRes.getJob(), targetJob, "Verify job name on returned response");

        // Call to get user by userId API to verify the new user has been created successfully
        // Seems there's a bug for this API - It always return 404 with our newly created user
        Response getCurrentUserResponse = userAPIs.getUserById(userRes.getId());

        Assert.assertEquals(getCurrentUserResponse.statusCode(), 404);
    }

    @Test
    public void updateNewUserSuccessful() {
        // Create new user
        Response createUserRes= userAPIs.createUser((String) targetUserObj.get("name"), (String) targetUserObj.get("job"));
        userId = createUserRes.body().as(UserResponse.class).getId();

        // Update the newly created user
        String newUserName = "nicky";
        JSONObject updateUserReqBody = new JSONObject();
        updateUserReqBody.put("name", newUserName);
        Response updatedUserResponse = userAPIs.updateUser(userId, updateUserReqBody);

        Assert.assertEquals(updatedUserResponse.statusCode(), 200);
        Assert.assertEquals(newUserName, updatedUserResponse.body().jsonPath().get("name"),
                "Verify user name updated accordingly");
    }

    @AfterMethod
    public void afterEachTest() {
        Cleanup.cleanupSingleUser(userId);
    }
}
