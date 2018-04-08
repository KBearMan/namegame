package bearpack.k.namegame.viewmodels;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import bearpack.k.namegame.core.ListRandomizer;
import bearpack.k.namegame.network.api2.ApiService;
import bearpack.k.namegame.network.api2.ApiUtil;
import bearpack.k.namegame.network.api2.Profile;
import bearpack.k.namegame.network.api2.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by A on 4/3/2018.
 */

public class GameViewModel extends ViewModel
{
    RetrofitClient client;
    ListRandomizer randomizer = new ListRandomizer(new Random());
    ArrayList<Profile> mDataList = new ArrayList<>();
    public GameViewModel()
    {
        ApiService mService = ApiUtil.getApiService();
        mService.getProfiles().enqueue(new Callback<List<Profile>>() {
            @Override
            public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {
                mDataList.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<Profile>> call, Throwable t) {

            }
        });
    }

    public GameData getGameSetup()
    {
        GameData gameSetup = new GameData();
        gameSetup.dataList = randomizer.pickN(mDataList,6);
        gameSetup.selectedProfile = randomizer.pickOne(gameSetup.dataList);
        return gameSetup;
    }
}
