package orderpac;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

import io.restassured.response.Response;

import org.junit.Rule;
import org.junit.Test;
import userpac.UserMethods;
import userpac.UserReg;
import userpac.UserTestRule;

public class GetConcreteUsersOrdersTest {
    //Получение заказов конкретного пользователя:
    //авторизованный пользователь,
    //неавторизованный пользователь.
    @Rule
    public UserTestRule userTestRule = new UserTestRule();

    @Test
    @DisplayName("Test to obtain orders of one concrete user while being authorized")
    @Description("API = api/orders. Response - 200 ")
    public void getUsersOrdersAuthorized(){
        var user = UserReg.random();
        Response response = UserMethods.createUser(user);
        String authToken = UserMethods.getAccessToken(response);
        userTestRule.setAuthToken(authToken);

        var request = SerealCreateOrderRequest.someIngreds();
        OrderMethods.createOrderAuthorized(request, authToken);

        Response response2 = OrderMethods.getUsersOrders(authToken);
        VerifyMethods2.verifySuccessfulIssuingUsersOrders(response2);
    }

    @Test
    @DisplayName("Test to obtain orders of one concrete user while being unauthorized")
    @Description("API = api/orders. Response - 401 You should be authorized")
    public void getUsersOrdersUnauthorized(){
        var user = UserReg.random();
        Response response = UserMethods.createUser(user);
        String authToken = UserMethods.getAccessToken(response);
        userTestRule.setAuthToken(authToken);

        var request = SerealCreateOrderRequest.someIngreds();
        OrderMethods.createOrderAuthorized(request, authToken);

        Response response2 = OrderMethods.getUsersOrdersUnauthorized();
        VerifyMethods2.verifyUnathorizedIssuingUsersOrders(response2);
    }

}
