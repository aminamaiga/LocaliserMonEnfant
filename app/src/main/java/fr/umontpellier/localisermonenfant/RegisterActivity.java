package fr.umontpellier.localisermonenfant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {
private Button signinButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        signinButton = (Button)findViewById(R.id.signinButton);
        signinButton.setOnClickListener(new SignupOnClickListerner());
    }

    class SignupOnClickListerner implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }
}