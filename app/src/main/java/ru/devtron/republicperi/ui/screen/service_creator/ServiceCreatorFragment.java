package ru.devtron.republicperi.ui.screen.service_creator;

import android.Manifest;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.devtron.republicperi.BuildConfig;
import ru.devtron.republicperi.R;
import ru.devtron.republicperi.data.network.response.Image;
import ru.devtron.republicperi.ui.views.adapter.SimpleItemTouchHelperCallback;
import ru.devtron.republicperi.utils.Const;
import ru.devtron.republicperi.utils.PermissionUtils;
import ru.devtron.republicperi.utils.Utils;

import static android.app.Activity.RESULT_OK;

public class ServiceCreatorFragment extends Fragment
        implements RecyclerAddingImagesAdapter.OnDragStartListener,
        DialogInterface.OnClickListener {


    Unbinder unbinder;
    @BindView(R.id.name_service)
    EditText mNameService;
    @BindView(R.id.video_service)
    EditText mVideoService;
    @BindView(R.id.description_service)
    EditText mDescriptionService;
    @BindView(R.id.select_photo_btn)
    ImageButton mSelectPhotoBtn;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;



    private ItemTouchHelper mItemTouchHelper;
    RecyclerAddingImagesAdapter imageAdapter;
    private File photoFile = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_service, container, false);


        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageAdapter = new RecyclerAddingImagesAdapter(this);


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(imageAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(imageAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onDragStarted(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onItemRemoved(int size, Image item) {

    }

    @OnClick(R.id.select_photo_btn)
    void onSelectPhotoBtnClick(View view) {
        if (imageAdapter.getItemCount() < 6) {
            showPhotoSourceDialog();
        } else {
            Toast.makeText(getContext(), R.string.error_max_count, Toast.LENGTH_SHORT).show();
        }
    }

    public void showPhotoSourceDialog() {
        String source[] = {"Загрузить из галереи", "Сделать фото", "Отмена"};
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Установить фото");
        alertDialog.setItems(source, this);
        alertDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case 0:
                onClickGallery();
                break;
            case 1:
                onClickCamera();
                break;
            case 2:
                dialog.cancel();
                break;
        }
    }

    public void onClickGallery() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            loadPhotoFromGallery();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, Const.PERMISSION_REQUEST_WRITE_EXTERNAL);
        }
    }

    public void onClickCamera() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            loadPhotoFromCamera();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, Const.PERMISSION_REQUEST_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Const.PERMISSION_REQUEST_CAMERA:
                if (PermissionUtils.verifyPermissions(grantResults)) {
                    loadPhotoFromCamera();
                }
                break;
            case Const.PERMISSION_REQUEST_WRITE_EXTERNAL:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadPhotoFromGallery();
                }
                break;
        }
    }

    private void loadPhotoFromGallery() {
        Intent takeGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        takeGalleryIntent.setType("image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            takeGalleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            takeGalleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        }
        startActivityForResult(Intent.createChooser(takeGalleryIntent, getString(R.string.photo_picker_title)), Const.REQUEST_GALLERY_PICTURE);
    }

    void loadPhotoFromCamera() {
        Intent takeCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            photoFile = Utils.createTempFile(getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (photoFile != null) {
            Uri photoURI = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                photoURI = FileProvider.getUriForFile(getContext(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
            } else {
                photoURI = Uri.fromFile(photoFile);
            }
            takeCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            takeCaptureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(takeCaptureIntent, Const.REQUEST_CAMERA_PICTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Const.REQUEST_GALLERY_PICTURE:
                if (resultCode == RESULT_OK && data != null) {
                    if (data.getData() != null) {
                        imageAdapter.addNewItem(new Image(data.getData()));
                    } else if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            imageAdapter.addNewItem(new Image(item.getUri()));
                        }
                    }

                }
                break;
            case Const.REQUEST_CAMERA_PICTURE:
                if (resultCode == RESULT_OK && photoFile != null) {
                    imageAdapter.addNewItem(new Image(Uri.fromFile(photoFile)));
                }
                break;
        }
    }


}
