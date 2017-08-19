package squirrel.pp.ua.arrive.inject;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import squirrel.pp.ua.arrive.data.GPSUtil;

@Module
public class ServiceModule {
    @Provides
    GPSUtil getGPSUtil(Context context) {
        return new GPSUtil(context);
    }
}
