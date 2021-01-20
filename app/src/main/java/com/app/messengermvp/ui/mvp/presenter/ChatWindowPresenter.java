package com.app.messengermvp.ui.mvp.presenter;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.app.messengermvp.R;
import com.app.messengermvp.ui.activities.ChatWindowActivity;
import com.app.messengermvp.ui.mvp.model.LoginModel;
import com.nerdscorner.mvplib.events.presenter.BaseActivityPresenter;

import com.app.messengermvp.ui.mvp.model.ChatWindowModel;
import com.app.messengermvp.ui.mvp.model.ChatWindowModel.SomeModelActionEvent;
import com.app.messengermvp.ui.mvp.view.ChatWindowView.SomeViewActionEvent;
import com.app.messengermvp.ui.mvp.view.ChatWindowView;
import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

public class ChatWindowPresenter extends BaseActivityPresenter<ChatWindowView, ChatWindowModel> {
    public ChatWindowPresenter(ChatWindowView view, ChatWindowModel model) {
        super(view, model);
    }

    @Subscribe
    public void onSomeViewAction(SomeViewActionEvent event) {
        model.doSomething();
    }

    @Subscribe
    public void onSomeModelAction(SomeModelActionEvent event) {
        view.doSomething();
    }

    @Subscribe
    public void onChildAddedEvent(ChatWindowModel.ChildAddedEvent event) {
        Map<String, String> map = event.getChildValue();
        String message = map.get("message").toString();
        String userName = map.get("user").toString();

        if(userName.equals(LoginModel.UserDetails.id)){
            view.addMessageBox(message, 1);
        }
        else{
            view.addMessageBox(message, 2);
        }
    }

    @Subscribe
    public void onSendMessageEvent(ChatWindowView.SendMessageEvent event) {
        Map<String, String> map = event.getMessageValue();
        model.pushMessageToDatabase(map, 1);
        model.pushMessageToDatabase(map,2);
    }

    public void initDatabase() {
        model.initDatabase();
    }

    public void removeEventListeners() { model.removeEventListeners(); }

    public void initView() {
        view.initView();
    }

}
