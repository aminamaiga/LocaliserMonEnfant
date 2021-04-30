package fr.umontpellier.localisermonenfant.networks.auth;

import fr.umontpellier.localisermonenfant.models.requests.UserLogin;
import fr.umontpellier.localisermonenfant.models.responses.UserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginWebService {
    @POST("users/signin")
    Call<UserResponse> login(@Body UserLogin login);
}
