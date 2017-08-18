package squirrel.pp.ua.arrive.inject;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import squirrel.pp.ua.arrive.interactor.MapInteractor;
import squirrel.pp.ua.arrive.utils.PreferencesUtils;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    Context context();

    PreferencesUtils preferences();

    void inject(MapInteractor iteractor);

}
