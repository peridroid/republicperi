package ru.devtron.republicperi.ui.screen.profile_edit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.devtron.republicperi.R;
import ru.devtron.republicperi.data.CommonRepository;
import ru.devtron.republicperi.data.network.response.ProfileRes;

public class ProfileEditFragment extends Fragment {
    CircleImageView avatarImageView;
    EditText emailEt;
    EditText nameEt;
    EditText surnameEt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        avatarImageView = view.findViewById(R.id.avatar_image_view);
        emailEt = view.findViewById(R.id.email_et);
        nameEt = view.findViewById(R.id.name_et);
        surnameEt = view.findViewById(R.id.surname_et);

        getUserInfo();
        return view;
    }

    private void getUserInfo() {
        CommonRepository.getInstance().getUserInfo().enqueue(new Callback<ProfileRes>() {
            @Override
            public void onResponse(Call<ProfileRes> call, Response<ProfileRes> response) {
                if (response.isSuccessful()) {
                    initUserData(response.body());
                }
            }

            @Override
            public void onFailure(Call<ProfileRes> call, Throwable t) {

            }
        });
    }

    private void initUserData(ProfileRes profile) {
        if (!TextUtils.isEmpty(profile.getImg())) {
            Picasso.with(getContext())
                    .load(profile.getImg())
                    .resizeDimen(R.dimen.picture_width, R.dimen.picture_height)
                    .into(avatarImageView);
        }
        emailEt.setText(profile.getEmail());
        nameEt.setText(profile.getName());
        surnameEt.setText(profile.getSurname());
    }
}
