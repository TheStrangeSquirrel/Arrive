package squirrel.pp.ua.arrive.presenter;

import com.google.android.gms.maps.GoogleMap;

public interface MainPresenter {

    void onOptionsItemSelected(int id);

    void onMapReadyCallback(GoogleMap googleMap);

    void distanceChanged(int distance);

    void onTraceSwitch(boolean b);

    boolean hasPermissions();
}
