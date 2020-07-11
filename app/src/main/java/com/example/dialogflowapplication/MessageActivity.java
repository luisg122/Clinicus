
package com.example.dialogflowapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import com.example.dialogflowapplication.databinding.MessageLayoutBinding;
import com.getstream.sdk.chat.StreamChat;
import com.getstream.sdk.chat.enums.FilterObject;
import com.getstream.sdk.chat.rest.User;
import com.getstream.sdk.chat.rest.core.Client;
import com.getstream.sdk.chat.viewmodel.ChannelListViewModel;
import java.util.HashMap;

import static com.getstream.sdk.chat.enums.Filters.eq;

public class MessageActivity extends AppCompatActivity {

    public static final String EXTRA_CHANNEL_TYPE = "com.example.dialogflowapplication.CHANNEL_TYPE";
    public static final String EXTRA_CHANNEL_ID = "com.example.dialogflowapplication.CHANNEL_ID";

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setup the client using the example API key
        // normally you would call init in your Application class and not the activity
        StreamChat.init("2uutvf5bnc72", this.getApplicationContext());
        Client client = StreamChat.getInstance(this.getApplication());
        HashMap<String, Object> extraData = new HashMap<>();
        extraData.put("name", "Luis Gualpa");
        extraData.put("image", "https://bit.ly/2TIt8NR");
        User currentUser = new User("username", extraData);
        // User token is typically provided by your server when the user authenticates
        // Every user should be able to have their very own toke, but how to get token from backend server post token generation
        client.setUser(currentUser,"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoidXNlcm5hbWUifQ.Q7Sb2y-0CoQTfX5Mdkuhq4GXSOh2iMcC7nvqgr0OHAA");

        // we're using data binding in this example
        MessageLayoutBinding binding =
                DataBindingUtil.setContentView(this, R.layout.message_layout);
        setUpToolbar();
        // Specify the current activity as the lifecycle owner.
        binding.setLifecycleOwner(this);

        // most the business logic for chat is handled in the ChannelListViewModel view model
        ChannelListViewModel viewModel = ViewModelProviders.of(this).get(ChannelListViewModel.class);
        binding.setViewModel(viewModel);
        binding.channelList.setViewModel(viewModel, this);

        // query all channels of type messaging
        FilterObject filter = eq("type", "messaging");
        viewModel.setChannelFilter(filter);

        // click handlers for clicking a user avatar or channel
        binding.channelList.setOnChannelClickListener(channel -> {
            Intent intent = new Intent(MessageActivity.this, ChannelActivity.class);
            intent.putExtra(EXTRA_CHANNEL_TYPE, channel.getType());
            intent.putExtra(EXTRA_CHANNEL_ID, channel.getId());
            startActivity(intent);
        });
        binding.channelList.setOnUserClickListener(user -> {
            // open your user profile
        });
    }

    public void setUpToolbar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Olli's Message Space");
        toolbar.setNavigationIcon(R.drawable.ic_white_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}