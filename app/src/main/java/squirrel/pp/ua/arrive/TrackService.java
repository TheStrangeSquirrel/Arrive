package squirrel.pp.ua.arrive;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import squirrel.pp.ua.arrive.data.GPSUtil;
import squirrel.pp.ua.arrive.view.MapActivity;

import static squirrel.pp.ua.arrive.AlarmService.ACTION_START_ALARM;
import static squirrel.pp.ua.arrive.App.LOG_TAG;
import static squirrel.pp.ua.arrive.interactor.MapInteractor.KEY_TARGET_LAT;
import static squirrel.pp.ua.arrive.interactor.MapInteractor.KEY_TARGET_LNG;
import static squirrel.pp.ua.arrive.interactor.MapInteractor.KEY_TARGET_RADIUS;

public class TrackService extends Service implements GPSUtil.OnArriveListeners {
    public static final String ACTION_STOP_TRACK = "squirrel.pp.ua.arrive.track_service.STOP_TRACK";
    public static final String ACTION_START_TRACK = "squirrel.pp.ua.arrive.track_service.START_TRACK";
    public static final AtomicBoolean isExist = new AtomicBoolean(false);
    @Inject
    GPSUtil gpsUtil;

    private Location targetLocation;
    private double targetRadius;

    public TrackService() {
        inject();
    }

    private void inject() {
        App.getComponentManager().getServiceComponent().inject(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (ACTION_STOP_TRACK.equals(action)) {
            stopService();
        } else if (ACTION_START_TRACK.equals(action)) {
            toForeground();
            targetLocation = buildLocation(intent);
            targetRadius = intent.getExtras().getDouble(KEY_TARGET_RADIUS);
            gpsUtil.startCheckingDistanceListeners(targetLocation, targetRadius, this);
        }
        return START_NOT_STICKY;
    }

    private void toForeground() {
        Notification notification = builderNotification();
        startForeground(5555, notification);
    }

    private Location buildLocation(Intent intent) {
        double lat = intent.getExtras().getDouble(KEY_TARGET_LAT);
        double lng = intent.getExtras().getDouble(KEY_TARGET_LNG);
        Location location = new Location("");
        location.setLatitude(lat);
        location.setLongitude(lng);
        return location;
    }

    private Notification builderNotification() {
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.marker)//TODO
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(getResources().getString(R.string.check_notification_text))
                .addAction(R.drawable.common_full_open_on_phone
                        , getString(R.string.stop_check), buildStopTrackPNotificationIntent())//TODO icon
                .setContentIntent(buildPNotificationIntent());
        Notification notification = builder.build();
        return notification;
    }

    private PendingIntent buildStopTrackPNotificationIntent() {
        Intent intent = new Intent(this, TrackService.class);
        intent.setAction(ACTION_STOP_TRACK);
        return PendingIntent.getService(this, 0, intent, 0);
    }

    private void stopService() {
        gpsUtil.stopCheckingDistance();
        stopForeground(true);
        stopSelf();
    }

    private PendingIntent buildPNotificationIntent() {
        Intent intent = new Intent(this, MapActivity.class);
//        intent.setAction();//TODO
        return PendingIntent.getActivity(this, 0, intent, 0);
    }

    @Override
    public void onArrive() {
        Log.d(LOG_TAG, "onArrive()");
        Toast.makeText(this, "onArrive", Toast.LENGTH_LONG).show();//TEST
        Intent intent = new Intent(this, AlarmService.class);
        intent.setAction(ACTION_START_ALARM);
        startService(intent);
        stopForeground(true);
        stopSelf();
    }

    @Override
    public void onCreate() {
        isExist.set(true);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        gpsUtil.stopCheckingDistance();
        isExist.set(false);
        super.onDestroy();
    }
}
