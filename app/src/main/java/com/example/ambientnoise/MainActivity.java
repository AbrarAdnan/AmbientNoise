package com.example.ambientnoise;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ListView soundList;
    Button stop_button;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stop_button=findViewById(R.id.stopID);
        stop_button.getBackground().setAlpha(192);
        soundList=findViewById(R.id.listviewID);

        final String[] sounds=getResources().getStringArray(R.array.noise_names);
        ArrayAdapter<String> adapter=new ArrayAdapter(MainActivity.this,R.layout.sounds,R.id.soundtextID,sounds);
        soundList.setAdapter(adapter);

        soundList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String songName=sounds[i];
                int ID=getResources().getIdentifier(songName,"raw",getPackageName());
                if (mediaPlayer==null){
                    mediaPlayer=MediaPlayer.create(MainActivity.this,ID);
                    mediaPlayer.start();
                    Toast.makeText(MainActivity.this,songName+" started",Toast.LENGTH_SHORT).show();
                }
                if(mediaPlayer!=null){
                    mediaPlayer.reset();
                    mediaPlayer=MediaPlayer.create(MainActivity.this,ID);
                    mediaPlayer.start();
                    Toast.makeText(MainActivity.this,songName+" started",Toast.LENGTH_SHORT).show();
                }

            }
        });

        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying())
                mediaPlayer.reset();
                Toast.makeText(MainActivity.this,"Sound Reset",Toast.LENGTH_SHORT).show();

            }
        });

    }
    @Override
    protected void onDestroy() {
        if(mediaPlayer!=null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
        super.onDestroy();
    }
}

