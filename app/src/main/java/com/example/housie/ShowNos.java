//Used for online multiplayer game

package com.example.housie;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowNos extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_numbers);
        final TextView list = (TextView)findViewById(R.id.allnumbers);

        final String codeEntered = getIntent().getStringExtra("UCode");
        final FirebaseDatabase rootDb = FirebaseDatabase.getInstance();
        final DatabaseReference rootRef1 = rootDb.getReference().child("Tambola").child(codeEntered).child("ListNos");

        rootRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String show = null;

                String value = String.valueOf(dataSnapshot.getValue());
                Log.i("value",value);
                String nos="";

                if(value.equals("0"))
                    nos = "No number picked yet!!";

                else {
                    show = value.replaceAll("\\[", "");
                    show = show.replaceAll("]", "");
                    String str[] = show.split(",");
                    for(int i=0;i<str.length;i++)
                    {
                        if (i % 5 == 0 && i != 0)
                            nos = nos + "\n\n";
                        nos = nos + str[i] + "\t\t\t\t";
                    }
                }
                list.setText(nos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}