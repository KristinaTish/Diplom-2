package userpac;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;

import org.junit.Rule;
import org.junit.Test;

public class CreateUserTest {

    @Rule
    public UserTestRule userTestRule = new UserTestRule();

    @Test
    @DisplayName("Create unique user")
    @Description("Path = api/auth/register. Test should return 200 ok ")
    public void createUniqueCourierTest(){
        var userr = UserReg.random();  //генерим данные для пользователя
        Response response = UserMethods.createUser(userr); //создаем юзера
        String authToken = UserMethods.getAccessToken(response); //забираем токен для последующего удаления
        userTestRule.setAuthToken(authToken);

        String email = userr.getEmail();  //вынимаем почту для проверок
        String name = userr.getName(); // вынимаем имя для проверок
        VerifyMethods.verifySuccessfulUserCreationOrLogin(response, email, name); // проверяем статус код и тело ответа на запрос создания пользователя
    }

    @Test
    @DisplayName("Create existing user")
    @Description("Path = api/auth/register. Test should return 403 Forbidden and message: User already exists.")
    public void createExistingUser(){
      var userr = UserReg.random(); // создаем данные пользователя
       Response response = UserMethods.createUser(userr); //создаем пользователя
       String authToken = UserMethods.getAccessToken(response); // забираем токен
        userTestRule.setAuthToken(authToken);

       Response response2 = UserMethods.createUser(userr); //повторно создаем пользователя с теми же данными
       VerifyMethods.verifyErrorWhileCreatingExistingUser(response2); // проверяем, что получаем ошибку
    }

}


