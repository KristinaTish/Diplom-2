package orderpac;

import io.restassured.response.Response;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.isEmptyOrNullString;

public class VerifyMethods2 {

    public static void verifySuccessfulOrderCreation(Response response){
        response.then().assertThat().statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .body("name", not(isEmptyOrNullString()))
                .body("order.number", not(isEmptyOrNullString()));
    }

    public static void verifyInvalidIngredientHashWhileOrder(Response response){
        response.then().assertThat().statusCode(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }

    public static void verifyNoIngredientsOrderError(Response response){
        response.then().assertThat().statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    public static void verifySuccessfulIssuingUsersOrders (Response response){
        response.then().assertThat().statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .body("total", not(isEmptyOrNullString()))
                .body("totalToday", not(isEmptyOrNullString()));
    }

    public static void verifyUnathorizedIssuingUsersOrders (Response response){
        response.then().assertThat().statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
