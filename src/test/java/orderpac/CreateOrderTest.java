package orderpac;

import io.qameta.allure.junit4.DisplayName;

import io.restassured.response.Response;
import jdk.jfr.Description;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import userpac.UserMethods;
import userpac.UserReg;
import userpac.UserTestRule;

import java.util.Arrays;
import java.util.Collection;


@RunWith(Parameterized.class)
public class CreateOrderTest {
    private String[] ingredientList;

    public CreateOrderTest(String[] ingredientList){
      this.ingredientList = ingredientList;
    }

    @Parameterized.Parameters
    public static Collection switchIngredients() {
        return Arrays.asList(new String[][]{{"61c0c5a71d1f82001bdaaa6d"}}, new String[][]{{"61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa70",}}, new String[][]{{"61c0c5a71d1f82001bdaaa75", "61c0c5a71d1f82001bdaaa76", "61c0c5a71d1f82001bdaaa77"}}, new String[][]{{"61c0c5a71d1f82001bdaaa78",
                "61c0c5a71d1f82001bdaaa79", "61c0c5a71d1f82001bdaaa7a"}}, new String[][]{{"61c0c5a71d1f82001bdaaa72", "61c0c5a71d1f82001bdaaa6e"}});
    }


    @Rule
    public UserTestRule userTestRule = new UserTestRule();

    @Test
    @DisplayName("Creating order while being authorized as user")
    @Description("API = api/orders. Response = 200 ok + success, name, order number")
    public void createOrderWithAuthorization(){
     var user = UserReg.random();
     Response response = UserMethods.createUser(user);
     String authToken = UserMethods.getAccessToken(response);
     userTestRule.setAuthToken(authToken);
    SerealCreateOrderRequest order = new SerealCreateOrderRequest(ingredientList);
     Response response2 = OrderMethods.createOrderAuthorized(order, authToken);
     VerifyMethods2.verifySuccessfulOrderCreation(response2);
    }

    @Test
    @DisplayName("Creating order while being unauthorized as user")
    @Description("API = api/orders. Response = 400 + message: You should be authorized")
    public void createOrderWithoutAuthorization(){
        SerealCreateOrderRequest order = new SerealCreateOrderRequest(ingredientList);
        Response response = OrderMethods.createOrderUnauthorized(order);
        VerifyMethods2.verifyUnathorizedIssuingUsersOrders(response);

    }

}
