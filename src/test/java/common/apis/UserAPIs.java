package common.apis;

import common.AutomationConfigs;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class UserAPIs extends BaseAPI {

    private static final String CREATE_USER_ENDPOINT = "/api/users";

    public UserAPIs() {
        RestAssured.baseURI = AutomationConfigs.getInstance().getConfigs().getProperty("application.baseUrl");
    }

    public Response createUser(String name, String job) {
        JSONObject bodyAsJson = new JSONObject();
        bodyAsJson.put("name", name);
        bodyAsJson.put("job", job);
        RequestSpecification customSpec = RestAssured.given();
        customSpec.header("Content-Type", "application/json");
        customSpec.body(bodyAsJson.toJSONString());
        return post(customSpec, CREATE_USER_ENDPOINT);
    }

}
