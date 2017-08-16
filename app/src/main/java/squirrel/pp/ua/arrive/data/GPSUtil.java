package squirrel.pp.ua.arrive.data;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;

import io.reactivex.Completable;
import io.reactivex.Observable;


public class GPSUtil {
    private static final int ACCURACY_DISTANCE_M = 2000;
    private Context context;
    private LocationRequest locationRequest;

    private FusedLocationProviderClient fusedLocationClient;

    public GPSUtil(Context context) {
        this.context = context;
        initLocationAPI();
    }

    public Completable checkingDistance(Location markerLocation, float targetDistance) throws SecurityException {
        Completable arrive = Observable.create(e -> {
            fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    e.onNext(locationResult.getLastLocation());
                }
            }, null);
        }).map(location -> ((Location) location).distanceTo(markerLocation))
                .filter(aFloat -> aFloat <= targetDistance).ignoreElements();
        return arrive;
    }

    private void initLocationAPI() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        locationRequest = fitInaccurateLocationRequest();
    }


    private LocationRequest fitAccuracyLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(1000 * 10);
        locationRequest.setFastestInterval(1000 * 2);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    private LocationRequest fitInaccurateLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(1000 * 60 * 10);
        locationRequest.setFastestInterval(1000 * 60 * 2);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        return locationRequest;
    }

    public void setAccuracy(double distance) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        LocationRequest locationRequest = new LocationRequest();
        final double KILOMETERS_2 = 1000 * 2;
        if (distance < KILOMETERS_2) {
            locationRequest = fitAccuracyLocationRequest();
        } else {
            locationRequest = fitInaccurateLocationRequest();
        }
        builder.addLocationRequest(locationRequest);
    }
}
