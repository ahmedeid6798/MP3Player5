package com.example.tecno.mp3player;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener{
    static MediaPlayer mp;
    ArrayList<File> mysongs;
    SeekBar seekBar;
    ImageButton play_btn1,right_btn2,left_btn3,next,pre,hate;
    int postion;
    Thread th;
    Uri uri;
    TextView songs_name;
    SearchView searchView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        searchView=(SearchView)findViewById(R.id.search);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer2);
        actionBarDrawerToggle=new ActionBarDrawerToggle(Main2Activity.this,drawerLayout,R.string.opne,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView=(NavigationView)findViewById(R.id.navigation_view2);
        navigationView.setNavigationItemSelectedListener(this);

        play_btn1=(ImageButton)findViewById(R.id.btn_play);
       right_btn2=(ImageButton)findViewById(R.id.btn_right);
        left_btn3=(ImageButton)findViewById(R.id.btn_left);
        hate=(ImageButton)findViewById(R.id.hate);
        next=(ImageButton)findViewById(R.id.next);
        pre=(ImageButton)findViewById(R.id.pre);
        songs_name=(TextView)findViewById(R.id.tv);

        play_btn1.setOnClickListener(this);
        right_btn2.setOnClickListener(this);
        left_btn3.setOnClickListener(this);
        next.setOnClickListener(this);
        pre.setOnClickListener(this);
        hate.setOnClickListener(this);


        Intent i=getIntent();
        Bundle b=i.getExtras();
        mysongs=(ArrayList) b.getParcelableArrayList("songslist");
        songs_name.setText(b.getString("username"));
        postion=b.getInt("pos",0);

        seekBar=(SeekBar)findViewById(R.id.seekBar);
        th=new Thread(){
            @Override
            public void run()
            {
                int total=mp.getDuration();
                int currentpos=0;
                while (currentpos<total){
                    try {
                        sleep(500);
                        currentpos=mp.getCurrentPosition();
                        seekBar.setProgress(currentpos);

                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };

        if (mp!=null)
        {
            mp.stop();
            mp.release();
        }
        uri=Uri.parse(mysongs.get(postion).toString());
        mp=MediaPlayer.create(getApplicationContext(),uri);
        mp.start();
       seekBar.setMax(mp.getDuration());
        th.start();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                mp.seekTo(seekBar.getProgress());
            }
        });

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch(id){

            case R.id.btn_play:
                if(mp.isPlaying()){
                   play_btn1.setImageResource(R.drawable.play);
                    mp.pause();
                }
                else {
                    mp.start();
                    play_btn1.setImageResource(R.drawable.pause);
                }
                break;

            case R.id.btn_right:
                mp.seekTo(mp.getCurrentPosition()+5000);
                break;

            case R.id.btn_left:
                mp.seekTo(mp.getCurrentPosition()-5000);
                break;

            case R.id.next:
                mp.stop();
                mp.release();
                postion=(postion+1)%mysongs.size();
                uri=Uri.parse(mysongs.get(postion).toString());
                // .replace("/storage/emulated/0/Download/","")
                songs_name.setText(mysongs.get(postion).getName().toString().replace(".mp3",""));
                mp=MediaPlayer.create(getApplicationContext(),uri);
                /////
                if(mp.isPlaying()){
                    play_btn1.setImageResource(R.drawable.play);
                    mp.pause();
                }
                else {
                    mp.start();
                    play_btn1.setImageResource(R.drawable.pause);
                }
                /////
                mp.start();
                seekBar.setMax(mp.getDuration());
                break;

            case R.id.pre:
                mp.stop();
                mp.release();
                postion=(postion-1<0)?mysongs.size()-1 :postion-1;
                uri=Uri.parse(mysongs.get(postion).toString());
                mp=MediaPlayer.create(getApplicationContext(),uri);
                /////////
                if(mp.isPlaying()){
                    play_btn1.setImageResource(R.drawable.play);
                    mp.pause();
                }
                else {
                    mp.start();
                    play_btn1.setImageResource(R.drawable.pause);
                }
                /////////
                songs_name.setText(mysongs.get(postion).getName().toString().replace(".mp3",""));
                mp.start();
                seekBar.setMax(mp.getDuration());

                break;



          /* case R.id.hate:
                hate.setImageResource(R.drawable.hate);
               hate.setImageResource(R.drawable.love);
               */

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.home)
        {
            Toast.makeText(this,"this is home",Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.music)
        {
            Toast.makeText(this,"this is music",Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.video)
        {
            Toast.makeText(this,"this is video",Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.log)
        {
            Toast.makeText(this,"this is log",Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.close)
        {
            Toast.makeText(this,"this is close",Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
