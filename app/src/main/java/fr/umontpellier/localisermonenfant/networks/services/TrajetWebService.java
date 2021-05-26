package fr.umontpellier.localisermonenfant.networks.services;

import java.util.List;

import fr.umontpellier.localisermonenfant.models.Trajet;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TrajetWebService {
    @POST("trajets")
    Call<Trajet> saveTrajet(@Body Trajet trajet);

    @GET("trajets/last/{idChild}")
    Call<Trajet> getLastTrajet(@Path("idChild") String idChild);

    @GET("trajets/{idChild}")
    Call<List<Trajet>> getAllTrajets(@Path("idChild") String idChild);
}
