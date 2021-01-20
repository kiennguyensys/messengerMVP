package com.app.messengermvp.ui.mvp.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.messengermvp.R;

import com.app.messengermvp.ui.fragments.LoginFragment;
import com.nerdscorner.mvplib.events.fragment.BaseFragment;
import com.nerdscorner.mvplib.events.view.BaseFragmentView;

import java.io.IOException;

public class RegisterView extends BaseFragmentView {
    BaseFragment mFragment;
    EditText email, password;
    Button registerButton;
    String user, pass;
    ProgressDialog pd;

    private final int PICK_IMAGE_REQUEST = 111;



    public RegisterView(BaseFragment fragment) {
        super(fragment);
        mFragment = fragment;
    }
    public void initView() {
        email = (EditText)mFragment.getView().findViewById(R.id.email);
        password = (EditText)mFragment.getView().findViewById(R.id.password);
        registerButton = (Button)mFragment.getView().findViewById(R.id.registerButton);
        pd = new ProgressDialog(mFragment.getContext());


        setBtnsOnClickListener();
    }

    public void setBtnsOnClickListener() {

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("shit", "register Event");
                user = email.getText().toString();
                pass = password.getText().toString();

                if(user.equals("")){
                    email.setError("can't be blank");
                }
                else if(pass.equals("")){
                    password.setError("can't be blank");
                }
                else if(user.length()<5){
                    email.setError("at least 5 characters long");
                }
                else if(pass.length()<5){
                    password.setError("at least 5 characters long");
                }
                else {

                    pd.setMessage("Loading...");
                    pd.show();

                    bus.post(new RegisterUserEvent(user, pass));
                }
            }
        });
    }

    public void hideProgressLoading() {
        pd.dismiss();
    }

    public void displayToast(String text) {
        Toast.makeText(mFragment.getContext(), text, Toast.LENGTH_LONG).show();
    }

    public void loginNavigating () {
        mFragment.startActivity(new Intent(mFragment.getContext(), LoginFragment.class));
    }



    public static class RegisterUserEvent {
        private String mEmail, passWord;
        public RegisterUserEvent(String email, String password) {
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
