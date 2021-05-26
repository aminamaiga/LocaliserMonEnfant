package fr.umontpellier.localisermonenfant.networks.businesses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import fr.umontpellier.localisermonenfant.activity.MainActivityChild;
import fr.umontpellier.localisermonenfant.R;
import fr.umontpellier.localisermonenfant.fragments.AddChildFragment;
import fr.umontpellier.localisermonenfant.fragments.ChildViewModel;
import fr.umontpellier.localisermonenfant.fragments.UploadPhotoFragment;
import fr.umontpellier.localisermonenfant.fragments.ZoneParameterFragment;
import fr.umontpellier.localisermonenfant.models.Child;
import fr.umontpellier.localisermonenfant.networks.RetrofitBuilder;
import fr.umontpellier.localisermonenfant.networks.services.ChildWebService;
import fr.umontpellier.localisermonenfant.utils.AlertNetworkError;
import fr.umontpellier.localisermonenfant.utils.PrefUserInfos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BusinessServiceChild {
    String TAG = "BusinessServiceChild";
    public Context context;
    Retrofit retrofit;
    public static final String USERDATA = "USERDATA";
    public static final String CHILD = "CHILD";

    public BusinessServiceChild(Context context) {
        this.context = context;
    }

    // The call for authentification
    Call<Child> authentificateUserCall;
    ChildWebService childWebService;

    // Should be called first when using authentificated request
    public void initializeAuthentificatedCommunication() {
        retrofit = RetrofitBuilder.getAuthenticatedClient(context);
        childWebService = retrofit.create(ChildWebService.class);
    }

    //  authenticated request
    public void register(Child child) {
        //  service add credentials in the header, so just call login
        PrefUserInfos prefUserInfos = new PrefUserInfos(context);
        JSONObject userObject = prefUserInfos.loadMap(USERDATA);
        try {
            String userId = userObject.getString("userId");
            child.setParent(userId.replaceAll("\"", ""));
            Log.i(TAG, "register: userObject " + userId.replaceAll("\"", ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        authentificateUserCall = childWebService.register(child);
        authentificateUserCall.enqueue(new Callback<Child>() {
            @Override
            public void onResponse(Call<Child> call, Response<Child> response) {
                Log.d(TAG, "message " + response.message() + "code " + response.code());
                // ok your user is authentificated
                if (response.isSuccessful()) {
                    UploadPhotoFragment fragment = new UploadPhotoFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(CHILD, response.body());
                    fragment.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) context;
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment, "PhotoUpload");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } else {
                    try {
                        AlertNetworkError.showNetworkDialog(context, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Child> call, Throwable t) {
                AlertNetworkError.showNetworkDialog(context, t.getMessage());
                Log.e(TAG, t.getMessage() + t.getLocalizedMessage());
                t.printStackTrace();
            }
        });
    }

    public void findByCode(int code) {
        authentificateUserCall = childWebService.getChildByCode(code);
        authentificateUserCall.enqueue(new Callback<Child>() {
            @Override
            public void onResponse(Call<Child> call, Response<Child> response) {
                Log.d(TAG, "message " + response.message() + "code " + response.code());
                // ok your user is authentificated
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Intent intent = new Intent(context, MainActivityChild.class);
                        Child child = response.body();
                        intent.putExtra("Child", child);
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "Vous n'êtes pas autorisé à utiliser cette application", Toast.LENGTH_LONG).show();
                    }
                } else {
                    try {
                        AlertNetworkError.showNetworkDialog(context, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Child> call, Throwable t) {
                AlertNetworkError.showNetworkDialog(context, t.getMessage());
                Log.e(TAG, t.getMessage() + t.getLocalizedMessage());
                t.printStackTrace();
            }
        });
    }
}
