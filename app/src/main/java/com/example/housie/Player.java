package com.example.housie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class Player extends AppCompatActivity {
    Button showList, exitGame;
    TextView showNo, showCode;
    String show = null;

    final FirebaseDatabase rootDb = FirebaseDatabase.getInstance();
    DatabaseReference rootRef1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

        final String codeEntered = getIntent().getStringExtra("UCode");
        rootRef1  = rootDb.getReference().child("Tambola").child(codeEntered).child("CurrentNumber");
//        ValueEventListener changelistener ;

        showList = (Button) findViewById(R.id.showList);
        showNo = (TextView) findViewById(R.id.showNo);
        showCode = (TextView) findViewById(R.id.showCode);
        exitGame = (Button) findViewById(R.id.exitGame);


        showCode.setText(codeEntered);
        showNo.setText("Waiting for host to start!!");

        setData();
//        Log.i("showno",show);
//        showNo.setText(show);

//        final DatabaseReference rootRef1 = rootDb.getReference().child("Tambola").child(codeEntered).child("ListNos");

        showList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Player.this, ShowNos.class);
                intent.putExtra("UCode", codeEntered);
                startActivity(intent);
            }
        });

        exitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Player.this, StartScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }
//    protected void onStart() {
//
//        super.onStart();
//
//    }

    public void setData() {
//        final String[] no = {};

        rootRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = String.valueOf(dataSnapshot.getValue());
                Log.i("value",value);
                if(value.equals("0")) {
                    showNo.setText("Waiting for host to start!!");
//                    no[0] = "Waiting for host to start!!";
                }
                else {
                    showNo.setText(value);
//                    no[0] = value;
                }
//                showNo.setText(value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
//        return no[0];

    }
}