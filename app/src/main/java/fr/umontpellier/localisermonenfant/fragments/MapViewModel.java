package fr.umontpellier.localisermonenfant.fragments;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;
import java.util.List;

import fr.umontpellier.localisermonenfant.models.Child;
import fr.umontpellier.localisermonenfant.models.Trajet;
import fr.umontpellier.localisermonenfant.networks.RetrofitBuilder;
import fr.umontpellier.localisermonenfant.networks.services.TrajetWebService;
import fr.umontpellier.localisermonenfant.utils.AlertNetworkError;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MapViewModel extends ViewModel {

    public MutableLiveData<Trajet> trajetMutableLiveDate;
    Context context;
    String TAG = "MapViewModel";
    Call<Trajet> authentificateUserCall2;
    TrajetWebService trajetWebService;
    public MutableLiveData<String> childId;
    public MutableLiveData<List<Trajet>> listTrajets;
    MutableLiveData<Child> clickedChild = new MutableLiveData<>();
    public MutableLiveData<Integer> postion = new MutableLiveData<>();

    public MapViewModel(Context context) {
        trajetMutableLiveDate = new MutableLiveData<>();
        this.context = context;
        childId = new MutableLiveData<>();
        listTrajets = new MutableLiveData<>();
    }

    public MutableLiveData<Trajet> getLocation() {
        return trajetMutableLiveDate;
    }

    public MutableLiveData<String> getChildId() {
        return childId;
    }

    public MutableLiveData<List<Trajet>> getAllTrajet(){
        return listTrajets;
    }

    //  authenticated request
    public void getLastTrajetByChildId(String childId) {
        //  service add credentials in the header, so just call login
        Retrofit retrofit = RetrofitBuilder.getAuthenticatedClient(context);
        trajetWebService = retrofit.create(TrajetWebService.class);
        authentificateUserCall2 = trajetWebService.getLastTrajet(childId);
        authentificateUserCall2.enqueue(new Callback<Trajet>() {

            @Override
            public void onResponse(Call<Trajet> call, Response<Trajet> response) {
                Log.d(TAG, "message " + response.message() + "code " + response.code());
                // ok your user is authentificated
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Position modifiée", Toast.LENGTH_LONG).show();
                    trajetMutableLiveDate.setValue(response.body());
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

    public void getAlltrajet(String childId) {
        //  service add credentials in the header, so just call login
        Retrofit retrofit = RetrofitBuilder.getAuthenticatedClient(context);
        trajetWebService = retrofit.create(TrajetWebService.class);
        Call<List<Trajet>> authentificateUserCall2 = trajetWebService.getAllTrajets(childId);
        authentificateUserCall2.enqueue(new Callback<List<Trajet>>() {

            @Override
            public void onResponse(Call<List<Trajet>> call, Response<List<Trajet>> response) {
                Log.d(TAG, "message " + response.message() + "code " + response.code());
                // ok your user is authentificated
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Position modifiée", Toast.LENGTH_LONG).show();
                    listTrajets.setValue(response.body());
                } else {
                    try {
                        AlertNetworkError.showNetworkDialog(context, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Trajet>> call, Throwable t) {
                AlertNetworkError.showNetworkDialog(context, t.getMessage());
                Log.e(TAG, t.getMessage() + t.getLocalizedMessage());
                t.printStackTrace();
            }
        });
    }
}

class ViewModelFactoryMap implements ViewModelProvider.Factory {
    private Context context;

    public ViewModelFactoryMap(Context context) {
        this.context = context;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MapViewModel.class)) {
            return (T) new MapViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}