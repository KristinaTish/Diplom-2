package userpac;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LoginUserTest {

    @Rule
    public UserTestRule userTestRule = new UserTestRule();

    @Test
    @DisplayName("Login as existing user")
    @Description("Path = api/auth/login, response should return 200 ok and body")
    public void LoginAsExistingUserTest(){
        var user = UserReg.random();
        Response response = UserMethods.createUser(user);
        String authToken = UserMethods.getAccessToken(response);
        userTestRule.setAuthToken(authToken);

        String email = user.getEmail();
        String name = user.getName();

        var user2 = UserLogin.from(user);
        Response response2 = UserMethods.loginUser(user2);
        VerifyMethods.verifySuccessfulUserCreationOrLogin(response2, email, name);
    }

    //логин с неверным логином и паролем.

    @Test
    @DisplayName("Login using incorrect login ")
    @Description("Path = api/auth/login, Status Code =  and message: email or password are incorrect ")
    public void loginWithIncorrectLoginTest(){
        var userr = UserReg.random();
        Response response = UserMethods.createUser(userr);
        String authToken = UserMethods.getAccessToken(response);
        userTestRule.setAuthToken(authToken);

        var user2 = UserLogin.wrongmail(userr);
        Response response2 = UserMethods.loginUser(user2);
        VerifyMethods.verifyErrorWhenLoginWithInvalidData(response2);

    }

    @Test
    @DisplayName("Login using incorrect password")
    @Description("Path = api/auth/login, Status Code =  and message: email or password are incorrect ")
    public void loginWithIncorrectPasswordTest(){
        var user = UserReg.random();
        Response response = UserMethods.createUser(user);
        String authToken = UserMethods.getAccessToken(response);
        userTestRule.setAuthToken(authToken);

        var user2 = UserLogin.wrongPassword(user);
        Response response2 = UserMethods.loginUser(user2);
        VerifyMethods.verifyErrorWhenLoginWithInvalidData(response2);

    }
}
