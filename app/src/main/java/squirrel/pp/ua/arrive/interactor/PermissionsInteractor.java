package squirrel.pp.ua.arrive.interactor;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import javax.inject.Inject;

import squirrel.pp.ua.arrive.exception.PermissionsNotReceivedException;
import squirrel.pp.ua.arrive.view.MapActivity;

public class PermissionsInteractor {
    private static final int PERMISSION_REQUEST_CODE = 792;
    private static final String[] REQUIRED_PERMISSION = {Manifest.permission.ACCESS_FINE_LOCATION};

    private Activity activity;

    @Inject
    public PermissionsInteractor(Activity activity) {
        this.activity = activity;
    }

    public void requestLocatePermission() {
        ActivityCompat.requestPermissions(activity, REQUIRED_PERMISSION, PERMISSION_REQUEST_CODE);
    }

    public void openApplicationSettings() {
        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + activity.getPackageName()));
        activity.startActivityForResult(appSettingsIntent, PERMISSION_REQUEST_CODE);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) throws PermissionsNotReceivedException {
        if (permissionsGrant(grantResults, permissions)) {
            Intent intent = new Intent(activity, MapActivity.class);
            activity.startActivity(intent);
        } else {
            throw new PermissionsNotReceivedException();
        }
    }

    private boolean permissionsGrant(int[] results, String[] permissions) {
        boolean permissionsGrant = false;
        if (results.length == REQUIRED_PERMISSION.length) {
            permissionsGrant = true;
            for (int i = 0; i < results.length; i++) {
                if (!REQUIRED_PERMISSION[i].equals(permissions[i]) ||
                        results[i] != PackageManager.PERMISSION_GRANTED) {
                    permissionsGrant = false;
                    break;
                }
            }
        }
        return permissionsGrant;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            requestLocatePermission();
        }
    }
}
