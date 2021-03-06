package resources;

import models.DataUsers;
import models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CreateNewUsers {

    @POST("/public/v1/users")
    Call<DataUsers> createNewUser(@Header("Authorization") String token, @Body User body);
}