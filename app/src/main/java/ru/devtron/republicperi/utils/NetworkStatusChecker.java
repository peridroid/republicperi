package ru.devtron.republicperi.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ru.devtron.republicperi.App;

public class NetworkStatusChecker {
    public static Boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
