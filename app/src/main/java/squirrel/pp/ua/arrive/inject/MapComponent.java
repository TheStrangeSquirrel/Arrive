package squirrel.pp.ua.arrive.inject;

import dagger.Component;
import squirrel.pp.ua.arrive.interactor.MapInteractor;
import squirrel.pp.ua.arrive.presenter.MapPresenterImpl;
import squirrel.pp.ua.arrive.view.MapActivity;

@Component(dependencies = AppComponent.class,
        modules = {MapViewModule.class, PresenterModule.class, MapInteractorModule.class})
@MapScope
public interface MapComponent {
    void inject(MapActivity mapActivity);

    void inject(MapPresenterImpl presenter);

    void inject(MapInteractor interactor);
}
