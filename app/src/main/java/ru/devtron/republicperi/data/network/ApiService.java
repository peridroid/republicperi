package ru.devtron.republicperi.data.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import ru.devtron.republicperi.data.network.requests.LoginReq;
import ru.devtron.republicperi.data.network.requests.SignUpReq;
import ru.devtron.republicperi.data.network.requests.SocialLoginReq;
import ru.devtron.republicperi.data.network.response.AccessToken;
import ru.devtron.republicperi.data.network.response.FacebookProfileRes;
import ru.devtron.republicperi.data.network.response.PlaceRes;
import ru.devtron.republicperi.data.network.response.ProfileRes;
import ru.devtron.republicperi.data.network.response.ServiceRes;
import ru.devtron.republicperi.data.network.response.TourRes;
import ru.devtron.republicperi.data.network.response.UserRes;
import ru.devtron.republicperi.data.network.response.VkProfileRes;
import ru.devtron.republicperi.utils.Const;

public interface ApiService {
	@GET("tours")
	Call<List<TourRes>> getTours();
	@GET("category/services")
	Call<List<ServiceRes>> getServices();
	@GET("places")
	Call<List<PlaceRes>> getPlaces(@Header(Const.ETAG_HEADER) String lastMod);
	@GET("me")
	Call<ProfileRes> getUserInfo();

	//region ==================== AUTH ====================

	@POST("signup")
	Call<AccessToken> signUp(@Body SignUpReq signUpReq);
	@POST("login")
	Call<AccessToken> loginUser(@Body LoginReq login);
	//social
	@POST("o-auth")
	Call<UserRes> socialLogin(@Body SocialLoginReq userSocReq);
	@GET
	Call<VkProfileRes> getVkProfile(@Url String baseVkApiUrl, @Query("access_token") String vkToken);
	@GET
	Call<FacebookProfileRes> getFacebookProfile(@Url String baseUrl, @Query("access_token") String accessToken);
	//endregion

}
