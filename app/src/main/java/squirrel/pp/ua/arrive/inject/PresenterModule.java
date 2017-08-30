package squirrel.pp.ua.arrive.inject;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import squirrel.pp.ua.arrive.interactor.MapInteractor;
import squirrel.pp.ua.arrive.presenter.MainPresenter;
import squirrel.pp.ua.arrive.presenter.MapPresenterImpl;
import squirrel.pp.ua.arrive.view.MapView;

@Module
@MapScope
class PresenterModule {
    @NonNull
    @Provides
    MainPresenter getMainPresenter(MapView view, MapInteractor interactor) {
        return new MapPresenterImpl(view, interactor);
    }

}
