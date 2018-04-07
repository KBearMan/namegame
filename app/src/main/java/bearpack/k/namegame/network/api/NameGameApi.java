package bearpack.k.namegame.network.api;

import bearpack.k.namegame.network.api.model.Profiles;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NameGameApi {
    @GET("/api/v1.0/profiles")
    Call<Profiles> getProfiles();
}
