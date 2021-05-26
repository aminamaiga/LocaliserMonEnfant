package fr.umontpellier.localisermonenfant.networks.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fr.umontpellier.localisermonenfant.GlobalClass;
import fr.umontpellier.localisermonenfant.activity.HomeActivity;
import fr.umontpellier.localisermonenfant.models.requests.UserLogin;
import fr.umontpellier.localisermonenfant.models.responses.UserResponse;
import fr.umontpellier.localisermonenfant.networks.RetrofitBuilder;
import fr.umontpellier.localisermonenfant.utils.AlertNetworkError;
import fr.umontpellier.localisermonenfant.utils.PrefUserInfos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BussnessServiceLogin {
    String TAG = "BussnessServiceLogin";
    public Context context;
    Retrofit retrofit;

    public BussnessServiceLogin(Context context){
        this.context = context;
    }

    // The call for authentification
    Call<UserResponse> authentificateUserCall;
    LoginWebService webServiceAuthenticated;

    // The authenticated user
    UserResponse currentUser;
    // Should be called first when using authentificated request
    public void initializeAuthentificatedCommunication(Context ctx){
       retrofit = RetrofitBuilder.getAuthenticatedClient(context);
        webServiceAuthenticated = retrofit.create(LoginWebService.class);
    }

    //  authenticated request
    public void login(UserLogin user) {
        //  service add credentials in the header, so just call login
        authentificateUserCall = webServiceAuthenticated.login(user);
        authentificateUserCall.enqueue(new Callback<UserResponse>() {

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if(response.body()!=null){
                    Log.d(TAG, "message " + response.message() + "code " +response.code() );
                    Log.d(TAG, response.body().getId() + " " +response.body().getToken());

                    // ok your user is authentificated
                    currentUser = response.body();
                    if (response.isSuccessful()){
                        final GlobalClass globalVariable = (GlobalClass) context.getApplicationContext();
                        SharedPreferences settings = context.getSharedPreferences(globalVariable.PREFS_LOGIN, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        //save token in local storage
                        editor.putString("TOKEN", currentUser.getToken());
                        editor.commit();

                        //
                        Algorithm algorithmHS = Algorithm.HMAC256("RANDOM_TOKEN_SECRET");
                        try {
                            Map<String, String> userData = new HashMap<>();
                            JWTVerifier verifier = JWT.require(algorithmHS).build();
                            DecodedJWT jwt = verifier.verify(currentUser.getToken());
                            Map<String, Claim> claims = jwt.getClaims();    //Key is the Claim name
                            for (Map.Entry<String, Claim> claim: claims.entrySet()) {
                                userData.put(claim.getKey(), claim.getValue().toString());
                                Log.d(TAG, "claim " + claim.getKey() + " " +claim.getValue().asString());
                            }
                            PrefUserInfos prefUserInfos = new PrefUserInfos(context);
                            prefUserInfos.saveMap(userData);
                        }catch (JWTVerificationException exception){
                            Log.e(TAG, "========== Exception " + exception.getMessage());
                        }

                        Intent intent = new Intent(context, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        context.startActivity(intent);
                    }
                }else{
                    try {
                        AlertNetworkError.showNetworkDialog(context, response.errorBody().string() + "code: " +response.code());
                    } catch (IOException e) {
                       Log.e(TAG, e.getMessage());
                    }
                }
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                AlertNetworkError.showNetworkDialog(context, t.getMessage());
                Log.e(TAG, t.getMessage() + t.getLocalizedMessage() );
                t.printStackTrace();
            }
        });
    }
}
