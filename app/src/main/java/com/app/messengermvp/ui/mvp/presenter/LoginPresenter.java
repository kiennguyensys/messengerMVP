package com.app.messengermvp.ui.mvp.presenter;

import com.app.messengermvp.ui.mvp.model.LoginModel;
import com.app.messengermvp.ui.mvp.view.LoginView;
import com.nerdscorner.mvplib.events.presenter.BaseFragmentPresenter;

import org.greenrobot.eventbus.Subscribe;

public class LoginPresenter extends BaseFragmentPresenter<LoginView, LoginModel> {
    public LoginPresenter(LoginView view, LoginModel model) {
        super(view, model);
    }

    public void initView() { view.initView(); }

    @Subscribe
    public void onLoginClickEvent(LoginView.LoginClickEvent loginClickEvent) {
        String email = loginClickEvent.getEmail();
        String password = loginClickEvent.getPassWord();
        model.signInWithEmail(email, password);
    }

    @Subscribe
    public void onLoginCompleteEvent(LoginModel.LoginCompleteEvent loginCompleteEvent) {
        view.hideProgressLoading();
        if(loginCompleteEvent.isTaskSuccessful()) {
            LoginModel.UserDetails.email = loginCompleteEvent.getCurrentUserEmail();
            LoginModel.UserDetails.id = loginCompleteEvent.getCurrentUserEmail().split("@")[0];
            view.startFriendListIntent();
        } else {
            view.displayToast("Authentication Failed. ");
        }
    }
}
