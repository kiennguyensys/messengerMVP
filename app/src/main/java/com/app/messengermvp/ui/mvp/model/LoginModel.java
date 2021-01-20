package com.app.messengermvp.ui.mvp.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nerdscorner.mvplib.events.model.BaseEventsModel;

public class LoginModel extends BaseEventsModel {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static class UserDetails {
        public static String id = "";
        public static String username = "";
        public static String email = "";
        public static String chatWithId = "";
        public static String chatWithUsername = "";
        public static String theirAvatar = "";
        public static String imageUrl = "";
    }

    public void signInWithEmail(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("signIn", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            bus.post(new LoginCompleteEvent(true, user.getEmail()));

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("signIn", "signInWithEmail:failure", task.getException());


                            bus.post(new LoginCompleteEvent(false, null));
                            // ...
                        }

                    }
                });
    }

    public static class LoginCompleteEvent {
        private boolean isSuccessful;
        private String userEmail;

        public LoginCompleteEvent(boolean isSuccessfulValue, String returnEmail) {
            isSuccessful = isSuccessfulValue;
            userEmail = returnEmail;
        }

        public boolean isTaskSuccessful() {
            return isSuccessful;
        }

        public String getCurrentUserEmail() {
            return userEmail;
        }
    }
}
