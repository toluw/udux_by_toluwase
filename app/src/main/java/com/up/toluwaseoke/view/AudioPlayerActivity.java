package com.up.toluwaseoke.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.util.Util;
import com.up.toluwaseoke.R;
import com.up.toluwaseoke.adapter.MyAdapter;

public class AudioPlayerActivity extends AppCompatActivity {
TextView title;
    SimpleDraweeView image;
    ProgressBar dialog;
    String source, artwork, name, name1, artwork1, source1;
    PlayerControlView playerControlView;
    private AudioPlayerService mService;
    SimpleExoPlayer play;
    private Intent intent;
    private boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);

        initialize();

        Bundle myData = getIntent().getExtras();
        if(myData!=null){
            name = myData.getString("name");
            source = myData.getString("source");
            artwork = myData.getString("artwork");
            name1 = myData.getString("name1");
            source1 = myData.getString("source1");
            artwork1 = myData.getString("artwork1");
        }



        if(name != null){
            //Activity loaded from Home page
            Log.d("source",source);
            title.setText(name);
            Uri uri = Uri.parse(artwork);
            image.setImageURI(uri);

            intent = new Intent(getApplicationContext(),AudioPlayerService.class);
            intent.putExtra("name",name);
            intent.putExtra("source",source);
            intent.putExtra("artwork",artwork);
            Util.startForegroundService(getApplicationContext(),intent);

        }else{
            //Activity loaded from pending intent from audio player service notification
            Log.d("source",source1);
            title.setText(name1);
            Uri uri = Uri.parse(artwork1);
            image.setImageURI(uri);

            intent = new Intent(getApplicationContext(),AudioPlayerService.class);

        }



    }

    private  void initialize(){
        dialog = (ProgressBar)findViewById(R.id.dialog);
        image = (SimpleDraweeView)findViewById(R.id.image);
        title = (TextView)findViewById(R.id.title);
        playerControlView = findViewById(R.id.player_control_view);

    }

    //Binder to audioplayer service
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            AudioPlayerService.LocalBinder binder = (AudioPlayerService.LocalBinder) iBinder;
            mService = binder.getService();
            mBound = true;
            initializePlayer();
        }



        @Override
        public void onServiceDisconnected(ComponentName componentName) {


            mBound = false;
        }
    };


    @Override
    public void onStart() {
        super.onStart();
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        initializePlayer();

    }

    @Override
    protected void onStop() {
        unbindService(mConnection);
        mBound = false;
        super.onStop();
    }

    //Initialize player controls
    private void initializePlayer() {
        if (mBound) {
            final SimpleExoPlayer player = mService.getplayerInstance();
            if(player != null){
                play = player;
                player.addListener(new Player.EventListener() {

                    @Override
                    public void onPlayerStateChanged(
                            boolean playWhenReady, int playbackState) {
                        if (playWhenReady && playbackState == Player.STATE_READY) {
                            dialog.setVisibility(View.GONE);
                            // Active playback.
                        } else if (playWhenReady) {
                            // Not playing because playback ended, the player is buffering, stopped or
                            // failed. Check playbackState and player.getPlaybackError for details.


                            if(playbackState == Player.STATE_BUFFERING){
                                dialog.setVisibility(View.VISIBLE);
                            }
                            else if (playbackState == Player.STATE_ENDED){
                                if(play != null) {
                                    play.stop(true);
                                }
                                if(name != null){
                                    finish();
                                }else{
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                }

                            }else if (playbackState == Player.STATE_IDLE){

                            }

                        } else {
                            // Paused by app.
                        }
                    }


                    @Override
                    public void onPlayerError(ExoPlaybackException error) {
                        Toast k = Toast.makeText(getApplicationContext(),"An error occured, Please reload stream",Toast.LENGTH_LONG);
                        k.setGravity(Gravity.CENTER,0,0);
                        k.show();
                    }


                    @Override
                    public void onPositionDiscontinuity(@Player.DiscontinuityReason int reason) {
                        // Load metadata for mediaId and update the UI.
                    }


                });


                playerControlView.setPlayer(player);
            }

        }else{
            dialog.setVisibility(View.GONE);
        }
    }



    @Override
    public void onBackPressed() {
        if(play != null) {
            play.stop(true);
        }
        if(name != null){
            finish();
        }else{
           Intent intent = new Intent(getApplicationContext(),MainActivity.class);
           startActivity(intent);
        }


    }



}