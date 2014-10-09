package com.hazelwood.labfour;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

public class Music_Service extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    MediaPlayer mediaPlayer;
    boolean playing, paused, stopped, prepared;
    boolean donePreparing;
    int position;
    int song;
    int[] playlist;
    public Music_Service() {
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        prepared = true;
        if (donePreparing){
            mediaPlayer.start();
            playing = true;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }


    public class Music_Binder extends Binder{
        public Music_Service getService(){
            return Music_Service.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        playlist = new int[]{
                R.raw.brain_cells,
                R.raw.hey_ma,
                R.raw.nostalgia
        };
        song = 0;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(Music_Service.this);

        try{
            mediaPlayer.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/" + playlist[song]));
            mediaPlayer.prepareAsync();
        } catch (Exception e){
            mediaPlayer.release();
        }

        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new Music_Binder();
    }

    public void play() {

//        mediaPlayer.start();
        if (paused){
            mediaPlayer.seekTo(position);
            mediaPlayer.start();
            paused = false;
        } else if (stopped){
            if (prepared){
                mediaPlayer.start();
                stopped = false;
            } else {
                donePreparing = true;
                mediaPlayer.prepareAsync();
            }

        } else {
            mediaPlayer.start();
            playing = true;
            prepared = false;
        }
    }

    public void pause() {
        if (playing){
            position = mediaPlayer.getCurrentPosition();
            paused = true;
            mediaPlayer.pause();
        }
    }

    public void stop() {
        if (playing){
            mediaPlayer.stop();
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/" + playlist[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            paused = false;
            playing = false;
            stopped = true;
        }

    }

    public void next() {
        mediaPlayer.reset();
        if (song < (playlist.length - 1)){
            song = song +1;
        } else {
            song = 0;
        }

        try {
            mediaPlayer.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/" + playlist[song]));
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void previous() {
        mediaPlayer.reset();
        if (song > (playlist.length - 1)){
            song = song - 1;
        } else {
            song = 0;
        }

        try {
            mediaPlayer.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/" + playlist[song]));
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPosition(){
        return mediaPlayer.getCurrentPosition();
    }

    public int getDuration(){
        return mediaPlayer.getDuration();
    }

}
