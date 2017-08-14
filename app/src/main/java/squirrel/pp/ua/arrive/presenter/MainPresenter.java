package squirrel.pp.ua.arrive.presenter;

public interface MainPresenter {
    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);

    void setMyLocationEnabled(boolean enabled);
}
