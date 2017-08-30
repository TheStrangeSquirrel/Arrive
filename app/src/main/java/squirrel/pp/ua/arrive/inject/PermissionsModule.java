package squirrel.pp.ua.arrive.inject;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import squirrel.pp.ua.arrive.interactor.PermissionsInteractor;
import squirrel.pp.ua.arrive.presenter.PermissionsPresenter;
import squirrel.pp.ua.arrive.presenter.PermissionsPresenterImpl;
import squirrel.pp.ua.arrive.view.PermissionsView;

@Module
class PermissionsModule {
    private PermissionsView view;

    PermissionsModule(PermissionsView view) {
        this.view = view;
    }

    @Provides
    PermissionsView getPermissionsView() {
        return view;
    }

    @Provides
    Activity getActivity() {
        return view.getActivity();
    }

    @Provides
    PermissionsPresenter getPermissionsPresenter(PermissionsInteractor interactor, PermissionsView view) {
        return new PermissionsPresenterImpl(interactor, view);
    }

    @Provides
    PermissionsInteractor getPermissionsInteractor(Activity activity) {
        return new PermissionsInteractor(activity);
    }

}
