package com.app.messengermvp.ui.mvp.presenter;


import android.net.Uri;

import com.app.messengermvp.ui.mvp.model.RegisterModel;
import com.app.messengermvp.ui.mvp.view.RegisterView;
import com.nerdscorner.mvplib.events.presenter.BaseFragmentPresenter;

import org.greenrobot.eventbus.Subscribe;

public class RegisterPresenter extends BaseFragmentPresenter<RegisterView, RegisterModel> {
    public RegisterPresenter(RegisterView view, RegisterModel model) {
        super(view, model);
    }

    public void initView() { view.initView(); }

    @Subscribe
    public void onRegisterUserEvent(RegisterView.RegisterUserEvent registerUserEvent) {
        model.registerWithEmailAndPass(registerUserEvent.getEmail() , registerUserEvent.getPassWord());
    }

    @Subscribe
    public void onRegisterCompleteEvent(RegisterModel.RegisterCompleteEvent registerCompleteEvent) {
        view.hideProgressLoading();
        if(registerCompleteEvent.isTaskSuccessful()) {
            view.displayToast("registration successful");
            view.loginNavigating();
        } else {
            view.displayToast("username already exists");
        }
    }
}
