package ru.devtron.republicperi.ui.screen.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.devtron.republicperi.R;
import ru.devtron.republicperi.data.CommonRepository;
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

import static ru.devtron.republicperi.ui.screen.auth.AuthActivity.SocialSdkType.TWITTER;

public class AuthActivity extends BaseActivity {
	Toolbar mToolbar;
	Button mBtnForRegistAtivity;
	@BindView(R.id.auth_email_et)
	EditText mAuthEmailEt;
	@BindView(R.id.auth_password_et)
	EditText mAuthPasswordEt;
	private Button mBtnForRepairAtivity;
	private Button mLoginBtn;

	private LoginManager mLoginManager;
	private CallbackManager mCallbackManager;
	private TwitterAuthClient mTwitterAuthClient;

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

		if (resultCode == Activity.RESULT_CANCELED &&
				(requestCode == VKServiceActivity.VKServiceType.Authorization.getOuterCode() ||
						requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode())) {
			onSocialCancel();
		}
	}
}
