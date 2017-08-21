package squirrel.pp.ua.arrive;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import java.io.IOException;

import javax.inject.Inject;

import squirrel.pp.ua.arrive.utils.PreferencesUtils;
import squirrel.pp.ua.arrive.view.MapActivity;

import static squirrel.pp.ua.arrive.App.LOG_TAG;

public class AlarmService extends Service {
    public static final String ACTION_STOP_ALARM = "squirrel.pp.ua.arrive.track_alarm.stop_alarm";
    public static final String ACTION_START_ALARM = "squirrel.pp.ua.arrive.track_alarm.start_alarm";
    @Inject
    PreferencesUtils preferences;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    public AlarmService() {
        App.getComponentManager().getAppComponent().inject(this);
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public void onCreate() {
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (ACTION_START_ALARM.equals(action)) {
            notifyy();
            startPlay();
        }
        if (ACTION_STOP_ALARM.equals(action)) {
            stopPlayAndStopService();
        }
        return START_STICKY;
    }

    private void notifyy() {
        Notification notification = builderNotification();
        startForeground(5555, notification);
    }

    private Notification builderNotification() {
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.marker)//TODO
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(getResources().getString(R.string.alarm_notification_text))
                .addAction(R.drawable.common_full_open_on_phone
                        , getString(R.string.dismiss), buildStopAlarmPNotificationIntent())//TODO icon
                .setContentIntent(buildPNotificationIntent());
        Notification notification = builder.build();
        return notification;
    }

    private PendingIntent buildStopAlarmPNotificationIntent() {
        Intent intent = new Intent(this, AlarmService.class);
        intent.setAction(ACTION_STOP_ALARM);
        return PendingIntent.getService(this, 0, intent, 0);
    }

    private PendingIntent buildPNotificationIntent() {
        Intent intent = new Intent(this, MapActivity.class);
//        intent.setAction(ACTION_ON_ARRIVE);//TODO
        return PendingIntent.getActivity(this, 0, intent, 0);
    }

    private void startPlay() {
        if (preferences.getVibroEnabled()) {
            long[] pattern = {500, 500, 500, 1000, 700, 1000, 700, 2000};
            vibrator.vibrate(pattern, 3);
        }
        Uri uri = preferences.getRingtone();
        mediaPlayer.setLooping(preferences.getLoopPlayEnabled());
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
        try {
            mediaPlayer.setDataSource(this, uri);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Log.w(LOG_TAG, "startPlay", e);
            try {
                uri = Uri.parse("default ringtone");
                mediaPlayer.setDataSource(this, uri);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e1) {
                Log.w(LOG_TAG, "startPlay catch", e);
            }
        }
    }

    private void stopPlayAndStopService() {
        mediaPlayer.stop();
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseMP();
    }

    private void releaseMP() {
        vibrator.cancel();
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                Log.w("LOG_TAG", "releaseMP", e);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("UnsupportedOperation onBind");
    }
}
