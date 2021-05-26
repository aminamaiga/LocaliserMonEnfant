package fr.umontpellier.localisermonenfant.networks.businesses;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import fr.umontpellier.localisermonenfant.models.Trajet;
import fr.umontpellier.localisermonenfant.networks.RetrofitBuilder;
import fr.umontpellier.localisermonenfant.networks.services.TrajetWebService;
import fr.umontpellier.localisermonenfant.utils.AlertNetworkError;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BussnessServiceTrajet {
    String TAG = "BussnessServiceTrajet";
    public Context context;
    Retrofit retrofit;

    public BussnessServiceTrajet(Context context) {
        this.context = context;
    }

    // The call for authentification
    Call<Trajet> authentificateUserCall;
    TrajetWebService trajetWebService;

    // Should be called first when using authentificated request
    public void initializeAuthentificatedCommunication() {
        retrofit = RetrofitBuilder.getAuthenticatedClient(context);
        trajetWebService = retrofit.create(TrajetWebService.class);
    }

    //  authenticated request
    public void save(Trajet trajet) {
        //  service add credentials in the header, so just call login
        authentificateUserCall = trajetWebService.saveTrajet(trajet);
        authentificateUserCall.enqueue(new Callback<Trajet>() {

            @Override
            public void onResponse(Call<Trajet> call, Response<Trajet> response) {
                Log.d(TAG, "message " + response.message() + "code " + response.code());
                // ok your user is authentificated
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Position modifiée", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        AlertNetworkError.showNetworkDialog(context, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Trajet> call, Throwable t) {
                AlertNetworkError.showNetworkDialog(context, t.getMessage());
                Log.e(TAG, t.getMessage() + t.getLocalizedMessage());
                t.printStackTrace();
            }
        });
    }
}
