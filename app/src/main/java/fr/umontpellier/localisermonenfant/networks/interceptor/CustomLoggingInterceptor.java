package fr.umontpellier.localisermonenfant.networks.interceptor;

import android.util.Log;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CustomLoggingInterceptor implements Interceptor {
    //Code pasted from okHttp webSite itself
    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();
        Log.e("Interceptor ", String.format("Sending request %s on %s  %s.",
                request.url(), chain.connection(), request.headers().toString()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        Log.e("Interceptor ", String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        return response;
    }
}