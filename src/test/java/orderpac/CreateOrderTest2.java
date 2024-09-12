package orderpac;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

import io.restassured.response.Response;
import org.junit.Rule;
import org.junit.Test;

import userpac.UserMethods;
import userpac.UserReg;
import userpac.UserTestRule;

public class CreateOrderTest2 {

    @Rule
    public UserTestRule userTestRule = new UserTestRule();

    @Test
    @DisplayName("Creating order without ingredients authorized")
    @Description("API = api/orders. Response = 401 + message: Ingredient ids must be provided")
    public void createOrderWithoutIngredients(){
        var user = UserReg.random();
        Response response = UserMethods.createUser(user);
        String authToken = UserMethods.getAccessToken(response);
        userTestRule.setAuthToken(authToken);

        var order = new SerealCreateOrderRequest(null);
        Response response2 = OrderMethods.createOrderAuthorized(order, authToken);
        VerifyMethods2.verifyNoIngredientsOrderError(response2);
    }

    @Test
    @DisplayName("Creating order without ingredients unauthorized")
    @Description("API = api/orders. Response = 401 + message: Ingredient ids must be provided")
    public void createOrderWithoutIngredientsUnauth(){
        var order = new SerealCreateOrderRequest(null);
        Response response2 = OrderMethods.createOrderUnauthorized(order);
        VerifyMethods2.verifyUnathorizedIssuingUsersOrders(response2);
    }

    @Test
    @DisplayName("Creating order with wrong ingredient hash authorized")
    @Description("API = api/orders. Response = 500")
    public void createOrderWithWrongIngredientHash(){
    String [] wrongIngredients = {"61c0c5a71d1f82001bda6d", "61c0c5a71d1f82001bda72"};
        var user = UserReg.random();
        Response response = UserMethods.createUser(user);
        String authToken = UserMethods.getAccessToken(response);
        userTestRule.setAuthToken(authToken);

        var order = new SerealCreateOrderRequest(wrongIngredients);
        Response response2 = OrderMethods.createOrderAuthorized(order, authToken);
        VerifyMethods2.verifyInvalidIngredientHashWhileOrder(response2);
    }

    @Test
    @DisplayName("Creating order with wrong ingredient hash unauthorized")
    @Description("API = api/orders. Response = 500")
    public void createOrderWithWrongIngredientHashUnauth(){
        String [] wrongIngredients = {"61c0c5a71d1f82001bda6d", "61c0c5a71d1f82001bda72"};
        var order = new SerealCreateOrderRequest(wrongIngredients);
        Response response2 = OrderMethods.createOrderUnauthorized(order);
        VerifyMethods2.verifyUnathorizedIssuingUsersOrders(response2);
    }
}
