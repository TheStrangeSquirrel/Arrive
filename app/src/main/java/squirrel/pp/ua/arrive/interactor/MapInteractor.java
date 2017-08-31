package squirrel.pp.ua.arrive.interactor;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import squirrel.pp.ua.arrive.R;
import squirrel.pp.ua.arrive.TrackService;
import squirrel.pp.ua.arrive.exception.NoSetDestinationException;
import squirrel.pp.ua.arrive.utils.PreferencesUtils;

import static squirrel.pp.ua.arrive.TrackService.ACTION_START_TRACK;

public class MapInteractor {
    public static final String KEY_TARGET_LAT = "TargetLat";
    public static final String KEY_TARGET_LNG = "TargetLng";
    public static final String KEY_TARGET_RADIUS = "TargetRadius";

    private Activity activity;

    private Intent serviceIntent;


    private GoogleMap map;
    private TargetDestination destination;

    private PreferencesUtils preferences;

    private int distance;

    @Inject
    public MapInteractor(Activity activity, PreferencesUtils preferences) {
        this.activity = activity;
        this.preferences = preferences;
    }

    public boolean hasPermissions() {
        int permission = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    public void initMap(GoogleMap googleMap) {
        map = googleMap;
        initMapInterface();
        initMarker();
        initCamera();
        map.setOnMapLongClickListener(latLng -> {
            preferences.saveLastMarkerPosition(latLng);
            if (destination == null) {
                destination = new TargetDestination(googleMap, latLng, distance);
            }
            destination.setPosition(latLng);
        });
    }

    private void initMapInterface() throws SecurityException {
        int typeMap = preferences.getTypeMap();
        map.setMapType(typeMap);
        map.setMyLocationEnabled(true);
        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setMapToolbarEnabled(false);
    }

    private void initMarker() {
        LatLng latLng = preferences.getLastMarkerPosition();
        if (latLng != null) {
            destination = new TargetDestination(map, latLng, distance);
        }
    }

    private void initCamera() throws SecurityException {
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        LatLng latLng;
        if (location != null) {
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
        } else {
            if (destination != null) {
                latLng = destination.position;
            } else {
                latLng = new LatLng(-34, 151);//  Sydney
            }
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
    }

    public void distanceChanged(int distance) {
        this.distance = distance;
        if (destination != null) {
            destination.serRadius(distance);
        }
    }

    public void traceOn() throws NoSetDestinationException {
        if (destination == null) {
            throw new NoSetDestinationException();
        }

        serviceIntent = new Intent(activity, TrackService.class);
        serviceIntent.setAction(ACTION_START_TRACK);
        serviceIntent.putExtra(KEY_TARGET_LAT, destination.position.latitude);
        serviceIntent.putExtra(KEY_TARGET_LNG, destination.position.longitude);
        serviceIntent.putExtra(KEY_TARGET_RADIUS, destination.radius);
        activity.startService(serviceIntent);
    }

    public void traceOff() {
        activity.stopService(serviceIntent);
    }

    private static class TargetDestination {
        private LatLng position;
        private double radius;
        private Marker marker;
        private Circle circle;

        TargetDestination(GoogleMap map, LatLng targetPosition, double targetRadius) {
            this.position = targetPosition;
            this.radius = targetRadius;
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(targetPosition);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

            CircleOptions circleOptions = new CircleOptions();
            circleOptions.center(targetPosition);
            circleOptions.radius(targetRadius);
            circleOptions.fillColor(R.color.fillTargetRadius);
            circleOptions.strokeWidth(1);

            marker = map.addMarker(markerOptions);
            circle = map.addCircle(circleOptions);
        }

        private void setPosition(LatLng position) {
            this.position = position;
            marker.setPosition(position);
            circle.setCenter(position);
        }

        private void serRadius(int radius) {
            this.radius = radius;
            circle.setRadius(radius);
        }
    }

}
