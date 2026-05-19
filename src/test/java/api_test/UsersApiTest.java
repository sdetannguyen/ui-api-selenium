package api_test;

import common.utils.JsonUtils;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pojos.JsonPlaceholderUser;
import pojos.UserResponse;

public class UsersApiTest extends BaseTest {

    private JSONObject testUserData;

    @BeforeMethod
    public void loadTestData() {
        testUserData = JsonUtils.convertFileToJsonObject(
                String.format("src/test/java/data/%s/users.json", env));
    }

    @Test
    public void listUsersReturnsNonEmptyList() {
        Response response = usersApiClient.listUsers();

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertTrue(response.jsonPath().getList("$").size() > 0,
                "User list should not be empty");
    }

    @Test
    public void getUserByIdReturnsCorrectUser() {
        Response response = usersApiClient.getUserById(2);
        JsonPlaceholderUser user = response.body().as(JsonPlaceholderUser.class);

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(user.getId(), 2, "User id should match requested id");
        Assert.assertNotNull(user.getEmail(), "User email should not be null");
        Assert.assertNotNull(user.getName(), "User name should not be null");
    }

    @Test
    public void createUserReturnsCreatedResource() {
        String name = (String) testUserData.get("name");
        String job = (String) testUserData.get("job");

        Response response = usersApiClient.createUser(name, job);
        UserResponse created = response.body().as(UserResponse.class);

        Assert.assertEquals(response.statusCode(), 201);
        Assert.assertEquals(created.getName(), name, "Name should match request body");
        Assert.assertEquals(created.getJob(), job, "Job should match request body");
        Assert.assertTrue(created.getId() > 0, "Created user should have an id");
    }

    @Test
    public void deleteUserReturns200() {
        Response response = usersApiClient.deleteUser(2);

        Assert.assertEquals(response.statusCode(), 200);
    }
}
