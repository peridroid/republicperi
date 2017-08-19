package ru.devtron.republicperi.data;

import android.content.SharedPreferences;

import ru.devtron.republicperi.App;
import ru.devtron.republicperi.data.network.requests.LoginReq;

public class KeyValueStorage {
    private SharedPreferences mSharedPreferences;
    private final String KEY_TOKEN = "KEY_TOKEN";
    private final String KEY_LOGIN = "KEY_LOGIN";
    private final String KEY_PASSWORD = "KEY_PASSWORD";
    private final String KEY_USER_ID = "KEY_USER_ID";
    String[] ITEM_LAST_UPDATE_KEYS = {
            "PRODUCT_LAST_UPDATE_TOURS",
            "PRODUCT_LAST_UPDATE_PLACES",
            "PRODUCT_LAST_UPDATE_SERVICES"
    };
    private static KeyValueStorage mKeyValueStorage;

    public static KeyValueStorage getInstance() {
        if (mKeyValueStorage == null) {
            mKeyValueStorage = new KeyValueStorage();
        }
        return mKeyValueStorage;
    }

    KeyValueStorage() {
        mSharedPreferences = App.getSharedPreferences();
    }

    public String getKeyToken() {
        return mSharedPreferences.getString(KEY_TOKEN, "");
    }

    public void saveToken(String token) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(KEY_TOKEN, token);
        edit.apply();
    }

    public void saveLastPlacesUpdate(String eTag) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(ITEM_LAST_UPDATE_KEYS[1], eTag);
        edit.apply();
    }

    public String getPlacesLastUpdate() {
        return mSharedPreferences.getString(ITEM_LAST_UPDATE_KEYS[1], "");
    }


    public void saveLogin(LoginReq loginReq) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(KEY_LOGIN, loginReq.getEmail());
        edit.putString(KEY_PASSWORD, loginReq.getPassword());
        edit.apply();
    }

    public String getKeyUserId() {
        return mSharedPreferences.getString(KEY_USER_ID, "");
    }

    public LoginReq getLoginReq() {
        return new LoginReq(mSharedPreferences.getString(KEY_LOGIN, ""), mSharedPreferences.getString(KEY_PASSWORD, ""));
    }
}
