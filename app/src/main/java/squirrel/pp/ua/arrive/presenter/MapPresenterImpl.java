package squirrel.pp.ua.arrive.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;

import javax.inject.Inject;

import squirrel.pp.ua.arrive.App;
import squirrel.pp.ua.arrive.R;
import squirrel.pp.ua.arrive.interactor.MapInteractor;
import squirrel.pp.ua.arrive.view.MapView;
import squirrel.pp.ua.arrive.view.SettingsActivity;

public class MapPresenterImpl implements MainPresenter {
    @Inject
    MapInteractor mapInteractor;
    private MapView view;

    @Inject
    public MapPresenterImpl(MapView view) {
        this.view = view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        inject();
    }

    private void inject() {
        App.getComponentManager().getMapIteratorComponent().inject(this);
    }

    @Override
    public void onOptionsItemSelected(int id) {
        Intent intent = null;
        Context context = view.getActivity();
        switch (id) {
            case R.id.action_settings:
                intent = new Intent(view.getActivity(), SettingsActivity.class);
                break;
            case R.id.action_place:
//                intent = new Intent(context, SaveTracksActivity.class);//TODO
                break;
            case R.id.action_about:
//                intent = new Intent(context, SettingsActivity.class);//TODO
                break;
            case R.id.action_rate:
                final String MARKET_URL = "market://details?id=ua.pp.squirrel.arrive";
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MARKET_URL));
                break;
        }
        context.startActivity(intent);
    }


    @Override
    public void OnMapReadyCallback(GoogleMap googleMap) {
        mapInteractor.initMap(googleMap);
    }

}
