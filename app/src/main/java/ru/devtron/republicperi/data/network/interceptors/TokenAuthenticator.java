package ru.devtron.republicperi.data.network.interceptors;

import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;
import ru.devtron.republicperi.data.CommonRepository;
import ru.devtron.republicperi.data.KeyValueStorage;
import ru.devtron.republicperi.data.network.response.AccessToken;

public class TokenAuthenticator implements Authenticator {
    private static final String TAG = "TokenAuthenticator";

    @Nullable
    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        /*if (response.request().header("Authorization") != null) {
            return null; // Give up, we've already failed to authenticate.
        }*/
        Call<AccessToken> call = CommonRepository.getInstance().loginUser(KeyValueStorage.getInstance().getLoginReq());
        String newAccessToken = call.execute().body().getToken();
        KeyValueStorage.getInstance().saveToken(newAccessToken);
        Log.d(TAG, "authenticate: newToken " + newAccessToken);
        return response.request().newBuilder()
                .removeHeader("Authorization")
                .header("Authorization", "Bearer " + newAccessToken)
                .build();
    }
}
