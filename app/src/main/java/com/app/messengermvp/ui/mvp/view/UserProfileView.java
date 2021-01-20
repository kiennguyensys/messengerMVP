package com.app.messengermvp.ui.mvp.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.messengermvp.MainActivity;
import com.app.messengermvp.R;

import com.app.messengermvp.ui.activities.UserProfileActivity;
import com.app.messengermvp.ui.mvp.model.LoginModel;
import com.nerdscorner.mvplib.events.view.BaseActivityView;
import com.nerdscorner.mvplib.events.activity.BaseActivity;
import com.squareup.picasso.Picasso;

public class UserProfileView extends BaseActivityView {
    BaseActivity mActivity;
    EditText nameInput, ageInput;
    Button uploadAvatarBtn, saveBtn, logOutBtn;
    ProgressDialog pd;
    ImageView imageView;

    public UserProfileView(BaseActivity activity) {
        super(activity);
        mActivity = activity;
    }
    public void initView() {
        nameInput = mActivity.findViewById(R.id.nameInput);
        ageInput = mActivity.findViewById(R.id.ageInput);
        imageView = mActivity.findViewById(R.id.imageView);
        uploadAvatarBtn = mActivity.findViewById(R.id.uploadAvatarBtn);
        saveBtn = mActivity.findViewById(R.id.saveBtn);
        logOutBtn = mActivity.findViewById(R.id.logOutBtn);

        pd = new ProgressDialog(mActivity);
        setBtnsOnClickListener();
        setCurrentUsernameAndAvatar();
    }

    public void setBtnsOnClickListener() {
        uploadAvatarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onUploadPhotoTapped(v);
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onSaveTapped(v);
            }
        });
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onLogoutTapped(v);
            }
        });
    }

    public void setCurrentUsernameAndAvatar() {
        nameInput.setText(LoginModel.UserDetails.username);
        Picasso.get()
                .load(LoginModel.UserDetails.imageUrl)
                .placeholder(android.R.drawable.screen_background_dark_transparent)
                .resize(200, 200)
                .into(imageView);
    }

    public static final int PICK_IMAGE = 1;

    public void onUploadPhotoTapped(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }


    public void onLogoutTapped(View view) {
        LoginModel.UserDetails.username = "";
        LoginModel.UserDetails.email = "";
        LoginModel.UserDetails.chatWithId = "";
        LoginModel.UserDetails.chatWithUsername = "";
        LoginModel.UserDetails.theirAvatar = "";
        LoginModel.UserDetails.imageUrl = "";
        mActivity.startActivity(new Intent(mActivity, MainActivity.class));
    }

    public void onSaveTapped(View view) {
        String username = nameInput.getText().toString();
        String age = ageInput.getText().toString();

        if(username.equals("")){
            nameInput.setError("can't be blank");
        }
        else if(age.equals("")){
            ageInput.setError("can't be blank");
        }
        else if(!username.matches("[A-Za-z0-9]+")){
            nameInput.setError("only alphabet or number allowed");
        }
        else if(username.length()<5){
            nameInput.setError("at least 5 characters long");
        }
        else if(!age.matches("[0-9]+")){
            ageInput.setError("only number allowed");
        } else {
            pd.setMessage("Saving...");
            pd.show();
            LoginModel.UserDetails.username = username;

            bus.post(new SaveProfileEvent(username, age));
        }
    }

    public void hideProgressLoading() {
        pd.dismiss();
    }

    public void displayToast(String text) {
        Toast.makeText(mActivity, text, Toast.LENGTH_LONG).show();
    }

    public static class SaveProfileEvent {
        private String mUsername, mAge;

        public SaveProfileEvent(String username, String age) {
            mUsername = username;
            mAge = age;
        }

        public String getUsername() {
            return mUsername;
        }

        public String getAge() {
            return mAge;
        }

    }
}
