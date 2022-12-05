package common.apis;

import common.AutomationConfigs;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class LoginAPIs extends BaseAPIs {

    private static final String GET_USER_ENDPOINT = "/api/login";

    public LoginAPIs() {
        RestAssured.baseURI = AutomationConfigs.getInstance().getConfigs().getProperty("application.api.baseUrl");
    }

    public String loginAndStoreToken(String username, String password) {
        JSONObject bodyAsJson = new JSONObject();
        bodyAsJson.put("username", username);
        bodyAsJson.put("password", password);
        RequestSpecification customSpec = RestAssured.given();
        customSpec.header("Content-Type", ContentType.JSON);
        customSpec.body(bodyAsJson.toJSONString());
        return post(customSpec, GET_USER_ENDPOINT).then().extract().body().jsonPath().get("token");
    }

}
