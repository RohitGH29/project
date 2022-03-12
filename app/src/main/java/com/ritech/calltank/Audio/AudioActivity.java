package com.ritech.calltank.Audio;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ritech.calltank.OnboardingScreens.OnboardingAdapter;
import com.ritech.calltank.R;

import java.util.ArrayList;
import java.util.List;

public class AudioActivity extends AppCompatActivity {

    Adapter adapter;
    RecyclerView recyclerView;


    private OnboardingAdapter onboardingAdapter;

    public static final int PERMIT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        Toolbar toolbar = findViewById(R.id.aa_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Call Tank");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerview);

        fetchSongs();
    }


    private void fetchSongs() {

        //define list to carry songs
        List<AudioModelClass> songs = new ArrayList<>();
        Uri songLibraryUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            songLibraryUri = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            songLibraryUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }

        //projection
        String[] projection = new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DATE_ADDED,
                MediaStore.Audio.Media.DATA
        };

        //sort order
        String sortOrder = MediaStore.Audio.Media.DATE_ADDED + " DESC";

        //Querying
        try (Cursor cursor = getContentResolver().query(songLibraryUri, projection, null, null, sortOrder)) {

            // cache the cursor indices
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
            int dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED);
            Log.d("itsapp", "DateColumn:" + dateColumn);
            int pathColimn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);



            //getting the values
            while (cursor.moveToNext()) {

                // get values of colums for a give audio files
                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                Long da = cursor.getLong(dateColumn);
                Log.d("itsapp", "D:" + da);
                String path = cursor.getString(pathColimn);
               // Date your = new Date(da);


                //song uri
                Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);

                //remove .mp3 extension on song's name
                name = name.substring(0, name.lastIndexOf("."));

                // song item
                AudioModelClass song = new AudioModelClass(path, name, uri, da);
                // add song to songs list
                songs.add(song);

            }

            showSongs(songs);
            Toast.makeText(this, "Number of Songs:" + songs.size(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showSongs(List<AudioModelClass> songs) {
        // songs.clear();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new Adapter(this,songs);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        item.getItemId();
        return super.onOptionsItemSelected(item);
    }


}