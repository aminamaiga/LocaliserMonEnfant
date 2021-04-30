package fr.umontpellier.localisermonenfant.activity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import fr.umontpellier.localisermonenfant.models.requests.UserLogin;
import fr.umontpellier.localisermonenfant.networks.auth.BussnessServiceLogin;

public class LoginViewModel extends AndroidViewModel {
    public MutableLiveData<String> Login = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();

    public MutableLiveData<UserLogin> loginUserMutableLiveDate;

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<UserLogin> getUser() {
        if (loginUserMutableLiveDate == null) {
            loginUserMutableLiveDate = new MutableLiveData<>();
        }
        return loginUserMutableLiveDate;
    }

    public void onLogin(View view) {
        UserLogin userLogin = new UserLogin(Login.getValue(), Password.getValue());
        loginUserMutableLiveDate.setValue(userLogin);
        Context context = view.getContext();
        if (userLogin.getEmail() != null && userLogin.isEmailValid() && userLogin.getPassword() != null && userLogin.isPasswordLengthGreaterThan5()) {
            BussnessServiceLogin bussnessServiceLogin = new BussnessServiceLogin(context);
            bussnessServiceLogin.initializeAuthentificatedCommunication(context);
            bussnessServiceLogin.login(userLogin);
        } else {
            Toast.makeText(context, "Merci de renseigner tous les champs ", Toast.LENGTH_LONG).show();
        }
    }

    public void onGoToRegister(View view){
        Context c = view.getContext();
        Intent intent = new Intent(c.getApplicationContext(), RegisterActivity.class);
        c.startActivity(intent);

    }
}
