package fr.umontpellier.localisermonenfant.viewmodel;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.umontpellier.localisermonenfant.models.Child;
import fr.umontpellier.localisermonenfant.networks.businesses.BusinessServiceChild;

public class ChildRegisterViewModel extends ViewModel {
    String TAG = "ChildRegisterViewModel";

    public MutableLiveData<String> FirstName = new MutableLiveData<>();
    public MutableLiveData<String> LastName = new MutableLiveData<>();
    public MutableLiveData<String> code = new MutableLiveData<>();
    public MutableLiveData<String> parent = new MutableLiveData<>();

    public MutableLiveData<Child> childMutableLiveDate;

    public MutableLiveData<Child> getChild() {
        if (childMutableLiveDate == null) {
            childMutableLiveDate = new MutableLiveData<>();
        }
        return childMutableLiveDate;
    }

    public void onRegisterChild(View view) {
        Context context = view.getContext();
       try{
           Child child = new Child();
           child.setFirstname(FirstName.getValue());
           child.setLastname(LastName.getValue());
           child.setCode(code.getValue() == null? 0 : Integer.parseInt(code.getValue()));
           childMutableLiveDate.setValue(child);
           if (child.getFirstname() != null && child.getLastname() != null && child.getCode() != null && child.getCode() > 1000) {
               BusinessServiceChild bussnessServiceChild = new BusinessServiceChild(context);
               bussnessServiceChild.initializeAuthentificatedCommunication();
               bussnessServiceChild.register(child);
           } else {
               Toast.makeText(context, "Merci de renseigner tous les champs ", Toast.LENGTH_LONG).show();
           }
       }catch (Exception e){
           Log.e(TAG, e.getMessage());
       }
    }

}
