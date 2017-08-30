package squirrel.pp.ua.arrive.inject;

import dagger.Component;
import squirrel.pp.ua.arrive.interactor.PermissionsInteractor;
import squirrel.pp.ua.arrive.presenter.PermissionsPresenterImpl;
import squirrel.pp.ua.arrive.view.PermissionsFragment;

@Component(modules = {PermissionsModule.class})
public interface PermissionsComponent {
    void inject(PermissionsPresenterImpl presenter);

    void inject(PermissionsFragment view);

    void inject(PermissionsInteractor interactor);
}
