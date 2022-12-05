package common.apis;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public abstract class BaseAPIs {

    protected Response get(RequestSpecification spec, String endpoint) {
        RequestSpecification httpRequest = RestAssured.given(spec);
        Response response = httpRequest.when().get(endpoint);
        response.then().log().all();
        return response;
    }

    protected Response post(RequestSpecification spec, String endpoint) {
        RequestSpecification httpRequest = RestAssured.given(spec);
        Response response = httpRequest.when().post(endpoint);
        response.then().log().all();
        return response;
    }

    protected Response put(RequestSpecification spec, String endpoint) {
        RequestSpecification httpRequest = RestAssured.given(spec);
        Response response = httpRequest.when().put(endpoint);
        response.then().log().all();
        return response;
    }

    protected Response patch(RequestSpecification spec, String endpoint) {
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.when().patch(endpoint);
        response.then().log().all();
        return response;
    }

    protected Response delete(RequestSpecification spec, String endpoint) {
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.when().patch(endpoint);
        response.then().log().all();
        return response;
    }

}
