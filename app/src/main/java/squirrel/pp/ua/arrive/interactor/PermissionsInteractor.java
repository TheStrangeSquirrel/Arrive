package squirrel.pp.ua.arrive.interactor;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import javax.inject.Inject;

import squirrel.pp.ua.arrive.R;
import squirrel.pp.ua.arrive.view.MapView;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class PermissionsInteractor {//TODO
    private static final int PERMISSION_REQUEST_CODE = 792;

    @Inject
    MapView view;
    private Activity activity;

    public PermissionsInteractor(MapView view) {//TODO
        this.view = view;
        activity = view.getActivity();
    }

    public void checkAndTryTakeLocationPermission() {
        final String[] necessaryPermission = {ACCESS_FINE_LOCATION};
        if (!hasPermissions(necessaryPermission)) {
            requestPermissionWithRationale();
        }

    }

    public void requestPermissionWithRationale() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                ACCESS_FINE_LOCATION)) {
            final String message = activity.getString(R.string.nead_permission);
            Snackbar.make(activity.findViewById(R.id.toolbar), message, Snackbar.LENGTH_LONG)
                    .setAction("GRANT", v -> requestPerms()).show();
        } else {
            requestPerms();
        }
    }

    private boolean hasPermissions(String[] necessaryPermission) {
        int res = 0;
        for (String perms : necessaryPermission) {
            res = activity.checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)) {
                return false;
            }
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (!isAllowedPermissions(requestCode, grantResults)) {
            // we will give warning to user that they haven't granted permissions.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (activity.shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                    Toast.makeText(activity, "Storage Permissions denied.", Toast.LENGTH_SHORT).show();
                } else {
                    showNoStoragePermissionSnackbar();
                }
            }
        } else {
            return;
        }
    }

    private boolean isAllowedPermissions(int requestCode, @NonNull int[] grantResults) {
        boolean allowed = true;
        if (requestCode != PERMISSION_REQUEST_CODE) {
            allowed = false;
        } else {
            for (int res : grantResults) {
                if (res != PackageManager.PERMISSION_GRANTED) {
                    allowed = false;
                    break;
                }
            }
        }
        return allowed;
    }

    public void showNoStoragePermissionSnackbar() {
        Snackbar.make(activity.findViewById(R.id.toolbar), "Storage permission isn't granted", Snackbar.LENGTH_LONG)
                .setAction("SETTINGS", v -> {
                    openApplicationSettings();

                    Toast.makeText(activity, "Open Permissions and grant the Storage permission",
                            Toast.LENGTH_SHORT).show();
                }).show();
    }


    public void openApplicationSettings() {
        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + activity.getPackageName()));
        activity.startActivityForResult(appSettingsIntent, PERMISSION_REQUEST_CODE);
    }

    private void requestPerms() {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.getActivity().requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        }
    }

}
