package ru.devtron.republicperi;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.vk.sdk.VKSdk;

import org.greenrobot.greendao.database.Database;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import ru.devtron.republicperi.data.entities.DaoMaster;
import ru.devtron.republicperi.data.entities.DaoSession;
import ru.devtron.republicperi.service.PushService;

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

		//при запуске приложения запускаем пуш сервис
		Intent pushIntent = new Intent(this, PushService.class);
		startService(pushIntent);
		initChannels(this);


		Stetho.initializeWithDefaults(this);

		DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "republic-db");
		Database db = helper.getWritableDb();
		sDaoSession = new DaoMaster(db).newSession();

		final FirebaseRemoteConfig mRemoteConfig = FirebaseRemoteConfig.getInstance();

		FirebaseRemoteConfigSettings firebaseConfig = new FirebaseRemoteConfigSettings.Builder()
				.setDeveloperModeEnabled(BuildConfig.DEBUG)
				.build();
		mRemoteConfig.setConfigSettings(firebaseConfig);

		mRemoteConfig.setDefaults(R.xml.remote_config_defaults); // по дефолту

		long cache = 3600;

		if (mRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
			cache = 0;
		}

		//получаем изменения с firebase
		mRemoteConfig.fetch(cache).addOnCompleteListener(new OnCompleteListener<Void>() {
			@Override
			public void onComplete(@NonNull Task<Void> task) {
				mRemoteConfig.activateFetched(); // применяем
			}
		});

		VKSdk.initialize(this);

		TwitterConfig twitterConfig = new TwitterConfig.Builder(this)
				.logger(new DefaultLogger(Log.DEBUG))
				.twitterAuthConfig(new TwitterAuthConfig(getString(R.string.twitter_api_key),
						getString(R.string.twitter_api_secret)))
				.debug(BuildConfig.DEBUG)
				.build();
		Twitter.initialize(twitterConfig);

		final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
		loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
		final OkHttpClient customClient = new OkHttpClient.Builder()
				.addInterceptor(loggingInterceptor).build();

		final TwitterSession activeSession = TwitterCore.getInstance()
				.getSessionManager().getActiveSession();

		final TwitterApiClient customApiClient;
		if (activeSession != null) {
			customApiClient = new TwitterApiClient(activeSession, customClient);
			TwitterCore.getInstance().addApiClient(activeSession, customApiClient);
		} else {
			customApiClient = new TwitterApiClient(customClient);
			TwitterCore.getInstance().addGuestApiClient(customApiClient);
		}
	}

	public void initChannels(Context context) {
		if (Build.VERSION.SDK_INT < 26) {
			return;
		}
		NotificationManager notificationManager =
				(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationChannel channel = new NotificationChannel("default",
				"Уведомления по умолчанию",
				NotificationManager.IMPORTANCE_DEFAULT);
		if (notificationManager != null) {
			notificationManager.createNotificationChannel(channel);
		}
	}


	public static SharedPreferences getSharedPreferences() {
		return sharedPreferences;
	}

	public static DaoSession getDaoSession() {
		return sDaoSession;
	}
}
