package com.app.messengermvp.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.messengermvp.R;

import com.nerdscorner.mvplib.events.fragment.BaseFragment;
import com.app.messengermvp.ui.mvp.model.RegisterModel;
import com.app.messengermvp.ui.mvp.presenter.RegisterPresenter;
import com.app.messengermvp.ui.mvp.view.RegisterView;

import java.io.IOException;

public class RegisterFragment extends BaseFragment<RegisterPresenter> {
    private final int PICK_IMAGE_REQUEST = 111;
    private Uri filePath;
    ImageView imageView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new RegisterPresenter(
                new RegisterView(this),
                new RegisterModel()
        );
        presenter.initView();
    }

}
