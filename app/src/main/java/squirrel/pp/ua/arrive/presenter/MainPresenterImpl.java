package squirrel.pp.ua.arrive.presenter;

import android.os.Bundle;

import javax.inject.Inject;

import squirrel.pp.ua.arrive.view.MainView;

public class MainPresenterImpl implements MainPresenter {
    @Inject
    MainView view;

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }
}
