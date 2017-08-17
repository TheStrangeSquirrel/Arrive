package squirrel.pp.ua.arrive.interactor;

import android.content.Context;
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

import squirrel.pp.ua.arrive.R;

public class MapInteractor {
    Context context;

    private GoogleMap map;
    private TargetDestination destination;

    @Inject
    public MapInteractor(Context context) {
        this.context = context;
    }

    public void initMap(GoogleMap googleMap) throws SecurityException {
        map = googleMap;

        map.setMyLocationEnabled(true);
        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setMapToolbarEnabled(false);
        initCamera();
        map.setOnMapLongClickListener(latLng -> {
            if (destination == null) {
                destination = new TargetDestination(googleMap, latLng, 2000);
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

    private static class TargetDestination {
        private Marker marker;
        private Circle circle;

        public TargetDestination(GoogleMap map, LatLng position, double radiusCircle) {
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
            marker.setPosition(position);
            circle.setCenter(position);
        }

        private void serRadius(double radius) {
            circle.setRadius(radius);
        }
    }

}
