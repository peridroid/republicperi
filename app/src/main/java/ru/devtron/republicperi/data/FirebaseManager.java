package ru.devtron.republicperi.data;


import com.google.firebase.auth.FirebaseAuth;

public class FirebaseManager {
	private static FirebaseManager ourInstance;
	private final FirebaseAuth mAuth;


	public static FirebaseManager getInstance() {
		if (ourInstance == null) {
			ourInstance = new FirebaseManager();
		}
		return ourInstance;
	}

	private FirebaseManager() {
		mAuth = FirebaseAuth.getInstance();
	}

	public FirebaseAuth getAuth() {
		return mAuth;
	}

	public boolean isAuthUser() {
		return mAuth.getCurrentUser() != null;
	}
}
