package squirrel.pp.ua.arrive;

public class AppPermissionsManager {//TODO
    private static final int REQUEST_CODE_LOCATION = 0;
//
//    Completable
//
//    Activity activity;
//
//    private boolean isNotPermission(String permission) {
//        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
//    }
//
//    public OnRequestPermissionsResultCallback checkAndTryTakeLocationPermission() {
//        final String[] necessaryPermission = {Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION};
//        if (isNotPermission((necessaryPermission[0]))) {
//            ActivityCompat.requestPermissions(activity, necessaryPermission, REQUEST_CODE_LOCATION);
//        }
//        return new LocationPermissionCallback();
//    }
//
//    public class LocationPermissionCallback implements OnRequestPermissionsResultCallback {
//
//        @Override
//        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//            requestPermissionsLocationResult(requestCode, permissions, grantResults);
//        }
//
//        public Completable requestPermissionsLocationResult(final int requestCode,
//                                                            @NonNull final String[] permissions, @NonNull final int[] grantResults) {
//            return Completable.create(new CompletableOnSubscribe() {
//                @Override
//                public void subscribe(@io.reactivex.annotations.NonNull CompletableEmitter e) throws Exception {
//                    if (isPermissions(requestCode, permissions, grantResults)) {
//                        e.onComplete();
//                    }
//                    e.onError(new RuntimeException("Permission was denied"));
//                }
//            });
//        }
//
//        private boolean isPermissions(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//            if (requestCode == REQUEST_CODE_LOCATION) {
//                if (permissions.length != 2) {
//                    if (permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
//                            grantResults[0] == PackageManager.PERMISSION_GRANTED ||
//                            permissions[1] == Manifest.permission.ACCESS_COARSE_LOCATION &&
//                                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                        return true;
//                    }
//                }
//            }
//            return false;
//        }
//    }

}



