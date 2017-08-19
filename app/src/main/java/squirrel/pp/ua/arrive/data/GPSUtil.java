package squirrel.pp.ua.arrive.data;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import javax.inject.Inject;

import static squirrel.pp.ua.arrive.App.LOG_TAG;


public class GPSUtil {
    private Context context;
    private LocationRequest locationRequest;

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback callback;

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
        locationRequest.setInterval(1000 * 5);
        locationRequest.setFastestInterval(1000 * 2);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        return locationRequest;
    }


    public void checkingDistance(Location markerLocation, double targetRadius, OnArriveListeners listeners) throws SecurityException {
        callback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location lastLocation = locationResult.getLastLocation();
                float distanceTo = markerLocation.distanceTo(lastLocation);
                Log.d(LOG_TAG, distanceTo + "");
                if (distanceTo < targetRadius) {
                    listeners.onArrive();
                    fusedLocationClient.removeLocationUpdates(this);
                }
            }
        };
        fusedLocationClient.requestLocationUpdates(locationRequest, callback, null);
    }

    public void stopCheckingDistance() {
        fusedLocationClient.removeLocationUpdates(callback);
    }

    public interface OnArriveListeners {
        void onArrive();
    }
}
