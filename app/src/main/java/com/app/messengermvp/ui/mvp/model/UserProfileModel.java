package com.app.messengermvp.ui.mvp.model;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nerdscorner.mvplib.events.model.BaseEventsModel;

import java.util.UUID;

public class UserProfileModel extends BaseEventsModel {
    private Uri mFilePath;
    private String email_prefix;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseStorage storage;
    StorageReference storageReference = storage.getInstance().getReference();

    public void setAvatarFilePath(Uri filePath) {
        mFilePath = filePath;
    }

    public void updateProfileData(String username, String age) {
        email_prefix = LoginModel.UserDetails.email.split("@")[0];
        reference.child("users").child(email_prefix).child("username").setValue(username);
        reference.child("users").child(email_prefix).child("age").setValue(age)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        // ...
                        bus.post(new SaveProfileCompleteEvent(true));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        // ...
                    }
                });

        uploadImage();
    }

    public void uploadImage() {
        if (mFilePath != null) {
            StorageReference ref
                    = storageReference
                    .child(
                            "userImages/"
                                    + UUID.randomUUID().toString());


            ref.putFile(mFilePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    ref.getDownloadUrl()
                                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    // Got the download URL for 'path/to/aFile'
                                                    setUserImageData(uri);
                                                    Log.d("imagePath", uri.toString());
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception exception) {
                                                    // Handle any errors
                                                }
                                            });

                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {

                                }
                            });
        }

    }

    public void setUserImageData(Uri uri) {
        reference.child("users").child(email_prefix).child("imageUrl").setValue(uri.toString());
    }

    public static class SaveProfileCompleteEvent {
        private boolean isSuccessful;

        public SaveProfileCompleteEvent(boolean isSuccessfulValue) {
            isSuccessful = isSuccessfulValue;
        }

        public boolean isTaskSuccessful() {
            return isSuccessful;
        }
    }
}
