package squirrel.pp.ua.arrive.inject;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import squirrel.pp.ua.arrive.interactor.MapInteractor;

@Module
@MapScope
public class MapIteractorModule {
    @Provides
    MapInteractor getMapIteractor(Context context) {
        return new MapInteractor(context);
    }
}
