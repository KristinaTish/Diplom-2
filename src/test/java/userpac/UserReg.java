package userpac;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Locale;

public class UserReg {

    // это для тела JSON
    private String email;
    private String password;
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserReg(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }
     // создаем юзера с каким-то почт ящиком
    public static UserReg random(){
        return new UserReg((RandomStringUtils.randomAlphabetic(5, 10)+"@mail.ru").toLowerCase(), "qwerty1234", "Ruslan");
    }

    public static UserReg someUserData(){
        return new UserReg("krovli-sarai2211567@mail.ru", "12345qwerty", "Oleg");
    }

}
