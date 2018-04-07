package bearpack.k.namegame.db;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shiva on 4/4/2018.
 */

@Module
public class DatabaseModule
{
    @Provides @Singleton
    SQLiteHelper providesSQLiteHelper(Context context)
    {
        return new SQLiteHelper(context);
    }
}
