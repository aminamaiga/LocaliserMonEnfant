package fr.umontpellier.localisermonenfant.networks.services;

import java.util.List;

import fr.umontpellier.localisermonenfant.models.Child;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ChildWebService {
    @GET("childs/1")
   public Call<Child> getChildOne();

    @GET("childs/{id}")
   public Call<Child> getChildById(@Path("id") int id);

    @GET("childs")
    public Call<List<Child>> getChildList();
}
