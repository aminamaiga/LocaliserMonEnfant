package fr.umontpellier.localisermonenfant.networks.services;

import fr.umontpellier.localisermonenfant.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserWebService {
    @POST("users/signup")
    Call<User> register(@Body User user);
}
