package ru.devtron.republicperi;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.facebook.stetho.Stetho;

import org.greenrobot.greendao.database.Database;

import ru.devtron.republicperi.data.entities.DaoMaster;
import ru.devtron.republicperi.data.entities.DaoSession;

public class App extends Application {
    public static SharedPreferences sharedPreferences;
    private static Context sContext;
    private static DaoSession sDaoSession;

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Stetho.initializeWithDefaults(this);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "republic-db");
        Database db = helper.getWritableDb();
        sDaoSession = new DaoMaster(db).newSession();
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public static DaoSession getDaoSession() {
        return sDaoSession;
    }
}
