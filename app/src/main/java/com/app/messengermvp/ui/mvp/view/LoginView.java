package com.app.messengermvp.ui.mvp.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.fragment.app.Fragment;

import com.app.messengermvp.R;
import com.app.messengermvp.ui.activities.FriendListActivity;

import com.app.messengermvp.ui.fragments.RegisterFragment;
import com.nerdscorner.mvplib.events.fragment.BaseFragment;
import com.nerdscorner.mvplib.events.view.BaseFragmentView;


public class LoginView extends BaseFragmentView {
    Fragment mFragment;
    EditText email, password;
    Button loginButton;
    String user, pass;
    ProgressDialog pd;
    public LoginView(BaseFragment fragment) {
        super(fragment);
        mFragment = fragment;
    }
    public void initView() {
        email = mFragment.getView().findViewById(R.id.email);
        password = mFragment.getView().findViewById(R.id.password);
        loginButton = mFragment.getView().findViewById(R.id.loginButton);
        pd = new ProgressDialog(mFragment.getContext());
        setOnClickBtnsListener();
    }

    public void setOnClickBtnsListener() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = email.getText().toString();
                pass = password.getText().toString();

                if(user.equals("")){
                    email.setError("can't be blank");
                }
                else if(pass.equals("")){
                    password.setError("can't be blank");
                }
                else{

                    pd.setMessage("Loading...");
                    pd.show();

                    bus.post(new LoginClickEvent(user, pass));
                }

            }
        });
    }

    public void hideProgressLoading() {
        pd.hide();
    }

    public void startFriendListIntent() {
        mFragment.startActivity(new Intent(mFragment.getContext(), FriendListActivity.class));
    }

    public void displayToast(String text) {
        Toast.makeText(mFragment.getContext(), text,
                Toast.LENGTH_SHORT).show();
    }

    public static class LoginClickEvent {
        private String mEmail, passWord;
        public LoginClickEvent(String email, String password) {
            mEmail = email;
            passWord = password;
        }

        public String getEmail() {
        return mEmail;
        }

        public String getPassWord() {
            return passWord;
        }
    }
}
