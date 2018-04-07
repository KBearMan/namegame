package bearpack.k.namegame.core;

import bearpack.k.namegame.network.NetworkModule;
import bearpack.k.namegame.ui.NameGameActivity;
import bearpack.k.namegame.ui.NameGameFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class
})
public interface ApplicationComponent {
    void inject(NameGameActivity activity);
}