package fr.umontpellier.localisermonenfant.networks;

import android.content.Context;
import androidx.annotation.NonNull;
import fr.umontpellier.localisermonenfant.networks.interceptor.CustomLoggingInterceptor;
import fr.umontpellier.localisermonenfant.networks.interceptor.JwtInterceptor;
import okhttp3.OkHttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitBuilder {
    public static final String BASE_URL = "http://10.0.2.2:8081/api/";
    private static final String TAG = "RetrofitBuilder";
    static Retrofit retrofit = null;

     public static Retrofit getAuthenticatedClient(Context ctx){
            // Get the OkHttp client
            if (retrofit == null){
                OkHttpClient client = getOkAuthenticatedHttpClient(ctx);
                // Using my HttpClient
                retrofit = new Retrofit.Builder()
                        .client(client)
                        .baseUrl(BASE_URL)// converter
                        .addConverterFactory(MoshiConverterFactory.create())
                        .build();
            }
            return retrofit;
        }

    @NonNull
    public static OkHttpClient getOkAuthenticatedHttpClient(Context ctx) {
        CustomLoggingInterceptor customLoggingInterceptor = new CustomLoggingInterceptor();
        return new OkHttpClient.Builder()
                .addInterceptor(new JwtInterceptor(ctx))
                .addInterceptor(customLoggingInterceptor)
                .build();
    }
    }
