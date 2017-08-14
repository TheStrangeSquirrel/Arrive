package squirrel.pp.ua.arrive.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import squirrel.pp.ua.arrive.R;
import squirrel.pp.ua.arrive.inject.DaggerMainActivityComponent;
import squirrel.pp.ua.arrive.inject.MainActivityComponent;
import squirrel.pp.ua.arrive.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainView {

    @Inject
    MainPresenter mainPresenter;
    private MainActivityComponent activityComponent;
    private ViewHolder views;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        views = new ViewHolder();
        findViews();
        initViews();

        buildComponent();
        activityComponent.inject(this);
    }

    private void findViews() {
        views.mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fMap);
        views.toolbar = (Toolbar) findViewById(R.id.toolbar);
        views.fActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        views.distance = (TextView) findViewById(R.id.tvDistance);
        views.distanceRegulator = (AppCompatSeekBar) findViewById(R.id.sbDistance);

    }

    private void initViews() {
        initToolbar();
        initFActionButton();
        initMap();
    }

    private void initToolbar() {
        setSupportActionBar(views.toolbar);
    }

    private void initFActionButton() {
        views.fActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();//TODO
            }
        });
    }

    private void initMap() {
        views.mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                // Add a marker in Sydney and move the camera
                LatLng sydney = new LatLng(-34, 151);
                map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                map.addCircle(new CircleOptions().center(sydney).radius(100));

                checkAndTryTakeLocationPermission();
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);

                map.moveCamera(CameraUpdateFactory.newLatLng(sydney));//TODO TEST
            }
        });
    }

    public void checkAndTryTakeLocationPermission() {
        final int REQUEST_CODE_LOCATION = 0;
        final String[] necessaryPermission = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};
        if (isNotPermission((necessaryPermission[0]))) {
            ActivityCompat.requestPermissions(this, necessaryPermission, REQUEST_CODE_LOCATION);
        }
    }

    private boolean isNotPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (isPermissions(requestCode, permissions, grantResults)) {

        }
    }

    private boolean isPermissions(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        final int REQUEST_CODE_LOCATION = 0;
        if (requestCode == REQUEST_CODE_LOCATION) {
            if (permissions.length != 2) {
                if (permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED ||
                        permissions[1] == Manifest.permission.ACCESS_COARSE_LOCATION &&
                                grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
            }
        }
        return false;
    }

    private void buildComponent() {
        activityComponent = DaggerMainActivityComponent.create();
    }

    private class ViewHolder {
        SupportMapFragment mapFragment;
        Toolbar toolbar;
        FloatingActionButton fActionButton;
        AppCompatSeekBar distanceRegulator;
        TextView distance;
    }

}
