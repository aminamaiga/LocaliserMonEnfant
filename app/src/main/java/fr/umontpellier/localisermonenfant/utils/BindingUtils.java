package fr.umontpellier.localisermonenfant.utils;

import android.widget.EditText;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;

public class BindingUtils {
    public static void setText(EditText view, String value) {
        view.setText(value);
    }

    public static int getText(EditText view) {
        String num = view.getText().toString();
        if (num.isEmpty()) return 0;
        try {
            return Integer.parseInt(num);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
