package com.app.messengermvp.ui.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.app.messengermvp.R;

import com.nerdscorner.mvplib.events.fragment.BaseFragment;
import com.app.messengermvp.ui.mvp.model.LoginModel;
import com.app.messengermvp.ui.mvp.presenter.LoginPresenter;
import com.app.messengermvp.ui.mvp.view.LoginView;

public class LoginFragment extends BaseFragment<LoginPresenter> {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new LoginPresenter(
                new LoginView(this),
                new LoginModel()
        );
        presenter.initView();
    }
}
