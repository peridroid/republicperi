package ru.devtron.republicperi.data;

import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;
import com.vk.sdk.VKSdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import ru.devtron.republicperi.App;
import ru.devtron.republicperi.data.entities.DaoSession;
import ru.devtron.republicperi.data.entities.PlaceEntity;
import ru.devtron.republicperi.data.entities.PlaceEntityDao;
import ru.devtron.republicperi.data.network.ApiService;
import ru.devtron.republicperi.data.network.PicassoCache;
import ru.devtron.republicperi.data.network.ServiceFactory;
import ru.devtron.republicperi.data.network.requests.LoginReq;
import ru.devtron.republicperi.data.network.requests.SignUpReq;
import ru.devtron.republicperi.data.network.requests.SocialLoginReq;
import ru.devtron.republicperi.data.network.response.AccessToken;
import ru.devtron.republicperi.data.network.response.FacebookProfileRes;
import ru.devtron.republicperi.data.network.response.PlaceRes;
import ru.devtron.republicperi.data.network.response.ProfileRes;
import ru.devtron.republicperi.data.network.response.ServiceRes;
import ru.devtron.republicperi.data.network.response.TourRes;
import ru.devtron.republicperi.data.network.response.TwitterProfileRes;
import ru.devtron.republicperi.data.network.response.UserRes;
import ru.devtron.republicperi.data.network.response.VkProfileRes;
import ru.devtron.republicperi.utils.Const;

public class CommonRepository {
	private static CommonRepository repository;
	private Picasso mPicasso;
	private DaoSession mDaoSession;
	private ApiService mApiService;

	private CommonRepository() {
		mPicasso = new PicassoCache(App.getContext()).getPicassoInstance();
		mDaoSession = App.getDaoSession();
		mApiService = ServiceFactory.getApiService();
	}

	public static CommonRepository getInstance() {
		if (repository == null) {
			repository = new CommonRepository();
		}
		return repository;
	}

	public Picasso getPicasso() {
		return mPicasso;
	}

	public Call<List<TourRes>> getNearestToursNetwork() {
		return mApiService.getTours();
	}

	public Call<List<PlaceRes>> getNearestPlacesNetwork() {
		return mApiService.getPlaces(KeyValueStorage.getInstance().getPlacesLastUpdate());
	}

	public List<PlaceRes> getNearestPlacesDb() {
		List<PlaceEntity> placeEntities = new ArrayList<>();
		try {
			placeEntities = App.getDaoSession().queryBuilder(PlaceEntity.class)
					.orderDesc(PlaceEntityDao.Properties.RemoteId)
					.build()
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<PlaceRes> placeResList = new ArrayList<>();
		for (PlaceEntity placeEntity : placeEntities) {
			placeResList.add(new PlaceRes(placeEntity));
		}
		return placeResList;
	}

	public Call<ResponseBody> setAvatar(MultipartBody.Part part){
		return mApiService.uploadAvatar(part);
	}

	public Call<List<ServiceRes>> getServicesNetwork() {
		return mApiService.getServices();
	}

	public Call<AccessToken> loginUser(LoginReq loginReq) {
		return mApiService.loginUser(loginReq);
	}

	public Call<ProfileRes> getUserInfo() {
		return mApiService.getUserInfo();
	}

	public Call<AccessToken> signUpUser(SignUpReq signUpReq) {
		return mApiService.signUp(signUpReq);
	}

	public Call<VkProfileRes> getVkProfile(String token) {
		return mApiService.getVkProfile(Const.VK_BASE_URL +
				"users.get?fields=photo_200,contacts", token);
	}

	public Call<FacebookProfileRes> getFacebookProfile(String facebookToken) {
		return mApiService.getFacebookProfile(Const.FACEBOOK_BASE_URL +
				"me?fields=email,picture.width(200).height(200),first_name,last_name",
				facebookToken);
	}

	public TwitterProfileRes getTwitterProfile(TwitterSession twitterSession) {

		Response<User> userResponse;
		try {
			userResponse = new TwitterApiClient(twitterSession).getAccountService()
					.verifyCredentials(true, false, true).execute();
			User user = userResponse.body();
			if (user != null) {
				return new TwitterProfileRes(user.idStr, user.name, user.profileImageUrlHttps, user.email);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Call<UserRes> socialLogin(SocialLoginReq loginReq) {
		return mApiService.socialLogin(loginReq);
	}

	private void logoutSocial() {
		if (VKSdk.isLoggedIn())  {
			VKSdk.logout();
		} else if (TwitterCore.getInstance().getSessionManager().getActiveSession() != null) {
			TwitterCore.getInstance().getSessionManager().clearActiveSession();
		} else if (com.facebook.AccessToken.getCurrentAccessToken() != null) {
			LoginManager.getInstance().logOut();
		}
	}


}
