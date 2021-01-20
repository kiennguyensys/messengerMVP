package com.app.messengermvp.ui.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.nerdscorner.mvplib.events.activity.BaseActivity;

import com.app.messengermvp.R;
import com.app.messengermvp.ui.mvp.model.ChatWindowModel;
import com.app.messengermvp.ui.mvp.presenter.ChatWindowPresenter;
import com.app.messengermvp.ui.mvp.view.ChatWindowView;


import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ChatWindowActivity extends BaseActivity<ChatWindowPresenter> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_window_activity);

        presenter = new ChatWindowPresenter(
                new ChatWindowView(this),
                new ChatWindowModel()
        );

        presenter.initDatabase();
        presenter.initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.removeEventListeners(); //ref will be your node where you are setting Event Listener.
    }


}
