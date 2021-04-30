package fr.umontpellier.localisermonenfant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fr.umontpellier.localisermonenfant.activity.LoginActivity;
import fr.umontpellier.localisermonenfant.ui.login.LoginActivityChild2;

public class MainActivity extends AppCompatActivity {
private Button nextButton, childButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextButton = (Button)findViewById(R.id.nextParent);
        childButton = (Button)findViewById(R.id.nextChild);
        nextButton.setOnClickListener(new ButtonNextListener());
        childButton.setOnClickListener(new ButtonNextListener());
    }

    class ButtonNextListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()){
                case R.id.nextParent:
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    break;

                case R.id.nextChild:
                     intent = new Intent(getApplicationContext(), LoginActivityChild2.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}