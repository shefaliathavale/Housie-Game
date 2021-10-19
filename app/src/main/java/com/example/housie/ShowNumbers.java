//Used for playing locally
package com.example.housie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowNumbers extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_numbers);
        TextView list = (TextView)findViewById(R.id.allnumbers);
        ArrayList<Integer> listnos = new ArrayList<Integer>();
        listnos = (ArrayList<Integer>) getIntent().getSerializableExtra("listofnos");
        String nos = "";
        if(listnos.size()==0)
            nos = "No numbers picked yet!!";
        else {
            for (int i = 0; i < (listnos.size()); i++) {
                if (i % 5 == 0 && i != 0)
                    nos = nos + "\n\n";
                nos = nos + String.valueOf(listnos.get(i)) + "\t\t\t\t";

            }
        }
        list.setText(nos);

    }
}

