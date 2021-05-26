package fr.umontpellier.localisermonenfant.networks.businesses;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import fr.umontpellier.localisermonenfant.models.Token;
import fr.umontpellier.localisermonenfant.models.Trajet;
import fr.umontpellier.localisermonenfant.models.Zone;
import fr.umontpellier.localisermonenfant.networks.RetrofitBuilder;
import fr.umontpellier.localisermonenfant.networks.services.TokenWebService;
import fr.umontpellier.localisermonenfant.networks.services.ZoneWebService;
import fr.umontpellier.localisermonenfant.utils.AlertNetworkError;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BussnessServiceToken {
    String TAG = "BussnessServiceToken";
    public Context context;
    Retrofit retrofit;

    public BussnessServiceToken(Context context) {
        this.context = context;
    }

    // The call for authentification
    Call<Token> authentificateUserCall;
    TokenWebService tokenWebService;

    // Should be called first when using authentificated request
    public void initializeAuthentificatedCommunication() {
        retrofit = RetrofitBuilder.getAuthenticatedClient(context);
        tokenWebService = retrofit.create(TokenWebService.class);
    }

    //  authenticated request
    public void save(Token token) {
        //  service add credentials in the header, so just call login
        authentificateUserCall = tokenWebService.saveToken(token);
        authentificateUserCall.enqueue(new Callback<Token>() {

            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                Log.d(TAG, "message " + response.message() + "code " + response.code());
                // ok your user is authentificated
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: token enregistré");
                } else {
                    Log.e(TAG, "onResponse: Erreur " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.e(TAG, t.getMessage() + t.getLocalizedMessage());
                t.printStackTrace();
            }
        });
    }
}
