package squirrel.pp.ua.arrive.inject;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import squirrel.pp.ua.arrive.AlarmService;
import squirrel.pp.ua.arrive.data.GPSUtil;
import squirrel.pp.ua.arrive.interactor.MapInteractor;
import squirrel.pp.ua.arrive.utils.PreferencesUtils;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    Context context();
    PreferencesUtils preferences();

    ServiceComponent subComponent(ServiceModule module);

    void inject(MapInteractor iteractor);

    void inject(GPSUtil gps);

    void inject(AlarmService alarmService);
}
