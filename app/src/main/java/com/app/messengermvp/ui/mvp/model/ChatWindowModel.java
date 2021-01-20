package com.app.messengermvp.ui.mvp.model;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nerdscorner.mvplib.events.model.BaseEventsModel;

import java.util.Map;

public class ChatWindowModel extends BaseEventsModel {

    DatabaseReference reference1, reference2;
    ChildEventListener childEventListener;

    public void initDatabase() {
        reference1 = FirebaseDatabase.getInstance().getReference("chatMessages/" + LoginModel.UserDetails.id + "_" + LoginModel.UserDetails.chatWithId);
        reference2 = FirebaseDatabase.getInstance().getReference("chatMessages/" + LoginModel.UserDetails.chatWithId + "_" + LoginModel.UserDetails.id);

        setValueListener();
    }

    public void setValueListener() {
        childEventListener = reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                //Log.i("url", dataSnapshot.getRef().toString())
                bus.post(new ChildAddedEvent(map));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
//                //Log.i("url", dataSnapshot.getRef().toString())
//                bus.post(new ChildAddedEvent(map));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void pushMessageToDatabase(Map<String, String> value, int referenceType) {
        if(referenceType == 1) {
            reference1.push().setValue(value);
        }

        if(referenceType == 2) {
            reference2.push().setValue(value);
        }
    }

    public void doSomething() {
        //Heavy work and then... notify the Presenter
        bus.post(new SomeModelActionEvent());
    }

    public void removeEventListeners() {
        reference1.removeEventListener(childEventListener);
    }

    public static class SomeModelActionEvent {
    }

    public static class ChildAddedEvent {
        private Map<String, String> dataValue;
        public ChildAddedEvent(Map<String, String> value) {
            dataValue = value;
        }

        public Map<String, String> getChildValue() {
            return dataValue;
        }
    }
}
