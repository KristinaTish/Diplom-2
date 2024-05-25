package userpac;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateUserTest {
    public String authToken;

    @Before
    public void setUp(){
        RestAssured.baseURI = UserMethods.STELLAR_URL;
    }

    @After
    public void coverItUp(){
        if(authToken != null && !authToken.isEmpty()){
            Response response2 = UserMethods.deleteUser(authToken);
            VerifyMethods.verifySuccessfulDeletion(response2);
        }
    }

    @Test
    @DisplayName("Create unique user")
    @Description("Path = api/auth/register. Test should return 200 ok and body")
    public void createUniqueCourierTest(){
        var userr = UserReg.random();
        Response response = UserMethods.createUser(userr);
        authToken = UserMethods.getAccessToken(response);
        System.out.println(authToken);
//        String email = userr.getEmail();
//        String name = userr.getName();
//        VerifyMethods.verifySuccessfulUserCreation(response, email, name);

//        UserLogin user2 = UserLogin.from(user);
//        authToken = VerifyMethods.getAccessToken(user2);

    }

//    @Test
//    @DisplayName("Create existing user")
//    @Description("Path = api/auth/register. Test should return 403 Forbidden and message: User already exists")
//    public void createExistingUser(){
//      var user = UserReg.random();
//      Response response = UserMethods.createUser(user);
//
//    }
}


