package com.example.housie;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class Creator extends AppCompatActivity {

    private TextToSpeech texttospeech;

    final ArrayList<Integer> numbers = new ArrayList<Integer>();
    final ArrayList<Integer> listofnos = new ArrayList<Integer>();
    final HashMap<String,String> map1 = new HashMap<>();
//    final HashMap<String,ArrayList<Integer>> map2 = new HashMap<>();

    int min = 1;
    int max = 90;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creator);
        final String uCode = getIntent().getStringExtra("UCode");
        final FirebaseDatabase rootDb = FirebaseDatabase.getInstance();


        for (int i = 0; i < max; i++) {
            numbers.add(i + min);
        }
        final int size = numbers.size();

        System.out.print(size);
        final Button pickno = (Button) findViewById(R.id.number);
        Button showlist = (Button) findViewById(R.id.listnos);
        Button exitGame = (Button)findViewById(R.id.exitGame);
        TextView showUCode = (TextView)findViewById(R.id.uCode);
        showUCode.setText(uCode);

        final TextView num = (TextView) findViewById(R.id.showno);
        texttospeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS) {
                    int ttsLang = texttospeech.setLanguage(Locale.ENGLISH);

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");
                } else {
                    Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        pickno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random randomGenerator;
                randomGenerator = new Random();
                int index = randomGenerator.nextInt(numbers.size());
                int no = numbers.get(index);
                numbers.remove(index);
                listofnos.add(no);
                int size1 = numbers.size();
                DatabaseReference rootRef = rootDb.getReference().child("Tambola");
                if (size1 > 0) {
                    num.setText(String.valueOf(no));
                    String speech = String.valueOf(no);
                    texttospeech.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
//                    map1.put("CurrentNumber",String.valueOf(no));
//                    map1.put("ListNos",String.valueOf(listofnos));
                    FirebaseDatabase.getInstance().getReference().child("Tambola").child(uCode).child("CurrentNumber").setValue(String.valueOf(no));
                    FirebaseDatabase.getInstance().getReference().child("Tambola").child(uCode).child("ListNos").setValue(String.valueOf(listofnos));
//                    FirebaseDatabase.getInstance().getReference().child("Tambola").child(uCode).setValue(map1);
                } else {
                    num.setText("All numbers exhausted!!");
                    pickno.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String speech1 = "All numbers exhausted!!";
                            Toast.makeText(getApplicationContext(), speech1, Toast.LENGTH_SHORT).show();
                            texttospeech.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
                        }
                    });


                }

            }
        });
        showlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Creator.this,ShowNumbers.class);
                intent.putExtra("listofnos", listofnos);
                startActivity(intent);

            }
        });

        exitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Creator.this,StartScreen.class);
                startActivity(intent);
                finish();


            }
        });
    }


}
