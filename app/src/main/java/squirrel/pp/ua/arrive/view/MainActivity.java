package squirrel.pp.ua.arrive.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import javax.inject.Inject;

import squirrel.pp.ua.arrive.R;
import squirrel.pp.ua.arrive.inject.DaggerMainActivityComponent;
import squirrel.pp.ua.arrive.inject.MainActivityComponent;
import squirrel.pp.ua.arrive.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity {
    @Inject
    MainPresenter mainPresenter;
    private MainActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initFActionButton();
        buildComponent();
        activityComponent.inject(this);

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initFActionButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void buildComponent() {
        activityComponent = DaggerMainActivityComponent.create();
    }

}
