package fr.umontpellier.localisermonenfant.networks.businesses;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import fr.umontpellier.localisermonenfant.models.Trajet;
import fr.umontpellier.localisermonenfant.models.Zone;
import fr.umontpellier.localisermonenfant.networks.RetrofitBuilder;
import fr.umontpellier.localisermonenfant.networks.services.TrajetWebService;
import fr.umontpellier.localisermonenfant.networks.services.ZoneWebService;
import fr.umontpellier.localisermonenfant.utils.AlertNetworkError;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BussnessServiceZone {
    String TAG = "BussnessServiceTrajet";
    public Context context;
    Retrofit retrofit;

    public BussnessServiceZone(Context context) {
        this.context = context;
    }

    // The call for authentification
    Call<Trajet> authentificateUserCall;
    ZoneWebService zoneWebService;

    // Should be called first when using authentificated request
    public void initializeAuthentificatedCommunication() {
        retrofit = RetrofitBuilder.getAuthenticatedClient(context);
        zoneWebService = retrofit.create(ZoneWebService.class);
    }

    //  authenticated request
    public void save(Zone zone) {
        //  service add credentials in the header, so just call login
        authentificateUserCall = zoneWebService.saveZone(zone);
        authentificateUserCall.enqueue(new Callback<Trajet>() {

            @Override
            public void onResponse(Call<Trajet> call, Response<Trajet> response) {
                Log.d(TAG, "message " + response.message() + "code " + response.code());
                // ok your user is authentificated
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Zone enregistrée", Toast.LENGTH_LONG).show();
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
                Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }
}
