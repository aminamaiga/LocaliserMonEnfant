package fr.umontpellier.localisermonenfant.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import java.util.Objects;

import fr.umontpellier.localisermonenfant.R;
import fr.umontpellier.localisermonenfant.databinding.ActivityRegisterBinding;
import fr.umontpellier.localisermonenfant.models.User;

public class RegisterActivity extends AppCompatActivity {
    ResgisterViewModel resgisterViewModel;
    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        resgisterViewModel = ViewModelProviders.of(this).get(ResgisterViewModel.class);
        binding = DataBindingUtil.setContentView(RegisterActivity.this, R.layout.activity_register);
        binding.setLifecycleOwner(RegisterActivity.this);
        binding.setRegisterViewModel(resgisterViewModel);

        resgisterViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (TextUtils.isEmpty(Objects.requireNonNull(user).getFirstName())) {
                    binding.editTextFirstName.setError("Prenom est vide.");
                    binding.editTextFirstName.requestFocus();
                } else if (TextUtils.isEmpty(Objects.requireNonNull(user).getLastName())) {
                    binding.editTextName.setError("Nom est vide.");
                    binding.editTextName.requestFocus();
                } else if (TextUtils.isEmpty(Objects.requireNonNull(user).getEmail())) {
                    binding.editTextEmail.setError("Entrer une e-mail");
                    binding.editTextEmail.requestFocus();
                } else if (!user.isEmailValid()) {
                    binding.editTextEmail.setError("Entrer une e-mail Valide");
                    binding.editTextEmail.requestFocus();
                } else if (TextUtils.isEmpty(Objects.requireNonNull(user).getPassword())) {
                    binding.editTextPassword.setError("Entrer un mot de passe");
                    binding.editTextPassword.requestFocus();
                } else if (!user.isPasswordLengthGreaterThan5()) {
                    binding.editTextPassword.setError("Mot de passe doit être au minimuin 4 chiffres");
                    binding.editTextPassword.requestFocus();
                } else {
                    binding.editTextEmail.setText(user.getEmail());
                    binding.editTextPassword.setText(user.getPassword());
                    binding.editTextFirstName.setText(user.getFirstName());
                    binding.editTextName.setText(user.getLastName());
                    binding.editTextAdresse.setText(user.getAdresse());
                }
            }
        });

        binding.buttonGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}