package userpac;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ChangeProfileDataTest {

    //через rule прописываем before - after действия
    @Rule
    public UserTestRule userTestRule = new UserTestRule();

    private final String email;
    private final String password;

    private final String name;

    public ChangeProfileDataTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters
    public static Object[][] getEditedData(){
        return  new Object[][]{
                {"rustem22356@yandex.ru", "asphalt2201","Катя"}, // тут поменяли все поля
                {"krovli-sarai2211567@mail.ru", "12345qwerty", "Катя"}, //тут поменяли имя
                {"krovli-sarai2211567@mail.ru", "asphalt2201", "Oleg"}, //тут поменяли пароль
                {"rustem22356@yandex.ru", "12345qwerty", "Oleg"},
        };
    }

    @Test
    @DisplayName("Change profile data as authorized user")
    @Description("Path = api/auth/user. StatusCode = 200. Body -")
    public void ChangeProfileAuthorizedTest(){
        // создаем пользователя и забираем токен
        var user = UserReg.someUserData();
        Response response = UserMethods.createUser(user);
        String authToken = UserMethods.getAccessToken(response);
        userTestRule.setAuthToken(authToken); // передаем токен для удаления
        //обновляем данные пользователя
        var editedUser = new UserReg(email, password, name);
        Response response2 = UserMethods.changeProfileData(editedUser, authToken);
        VerifyMethods.verifySuccessfulProfileEdition(response2, email, name);

       UserLogin userLogin = new UserLogin(email, password); // проверяем через логин, что пароль поменялся
        Response response3 = UserMethods.loginUser(userLogin);
        VerifyMethods.verifySuccessfulUserCreationOrLogin(response3, email, name);
    }

    @Test
    @DisplayName("Change profile data as unauthorized user")
    @Description("Path = api/auth/user. StatusCode = 401 Unauthorized .Message = You should be authorised.")
    public void ChangeProfileUnauthorizedTest(){
        var user = UserReg.someUserData();
        Response response = UserMethods.createUser(user);
        String authToken = UserMethods.getAccessToken(response);
        userTestRule.setAuthToken(authToken);

        var user2 = new UserReg(email, password, name);

        Response response2 = UserMethods.changeProfileDataUnauthorized(user2);
        VerifyMethods.verifyErrorWhileUnauthorizedProfileEdition(response2);

    }
}
