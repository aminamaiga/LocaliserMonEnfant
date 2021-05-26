package fr.umontpellier.localisermonenfant.fragments;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import fr.umontpellier.localisermonenfant.models.Child;
import fr.umontpellier.localisermonenfant.networks.RetrofitBuilder;
import fr.umontpellier.localisermonenfant.networks.services.ChildWebService;
import fr.umontpellier.localisermonenfant.utils.AlertNetworkError;
import fr.umontpellier.localisermonenfant.utils.PrefUserInfos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static fr.umontpellier.localisermonenfant.utils.PrefUserInfos.USERDATA;

public class ChildViewModel extends ViewModel {
    String TAG = "ChildViewModel";
    Call<List<Child>> authentificateUserCall2;
    ChildWebService childWebService;
    public MutableLiveData<List<Child>> childMutableLiveDate;
    Context context;

    public ChildViewModel(Context context) {
        this.context =context;
        childMutableLiveDate = new MutableLiveData<>();
        getChildren();
    }

    public MutableLiveData<List<Child>> getChild() {
        return childMutableLiveDate;
    }

    public void getChildren() {
        String parentId = null;
        PrefUserInfos prefUserInfos = new PrefUserInfos(context);
        JSONObject userObject = prefUserInfos.loadMap(USERDATA);
        try {
            String userId = userObject.getString("userId");
            parentId = userId.replaceAll("\"", "");
            Log.i(TAG, "register: userObject " + userId.replaceAll("\"", ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        findByParentId(parentId);
    }

    public void findByParentId(String parentId) {
        Retrofit retrofit = RetrofitBuilder.getAuthenticatedClient(context);
        childWebService = retrofit.create(ChildWebService.class);
        authentificateUserCall2 = childWebService.getChildList(parentId);
        authentificateUserCall2.enqueue(new Callback<List<Child>>() {
            @Override
            public void onResponse(Call<List<Child>> call, Response<List<Child>> response) {
                Log.d(TAG, "message " + response.message() + "code " + response.code());
                // ok your user is authentificated
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        List<Child> child = response.body();
                        childMutableLiveDate.setValue(child);
                        Log.d(TAG, "onResponse: " + child.size());
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
            public void onFailure(Call<List<Child>> call, Throwable t) {
                AlertNetworkError.showNetworkDialog(context, t.getMessage());
                Log.e(TAG, t.getMessage() + t.getLocalizedMessage());
                t.printStackTrace();
            }
        });
    }
}

class ViewModelFactory implements ViewModelProvider.Factory {
    private Context context;

    public ViewModelFactory(Context context) {
        this.context = context;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ChildViewModel.class)) {
            return (T) new ChildViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}