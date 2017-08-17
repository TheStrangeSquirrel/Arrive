package squirrel.pp.ua.arrive.presenter;

import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;

public interface MainPresenter {
    void onCreate(Bundle savedInstanceState);

    void onOptionsItemSelected(int id);

    void OnMapReadyCallback(GoogleMap googleMap);
}
