package ru.devtron.republicperi.service;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import ru.devtron.republicperi.utils.NotificationHelper;


public class PushService extends FirebaseMessagingService {
	private static final String TAG = "PushService";

	@Override
	public void onMessageReceived(RemoteMessage remoteMessage) {
		String id = remoteMessage.getMessageId();
		String type = remoteMessage.getData().get("type");
		String meta = remoteMessage.getData().get("meta");

		NotificationHelper.createNofitication(remoteMessage, type, meta);
		super.onMessageReceived(remoteMessage);
	}
}
