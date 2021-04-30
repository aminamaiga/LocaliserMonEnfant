package fr.umontpellier.localisermonenfant.networks.interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import java.io.IOException;

import fr.umontpellier.localisermonenfant.GlobalClass;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class JwtInterceptor implements Interceptor {
    Context context;
    String TAG = "JwtInterceptor";

   public JwtInterceptor(Context context){
        this.context = context;
     }
    @Override
    public Response intercept(Chain chain) throws IOException {
       Response response;
           final GlobalClass globalVariable = (GlobalClass) context.getApplicationContext();
           SharedPreferences sharedPreferences = context.getSharedPreferences(globalVariable.PREFS_LOGIN, 0);
           String token = sharedPreferences.getString(globalVariable.TOKEN, "");

           Request original = chain.request();

           Request.Builder requestBuilder = original.newBuilder();
           requestBuilder.header("Accept", "applicaton/json");
           requestBuilder.header("Authorization", "Bearer" + " "+ token);

           requestBuilder.method(original.method(), original.body());
           Request request = requestBuilder.build();
            response = chain.proceed(request);
        return response;
   }
}
