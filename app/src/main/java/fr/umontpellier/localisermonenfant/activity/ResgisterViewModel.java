package fr.umontpellier.localisermonenfant.activity;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.Date;
import fr.umontpellier.localisermonenfant.models.User;
import fr.umontpellier.localisermonenfant.networks.businesses.BussnessServiceUser;

public class ResgisterViewModel extends ViewModel {

    public MutableLiveData<String> FirstName_ = new MutableLiveData<>();
    public MutableLiveData<String> LastName_ = new MutableLiveData<>();
    public MutableLiveData<String> Email_ = new MutableLiveData<>();
    public MutableLiveData<String> Adresse_ = new MutableLiveData<>();
    public MutableLiveData<String> Password_ = new MutableLiveData<>();

    public MutableLiveData<User> userMutableLiveDate;

    public MutableLiveData<User> getUser() {
        if (userMutableLiveDate == null) {
            userMutableLiveDate = new MutableLiveData<>();
        }
        return userMutableLiveDate;
    }

    public void onSignUp(View view) {
        Context context = view.getContext();
        User user = new User(FirstName_.getValue(), LastName_.getValue(),
               Email_.getValue(), Password_.getValue(), Adresse_.getValue(), new Date());
        userMutableLiveDate.setValue(user);
        if (user.getFirstName() != null && user.getLastName() != null && user.getEmail() != null && user.isEmailValid() && user.getPassword() != null && user.isPasswordLengthGreaterThan5()) {
            BussnessServiceUser bussnessServiceUser = new BussnessServiceUser(context);
            bussnessServiceUser.initializeAuthentificatedCommunication();
            bussnessServiceUser.register(user);
        } else {
            Toast.makeText(context, "Merci de renseigner tous les champs ", Toast.LENGTH_LONG).show();
        }
    }

}
