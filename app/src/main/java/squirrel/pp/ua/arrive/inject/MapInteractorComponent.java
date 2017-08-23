package squirrel.pp.ua.arrive.inject;

import dagger.Component;
import squirrel.pp.ua.arrive.interactor.MapInteractor;
import squirrel.pp.ua.arrive.presenter.MapPresenterImpl;

@Component(dependencies = AppComponent.class, modules = MapIteractorModule.class)
@MapScope
public interface MapInteractorComponent {
    void inject(MapPresenterImpl mapPresenter);
    void inject(MapInteractor mapInteractor);
}
