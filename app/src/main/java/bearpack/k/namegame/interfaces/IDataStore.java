package bearpack.k.namegame.interfaces;

import java.util.List;
import java.util.Map;

/**
 * Created by A on 4/3/2018.
 */

public interface IDataStore
{
    List<IEmployee> getAllEmployees();

    Map<String,String> getAllStats();
}
