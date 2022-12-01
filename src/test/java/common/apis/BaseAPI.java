package common.apis;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public abstract class BaseAPI {

    protected Response get(RequestSpecification spec, String endpoint) {
        RequestSpecification httpRequest = RestAssured.given(spec);
        return httpRequest.when().get(endpoint);
    }

    protected Response post(RequestSpecification spec, String endpoint) {
        RequestSpecification httpRequest = RestAssured.given(spec);
        return httpRequest.when().post(endpoint);
    }

    protected Response put(RequestSpecification spec, String endpoint) {
        RequestSpecification httpRequest = RestAssured.given(spec);
        return httpRequest.when().put(endpoint);
    }

    protected Response patch(RequestSpecification spec, String endpoint) {
        RequestSpecification httpRequest = RestAssured.given();
        return httpRequest.when().patch(endpoint);
    }

    protected Response delete(RequestSpecification spec, String endpoint) {
        RequestSpecification httpRequest = RestAssured.given();
        return httpRequest.when().patch(endpoint);
    }

}
