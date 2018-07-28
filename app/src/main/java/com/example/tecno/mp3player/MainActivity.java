package com.example.tecno.mp3player;

import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ListView listView;
    String[] item;
    int [] images={R.drawable.music_icon} ;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    MenuInflater menuInflater;
    MenuItem menuItem;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView)findViewById(R.id.lv);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer1);
        actionBarDrawerToggle=new ActionBarDrawerToggle(MainActivity.this,drawerLayout,R.string.opne,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView=(NavigationView)findViewById(R.id.navigation_view1);
        navigationView.setNavigationItemSelectedListener(this);



        final ArrayList<File> mysongs=findsongs(Environment.getExternalStorageDirectory());
        item=new String[mysongs.size()];
         for (int i=0;i<mysongs.size();i++){
            // Toast.makeText(MainActivity.this,mysongs.get(i).getName().toString(),Toast.LENGTH_SHORT).show();
             item[i]= mysongs.get(i).getName().toString()
                     .replace(".mp3","").replace(".wav","");
         }
        ArrayAdapter<String> adb=new ArrayAdapter<String>(getApplicationContext(),R.layout.songs_layout,R.id.textView,item);
         listView.setAdapter(adb);

         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 startActivity(new Intent(getApplicationContext(),Main2Activity.class)
                         .putExtra("pos",i).putExtra("songslist",mysongs)
                         .putExtra("username", mysongs.get(i).getName().toString().replace(".mp3","")));
             }
         });

    }
    public ArrayList<File> findsongs (File root){
        ArrayList<File> al=new ArrayList<File>();
        File[] files=root.listFiles();
        for(File singlefile : files){
            if(singlefile.isDirectory() && !singlefile.isHidden()){
                al.addAll(findsongs(singlefile));
            }else {
                if (singlefile.getName().endsWith(".mp3")|| singlefile.getName().endsWith(".wav"))
                    al.add(singlefile);
            }
        }
        return al;
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
            Toast.makeText(this,"this is home",Toast.LENGTH_LONG).show();
        }
        if(id==R.id.music)
        {
            Toast.makeText(this,"this is music",Toast.LENGTH_LONG).show();
        }
        if(id==R.id.video)
        {
            Toast.makeText(this,"this is video",Toast.LENGTH_LONG).show();
        }
        if(id==R.id.log)
        {
            Toast.makeText(this,"this is log",Toast.LENGTH_LONG).show();
        }
        if(id==R.id.close)
        {
            Toast.makeText(this,"this is close",Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_search,menu);
        menuItem=menu.findItem(R.id.menuSearch);
        searchView=(SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
           //     adb.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
