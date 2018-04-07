package bearpack.k.namegame.core;

import bearpack.k.namegame.db.DatabaseModule;
import bearpack.k.namegame.db.SQLiteHelper;
import bearpack.k.namegame.network.NetworkModule;
import bearpack.k.namegame.ui.NameGameActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class,
        DatabaseModule.class
})
public interface ApplicationComponent
{
    void inject(NameGameActivity activity);
    SQLiteHelper getSQLiteHelper();
    
}