package com.app.messengermvp.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.messengermvp.ui.mvp.model.LoginModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nerdscorner.mvplib.events.activity.BaseActivity;

import com.app.messengermvp.R;
import com.app.messengermvp.ui.mvp.model.FriendListModel;
import com.app.messengermvp.ui.mvp.presenter.FriendListPresenter;
import com.app.messengermvp.ui.mvp.view.FriendListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class FriendListActivity extends BaseActivity<FriendListPresenter> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_list_activity);

//        usersDB = FirebaseDatabase.getInstance().getReference().child("users");
//        Log.i("url", usersDB.getRef().toString());
//
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.i("user", "data changed");
//                //doOnSuccess(user);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.i("The read failed: ", String.valueOf(databaseError.getCode()));
//            }
//        };
//        usersDB.addListenerForSingleValueEvent(valueEventListener);


        presenter = new FriendListPresenter(
                new FriendListView(this),
                new FriendListModel()
        );

        presenter.initView();
        presenter.requestListData();
    }



}
