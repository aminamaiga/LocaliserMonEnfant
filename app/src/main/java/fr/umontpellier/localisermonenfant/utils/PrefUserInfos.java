package fr.umontpellier.localisermonenfant.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import java.util.Map;

public class PrefUserInfos {

    public static final String USERDATA = "USERDATA";
    Context context;

    public PrefUserInfos(Context context) {
        this.context = context;
    }

    public void saveMap(Map<String, String> inputMap) {
        SharedPreferences pSharedPref = context.getSharedPreferences(USERDATA, Context.MODE_PRIVATE);
        if (pSharedPref != null) {
            JSONObject jsonObject = new JSONObject(inputMap);
            String jsonString = jsonObject.toString();
            SharedPreferences.Editor editor = pSharedPref.edit();
            editor.remove(USERDATA).commit();
            editor.putString(USERDATA, jsonString);
            editor.commit();
        }
    }

    public JSONObject loadMap(String key) {
        SharedPreferences pSharedPref = context.getSharedPreferences(USERDATA, Context.MODE_PRIVATE);
        JSONObject jsonObject;
        jsonObject = null;
        try {
            if (pSharedPref != null) {
                String jsonString = pSharedPref.getString(key, (new JSONObject()).toString());
                jsonObject = new JSONObject(jsonString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
