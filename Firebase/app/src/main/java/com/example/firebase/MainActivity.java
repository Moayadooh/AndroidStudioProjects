package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String ARTIST_NAME = "artistname";
    public static final String ARTIST_ID = "artistid";

    EditText etName;
    Button btnAdd;
    Spinner spinnerGenres;

    DatabaseReference databaseArtists;

    ListView listViewArtists;
    List<Artist> artistList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.editTextName);
        btnAdd = findViewById(R.id.btnAddArtist);
        spinnerGenres = findViewById(R.id.spinnerGenres);

        databaseArtists = FirebaseDatabase.getInstance().getReference("artists");

        listViewArtists = findViewById(R.id.listViewArtists);
        artistList = new ArrayList<>();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addArtist();
            }
        });

        listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Artist artist = artistList.get(position);
                Intent intent = new Intent(getApplicationContext(), AddTrackActivity.class);
                intent.putExtra(ARTIST_ID, artist.getArtistId());
                intent.putExtra(ARTIST_NAME, artist.getArtistName());
                startActivity(intent);
            }
        });

        listViewArtists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Artist artist = artistList.get(position);
                showUpdateDialog(artist.getArtistId(), artist.getArtistName());
                return false;
            }
        });
    }

    private void addArtist() {
        String name = etName.getText().toString().trim();
        String genre = spinnerGenres.getSelectedItem().toString();

        if(!TextUtils.isEmpty(name)) {
            String id = databaseArtists.push().getKey();

            Artist artist = new Artist(id, name, genre);
            databaseArtists.child(id).setValue(artist);

            Toast.makeText(this,"Artist added", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"You should enter a name", Toast.LENGTH_LONG).show();
        }
    }

    private void showUpdateDialog(final String artistId, String artistName){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final TextView tvTitle = dialogView.findViewById(R.id.textViewName);
        final EditText editTextName = dialogView.findViewById(R.id.editTextName);
        final Spinner spinnerGenre = dialogView.findViewById(R.id.spinnerGenres);
        final Button btnUpdate = dialogView.findViewById(R.id.btnUpdate);
        final Button btnDelete = dialogView.findViewById(R.id.btnDelete);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        tvTitle.setText("Edit Artist "+artistName);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String genre = spinnerGenre.getSelectedItem().toString();
                if (TextUtils.isEmpty(name)){
                    editTextName.setError("Name required");
                    return;
                }
                updateArtist(artistId, name, genre);
                alertDialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteArtist(artistId);
                alertDialog.dismiss();
            }
        });
    }

    private void deleteArtist(String artistId) {
        DatabaseReference drArtist = FirebaseDatabase.getInstance().getReference("artists").child(artistId);
        DatabaseReference drTracks = FirebaseDatabase.getInstance().getReference("tracks").child(artistId);
        drArtist.removeValue();
        drTracks.removeValue();
        Toast.makeText(this, "Artist is deleted",Toast.LENGTH_LONG).show();
    }

    private boolean updateArtist(String id, String name, String genre){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("artists").child(id);
        Artist artist = new Artist(id, name, genre);
        databaseReference.setValue(artist);
        Toast.makeText(this, "Artist Updated Successfully ", Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                artistList.clear();
                for(DataSnapshot artistSnapshot : dataSnapshot.getChildren()){
                    Artist artist = artistSnapshot.getValue(Artist.class);
                    artistList.add(artist);
                }
                ArtistList adapter = new ArtistList(MainActivity.this, artistList);
                listViewArtists.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
