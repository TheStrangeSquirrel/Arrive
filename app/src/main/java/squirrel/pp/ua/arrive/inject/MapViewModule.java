package squirrel.pp.ua.arrive.inject;

import dagger.Module;
import dagger.Provides;
import squirrel.pp.ua.arrive.view.MapView;

@Module
@MapScope
public class MapViewModule {
    private MapView view;

    public MapViewModule(MapView view) {
        this.view = view;
    }

    @Provides
    public MapView getView() {
        return view;
    }
}
