package userpac;

import io.restassured.response.Response;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.isEmptyOrNullString;

public class VerifyMethods {
    //здесь проверочные тесты для сверки кода ответа и тела
    public static void verifySuccessfulUserCreationOrLogin(Response response, String email, String name) {
        response.then().assertThat().statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(email))
                .body("user.name", equalTo(name))
                .body("accessToken", containsString("Bearer"))
                .body("refreshToken", not(isEmptyOrNullString()));
    }

    public static void verifyErrorWhileCreatingExistingUser(Response response) {
        response.then().assertThat().statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }

    public static void verifyErrorWhileCreatingUserWithoutRequiredField(Response response) {
        response.then().assertThat().statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    public static void verifySuccessfulDeletion(Response response) {
        response.then().assertThat().statusCode(HttpURLConnection.HTTP_ACCEPTED);
    }

    public static void verifyErrorWhenLoginWithInvalidData(Response response) {
        response.then().assertThat().statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }

    public static void verifySuccessfulProfileEdition(Response response, String email, String name) {
        response.then().assertThat().statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(email))
                .body("user.name", equalTo(name));
    }

    public static void verifyErrorWhileUnauthorizedProfileEdition(Response response) {
        response.then().assertThat().statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

}
