package com.app.messengermvp.ui.mvp.model;

import android.util.Log;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nerdscorner.mvplib.events.model.BaseEventsModel;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class FriendListModel extends BaseEventsModel {
    DatabaseReference usersDB = FirebaseDatabase.getInstance().getReference("users");

    public StringRequest getDataStringRequest(String query) {
        String url = "https://messenger-mvp-bc381-default-rtdb.firebaseio.com/users" + query + ".json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                bus.post(new DataResponseEvent(s));
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        return request;
    }

    public void queryUser(String query) {
        usersDB.orderByChild("username").startAt(query).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Map<String, String>> list = new HashMap<>();
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    Log.d("query", userSnapshot.getKey());
                    list.put(userSnapshot.getKey(), (Map<String, String>)userSnapshot.getValue());
                }

                JSONObject json = new JSONObject(list);
                bus.post(new DataResponseEvent(json.toString()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.w(TAG, "onCancelled", databaseError.toException());
            }

        });

    }

    public static class DataResponseEvent {
        private String string;

        public DataResponseEvent(String s) {
            string = s;
        }

        public String getResponseString() {
            return string;
        }

    }

    public static class FriendObject {
        private String name;
        private String desc;
        private String photo;
        private String id;
        public FriendObject(String id, String name, String desc , String photoURL) {
            this.id = id;
            this.name = name;
            this.desc = desc;
            this.photo = photoURL;
        }
        public String getId() { return id; }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getDesc() {
            return desc;
        }
        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPhoto() {
            return photo;
        }
        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }

    public static class User {

        public String username;
        public String email;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String username, String email) {
            this.username = username;
            this.email = email;
        }

    }

    public static class RxSearchObservable {

        public static Observable<String> fromSearchView(@NonNull final SearchView searchView) {
            final BehaviorSubject<String> subject = BehaviorSubject.create();

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    subject.onComplete();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (!newText.isEmpty()) {
                        subject.onNext(newText);
                    }
                    return true;
                }
            });

            return subject;
        }
    }

}
