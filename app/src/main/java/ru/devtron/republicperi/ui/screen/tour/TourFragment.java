package ru.devtron.republicperi.ui.screen.tour;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.devtron.republicperi.R;
import ru.devtron.republicperi.data.CommonRepository;
import ru.devtron.republicperi.data.network.response.BaseResponse;
import ru.devtron.republicperi.data.network.response.TourRes;

public class TourFragment extends Fragment {
    private static final String EXTRA_TRANSITION_NAME = "transition_name";

    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.make_tour_button)
    Button mMakeTourButton;
    @BindView(R.id.city)
    TextView mCity;
    @BindView(R.id.time)
    TextView mTime;
    @BindView(R.id.error_tv)
    TextView mErrorTv;
    @BindView(R.id.reload_btn)
    Button mReloadBtn;
    @BindView(R.id.error_ll)
    LinearLayout mErrorLl;
    Unbinder unbinder;

    private BaseResponse mModel;

    public static TourFragment newInstance(BaseResponse responseModel, String transitionName) {
        Bundle args = new Bundle();
        args.putString(EXTRA_TRANSITION_NAME, transitionName);

        TourFragment fragment = new TourFragment();
        fragment.mModel = responseModel;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tour, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);
        String transitionName = getArguments().getString(EXTRA_TRANSITION_NAME);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mImage.setTransitionName(transitionName);
        }
        initFields();
    }

    private void initFields() {
        if (mModel instanceof TourRes) {
            if (!((TourRes) mModel).getImages().isEmpty()) {
                CommonRepository.getInstance().getPicasso()
                        .load(((TourRes) mModel).getImages().get(0).src)
                        .into(mImage, new Callback() {
                            @Override
                            public void onSuccess() {
                                startPostponedEnterTransition();
                            }

                            @Override
                            public void onError() {
                                startPostponedEnterTransition();
                            }
                        });
            } else {
                CommonRepository.getInstance().getPicasso().load(R.drawable.ui_appbar_light)
                        .into(mImage, new Callback() {
                            @Override
                            public void onSuccess() {
                                startPostponedEnterTransition();
                            }

                            @Override
                            public void onError() {
                                startPostponedEnterTransition();
                            }
                        });
            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
