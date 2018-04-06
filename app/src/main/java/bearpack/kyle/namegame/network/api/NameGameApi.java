package bearpack.kyle.namegame.network.api;

import bearpack.kyle.namegame.network.api.model.Profiles;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NameGameApi {
    @GET("/api/v1.0/profiles")
    Call<Profiles> getProfiles();
}
