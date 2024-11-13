package com.example.livestreamapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.zegocloud.uikit.prebuilt.livestreaming.ZegoUIKitPrebuiltLiveStreamingConfig;
import com.zegocloud.uikit.prebuilt.livestreaming.ZegoUIKitPrebuiltLiveStreamingFragment;

import java.util.jar.Attributes;

public class LiveActivity extends AppCompatActivity {
    String userID,name,liveID;
    boolean isHost;

    TextView txtLiveId;
    ImageView btnShare;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_live);
     /*   ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        txtLiveId = findViewById(R.id.textView4);
        btnShare = findViewById(R.id.btnShare);

        userID = getIntent().getStringExtra("user_id");
        name = getIntent().getStringExtra("name");
        liveID = getIntent().getStringExtra("Live_Id");
        isHost = getIntent().getBooleanExtra("host",false);

        txtLiveId.setText(liveID);
        addFragment();
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,"Join my Live ID - "+ liveID);
                startActivity(Intent.createChooser(intent,"Share Via"));

            }
        });
    }

    void addFragment(){
        ZegoUIKitPrebuiltLiveStreamingConfig config;
        if (isHost){
            config = ZegoUIKitPrebuiltLiveStreamingConfig.host();
        }else {
            config = ZegoUIKitPrebuiltLiveStreamingConfig.audience();
        }
        ZegoUIKitPrebuiltLiveStreamingFragment fragment = ZegoUIKitPrebuiltLiveStreamingFragment.newInstance(
                Contants.appId,Contants.appSign,userID,name,liveID,config);

        getSupportFragmentManager().beginTransaction().
                replace(R.id.liveContainer,fragment)
                .commitNow();;
    }
}