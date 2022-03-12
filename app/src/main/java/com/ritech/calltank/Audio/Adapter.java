package com.ritech.calltank.Audio;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.ritech.calltank.R;

import java.io.IOException;
import java.text.DateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Activity activity;
    List<AudioModelClass> songs;

    MediaPlayer mediaPlayer ;
    SeekBar seekBar;
    Runnable runnable;
    Handler handler ;

    public Adapter(Activity activity, List<AudioModelClass> songs) {
        this.activity = activity;
        this.songs = songs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.audio_item_layout,parent,false);

        return new ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            AudioModelClass audioModelClass = songs.get(position);

            String file = audioModelClass.getFilename();
            holder.fileName.setText(file);

            long dateTime = (audioModelClass.getDate()*1000);
            String currentDate = DateFormat.getDateInstance().format(dateTime);
             Log.d("itsapp", "Date:" + currentDate) ;
            holder.date.setText(currentDate);


             mediaPlayer = new MediaPlayer();

        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                {
                    mediaPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


            String audioPath = audioModelClass.getPath();
            try {
                mediaPlayer.setDataSource(audioPath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            holder.play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


//                    try {
                        mediaPlayer.prepareAsync();
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mP) {
                                mP.start();
                                holder.seekBar.setMax(mP.getDuration());
                                updateSeekbar();
                                holder.play.setVisibility(View.INVISIBLE);
                                holder.pause.setVisibility(View.VISIBLE);
                            }
                        });

//                        mediaPlayer.prepare();
//
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            });

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                double ratio = percent / 100.0;
                int bufferingLevel = (int)(mp.getDuration() * ratio);
                holder.seekBar.setSecondaryProgress(bufferingLevel);
            }
        });

            holder.pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaPlayer.stop();
                    holder.play.setVisibility(View.VISIBLE);
                    holder.pause.setVisibility(View.INVISIBLE);
                }
            });

    }



    @Override
    public int getItemCount() {
        return songs.size();
    }


    private void updateSeekbar() {
        int currPos = mediaPlayer.getCurrentPosition();
        seekBar = activity.findViewById(R.id.sentSeek);
        seekBar.setProgress(currPos);
        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                updateSeekbar();
            }
        };
        handler.postDelayed(runnable,1000);


    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView play , pause;
        TextView fileName, date;
        SeekBar seekBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            play = itemView.findViewById(R.id.play);
            pause= itemView.findViewById(R.id.pause);
            fileName = itemView.findViewById(R.id.fileName);
            pause.setVisibility(View.INVISIBLE);
            date = itemView.findViewById(R.id.date);
            seekBar= itemView.findViewById(R.id.sentSeek);

        }
    }
}
