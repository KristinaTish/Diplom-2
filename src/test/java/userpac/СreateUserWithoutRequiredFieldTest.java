package userpac;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class СreateUserWithoutRequiredFieldTest {

    private final String email;
    private final String password;
    private final String name;

    public СreateUserWithoutRequiredFieldTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters
    public static Object[][] getNewRegData(){
        return  new Object[][]{
                {"", "qwerty123","Катя"}, // без почты
                {null, "qwerty123","Катя"},
                {"rustem22356@yandex.ru", "qwerty123", ""}, // без пароля
                {"rustem22356@yandex.ru", "qwerty123", null},
                {"sabaka1998@gmail.com", "qwerty123", ""}, // без имени
                {"sabaka1998@gmail.com", "qwerty123", null},
        };
    }


    @Rule
    public UserTestRule userTestRule = new UserTestRule();

    @Test
    @DisplayName("Create user without email")
    @Description("Path = api/auth/register. Test should return 403 Forbidden and message: Email, password and name are required fields")
    public void createUserWithoutRequiredField(){
        var user = new UserReg(email, password, name);
        Response response = UserMethods.createUser(user);
       String authToken = UserMethods.getAccessToken(response);
       userTestRule.setAuthToken(authToken);

        VerifyMethods.verifyErrorWhileCreatingUserWithoutRequiredField(response);
    }
}
