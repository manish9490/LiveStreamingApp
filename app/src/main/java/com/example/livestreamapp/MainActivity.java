package com.example.livestreamapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    Button btnStartNewLive;
    TextInputEditText editLiveId, editName;

    String liveId,name,userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
       /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
        btnStartNewLive = findViewById(R.id.btnStartLive);
        editLiveId = findViewById(R.id.edtLiveId);
        editName = findViewById(R.id.edtName);

        editLiveId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                liveId = editLiveId.getText().toString();
                if (liveId.length()==0){
                    btnStartNewLive.setText("join Live");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnStartNewLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editName.getText().toString();
                liveId = editLiveId.getText().toString();
                if (name.isEmpty()){
                    editName.setError("Name is Required");
                    editName.requestFocus();
                    return;
                }
             if (liveId.length()>0 && liveId.length()!=5){
                    editLiveId.setError("Invalid Live Id");
                    editLiveId.requestFocus();
                    return;
                }

                startMeeting();

            }
        });
    }
    void startMeeting(){
        boolean isHost = true;
        if (liveId.length()==5){
            isHost = false;
        }else {
            liveId = generateLiveId();
        }

        userID = UUID.randomUUID().toString();

        Intent intent = new Intent(MainActivity.this,LiveActivity.class);
        intent.putExtra("user_id", userID);
        intent.putExtra("name",    name);
        intent.putExtra("Live_Id" , liveId);
        intent.putExtra("host", isHost);
        startActivity(intent);
    }
    String generateLiveId(){
        StringBuilder id = new StringBuilder();
        while (id.length()!=5){
            int random = new Random().nextInt(10);
            id.append(random);
        }
        return id.toString();
    }
}