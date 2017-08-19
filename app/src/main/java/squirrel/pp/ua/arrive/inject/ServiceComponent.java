package squirrel.pp.ua.arrive.inject;

import dagger.Subcomponent;
import squirrel.pp.ua.arrive.TrackService;

@Subcomponent(modules = ServiceModule.class)
public interface ServiceComponent {
    void inject(TrackService trackService);
}
