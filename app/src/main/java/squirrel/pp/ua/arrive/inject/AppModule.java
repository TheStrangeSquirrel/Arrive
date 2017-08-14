package squirrel.pp.ua.arrive.inject;

import android.content.Context;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @NonNull
    @Provides
    Context getContext() {
        return this.context;
    }
}
