package ru.devtron.republicperi.data.network;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class PicassoCache {

    private Context mContext;
    private Picasso mPicassoInstance;

    public PicassoCache(Context context) {
        mContext = context;
        OkHttp3Downloader okHttp3Downloader = new OkHttp3Downloader(context);
        mPicassoInstance = new Picasso.Builder(context)
                .downloader(okHttp3Downloader)
                .indicatorsEnabled(true)
                .build();
        Picasso.setSingletonInstance(mPicassoInstance);
    }

    public Picasso getPicassoInstance() {
        if (mPicassoInstance == null){
            new PicassoCache(mContext);
            return mPicassoInstance;
        }
        return mPicassoInstance;
    }
}