package squirrel.pp.ua.arrive.inject;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @NonNull
    @Singleton
    @Provides
    Context getContext() {
        return this.context;
    }
}
