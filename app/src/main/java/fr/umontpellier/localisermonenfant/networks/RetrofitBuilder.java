package fr.umontpellier.localisermonenfant.networks;

import android.content.Context;

import androidx.annotation.NonNull;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.ToJson;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import fr.umontpellier.localisermonenfant.networks.interceptor.CustomLoggingInterceptor;
import fr.umontpellier.localisermonenfant.networks.interceptor.JwtInterceptor;
import okhttp3.OkHttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitBuilder {
    public static final String BASE_URL = "http://10.0.2.2:8081/api/";
    static Retrofit retrofit = null;

    public static Retrofit getAuthenticatedClient(Context ctx) {
        Moshi moshi = new Moshi.Builder()
                .add(Date.class, new CustomDateAdapter())
                .build();
        // Get the OkHttp client
        if (retrofit == null) {
            OkHttpClient client = getOkAuthenticatedHttpClient(ctx);
            // Using my HttpClient
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_URL)// converter
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
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

class CustomDateAdapter extends JsonAdapter<Date> {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault());

    @FromJson
    @Override
    public Date fromJson(JsonReader reader) throws IOException {
        String dateAsString;
        Date date = new Date();
        try {
            dateAsString = reader.nextString();
            synchronized (dateFormat) {
                date = dateFormat.parse(dateAsString);
            }
        } catch (Exception e) {
        }
        return date;
    }

    @ToJson
    @Override
    public void toJson(JsonWriter writer, @Nullable Date value) throws IOException {
        if (value != null) {
            synchronized (dateFormat) {
                writer.value(dateFormat.format(value));
            }
        }
    }
};