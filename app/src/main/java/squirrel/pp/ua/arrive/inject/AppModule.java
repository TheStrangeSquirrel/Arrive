package squirrel.pp.ua.arrive.inject;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import squirrel.pp.ua.arrive.utils.PreferencesUtils;

@Module
@Singleton
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @NonNull
    @Provides
    @Singleton
    Context getContext() {
        return this.context;
    }

    @NonNull
    @Provides
    @Singleton
    PreferencesUtils preferences() {
        return new PreferencesUtils(context);
    }
}
