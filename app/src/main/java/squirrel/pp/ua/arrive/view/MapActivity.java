package squirrel.pp.ua.arrive.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;

import javax.inject.Inject;

import squirrel.pp.ua.arrive.App;
import squirrel.pp.ua.arrive.R;
import squirrel.pp.ua.arrive.inject.MapComponent;
import squirrel.pp.ua.arrive.presenter.MainPresenter;
import squirrel.pp.ua.arrive.utils.PreferencesUtils;

public class MapActivity extends AppCompatActivity implements MapView {
    public static final String ACTION_ON_ARRIVE = "squirrel.pp.ua.arrive.map_activity.actoin_on_arrive";

    @Inject
    MainPresenter presenter;
    @Inject
    PreferencesUtils preferences;
    private ViewHolder views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String action = getIntent().getAction();
        if (ACTION_ON_ARRIVE.equals(action)) {
            //TODO
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        views = new ViewHolder();
        findViews();
        inject();
        presenter.onCreate(savedInstanceState);
        initViews();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void switchCheckedTraceTB(boolean enable) {
        views.sTrace.setChecked(enable);
    }

    @Override
    public void massageSetDestination() {
        views.sTrace.setChecked(false);
        Toast.makeText(this, "Ned Set Destination", Toast.LENGTH_SHORT).show(); //TODO
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
        initDistance();
        initMap();
    }

    private void initTrace() {
        views.sTrace.setChecked(false);
        views.sTrace.setOnCheckedChangeListener((compoundButton, b) -> presenter.onTraceSwitch(b));
    }

    private void initDistance() {
        int distance = preferences.getDistance();
        onDistanceChanged(distance);
        views.distanceRegulator.setProgress(distance);
        views.distanceRegulator.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i < 100) return;
                onDistanceChanged(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void onDistanceChanged(int i) {
        presenter.distanceChanged(i);
        String distance = prepareToViewDistance(i);
        views.distance.setText(distance);
        preferences.setDistance(i);
    }

    private String prepareToViewDistance(int i) {
        String distance;
        if (i > 999) {
            distance = i / 1000 + " km\n" + i % 1000;
        } else {
            distance = i + "";
        }
        distance += " m";
        return distance;
    }

    private void initToolbar() {
        setSupportActionBar(views.toolbar);
    }

    private void initFActionButton() {
        views.fActionButton.setOnClickListener(view -> {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();//TODO
        });
    }

    private void initMap() {
        views.mapFragment.getMapAsync(googleMap -> presenter.onMapReadyCallback(googleMap));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.sTrace);
        views.sTrace = (Switch) item.getActionView().findViewById(R.id.switchForActionBar);
        initTrace();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        presenter.onOptionsItemSelected(item.getItemId());
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void inject() {
        MapComponent mapComponent = App.getComponentManager().getMapComponent(this);
        mapComponent.inject(this);
    }

    private class ViewHolder {
        SupportMapFragment mapFragment;
        Toolbar toolbar;
        Switch sTrace;
        FloatingActionButton fActionButton;
        AppCompatSeekBar distanceRegulator;
        TextView distance;
    }

}
