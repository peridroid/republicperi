package ru.devtron.republicperi.data.network.interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import ru.devtron.republicperi.data.KeyValueStorage;

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = KeyValueStorage.getInstance().getKeyToken();
        token = !token.isEmpty() ? "token " + token : "";
        Request original = chain.request();

        Request.Builder requestBuilder = original.newBuilder()
                .header("Authorization", token)
                .header("Request-User-Id", KeyValueStorage.getInstance().getKeyUserId());

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
