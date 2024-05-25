package userpac;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserMethods {
    static final String STELLAR_URL = "https://stellarburgers.nomoreparties.site/";
    static final String  PATH_CREATE_USER = "api/auth/register";
    static final String  PATH_LOGIN_USER = "api/auth/login";
    static  final String PATH_DELETE_CHANGE_USER = "api/auth/user";


    static Response createUser(UserReg user){
        Response response = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(PATH_CREATE_USER);
        return response;
    }

    static Response loginUser(UserLogin user2){
        Response response = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(user2)
                .when()
                .post(PATH_LOGIN_USER);
        return response;
    }

    static Response deleteUser (String token){
        Response response = given()
                .log().all()
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .delete(PATH_DELETE_CHANGE_USER);
        return  response;
    }

    static Response changeUserData (User user, String token){
        Response response = given()
                .log().all()
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(user)
                .when()
                .patch(PATH_DELETE_CHANGE_USER);
        return  response;
    }

    public static String getAccessToken (Response response){ //UserLogin user2
        DeserealLoginResponse r2 = response.body()
                .as(DeserealLoginResponse.class);
//        response.as(DeserealLoginResponse.class);
        String token = r2.getAccessToken();
        return token;
//        DeserealLoginResponse response = given()
//                .log().all()
//                .contentType(ContentType.JSON)
//                .body(user2)
//                .when()
//                .post(PATH_LOGIN_USER)
//                .body().as(DeserealLoginResponse.class);
  //      return response.getAccessToken();
    }
}
