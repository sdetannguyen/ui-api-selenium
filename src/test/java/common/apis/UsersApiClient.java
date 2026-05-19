package common.apis;

import common.AutomationConfigs;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class UsersApiClient extends BaseAPIs {

    private static final String USERS_ENDPOINT = "/users";
    private static final String USER_BY_ID_ENDPOINT = "/users/%d";

    public UsersApiClient() {
        RestAssured.baseURI = AutomationConfigs.getInstance().getConfigs()
                .getProperty("application.api.baseUrl");
    }

    public Response listUsers() {
        RequestSpecification spec = RestAssured.given()
                .contentType(ContentType.JSON);
        return get(spec, USERS_ENDPOINT);
    }

    public Response getUserById(int userId) {
        RequestSpecification spec = RestAssured.given()
                .contentType(ContentType.JSON);
        return get(spec, String.format(USER_BY_ID_ENDPOINT, userId));
    }

    public Response createUser(String name, String job) {
        JSONObject body = new JSONObject();
        body.put("name", name);
        body.put("job", job);
        RequestSpecification spec = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body.toJSONString());
        return post(spec, USERS_ENDPOINT);
    }

    public Response deleteUser(int userId) {
        RequestSpecification spec = RestAssured.given()
                .contentType(ContentType.JSON);
        return delete(spec, String.format(USER_BY_ID_ENDPOINT, userId));
    }
}
