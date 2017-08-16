package squirrel.pp.ua.arrive;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import squirrel.pp.ua.arrive.data.GPSUtil;

public class TrackService extends Service {

    GPSUtil gpsUtil;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Location location = (Location) intent.getExtras().getSerializable("");//TODO
        int distance = intent.getExtras().getInt("");//TODO
        checkDistance(location, distance);
        return START_STICKY;
    }

    private void checkDistance(Location markerLocation, float targetDistance) {
        Completable completable = gpsUtil.checkingDistance(markerLocation, targetDistance);
        completable.subscribeOn(Schedulers.newThread()).doOnComplete(() -> activAlarm());
    }

    private void activAlarm() {

    }


}
//.subscribe(new DisposableCompletableObserver() {
//@Override
//public void onComplete() {
//
//        }
//
//@Override
//public void onError(@NonNull Throwable e) {
//
//        }
//        }).