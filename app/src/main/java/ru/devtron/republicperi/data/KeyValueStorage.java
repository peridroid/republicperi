package ru.devtron.republicperi.data;

import android.content.SharedPreferences;

import ru.devtron.republicperi.App;

public class KeyValueStorage {
    private SharedPreferences mSharedPreferences;
    private final String KEY_TOKEN = "KEY_TOKEN";
    private final String KEY_USER_ID = "KEY_USER_ID";
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

    public String getKeyUserId() {
        return mSharedPreferences.getString(KEY_USER_ID, "");
    }
}
