package squirrel.pp.ua.arrive;

import android.app.Application;

import squirrel.pp.ua.arrive.inject.AppComponent;
import squirrel.pp.ua.arrive.inject.AppModule;
import squirrel.pp.ua.arrive.inject.DaggerAppComponent;


public class App extends Application {
    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
    }

    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this)).
                        build();
    }

}
