package com.app.messengermvp.ui.mvp.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.messengermvp.R;
import com.app.messengermvp.ui.mvp.model.FriendListModel.FriendObject;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class FriendListAdapter extends  RecyclerView.Adapter<FriendListAdapter.ViewHolder> {
    private List<FriendObject> itemList;
    private Context context;
    private ItemClickListener clickListener;
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv1,tv2;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.list_title);
            tv2 = (TextView) itemView.findViewById(R.id.list_desc);
            imageView = (ImageView)itemView.findViewById(R.id.list_avatar);
        }
    }

    public FriendListAdapter(Context context, List<FriendObject> itemList) {

        this.itemList = itemList;
        this.context = context;
    }
    @Override
    public FriendListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item, parent, false);
// set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(FriendListAdapter.ViewHolder holder, final int position){
        holder.tv1.setText(itemList.get(position).getName());
        holder.tv2.setText(itemList.get(position).getDesc());


//        URL myUrl = null;
//        try {
//            myUrl = new URL(itemList.get(position).getPhoto());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        InputStream inputStream = null;
//        try {
//            inputStream = (InputStream)myUrl.getContent();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Drawable drawable = Drawable.createFromStream(inputStream, null);

        Picasso.get()
                .load(itemList.get(position).getPhoto())
                .placeholder(android.R.drawable.screen_background_dark_transparent)
                .resize(50, 50)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) clickListener.itemClick(v, position);
            }
        });
        holder.imageView.setTag(holder);
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
    public interface ItemClickListener {
        public void itemClick(View view, int position);
    }
}