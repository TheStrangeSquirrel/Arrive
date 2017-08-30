package squirrel.pp.ua.arrive.presenter;

import android.content.Intent;

public interface PermissionsPresenter {
    void onClickGetPermissions();

    void onClickOpenApplicationSettings();

    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
