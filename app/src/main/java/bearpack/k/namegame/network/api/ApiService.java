package bearpack.k.namegame.network.api;

import java.util.List;

import bearpack.k.namegame.model.Profile;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/api/v1.0/profiles")
    Observable<List<Profile>> getProfiles();
}
