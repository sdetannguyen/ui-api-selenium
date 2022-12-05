package common.apis;

import common.AutomationConfigs;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class UserAPIs extends LoginAPIs {

    private static final String GET_USER_ENDPOINT = "/api/users/%d";
    private static final String CREATE_USER_ENDPOINT = "/api/users";
    private static final String UPDATE_USER_ENDPOINT = "/api/users/%d";
    private static final String DELETE_USER_ENDPOINT = "/api/users/%d";
    private static String token;

    public UserAPIs() {
        RestAssured.baseURI = AutomationConfigs.getInstance().getConfigs().getProperty("application.api.baseUrl");
        token = loginAndStoreToken(AutomationConfigs.getInstance().getConfigs().getProperty("application.testUser1.username"),
                AutomationConfigs.getInstance().getConfigs().getProperty("application.testUser1.password"));
    }

    public Response getUserById(int userId) {
        RequestSpecification customSpec = RestAssured.given();
        customSpec.header("Content-Type", ContentType.JSON);
        customSpec.header("token", token);
        return get(customSpec, String.format(GET_USER_ENDPOINT, userId));
    }

    public Response createUser(String name, String job) {
        JSONObject bodyAsJson = new JSONObject();
        bodyAsJson.put("name", name);
        bodyAsJson.put("job", job);
        RequestSpecification customSpec = RestAssured.given();
        customSpec.header("Content-Type", ContentType.JSON);
        customSpec.header("token", token);
        customSpec.body(bodyAsJson.toJSONString());
        return post(customSpec, CREATE_USER_ENDPOINT);
    }

    public Response updateUser(int userId, JSONObject body) {
        RequestSpecification customSpec = RestAssured.given();
        customSpec.header("Content-Type", ContentType.JSON);
        customSpec.header("token", token);
        customSpec.body(body.toJSONString());
        return put(customSpec, String.format(UPDATE_USER_ENDPOINT, userId));
    }

    public Response deleteUser(int userId) {
        RequestSpecification customSpec = RestAssured.given();
        customSpec.header("Content-Type", ContentType.JSON);
        customSpec.header("token", token);
        return delete(customSpec, String.format(DELETE_USER_ENDPOINT, userId));
    }
}
