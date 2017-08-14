package squirrel.pp.ua.arrive.inject;

import dagger.Component;
import squirrel.pp.ua.arrive.view.MainActivity;

@Component(modules = {PresenterModule.class})
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
}
