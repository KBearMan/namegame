package bearpack.k.namegame.network.api2;

public class ApiUtil
{
    public static final String BASE_URL = "https://www.willowtreeapps.com";

    public static ApiService getApiService() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}
