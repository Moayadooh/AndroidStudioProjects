package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddTrackActivity extends AppCompatActivity {

    TextView tvArtistName;
    EditText etTrackName;
    Button btnAddTrack;
    SeekBar seekBarRating;

    ListView listViewTracks;
    DatabaseReference databaseTracks;
    List<Track> tracks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);

        tvArtistName = findViewById(R.id.textViewArtistName);
        etTrackName = findViewById(R.id.editTextTrackName);
        btnAddTrack = findViewById(R.id.btnAddTrack);
        seekBarRating = findViewById(R.id.seekBarRating);

        listViewTracks = findViewById(R.id.listViewTracks);

        Intent intent = getIntent();

        tracks = new ArrayList<>();
        String id = intent.getStringExtra(MainActivity.ARTIST_ID);
        String name = intent.getStringExtra(MainActivity.ARTIST_NAME);

        tvArtistName.setText(name);
        databaseTracks = FirebaseDatabase.getInstance().getReference("tracks").child(id);

        btnAddTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTrack();
            }
        });
    }

    private void saveTrack(){
        String trackName = etTrackName.getText().toString().trim();
        int rating = seekBarRating.getProgress();

        if(!TextUtils.isEmpty(trackName)) {
            String id = databaseTracks.push().getKey();

            Track track = new Track(id, trackName, rating);
            databaseTracks.child(id).setValue(track);

            Toast.makeText(this,"ATrack saved successfully", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Track name should not be empty", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseTracks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tracks.clear();
                for (DataSnapshot tracksnaphot : dataSnapshot.getChildren()){
                    Track track = tracksnaphot.getValue(Track.class);
                    tracks.add(track);
                }
                TrackList trackListAdapter = new TrackList(AddTrackActivity.this, tracks);
                listViewTracks.setAdapter(trackListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
