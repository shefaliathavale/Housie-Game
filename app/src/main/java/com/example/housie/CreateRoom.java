package com.example.housie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CreateRoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_room);
        final int[] random = {0};
        final HashMap<String,String> map1 = new HashMap<>();
        final EditText name1 = (EditText)findViewById(R.id.enterName);
//        final String nameEntered = name1.getText().toString();

        Button genCode = (Button)findViewById(R.id.genCode);
        Button startGame = (Button)findViewById(R.id.startGame);
        final TextView showCode = (TextView)findViewById(R.id.showCode);
        final FirebaseDatabase rootDb = FirebaseDatabase.getInstance();

        genCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current_no = 23;
                int randomNo = generateCode();
                random[0] = randomNo;
                String nameEntered = name1.getText().toString();
                showCode.setText(String.valueOf(randomNo));
                DatabaseReference rootRef = rootDb.getReference().child("Tambola");
//                rootRef.child("Tambola").child(String.valueOf(randomNo)).setValue(1);
                map1.put(String.valueOf(nameEntered),"Creator");
                map1.put("CurrentNumber","0");
                map1.put("ListNos","0");
                FirebaseDatabase.getInstance().getReference().child("Tambola").child(String.valueOf(randomNo)).setValue(map1);

            }
        });

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateRoom.this,Creator.class);
                intent.putExtra("UCode", String.valueOf(random[0]));
                startActivity(intent);
                finish();
            }
        });

    }

    public int generateCode()  {
        int min = 100000;
        int max = 999999;
        int randomNumber = (int) (Math.random()*(max-min+1)+min);
        return randomNumber;
    }

}
