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

import javax.inject.Inject;

import squirrel.pp.ua.arrive.data.GPSUtil;
import squirrel.pp.ua.arrive.view.MapActivity;

import static squirrel.pp.ua.arrive.App.LOG_TAG;
import static squirrel.pp.ua.arrive.interactor.MapInteractor.KEY_TARGET_LAT;
import static squirrel.pp.ua.arrive.interactor.MapInteractor.KEY_TARGET_LNG;
import static squirrel.pp.ua.arrive.interactor.MapInteractor.KEY_TARGET_RADIUS;

public class TrackService extends Service implements GPSUtil.OnArriveListeners {
    public static final String ACTION_STOP_TRACK = "squirrel.pp.ua.arrive.track_service.stop_track";
    public static final String ACTION_START_TRACK = "squirrel.pp.ua.arrive.track_service.start_track";
    @Inject
    GPSUtil gpsUtil;

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
            Location location = buildLocation(intent);
            double radius = intent.getExtras().getDouble(KEY_TARGET_RADIUS);
            checkDistance(location, radius);
        }
        return START_NOT_STICKY;
    }

    private void stopService() {
        gpsUtil.stopCheckingDistance();
        stopForeground(true);
        stopSelf();
    }

    private void toForeground() {
        Notification notification = builderNotification();
        startForeground(5555, notification);
    }

    private Notification builderNotification() {
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.marker)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(getResources().getString(R.string.notification_text))
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

    private PendingIntent buildPNotificationIntent() {
        Intent intent = new Intent(this, MapActivity.class);
//        intent.setAction(ACTION_ON_ARRIVE);//TODO
        return PendingIntent.getActivity(this, 0, intent, 0);
    }

    private Location buildLocation(Intent intent) {
        double lat = intent.getExtras().getDouble(KEY_TARGET_LAT);
        double lng = intent.getExtras().getDouble(KEY_TARGET_LNG);
        Location location = new Location("");
        location.setLatitude(lat);
        location.setLongitude(lng);
        return location;
    }

    private void checkDistance(Location markerLocation, double targetDistance) {
        gpsUtil.checkingDistance(markerLocation, targetDistance, this);
    }

    @Override
    public void onArrive() {
        Log.d(LOG_TAG, "onArrive()");
        Toast.makeText(this, "onArrive", Toast.LENGTH_LONG).show();//TEST
        stopForeground(true);
        stopSelf();
    }

    @Override
    public void onDestroy() {
        gpsUtil.stopCheckingDistance();
        super.onDestroy();
    }
}
