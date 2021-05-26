package fr.umontpellier.localisermonenfant.networks.services;

import java.util.List;

import fr.umontpellier.localisermonenfant.models.Token;
import fr.umontpellier.localisermonenfant.models.Trajet;
import fr.umontpellier.localisermonenfant.models.Zone;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TokenWebService {
    @POST("tokens")
    Call<Token> saveToken(@Body Token token);

    @GET("tokens")
    Call<List<Zone>> getZones(@Query("parentId") String parentId, @Query("type") int type);
}
