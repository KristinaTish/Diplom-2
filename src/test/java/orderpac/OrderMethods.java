package orderpac;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderMethods {
    static final String STELLAR_URL = "https://stellarburgers.nomoreparties.site/";
    static final String CREATE_ORDER_PATH = "api/orders";


    public static Response createOrderUnauthorized(SerealCreateOrderRequest request) {
        Response response = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(CREATE_ORDER_PATH);
        return response;
    }

    public static Response createOrderAuthorized(SerealCreateOrderRequest request, String token) {
        Response response = given()
                .log().all()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(CREATE_ORDER_PATH);
        return response;
    }

    public static Response getUsersOrders(String token) {
        Response response = given()
                .log().all()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .get(CREATE_ORDER_PATH);
        return response;
    }

    public static Response getUsersOrdersUnauthorized() {
        Response response = given()
                .log().all()
                .contentType(ContentType.JSON)
                .get(CREATE_ORDER_PATH);
        return response;
    }
}