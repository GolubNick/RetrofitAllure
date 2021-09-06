package resources;

import models.DataUsers;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetUserDetails {

    @GET("/public/v1/users/{id}")
    Call<DataUsers> getUserById(@Path("id") Integer id);
}
