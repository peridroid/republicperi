package ru.devtron.republicperi.data.network;

import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.devtron.republicperi.data.network.interceptors.HeaderInterceptor;
import ru.devtron.republicperi.data.network.interceptors.TokenAuthenticator;
import ru.devtron.republicperi.utils.Const;

public final class ServiceFactory {

    private static OkHttpClient sClient;
    private static Retrofit sRetrofit;
    private static volatile ApiService sService;


    @NonNull
    public static ApiService getApiService() {
        ApiService service = sService;
        if (service == null) {
            synchronized (ServiceFactory.class) {
                service = sService;
                if (service == null) {
                    service = sService = buildRetrofit().create(ApiService.class);
                }
            }
        }
        return service;
    }


    @NonNull
    private static Retrofit buildRetrofit() {
        if (sRetrofit != null) {
            return sRetrofit;
        }
        else {
            Gson builder = new GsonBuilder().create();

            sRetrofit = new Retrofit.Builder()
                    .baseUrl(Const.BASE_URL)
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create(builder))
                    .build();
            return sRetrofit;
        }
    }

    public static Retrofit getRetrofit() {
        return sRetrofit;
    }

    @NonNull
    private static OkHttpClient getClient() {
        OkHttpClient client = sClient;
        if (client == null) {
            synchronized (ServiceFactory.class) {
                client = sClient;
                if (client == null) {
                    client = sClient = buildClient();
                }
            }
        }
        return client;
    }

    @NonNull
    private static OkHttpClient buildClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(new HeaderInterceptor())
                .authenticator(new TokenAuthenticator())
                .addInterceptor(loggingInterceptor)
                .build();
    }
}
