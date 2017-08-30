package squirrel.pp.ua.arrive.inject;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import squirrel.pp.ua.arrive.view.MapView;

@Module
@MapScope
class MapViewModule {
    private MapView view;

    MapViewModule(MapView view) {
        this.view = view;
    }

    @Provides
    public MapView getView() {
        return view;
    }

    @Provides
    public Activity getActivity() {
        return view.getActivity();
    }
}
