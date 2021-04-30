package fr.umontpellier.localisermonenfant.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import fr.umontpellier.localisermonenfant.databinding.ActivityLoginBinding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import java.util.Objects;

import fr.umontpellier.localisermonenfant.R;
import fr.umontpellier.localisermonenfant.models.requests.UserLogin;

public class LoginActivity extends AppCompatActivity {
    public LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        binding.setLifecycleOwner(LoginActivity.this);
        binding.setViewmodel(loginViewModel);

        loginViewModel.getUser().observe(this, new Observer<UserLogin>() {
            @Override
            public void onChanged(UserLogin userLogin) {
                if (TextUtils.isEmpty(Objects.requireNonNull(userLogin).getEmail())) {
                    binding.editTextEmail.setError("Entrer une e-mail");
                    binding.editTextEmail.requestFocus();
                } else if (!userLogin.isEmailValid()) {
                    binding.editTextEmail.setError("Entrer une e-mail Valide");
                    binding.editTextEmail.requestFocus();
                } else if (TextUtils.isEmpty(Objects.requireNonNull(userLogin).getPassword())) {
                    binding.editTextPassword.setError("Entrer un mot de passe");
                    binding.editTextPassword.requestFocus();
                } else if (userLogin.isPasswordLengthGreaterThan5()) {
                    binding.editTextPassword.setError("Mot de passe doit être au minimuin 4 chiffres");
                    binding.editTextPassword.requestFocus();
                } else {
                    binding.editTextEmail.setText(userLogin.getEmail());
                    binding.editTextPassword.setText(userLogin.getPassword());
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish(); //only call if you want to clear this activity after go to other activity
    }
}