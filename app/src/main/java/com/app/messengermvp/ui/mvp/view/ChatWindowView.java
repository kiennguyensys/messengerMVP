package com.app.messengermvp.ui.mvp.view;

import android.media.Image;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.app.messengermvp.R;
import com.app.messengermvp.ui.mvp.model.LoginModel;
import com.nerdscorner.mvplib.events.view.BaseActivityView;
import com.nerdscorner.mvplib.events.activity.BaseActivity;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class ChatWindowView extends BaseActivityView {
    BaseActivity mActivity;
    LinearLayout layout;
    View layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;


    public ChatWindowView(BaseActivity activity) {
        super(activity);
        mActivity = activity;
    }

    public void initView() {
        layout = mActivity.findViewById(R.id.layout1);
        sendButton = mActivity.findViewById(R.id.sendButton);
        messageArea = mActivity.findViewById(R.id.messageArea);
        scrollView = mActivity.findViewById(R.id.scrollView);
        initOnClickListener();
    }

    public void initOnClickListener() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", LoginModel.UserDetails.id);
                    bus.post(new SendMessageEvent(map));
                    messageArea.setText("");
                }
            }
        });
    }

    public void doSomething() {
        //Update UI
    }

    public static class SomeViewActionEvent {
    }

    public void addMessageBox(String message, int type){
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 30.0f;


        if(type == 1) {
            lp2.gravity = Gravity.RIGHT;
            layout_2 = RelativeLayout.inflate(mActivity.getBaseContext(), R.layout.my_message, null);
        }
        else{
            lp2.gravity = Gravity.LEFT;
            layout_2 = RelativeLayout.inflate(mActivity.getBaseContext(), R.layout.their_message, null);
            TextView name = layout_2.findViewById(R.id.name);
            name.setText(LoginModel.UserDetails.chatWithUsername);
            ImageView userAvatar = layout_2.findViewById(R.id.avatar);
            Picasso.get()
                    .load(LoginModel.UserDetails.theirAvatar)
                    .placeholder(android.R.drawable.screen_background_dark_transparent)
                    .resize(34, 34)
                    .centerCrop()
                    .into(userAvatar);
        }

        TextView messageBody = layout_2.findViewById(R.id.message_body);
        messageBody.setText(message);

        layout_2.setLayoutParams(lp2);

        layout.addView(layout_2);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    public static class SendMessageEvent {
        private Map<String, String> messageValue;
        public SendMessageEvent(Map<String, String> value) {
            messageValue = value;
        }

        public Map<String, String> getMessageValue() {
            return messageValue;
        }
    }
}
