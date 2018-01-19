package hackergames.resilientplc.com.hackergamesapp.data;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import hackergames.resilientplc.com.hackergamesapp.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by eduar on 18/01/2018.
 */

public class NetworkClient {

    private final Context context;
    private String token;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;

    public NetworkClient(@NotNull Context context, @Nullable String token) {
        this.context = context;
        this.token = token;
        buildBaseRetrofit();
    }


    public void addAuthToken(@NotNull String token) {
        this.token = token;
        buildBaseRetrofit();
    }

    private void buildBaseRetrofit() {
        okHttpClient = getBaseHttpClientBuilder().build();
        retrofit = getBaseRetrofitBuilder().client(okHttpClient).build();
    }

    private Retrofit.Builder getBaseRetrofitBuilder() {
        Gson gson = new GsonBuilder().setLenient().create();


        return new Retrofit.Builder()
                .baseUrl("http://resilient-hackathon.eu-west-1.elasticbeanstalk.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    private OkHttpClient.Builder getBaseHttpClientBuilder() {
        OkHttpClient.Builder builder =
                new OkHttpClient().newBuilder()
                        .readTimeout(10, TimeUnit.SECONDS)
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .addInterceptor(new SmartInterceptor(token));

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(getInterceptorForDebugging());
        }

        return builder;
    }


    private HttpLoggingInterceptor getInterceptorForDebugging() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS);
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    private class SmartInterceptor implements Interceptor {
        private static final String HEADER_AUTH_KEY = "Authorization";
        private static final String HEADER_AUTH_VALUE_PREFFIX = "Bearer ";
        private static final String HEADER_CONTENT_TYPE_KEY = "Content-Type";
        private static final String HEADER_CONTENT_TYPE_VALUE = "application/json";
        private static final String HEADER_APP_CODE_KEY = "X-Smartnumbers-Android-Version";

        private final String token;

        public SmartInterceptor(@Nullable String token) {
            this.token = token;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder requestBuilder = chain.request().newBuilder();
            requestBuilder.addHeader(HEADER_CONTENT_TYPE_KEY, HEADER_CONTENT_TYPE_VALUE);
            requestBuilder.addHeader(HEADER_APP_CODE_KEY, "54");

            if (TextUtils.isEmpty(token)) {
                return chain.proceed(requestBuilder.build());
            }

            requestBuilder.addHeader(HEADER_AUTH_KEY, HEADER_AUTH_VALUE_PREFFIX + token);
            return chain.proceed(requestBuilder.build());
        }
    }

}
