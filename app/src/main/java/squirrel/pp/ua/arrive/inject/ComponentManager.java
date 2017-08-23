package squirrel.pp.ua.arrive.inject;

import android.content.Context;

import squirrel.pp.ua.arrive.view.MapView;

public class ComponentManager {
    private Context context;
    private AppComponent appComponent;
    private MapComponent mapComponent;
    private ServiceComponent serviceComponent;
    private MapInteractorComponent mapInteractorComponent;

    public ComponentManager(Context context) {
        this.context = context;
        getAppComponent();
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            AppModule module = new AppModule(context);
            appComponent = DaggerAppComponent.builder().appModule(module).build();
        }
        return appComponent;
    }

    public MapComponent getMapComponent(MapView mapView) {
        if (mapComponent == null) {
            MapViewModule mapViewModule = new MapViewModule(mapView);
            mapComponent = DaggerMapComponent.builder().appComponent(appComponent).mapViewModule(mapViewModule).build();
        }
        return mapComponent;
    }

    public ServiceComponent getServiceComponent() {
        if (serviceComponent == null) {
            serviceComponent = appComponent.subComponent(new ServiceModule());
        }
        return serviceComponent;
    }

    public MapInteractorComponent getMapIteratorComponent() {
        if (mapInteractorComponent == null) {
            mapInteractorComponent = DaggerMapInteractorComponent.builder().appComponent(appComponent).build();
        }
        return mapInteractorComponent;
    }
}
