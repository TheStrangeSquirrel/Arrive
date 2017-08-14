package squirrel.pp.ua.arrive;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class AppPermissionsManager implements ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int REQUEST_CODE_LOCATION = 0;

    Activity activity;

    private boolean isNotPermission(String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void checkAndTryTakeLocationPermission() {
        final String[] necessaryPermission = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};
        if (isNotPermission((necessaryPermission[0]))) {
            ActivityCompat.requestPermissions(activity, necessaryPermission, REQUEST_CODE_LOCATION);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_LOCATION) {
            if (permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED ||
                    permissions[1] == Manifest.permission.ACCESS_COARSE_LOCATION &&
                            grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                mMap.setMyLocationEnabled(true);//TODO
            } else {
                // Permission was denied. Display an error message.
            }

        }

//    public void checkAndTryTakeLocationPermission(){
//        // Here, thisActivity is the current activity
//        if (ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION)!=
//                PackageManager.PERMISSION_GRANTED) {
//
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
//                    Manifest.permission.ACCESS_FINE_LOCATION)) {
//
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//            } else {
//                // No explanation needed, we can request the permission
//                ActivityCompat.requestPermissions(context,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//
//                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//            }
//        }
//    }
    }
