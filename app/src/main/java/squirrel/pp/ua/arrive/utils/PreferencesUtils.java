package squirrel.pp.ua.arrive.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class PreferencesUtils {
    private final SharedPreferences settings;
    private ExecutorService executor;

    @Inject
    public PreferencesUtils(Context context) {
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        executor = Executors.newSingleThreadExecutor();
    }

    public int getTypeMap() {
        return settings.getInt("pref_typeMap", 1);
    }

    public Uri getRingtone() {
        String pref_ringtone = settings.getString("pref_ringtone", "default ringtone");
        return Uri.parse(pref_ringtone);
    }

    public boolean getVibroEnabled() {
        return settings.getBoolean("pref_vibro", true);
    }

    public boolean getLoopPlayEnabled() {
        return settings.getBoolean("pref_loopPlay", true);
    }

    public int getDistance() {
        return settings.getInt("pref_distance", 1000);
    }

    public void setDistance(int distance) {
        executor.execute(() -> settings.edit().putInt("pref_distance", distance).apply());
    }


}
