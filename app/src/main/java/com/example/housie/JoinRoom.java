package com.example.housie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class JoinRoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_room);
//        final HashMap<String,String> map1 = new HashMap<>();
        Button joinRoom = (Button)findViewById(R.id.join);
        final EditText name = (EditText)findViewById(R.id.enterTheName);
        final EditText code = (EditText)findViewById(R.id.enterCode);
        final FirebaseDatabase rootDb = FirebaseDatabase.getInstance();

        joinRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nameEntered = name.getText().toString();
                final String codeEntered = code.getText().toString();
                final DatabaseReference rootRef = rootDb.getReference().child("Tambola");
                rootRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        byte flag = 0;
                        String key = null;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            key = snapshot.getRef().getKey();
                            if (key.equals(codeEntered)) {
//                                map1.put(nameEntered, "Player");
                                FirebaseDatabase.getInstance().getReference().child("Tambola").child(codeEntered).child(nameEntered).setValue("Player");
//                                rootRef.child(codeEntered).setValue(map1);
                                flag = 1;
                                Toast.makeText(getApplicationContext(), "You joined the Room!!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(JoinRoom.this,Player.class);
                                intent.putExtra("UCode", codeEntered);
                                startActivity(intent);
                                finish();
                            }
                        }
                        if (flag == 0)
                            Toast.makeText(getApplicationContext(), "Wrong Code!!", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        throw databaseError.toException();
                    }
                });
            }
        });

    }

}
