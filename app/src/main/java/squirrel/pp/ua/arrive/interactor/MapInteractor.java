package squirrel.pp.ua.arrive.interactor;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import squirrel.pp.ua.arrive.App;
import squirrel.pp.ua.arrive.NoSetDestinationException;
import squirrel.pp.ua.arrive.R;
import squirrel.pp.ua.arrive.TrackService;
import squirrel.pp.ua.arrive.utils.PreferencesUtils;

import static squirrel.pp.ua.arrive.TrackService.ACTION_START_TRACK;

public class MapInteractor {
    public static final String KEY_TARGET_LAT = "TargetLat";
    public static final String KEY_TARGET_LNG = "TargetLng";
    public static final String KEY_TARGET_RADIUS = "TargetRadius";

    @Inject
    PreferencesUtils preferences;

    private Context context;
    private Intent serviceIntent;

    private GoogleMap map;
    private TargetDestination destination;
    private int distance = 1000;

    @Inject
    public MapInteractor(Context context) {
        this.context = context;
        App.getComponentManager().getAppComponent().inject(this);
    }

    public void initMap(GoogleMap googleMap) throws SecurityException {
        map = googleMap;
//        int typeMap = preferences.getTypeMap();//TODO
        int typeMap = 4;//TODO
        map.setMapType(typeMap);

        map.setMyLocationEnabled(true);
        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setMapToolbarEnabled(false);
        initCamera();
        map.setOnMapLongClickListener(latLng -> {
            if (destination == null) {
                destination = new TargetDestination(googleMap, latLng, distance);
            }
            destination.setPosition(latLng);
        });
    }

    private void initCamera() throws SecurityException {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        LatLng latLng;
        if (location != null) {
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
        } else {
            latLng = new LatLng(-34, 151);//  Sydney
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

        serviceIntent = new Intent(context, TrackService.class);
        serviceIntent.setAction(ACTION_START_TRACK);
        serviceIntent.putExtra(KEY_TARGET_LAT, destination.position.latitude);
        serviceIntent.putExtra(KEY_TARGET_LNG, destination.position.longitude);
        serviceIntent.putExtra(KEY_TARGET_RADIUS, destination.radius);
        context.startService(serviceIntent);
    }

    public void traceOff() {
        context.stopService(serviceIntent);
    }

    private static class TargetDestination {
        private LatLng position;
        private double radius;
        private Marker marker;
        private Circle circle;

        public TargetDestination(GoogleMap map, LatLng position, double radiusCircle) {
            this.position = position;
            this.radius = radiusCircle;
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(position);
//            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

            CircleOptions circleOptions = new CircleOptions();
            circleOptions.center(position);
            circleOptions.radius(radiusCircle);
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
