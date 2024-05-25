package userpac;

public class UserLogin {

    private String email;
    private String password;

    public static UserLogin from (UserReg user){
        return new UserLogin(user.getEmail(), user.getPassword());
    }

    public UserLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
