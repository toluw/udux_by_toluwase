package com.up.toluwaseoke.view;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.up.toluwaseoke.R;

public class AudioPlayerService extends Service {
    String source,name,artwork;
    private final IBinder mBinder = new LocalBinder();
    Uri uri;


    private SimpleExoPlayer player;
    private PlayerNotificationManager playerNotificationManager;
    Context context;

    public class LocalBinder extends Binder {
        public AudioPlayerService getService() {
            return AudioPlayerService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        source = intent.getStringExtra("source");
       name = intent.getStringExtra("name");
        artwork = intent.getStringExtra("artwork");
        uri = Uri.parse(source);



        player = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSourceFactory(Util.getUserAgent(context, getString(R.string.app_name)));

        HlsMediaSource hlsMediaSource =
                new HlsMediaSource.Factory(dataSourceFactory)
                        .setAllowChunklessPreparation(true)
                        .createMediaSource(uri);

        player.prepare(hlsMediaSource);

        player.setPlayWhenReady(true);

        playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(


                context,
                "qanda",
                R.string.app_name,
                R.string.audio,
                87,
                new PlayerNotificationManager.MediaDescriptionAdapter() {
                    @Override
                    public String getCurrentContentTitle(Player player) {
                        return name;
                    }

                    @Nullable
                    @Override
                    public PendingIntent createCurrentContentIntent(Player player) {
                        Intent intent =new Intent(context,AudioPlayerActivity.class);
                        intent.putExtra("name1",name);
                        intent.putExtra("artwork1",artwork);
                        intent.putExtra("source1",source);
                        return PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    }

                    @Nullable
                    @Override
                    public String getCurrentContentText(Player player) {

                        return name;
                    }

                    @Nullable
                    @Override
                    public Bitmap getCurrentLargeIcon(Player player, PlayerNotificationManager.BitmapCallback callback) {
                        Bitmap icon = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
                        return icon;
                    }
                },
                new PlayerNotificationManager.NotificationListener(){
                    @Override
                    public void onNotificationPosted(int notificationId, Notification notification, boolean ongoing) {
                        startForeground(notificationId, notification);
                    }

                    @Override
                    public void onNotificationCancelled(int notificationId, boolean dismissedByUser) {
                        stopSelf();
                    }
                }
        );
        playerNotificationManager.setUseStopAction(true);
        playerNotificationManager.setPlayer(player);

        return START_REDELIVER_INTENT;
    }

    public SimpleExoPlayer getplayerInstance() {
        return player;
    }

    public  String get_ptitle(){
        return name;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        if(playerNotificationManager != null) {
            playerNotificationManager.setPlayer(null);
            player.release();
            player = null;
        }
        super.onDestroy();
    }

}
