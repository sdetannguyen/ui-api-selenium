package api_test;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UserManagementTest extends BaseTest {

    @BeforeMethod
    public void beforeEachTest() {

    }

    @Test
    public void createNewUserSuccessful() {
        String name = "assessmentUserName";
        String job = "qaAuto";
        Response response = userAPIs.createUser(name, job);
        Assert.assertEquals(response.statusCode(), 201);
    }

    @AfterMethod
    public void afterEachTest() {

    }
}
