package fr.umontpellier.localisermonenfant.fragments;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;
import java.util.List;

import fr.umontpellier.localisermonenfant.models.Zone;
import fr.umontpellier.localisermonenfant.networks.RetrofitBuilder;
import fr.umontpellier.localisermonenfant.networks.services.ZoneWebService;
import fr.umontpellier.localisermonenfant.utils.AlertNetworkError;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ZoneParameterViewModel extends ViewModel {
    MutableLiveData<List<Zone>> zoneMutableLiveData;
    Context context;
    int zoneLevel;
    String parentId;
    private static final String TAG = "ZoneParameterViewModel";
    Call<List<Zone>> authentificateUserCall2;

    public ZoneParameterViewModel(Context context, String parentId, int zoneLevel) {
        this.context = context;
        zoneMutableLiveData = new MutableLiveData<>();
        this.zoneLevel = zoneLevel;
        this.parentId = parentId;
        findByParentIdAndLevel(parentId, zoneLevel);
    }

    public MutableLiveData<List<Zone>> getZoneMutableLiveData() {
        return zoneMutableLiveData;
    }


    public void findByParentIdAndLevel(String parentId, int level) {
        Retrofit retrofit = RetrofitBuilder.getAuthenticatedClient(context);
        ZoneWebService zoneWebService = retrofit.create(ZoneWebService.class);
        authentificateUserCall2 = zoneWebService.getZones(parentId, level);
        authentificateUserCall2.enqueue(new Callback<List<Zone>>() {
            @Override
            public void onResponse(Call<List<Zone>> call, Response<List<Zone>> response) {
                Log.d(TAG, "message " + response.message() + "code " + response.code());
                // ok your user is authentificated
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        List<Zone> zones = response.body();
                        zoneMutableLiveData.setValue(zones);
                        Log.d(TAG, "onResponse: " + zones.size());
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
            public void onFailure(Call<List<Zone>> call, Throwable t) {
                AlertNetworkError.showNetworkDialog(context, t.getMessage());
                Log.e(TAG, t.getMessage() + t.getLocalizedMessage());
                t.printStackTrace();
            }
        });
    }
}

class ViewModelFactoryParameter implements ViewModelProvider.Factory {
    private Context context;
    private String parentId;
    private int level_zone;

    public ViewModelFactoryParameter(Context context, String parentId, int level_zone) {
        this.context = context;
        this.parentId = parentId;
        this.level_zone = level_zone;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ZoneParameterViewModel.class)) {
            return (T) new ZoneParameterViewModel(context, parentId, level_zone);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}