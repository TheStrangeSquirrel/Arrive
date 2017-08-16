package squirrel.pp.ua.arrive.inject;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import squirrel.pp.ua.arrive.presenter.MainPresenter;
import squirrel.pp.ua.arrive.presenter.MapPresenterImpl;
import squirrel.pp.ua.arrive.view.MapView;

@Module
@MapScope
public class PresenterModule {

    @NonNull
    @Provides
    public MainPresenter getMainPresenter(MapView view) {
        return new MapPresenterImpl(view);
    }

}
