package squirrel.pp.ua.arrive.presenter;

import javax.inject.Inject;

import squirrel.pp.ua.arrive.view.MainView;

public class MapPresenterImpl implements MainPresenter {

    @Inject
    MainView view;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

    }

    @Override
    public void setMyLocationEnabled(boolean enabled) {
//        view.setMyLocationEnabled(enabled);
    }
}
