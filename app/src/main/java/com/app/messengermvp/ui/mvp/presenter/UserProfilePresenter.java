package com.app.messengermvp.ui.mvp.presenter;

import android.net.Uri;

import com.nerdscorner.mvplib.events.presenter.BaseActivityPresenter;

import com.app.messengermvp.ui.mvp.model.UserProfileModel;
import com.app.messengermvp.ui.mvp.view.UserProfileView;

import org.greenrobot.eventbus.Subscribe;

public class UserProfilePresenter extends BaseActivityPresenter<UserProfileView, UserProfileModel> {
    public UserProfilePresenter(UserProfileView view, UserProfileModel model) {
        super(view, model);
    }

    public void initView() { view.initView(); }

    public void setAvatarFilePath(Uri filePath) { model.setAvatarFilePath(filePath); }

    @Subscribe
    public void onSaveProfileEvent(UserProfileView.SaveProfileEvent saveProfileEvent) {
        model.updateProfileData(saveProfileEvent.getUsername(), saveProfileEvent.getAge());
    }

    @Subscribe
    public void onSaveProfileCompleteEvent(UserProfileModel.SaveProfileCompleteEvent saveProfileCompleteEvent) {
        view.hideProgressLoading();
        view.displayToast("Save Complete!");
    }

}
