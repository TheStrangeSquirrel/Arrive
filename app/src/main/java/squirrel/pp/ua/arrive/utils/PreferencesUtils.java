package squirrel.pp.ua.arrive.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.google.android.gms.maps.model.LatLng;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class PreferencesUtils {
    private final SharedPreferences prefs;
    private ExecutorService executor;

    @Inject
    public PreferencesUtils(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        executor = Executors.newSingleThreadExecutor();
    }

    public int getTypeMap() {
        return Integer.parseInt(prefs.getString("pref_typeMap", "1"));
    }

    public Uri getRingtone() {
        String pref_ringtone = prefs.getString("pref_ringtone", "default ringtone");
        return Uri.parse(pref_ringtone);
    }

    public boolean getVibroEnabled() {
        return prefs.getBoolean("pref_vibro", true);
    }

    public boolean getLoopPlayEnabled() {
        return prefs.getBoolean("pref_loopPlay", true);
    }

    public int getDistance() {
        return prefs.getInt("pref_distance", 1000);
    }

    public void setDistance(int distance) {
        executor.execute(() -> prefs.edit().putInt("pref_distance", distance).apply());
    }

    public LatLng getLastMarkerPosition() {
        if (prefs.contains("pref_marker_lat")) {
            double lat = getDouble("pref_marker_lat", 64.9751422);
            double lng = getDouble("pref_marker_lng", -18.084354);
            return new LatLng(lat, lng);
        } else {
            return null;
        }
    }

    public void saveLastMarkerPosition(LatLng latLng) {
        executor.execute(() -> {
            putDouble("pref_marker_lng", latLng.longitude);
            putDouble("pref_marker_lat", latLng.latitude);
        });
    }

    private void putDouble(final String key, final double value) {
        SharedPreferences.Editor edit = prefs.edit();
        edit.putLong(key, Double.doubleToRawLongBits(value));
        edit.apply();
    }

    private double getDouble(final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }

}
