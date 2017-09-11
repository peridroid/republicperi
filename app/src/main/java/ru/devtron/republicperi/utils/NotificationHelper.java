package ru.devtron.republicperi.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import ru.devtron.republicperi.App;
import ru.devtron.republicperi.R;
import ru.devtron.republicperi.ui.screen.main.MainActivity;

public class NotificationHelper {

	//Простая нотификация
	public static void createSimpleNotification(String title, String content) {
		final Context context = App.getContext();
		Intent contentIntent = new Intent(context, MainActivity.class);
		PendingIntent resultIntent = PendingIntent.getActivity(context,
				Const.PUSH_REQ_CODE, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default")
				.setContentTitle(title)
				.setContentText(content)
				.setContentIntent(resultIntent)
				.setSmallIcon(R.drawable.ic_notifications)
				.setAutoCancel(true)
				.setColor(context.getResources().getColor(R.color.colorAccent))
				.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.notify(Const.NOTIFICATION_MANAGER_ID, builder.build());
	}

	//notification message или смешанный тип notificatiom message + смешанный
	public static void createNofitication(RemoteMessage remoteMessage, @Nullable String type, @Nullable String meta) {
		final RemoteMessage.Notification notification = remoteMessage.getNotification();
		final Context context = App.getContext();
		Intent contentIntent = new Intent(context, MainActivity.class);
		String title = notification.getTitle();
		if (TextUtils.isEmpty(title)) {
			title = context.getString(R.string.app_name);
		}
		String content = notification.getBody();
		PendingIntent resultIntent = PendingIntent.getActivity(context,
				Const.PUSH_REQ_CODE, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default")
				.setContentTitle(title)
				.setContentText(content)
				.setContentIntent(resultIntent)
				.setSmallIcon(R.drawable.ic_notifications)
				.setAutoCancel(true)
				.setColor(context.getResources().getColor(R.color.colorAccent))
				.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        if (!TextUtils.isEmpty(type)) {
            if (TextUtils.equals(type, Const.NOTIFICATION_URGENT_TOUR_TYPE)) {
                builder.addAction(R.drawable.ic_announcement_black_24dp, "Срочный тур", resultIntent);
            } else if (TextUtils.equals(type, Const.NOTIFICATION_ACTION_TYPE)) {
                builder.addAction(R.drawable.ic_sale, "Успеть на акцию", resultIntent);
            }
        }

		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.notify(Const.NOTIFICATION_MANAGER_ID, builder.build());
	}

	//data message
	public static void createNofitication(Map<String, String> notification, @Nullable String type, @Nullable String meta) {
		final Context context = App.getContext();
		Intent contentIntent = new Intent(context, MainActivity.class);
		String title = notification.get("title");
		if (TextUtils.isEmpty(title)) {
			title = context.getString(R.string.app_name);
		}
		String content = notification.get("content");
		PendingIntent resultIntent = PendingIntent.getActivity(context,
				Const.PUSH_REQ_CODE, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default")
				.setContentTitle(title)
				.setContentText(content)
				.setContentIntent(resultIntent)
				.setSmallIcon(R.drawable.ic_notifications)
				.setAutoCancel(true)
				.setColor(context.getResources().getColor(R.color.colorAccent))
				.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

		if (!TextUtils.isEmpty(type)) {
			if (TextUtils.equals(type, Const.NOTIFICATION_URGENT_TOUR_TYPE)) {
				builder.addAction(R.drawable.ic_announcement_black_24dp, "Срочный тур", resultIntent);
			} else if (TextUtils.equals(type, Const.NOTIFICATION_ACTION_TYPE)) {
				builder.addAction(R.drawable.ic_sale, "Успеть на акцию", resultIntent);
			}
		}

		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.notify(Const.NOTIFICATION_MANAGER_ID, builder.build());
	}
}