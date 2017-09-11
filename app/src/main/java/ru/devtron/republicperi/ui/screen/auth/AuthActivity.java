package ru.devtron.republicperi.ui.screen.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKServiceActivity;
import com.vk.sdk.api.VKError;

import java.util.Arrays;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.devtron.republicperi.R;
import ru.devtron.republicperi.data.CommonRepository;
import ru.devtron.republicperi.data.FirebaseManager;
import ru.devtron.republicperi.data.KeyValueStorage;
import ru.devtron.republicperi.data.network.requests.LoginReq;
import ru.devtron.republicperi.data.network.requests.SocialLoginReq;
import ru.devtron.republicperi.data.network.response.AccessToken;
import ru.devtron.republicperi.data.network.response.FacebookProfileRes;
import ru.devtron.republicperi.data.network.response.TwitterProfileRes;
import ru.devtron.republicperi.data.network.response.UserRes;
import ru.devtron.republicperi.data.network.response.VkProfileRes;
import ru.devtron.republicperi.ui.base.BaseActivity;
import ru.devtron.republicperi.ui.screen.registr.RegistrActivity;
import ru.devtron.republicperi.ui.screen.repair.RepairActivity;
import ru.devtron.republicperi.utils.Const;
import ru.devtron.republicperi.utils.validator.EmailValidator;
import ru.devtron.republicperi.utils.validator.EmptyValidator;
import ru.devtron.republicperi.utils.validator.Validator;
import ru.devtron.republicperi.utils.validator.ValidatorsComposer;

import static ru.devtron.republicperi.ui.screen.auth.AuthActivity.SocialSdkType.TWITTER;

public class AuthActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {
	private static final int RC_SIGN_IN = 123;
	private static final String TAG = AuthActivity.class.getSimpleName();
	Toolbar mToolbar;
	Button mBtnForRegistAtivity;
	@BindView(R.id.auth_email_et)
	EditText mAuthEmailEt;
	@BindView(R.id.auth_password_et)
	EditText mAuthPasswordEt;
	@BindView(R.id.email_til)
    TextInputLayout mEmailTil;
	@BindView(R.id.password_til)
    TextInputLayout mPasswordTil;
	private Button mBtnForRepairAtivity;
	private Button mLoginBtn;

	private LoginManager mLoginManager;
	private CallbackManager mCallbackManager;
	private TwitterAuthClient mTwitterAuthClient;

	final ValidatorsComposer<String> emptinessValidatorComposer = new ValidatorsComposer<>(new EmptyValidator());
	final ValidatorsComposer<String> emailValidatorComposer = new ValidatorsComposer<>(new EmptyValidator(), new EmailValidator());
	private GoogleApiClient mGoogleApiClient;


	@OnClick(R.id.google_plus_social_btn)
	public void onGooglePlusClick() {
		Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
		startActivityForResult(signInIntent, RC_SIGN_IN);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth);
		ButterKnife.bind(this);
		mToolbar = findViewById(R.id.toolbar_profile);
		setSupportActionBar(mToolbar);
		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		mBtnForRegistAtivity = findViewById(R.id.auth_registration_btn);
		mBtnForRepairAtivity = findViewById(R.id.auth_forgot_btn);
		mLoginBtn = findViewById(R.id.login_btn);
		mLoginBtn.setOnClickListener(mOnClickListener);

		mBtnForRegistAtivity.setOnClickListener(mOnClickListener);
		mBtnForRepairAtivity.setOnClickListener(mOnClickListener);
		initSocialSdk();
	}

	public void initSocialSdk() {
		if (mLoginManager == null) mLoginManager = LoginManager.getInstance();
		if (mCallbackManager == null) mCallbackManager = CallbackManager.Factory.create();
		if (mTwitterAuthClient == null) mTwitterAuthClient = new TwitterAuthClient();

		mLoginManager.registerCallback(mCallbackManager, facebookCallback);

		// Configure sign-in to request the user's ID, email address, and basic
		// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestEmail()
				.build();


		// Build a GoogleApiClient with access to the Google Sign-In API and the
		// options specified by gso.
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.enableAutoManage(this, this)
				.addApi(Auth.GOOGLE_SIGN_IN_API, gso)
				.build();

	}

	@OnClick(R.id.vk_social_btn)
	public void onVkClick() {
		VKSdk.login(this, "email");
	}

	@OnClick(R.id.fb_social_btn)
	public void onFbClick() {
		mLoginManager.logInWithReadPermissions(this, Collections.singletonList("email"));
	}

	@OnClick(R.id.twitter_social_btn)
	public void onTwClick() {
		mTwitterAuthClient.authorize(this, twitterCallback);
	}

	View.OnClickListener mOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			Intent intent = new Intent();
			switch (view.getId()) {
				case R.id.auth_registration_btn:
					intent.setClass(AuthActivity.this, RegistrActivity.class);
					startActivity(intent);
					break;
				case R.id.auth_forgot_btn:
					intent.setClass(AuthActivity.this, RepairActivity.class);
					startActivity(intent);
					break;
				case R.id.login_btn:
					final LoginReq blablabla = new LoginReq(mAuthEmailEt.getText().toString(),
							mAuthPasswordEt.getText().toString());
					Call<AccessToken> tokenCall = CommonRepository.getInstance()
							.loginUser(blablabla);
					tokenCall.enqueue(new Callback<AccessToken>() {
						@Override
						public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
							if (response.isSuccessful()) {
								KeyValueStorage.getInstance().saveToken(response.body().getToken());
								KeyValueStorage.getInstance().saveLogin(blablabla);
								Toast.makeText(AuthActivity.this, "Вы успешно авторизовались", Toast.LENGTH_SHORT).show();
								AuthActivity.this.finish();
							} else {
								Toast.makeText(AuthActivity.this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
							}
						}

						@Override
						public void onFailure(Call<AccessToken> call, Throwable t) {
							Toast.makeText(AuthActivity.this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
						}
					});

			}
		}
	};

	//region Callbacks
	private VKCallback<VKAccessToken> vkCallback = new VKCallback<VKAccessToken>() {
		@Override
		public void onResult(VKAccessToken res) {
			onSocialResult(res, SocialSdkType.VK);
		}

		@Override
		public void onError(VKError error) {
			onSocialError();
		}
	};

	private FacebookCallback<LoginResult> facebookCallback = new FacebookCallback<LoginResult>() {
		@Override
		public void onSuccess(LoginResult loginResult) {
			onSocialResult(loginResult, SocialSdkType.FACEBOOK);
		}

		@Override
		public void onCancel() {
			onSocialCancel();
		}

		@Override
		public void onError(FacebookException error) {
			onSocialError();
		}
	};

	private com.twitter.sdk.android.core.Callback<TwitterSession> twitterCallback = new com.twitter.sdk.android.core.Callback<TwitterSession>() {
		@Override
		public void success(Result<TwitterSession> result) {
			onSocialResult(result.data, TWITTER);
		}

		@Override
		public void failure(TwitterException exception) {
			onSocialError();
		}
	};
	//endregion


	public void onSocialResult(final Object res, SocialSdkType type) {
		switch (type) {
			case VK:
				final VKAccessToken vkRes = (VKAccessToken) res;
				CommonRepository.getInstance().getVkProfile(vkRes.accessToken)
						.enqueue(new Callback<VkProfileRes>() {
							@Override
							public void onResponse(Call<VkProfileRes> call, Response<VkProfileRes> response) {
								if (response.isSuccessful()) {
									VkProfileRes vkProfileRes = response.body();
									SocialLoginReq socialLoginReq = new SocialLoginReq(vkProfileRes.getFullName(),
											vkProfileRes.getAvatar(), vkRes.email, vkProfileRes.getPhone());
									authorizeUserOnOurBackendWithData(socialLoginReq);
								}
							}

							@Override
							public void onFailure(Call<VkProfileRes> call, Throwable throwable) {
								showMessage(throwable.getMessage());
							}
						});
				break;
			case FACEBOOK:
				LoginResult facebookRes = (LoginResult) res;
				CommonRepository.getInstance().getFacebookProfile(facebookRes.getAccessToken().getToken())
						.enqueue(new Callback<FacebookProfileRes>() {
							@Override
							public void onResponse(Call<FacebookProfileRes> call, Response<FacebookProfileRes> response) {
								if (response.isSuccessful()) {
									FacebookProfileRes facebookProfileRes = response.body();
									SocialLoginReq socialLoginReq = new SocialLoginReq(facebookProfileRes.getFirstName() + facebookProfileRes.getLastName(),
											facebookProfileRes.getAvatar(), facebookProfileRes.getEmail(), null);
									authorizeUserOnOurBackendWithData(socialLoginReq);
								}
							}

							@Override
							public void onFailure(Call<FacebookProfileRes> call, Throwable throwable) {
								showMessage(throwable.getMessage());
							}
						});

				break;
			case TWITTER:
				TwitterSession twitterSession = (TwitterSession) res;
				TwitterProfileRes twitterProfileRes = CommonRepository.getInstance()
						.getTwitterProfile(twitterSession);
				SocialLoginReq socialLoginReq = new SocialLoginReq(twitterProfileRes.getName(),
						twitterProfileRes.getAvatar(), twitterProfileRes.getEmail(), null);
				authorizeUserOnOurBackendWithData(socialLoginReq);
				break;
		}
	}

	private void authorizeUserOnOurBackendWithData(SocialLoginReq socialLoginReq) {
		if (Const.isBackendReadyForSocialAuth) {
			CommonRepository.getInstance().socialLogin(socialLoginReq)
					.enqueue(new Callback<UserRes>() {
						@Override
						public void onResponse(Call<UserRes> call, Response<UserRes> response) {
							if (response.isSuccessful()) {
								onSocialSuccess(response.body().getName());
							}
						}

						@Override
						public void onFailure(Call<UserRes> call, Throwable throwable) {
							showMessage(throwable.getMessage());
						}
					});
		} else {
			onSocialSuccess(socialLoginReq.getName());
		}
	}

	public void onSocialSuccess(String name) {
		showMessage("Вы успешно авторизованы как " + name);
		finish();
	}

	public void onSocialError() {
		showMessage("Ошибка авторизации");
	}

	public void onSocialCancel() {
		showMessage("Авторизация отменена");
	}

	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
		Log.d(TAG, "onConnectionFailed: ");
	}

	enum SocialSdkType {
		VK,
		FACEBOOK,
		TWITTER
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == VKServiceActivity.VKServiceType.Authorization.getOuterCode()) {
			VKSdk.onActivityResult(requestCode, resultCode, intent, vkCallback);
		}

		if (requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()) {
			mCallbackManager.onActivityResult(requestCode, resultCode, intent);
		}

		if (requestCode == mTwitterAuthClient.getRequestCode()) {
			mTwitterAuthClient.onActivityResult(requestCode, resultCode, intent);
		}

		// Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
		if (requestCode == RC_SIGN_IN) {
			GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
			if (result.isSuccess()) {
				// Google Sign In was successful, authenticate with Firebase
				GoogleSignInAccount account = result.getSignInAccount();
				firebaseAuthWithGoogle(account);
			} else {
				// Google Sign In failed, update UI appropriately
				// ...
			}
		}

		if (resultCode == Activity.RESULT_CANCELED &&
				(requestCode == VKServiceActivity.VKServiceType.Authorization.getOuterCode() ||
						requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode())) {
			onSocialCancel();
		}
	}

	private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
		Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

		AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
		FirebaseManager.getInstance().getAuth()
				.signInWithCredential(credential)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							// Sign in success, update UI with the signed-in user's information
							Log.d(TAG, "signInWithCredential:success");
							FirebaseUser user = FirebaseManager.getInstance().getAuth().getCurrentUser();
							if (user != null) {
								showMessage(user.getDisplayName());
							}
						} else {
							// If sign in fails, display a message to the user.
							Log.d(TAG, "signInWithCredential:failure", task.getException());
							Toast.makeText(AuthActivity.this, "Authentication failed.",
									Toast.LENGTH_SHORT).show();
						}

						// ...
					}
				});
	}

	@OnTextChanged(value = {R.id.auth_email_et, R.id.auth_password_et},
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChanged() {
        mLoginBtn.setEnabled(false);

        mEmailTil.setErrorEnabled(false);
        if(!emailValidatorComposer.isValid(mAuthEmailEt.getText().toString())){
            showFieldError(mEmailTil, emailValidatorComposer.getDescription());
            return;
        }


        mPasswordTil.setErrorEnabled(false);

        mLoginBtn.setEnabled(true);
    }

    void showFieldError(TextInputLayout til, String error) {
        til.setErrorEnabled(true);
        til.setError(error);
        mLoginBtn.setEnabled(false);
    }

}
