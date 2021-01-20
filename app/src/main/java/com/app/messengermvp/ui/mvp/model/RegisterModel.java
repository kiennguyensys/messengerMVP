package com.app.messengermvp.ui.mvp.model;

import android.app.ProgressDialog;
import android.net.Uri;
import android.util.Log;
import java.util.UUID;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.app.messengermvp.ui.mvp.model.FriendListModel.User;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nerdscorner.mvplib.events.model.BaseEventsModel;

import java.util.HashMap;
import java.util.Map;

public class RegisterModel extends BaseEventsModel {
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private String username;
    FirebaseStorage storage;
    StorageReference storageReference = storage.getInstance().getReference();

    public void registerWithEmailAndPass(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("register", "createUserWithEmail:success");
                            username = email.split("@")[0];
                            User user = new User(username, email);

                            reference.child("users").child(username).setValue(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Write was successful!
                                        // ...
                                            Log.d("firebaseSuccess", "fucking success");

                                            bus.post(new RegisterCompleteEvent(true));
                                            setUserDefaultImageData();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Write failed
                                            // ...
                                            Log.d("firebaseFailed", "fucking write false");
                                        }
                                    });



                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("register", "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
                            bus.post(new RegisterCompleteEvent(false));

                        }

                        // ...
                    }
                });
    }


    public void setUserDefaultImageData() {
        String defaultImgUrl =
                "https://media.istockphoto.com/vectors/avatar-icon-design-for-man-vector-id648229986?b=1&k=6&m=648229986&s=612x612&w=0&h=q63d9btUl0vzNubqWExOzp_7pKM6zC9aaQ0R157KPmw=";
        reference.child("users").child(username).child("imageUrl").setValue(defaultImgUrl)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        // ...
                        Log.d("firebaseSuccess", "fucking success");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        // ...
                        Log.d("firebaseFailed", "fucking write false");
                    }
                });
    }


    public static class RegisterCompleteEvent {
        private boolean isSuccessful;

        public RegisterCompleteEvent(boolean isSuccessfulValue) {
            isSuccessful = isSuccessfulValue;
        }

        public boolean isTaskSuccessful() {
            return isSuccessful;
        }
    }


}
