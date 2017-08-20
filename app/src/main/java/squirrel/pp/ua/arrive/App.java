package squirrel.pp.ua.arrive;

import android.app.Application;

import squirrel.pp.ua.arrive.inject.ComponentManager;


public class App extends Application {
    public static final String LOG_TAG = "Squirrel";
    private static ComponentManager componentManager;

    public static ComponentManager getComponentManager() {
        return componentManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
    }

    private void initAppComponent() {
        componentManager = new ComponentManager(this);
    }

}
