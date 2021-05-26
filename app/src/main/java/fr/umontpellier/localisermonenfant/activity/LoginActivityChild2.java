package fr.umontpellier.localisermonenfant.activity;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import fr.umontpellier.localisermonenfant.R;
import fr.umontpellier.localisermonenfant.networks.businesses.BusinessServiceChild;
import fr.umontpellier.localisermonenfant.utils.CustomKeyboard;
public class LoginActivityChild2 extends AppCompatActivity {

    public Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_child2);

        context = this;
        Button mButtonEnter;

        EditText editText = (EditText) findViewById(R.id.editText);
        CustomKeyboard keyboard = (CustomKeyboard) findViewById(R.id.keyboard);
        //
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setTextIsSelectable(true);

        //
        InputConnection ic = editText.onCreateInputConnection(new EditorInfo());
        keyboard.setInputConnection(ic);

        mButtonEnter = (Button) findViewById(R.id.button_enter);

        mButtonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Vérication en cours....", Toast.LENGTH_LONG).show();
                BusinessServiceChild bussnessServiceChild = new BusinessServiceChild(context);
                bussnessServiceChild.initializeAuthentificatedCommunication();
                bussnessServiceChild.findByCode(Integer.parseInt(editText.getText().toString()));
            }
        });
    }
}