package fr.umontpellier.localisermonenfant.models.responses;

public class UserResponse {
    
    private String userId;
    private String token;

    public UserResponse(){
    }

    public UserResponse(String userId, String token) {
        userId = userId;
        this.token = token;
    }

    public String getId() {
        return userId;
    }

    public void setId(String id) {
        userId = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
