package fr.umontpellier.localisermonenfant.networks.businesses;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.io.IOException;

import fr.umontpellier.localisermonenfant.activity.LoginActivity;
import fr.umontpellier.localisermonenfant.models.User;
import fr.umontpellier.localisermonenfant.networks.RetrofitBuilder;
import fr.umontpellier.localisermonenfant.networks.services.UserWebService;
import fr.umontpellier.localisermonenfant.utils.AlertNetworkError;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BussnessServiceUser {
    String TAG = "BussnessServiceUser";
    public Context context;
    Retrofit retrofit;

    public BussnessServiceUser(Context context){
      this.context = context;
    }
    // The call for authentification
    Call<User> authentificateUserCall;
    UserWebService userWebService;

    // Should be called first when using authentificated request
    public void initializeAuthentificatedCommunication(){
        retrofit = RetrofitBuilder.getAuthenticatedClient(context);
        userWebService = retrofit.create(UserWebService.class);
    }

    //  authenticated request
    public void register(User user) {
        //  service add credentials in the header, so just call login
        authentificateUserCall = userWebService.register(user);
        authentificateUserCall.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                    Log.d(TAG, "message " + response.message() + "code " +response.code() );
                    // ok your user is authentificated
                    if (response.isSuccessful()){
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        context.startActivity(intent);
                    } else {
                        try {
                            AlertNetworkError.showNetworkDialog(context, response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                AlertNetworkError.showNetworkDialog(context, t.getMessage());
                Log.e(TAG, t.getMessage() + t.getLocalizedMessage() );
                t.printStackTrace();
            }
        });
    }
}
