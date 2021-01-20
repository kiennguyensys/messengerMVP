package com.app.messengermvp.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.nerdscorner.mvplib.events.activity.BaseActivity;

import com.app.messengermvp.R;
import com.app.messengermvp.ui.mvp.model.UserProfileModel;
import com.app.messengermvp.ui.mvp.presenter.UserProfilePresenter;
import com.app.messengermvp.ui.mvp.view.UserProfileView;

import java.io.IOException;

public class UserProfileActivity extends BaseActivity<UserProfilePresenter> {
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_activity);

        presenter = new UserProfilePresenter(
                new UserProfileView(this),
                new UserProfileModel()
        );

        presenter.initView();
    }

    public static final int PICK_IMAGE = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedImage = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    imageView = findViewById(R.id.imageView);
                    imageView.setImageBitmap(bitmap);
                    presenter.setAvatarFilePath(selectedImage);
                } catch (IOException ex) {
                    Log.i("SelectPhoto", ex.getMessage());
                }
            }
        }
    }
}
