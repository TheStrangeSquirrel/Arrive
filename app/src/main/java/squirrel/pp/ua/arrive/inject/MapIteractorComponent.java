package squirrel.pp.ua.arrive.inject;

import dagger.Component;
import squirrel.pp.ua.arrive.presenter.MapPresenterImpl;

@Component(dependencies = AppComponent.class, modules = MapIteractorModule.class)
@MapScope
public interface MapIteractorComponent {
    void inject(MapPresenterImpl mapPresenter);
}
