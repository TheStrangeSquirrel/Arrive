package squirrel.pp.ua.arrive.inject;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import squirrel.pp.ua.arrive.interactor.MapInteractor;
import squirrel.pp.ua.arrive.utils.PreferencesUtils;

@Module
@MapScope
class MapInteractorModule {
    @Provides
    MapInteractor getMapInteractor(Activity activity, PreferencesUtils preferences) {
        return new MapInteractor(activity, preferences);
    }
}
