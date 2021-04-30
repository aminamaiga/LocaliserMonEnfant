package fr.umontpellier.localisermonenfant.models.requests;

import android.util.Patterns;

public class UserLogin {
    private String email;
    private String password;
    private Integer code;

    public UserLogin(){
    }

    public UserLogin(String email, String password){
        this.email = email;
        this.password = password;
    }

    public UserLogin(Integer code){
        this.code = code;
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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
    }

    public boolean isPasswordLengthGreaterThan5() {
        return ! (getPassword().length() < 4);
    }
}
