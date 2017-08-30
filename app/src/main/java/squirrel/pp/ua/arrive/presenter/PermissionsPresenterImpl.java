package squirrel.pp.ua.arrive.presenter;

import android.content.Intent;

import squirrel.pp.ua.arrive.exception.PermissionsNotReceivedException;
import squirrel.pp.ua.arrive.interactor.PermissionsInteractor;
import squirrel.pp.ua.arrive.view.PermissionsView;

public class PermissionsPresenterImpl implements PermissionsPresenter {

    private PermissionsInteractor interactor;
    private PermissionsView view;

    public PermissionsPresenterImpl(PermissionsInteractor interactor, PermissionsView view) {
        this.interactor = interactor;
        this.view = view;
    }

    @Override
    public void onClickGetPermissions() {
        interactor.requestLocatePermission();
    }

    @Override
    public void onClickOpenApplicationSettings() {
        interactor.openApplicationSettings();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        try {
            interactor.onRequestPermissionsResult(requestCode, permissions, grantResults);
        } catch (PermissionsNotReceivedException e) {
            view.showPermissionsNotReceivedMassage();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        interactor.onActivityResult(requestCode, resultCode, data);
    }
}
