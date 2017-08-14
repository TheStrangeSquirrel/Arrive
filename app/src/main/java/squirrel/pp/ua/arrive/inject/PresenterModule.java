package squirrel.pp.ua.arrive.inject;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import squirrel.pp.ua.arrive.presenter.MainPresenter;
import squirrel.pp.ua.arrive.presenter.MainPresenterImpl;

@Module
public class PresenterModule {
    @NonNull
    @Provides
    public MainPresenter getMainPresenter() {
        return new MainPresenterImpl();
    }

}
