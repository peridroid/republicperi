package ru.devtron.republicperi.data.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.devtron.republicperi.App;
import ru.devtron.republicperi.data.network.interceptors.CacheInterceptor;
import ru.devtron.republicperi.data.network.interceptors.HeaderInterceptor;
import ru.devtron.republicperi.data.network.interceptors.OfflineCacheInterceptor;
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

    private static Cache provideCache(Context context) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = null;
        try {
            cache = new Cache(new File(context.getCacheDir(), "http-cache"),
                    cacheSize); // 10 MB
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cache;
    }

    @NonNull
    private static OkHttpClient buildClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new OfflineCacheInterceptor())
                .addNetworkInterceptor(new CacheInterceptor())
                .authenticator(new TokenAuthenticator())
                .cache(provideCache(App.getContext()))
                .addInterceptor(loggingInterceptor)
                .build();
    }
}
