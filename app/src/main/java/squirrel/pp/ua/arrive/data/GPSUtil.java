package squirrel.pp.ua.arrive.data;

import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import javax.inject.Inject;

import static squirrel.pp.ua.arrive.App.LOG_TAG;


public class GPSUtil {
    private static final long LOCATION_UPDATE_FASTEST_INTERVAL = 1000 * 2;
    private static final long LOCATION_UPDATE_INTERVAL = 1000 * 5;

    private LocationCallback locationCallback;
    private Context context;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationClient;

    @Inject
    public GPSUtil(Context context) {
        this.context = context;
        initLocationAPI();
    }

    private void initLocationAPI() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        locationRequest = buildRequest();
    }

    private LocationRequest buildRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(LOCATION_UPDATE_INTERVAL);
        locationRequest.setFastestInterval(LOCATION_UPDATE_FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    public void startCheckingDistanceListeners(Location targetLocation, double targetRadius, OnArriveListeners listeners) throws SecurityException {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location lastLocation = locationResult.getLastLocation();
                if (isArrive(targetLocation, lastLocation, targetRadius)) {
                    listeners.onArrive();
                }
            }
        };
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }


    private boolean isArrive(Location targetLocation, Location lastLocation, double targetRadius) throws SecurityException {
        float distanceTo = lastLocation.distanceTo(targetLocation);
        Log.d(LOG_TAG, "Distance to " + distanceTo);
        return distanceTo < targetRadius;
    }

    public void stopCheckingDistance() {
        if (locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    public interface OnArriveListeners {
        void onArrive();
    }
}
