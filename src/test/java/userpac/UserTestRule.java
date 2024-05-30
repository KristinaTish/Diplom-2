package userpac;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.rules.ExternalResource;


// В этом классе мы переопределяем before и after, которые потом в каждом тесте через Rule вызываются
public class UserTestRule extends ExternalResource {
    private String authToken;

    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }

    @Override
    protected void before(){
        RestAssured.baseURI = UserMethods.STELLAR_URL;
    }

    @Override
    protected void after(){
        if(authToken != null && !authToken.isEmpty()){
            Response response2 = UserMethods.deleteUser(authToken);
            VerifyMethods.verifySuccessfulDeletion(response2);
        }
    }
}
