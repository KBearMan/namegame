package bearpack.k.namegame.network.api2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/api/v1.0/profiles")
    Call<List<Profile>> getProfiles();
}
