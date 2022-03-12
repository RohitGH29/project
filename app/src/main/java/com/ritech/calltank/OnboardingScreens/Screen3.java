package com.ritech.calltank.OnboardingScreens;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ritech.calltank.MainActivity;
import com.ritech.calltank.Audio.AudioModelClass;
import com.ritech.calltank.R;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Screen3 extends AppCompatActivity {

    TextView filename, time, reference;
    Button yes, no;
    ImageView play, pause;
    List<AudioModelClass> songs = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen3);

        getSupportActionBar().hide();

        findId();

        fetchSongs();

        setData();

        onclick();


    }

    private void onclick() {

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Screen3.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    private void setData() {

        AudioModelClass audioModelClass = songs.get(0);

        filename.setText(audioModelClass.getFilename());


        Long dateTime = (audioModelClass.getDate()*1000);
        String currentDate = DateFormat.getDateInstance().format(dateTime);
        time.setText(currentDate);
        Log.d("itsapp", "Date Minutes:" + currentDate) ;



        MediaPlayer mediaPlayer = new MediaPlayer();
        String audioPath = audioModelClass.getPath();
        try {
            mediaPlayer.setDataSource(audioPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mP) {
                            mP.start();
                            play.setVisibility(View.INVISIBLE);
                            pause.setVisibility(View.VISIBLE);
                        }
                    });

                    mediaPlayer.prepare();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });


        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                play.setVisibility(View.VISIBLE);
                pause.setVisibility(View.INVISIBLE);
            }
        });


    }

    private void findId() {

        filename = findViewById(R.id.recText);
        time = findViewById(R.id.recTime);
        reference = findViewById(R.id.recSource);
        yes = findViewById(R.id.yesB);
        no = findViewById(R.id.noB);
        no = findViewById(R.id.noB);
        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);


    }


    private void fetchSongs() {

        //define list to carry songs

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
            int pathColimn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);


            //getting the values
            while (cursor.moveToNext()) {

                // get values of colums for a give audio files
                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                Long da = cursor.getLong(dateColumn);
                Log.d("itsapp", "D:" + da);
                String path = cursor.getString(pathColimn);
                //String dates = new SimpleDateFormat("yyyy-MM-dd", Locale.forLanguageTag(date)).format(new Date());

                //song uri
                Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);

                //remove .mp3 extension on song's name
                name = name.substring(0, name.lastIndexOf("."));

                // song item
                AudioModelClass song = new AudioModelClass(path, name, uri, da);
                // add song to songs list
                songs.add(song);

            }

        }
    }


}