package com.app.messengermvp.ui.mvp.presenter;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nerdscorner.mvplib.events.presenter.BaseActivityPresenter;

import com.app.messengermvp.ui.mvp.model.FriendListModel;
import com.app.messengermvp.ui.mvp.view.FriendListView;

import org.greenrobot.eventbus.Subscribe;

public class FriendListPresenter extends BaseActivityPresenter<FriendListView, FriendListModel> {
    public FriendListPresenter(FriendListView view, FriendListModel model) {
        super(view, model);
    }

    public void initView(){ view.initView(); }

    public void requestListData() {
        RequestQueue rQueue = Volley.newRequestQueue(view.getActivity());
        rQueue.add(model.getDataStringRequest(""));
    }

    @Subscribe
    public void onDataResponseEvent(FriendListModel.DataResponseEvent dataResponseEvent) {
        view.doOnSuccess(dataResponseEvent.getResponseString());
    }

    @Subscribe
    public void onQueryUserEvent(FriendListView.QueryUserEvent queryUserEvent) {
        model.queryUser(queryUserEvent.getQuery());
    }

}
