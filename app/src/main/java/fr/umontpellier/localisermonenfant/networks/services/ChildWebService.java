package fr.umontpellier.localisermonenfant.networks.services;

import java.util.List;
import fr.umontpellier.localisermonenfant.models.Child;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ChildWebService {
    @POST("childs")
    Call<Child> register(@Body Child child);

    @GET("childs/1")
   public Call<Child> getChildOne();

    @GET("childs/child/{code}")
   public Call<Child> getChildByCode(@Path("code") int code);

    @GET("childs/{parentId}")
    public Call<List<Child>> getChildList(@Path("parentId") String parentId);

    @POST("childs/upload")
    public Call<Child> uploadPhoto(@Path("childId") String childId, @Part("image\"; filename=\"photo.jpg\" ") RequestBody file);
}
