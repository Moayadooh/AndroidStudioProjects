package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar1;
    MyCustomAdapter Adapter;
    MediaPlayer mp;
    int SeekValue;
    ListView ls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar1 = findViewById(R.id.seekBar1);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SeekValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(SeekValue);
            }
        });

        ls = findViewById(R.id.listView);
        CheckUserPermsions();
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SongInfo songInfo = SongList.get(position);
                mp = new MediaPlayer();
                try {
                    //mp.setDataSource(Environment.getExternalStorageDirectory().getPath()+"/storage/emulated/0/Download/001.mp3");
                    //mp=MediaPlayer.create(getApplicationContext(), getResources().getIdentifier(songInfo.path,"raw",getPackageName()));
                    mp.setDataSource(songInfo.path);
                    mp.prepare();
                    mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            seekBar1.setMax(mp.getDuration());
                            mp.start();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        myThread my = new myThread();
        my.start();
    }

    ArrayList<SongInfo> SongList = new ArrayList<SongInfo>();
    //online
    /*public ArrayList<SongInfo> getAllSongs(){
        SongList.clear();
        SongList.add(new SongInfo("http://server6.mp3quran.net/thubti/001.mp3","Fataha","bakar","quran"));
        SongList.add(new SongInfo("http://server6.mp3quran.net/thubti/002.mp3","Bakara","bakar","quran"));
        SongList.add(new SongInfo("http://server6.mp3quran.net/thubti/003.mp3","Al-Imran","bakar","quran"));
        SongList.add(new SongInfo("http://server6.mp3quran.net/thubti/004.mp3","An-Nisa","bakar","quran"));
        SongList.add(new SongInfo("http://server6.mp3quran.net/thubti/005.mp3","Al-Ma'idah","bakar","quran"));
        SongList.add(new SongInfo("http://server6.mp3quran.net/thubti/006.mp3","Al-An'am","bakar","quran"));
        SongList.add(new SongInfo("http://server6.mp3quran.net/thubti/007.mp3","Al-A'raf","bakar","quran"));
        return SongList;
    }*/

    //local
    public ArrayList<SongInfo> getAllSongs(){
        Uri allsonguri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        Cursor cursor = getContentResolver().query(allsonguri,null,selection,null,null);

        if (cursor != null){
            if (cursor.moveToFirst()){
                do {
                    String song_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    String fullpath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String album_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    String artist_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    SongList.add(new SongInfo(song_name,fullpath,album_name,artist_name));
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        return SongList;
    }

    class myThread extends Thread{

        @Override
        public void run(){
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mp != null)
                            seekBar1.setProgress(mp.getCurrentPosition());
                    }
                });
            }
        }
    }

    public void buplay(View view) {
        mp.start();
    }

    public void bustop(View view) {
        mp.stop();
    }

    public void bupause(View view) {
        mp.pause();
    }

    private class MyCustomAdapter extends BaseAdapter {
        public  ArrayList<SongInfo>  fullsongpath ;

        public MyCustomAdapter(ArrayList<SongInfo>  fullsongpath) {
            this.fullsongpath=fullsongpath;
        }


        @Override
        public int getCount() {
            return fullsongpath.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.item, null);

            SongInfo s = fullsongpath.get(position);

            TextView textView = myView.findViewById(R.id.textView);
            textView.setText(s.song_name);

            TextView textView2 = myView.findViewById(R.id.textView2);
            textView2.setText(s.artist_name);

            return myView;
        }

    }

    void CheckUserPermsions(){
        if (Build.VERSION.SDK_INT >= 23){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED  ){
                requestPermissions(new String[]{
                                android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return ;
            }
        }

        LoadSong();

    }
    //get acces to location permsion
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LoadSong();
                } else {
                    // Permission Denied
                    Toast.makeText( this,"denail" , Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    void LoadSong(){
        Adapter = new MyCustomAdapter(getAllSongs());
        ls.setAdapter(Adapter);
    }
}
