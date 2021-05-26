package fr.umontpellier.localisermonenfant.networks.services;

import java.util.List;

import fr.umontpellier.localisermonenfant.models.Trajet;
import fr.umontpellier.localisermonenfant.models.Zone;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ZoneWebService {
    @POST("zones")
    Call<Trajet> saveZone(@Body Zone zone);

    @GET("zones/parent-and-level")
    Call<List<Zone>> getZones(@Query("parentId") String parentId, @Query("type") int type);

    @GET("zones")
    Call<List<Trajet>> getAllTrajets(@Path("idChild") String idChild);
}
