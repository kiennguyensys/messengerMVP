package com.app.messengermvp.ui.mvp.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.messengermvp.MainActivity;
import com.app.messengermvp.R;
import com.app.messengermvp.ui.activities.ChatWindowActivity;

import com.app.messengermvp.ui.activities.UserProfileActivity;
import com.app.messengermvp.ui.mvp.model.FriendListModel;
import com.app.messengermvp.ui.mvp.model.FriendListModel.FriendObject;
import com.app.messengermvp.ui.mvp.model.LoginModel;
import com.nerdscorner.mvplib.events.view.BaseActivityView;
import com.nerdscorner.mvplib.events.activity.BaseActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FriendListView extends BaseActivityView implements FriendListAdapter.ItemClickListener {
    BaseActivity mActivity;
    TextView noUsersText;
    CircleImageView imageView;
    SearchView searchView;
    ArrayList<FriendObject> al = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;
    private RecyclerView mRecyclerView;
    private FriendListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<FriendObject> rowListItem;

    public FriendListView(BaseActivity activity) {
        super(activity);
        mActivity = activity;
    }

    public void initView() {
        mRecyclerView = (RecyclerView) mActivity.findViewById(R.id.recyclerView);
        noUsersText = (TextView)mActivity.findViewById(R.id.noUsersText);
        imageView = mActivity.findViewById(R.id.user_avatar);
        searchView = mActivity.findViewById(R.id.searchView);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);

        pd = new ProgressDialog(mActivity);
        pd.setMessage("Loading...");
        pd.show();

        setBtnsOnClickListener();
    }

    public void setBtnsOnClickListener() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(new Intent(mActivity, UserProfileActivity.class));
            }
        });

        FriendListModel.RxSearchObservable.fromSearchView(searchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(item -> item.length() > 1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> {
                    bus.post(new QueryUserEvent(query));
//                    adapter.setNamesList(namesAPI.searchForName(query));
//                    adapter.notifyDataSetChanged();
//                    apiCallsTextView.setText("API CALLS: " + apiCalls++);
                });
    }


    @Override
    public void itemClick(View view, int position) {
        LoginModel.UserDetails.chatWithUsername = al.get(position).getName();
        LoginModel.UserDetails.chatWithId = al.get(position).getId();
        LoginModel.UserDetails.theirAvatar = al.get(position).getPhoto();
        mActivity.startActivity(new Intent(mActivity, ChatWindowActivity.class));

    }

    public void doOnSuccess(String s){
        //Log.d("json", s);
        al = new ArrayList<>();
        totalUsers = 0;

        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";

            while(i.hasNext()){
                key = i.next().toString();

                if(!key.equals(LoginModel.UserDetails.id)) {
                    al.add(new FriendObject(key, obj.getJSONObject(key).getString("username"), "friend", obj.getJSONObject(key).getString("imageUrl")));
                } else {
                    setCurrentUsernameAndAvatar(obj.getJSONObject(key).getString("username"), obj.getJSONObject(key).getString("imageUrl"));
                }

                totalUsers++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(totalUsers <1){
            noUsersText.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
        else{
            noUsersText.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }

        mAdapter = new FriendListAdapter(mActivity ,al);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);

        pd.dismiss();
    }

    public void setCurrentUsernameAndAvatar(String username, String photo) {
        LoginModel.UserDetails.username = username;
        LoginModel.UserDetails.imageUrl = photo;
        Picasso.get()
                .load(photo)
                .placeholder(android.R.drawable.screen_background_dark_transparent)
                .resize(50, 50)
                .into(imageView);
    }

    public static class QueryUserEvent {
        private String query;
        public QueryUserEvent(String query) {
            this.query = query;
        }

        public String getQuery() {
            return query;
        }
    }
}
