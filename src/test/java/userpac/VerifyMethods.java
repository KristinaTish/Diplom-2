package userpac;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static userpac.UserMethods.PATH_LOGIN_USER;

public class VerifyMethods {
    //здесь проверочные тесты для сверки кода ответа и тела
    public static void verifySuccessfulUserCreation(Response response, String email, String name){
        response.then().assertThat().statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(email))
                .body("user.name", equalTo(name))
                .body("accessToken", containsString("Bearer"))
                .body("refreshToken", not(isEmptyOrNullString()));
    }


    public static void verifySuccessfulDeletion(Response response){
        response.then().assertThat().statusCode(HttpURLConnection.HTTP_ACCEPTED);
    }
}
