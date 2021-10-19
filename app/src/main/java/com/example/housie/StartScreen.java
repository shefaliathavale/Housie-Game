package com.example.housie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);
        Button joinRoom = (Button)findViewById(R.id.joinRoom);
        Button createRoom = (Button)findViewById(R.id.createRoom);
        Button playLocally = (Button)findViewById(R.id.playLocal);

        joinRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartScreen.this,JoinRoom.class);
                startActivity(intent);

            }
        });

        createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartScreen.this,CreateRoom.class);
                startActivity(intent);

            }
        });

        playLocally.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartScreen.this,MainActivity.class);
                startActivity(intent);

            }
        });


    }
}