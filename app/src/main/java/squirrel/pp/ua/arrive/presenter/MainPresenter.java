package squirrel.pp.ua.arrive.presenter;

import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;

public interface MainPresenter {
    void onCreate(Bundle savedInstanceState);

    void onOptionsItemSelected(int id);

    void onMapReadyCallback(GoogleMap googleMap);

    void distanceChanged(int distance);

    void onTraceSwitch(boolean b);

    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);
}
