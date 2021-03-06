package resources;

import models.DataUsers;
import models.User;
import retrofit2.Call;
import retrofit2.http.*;

public interface UpdateUserDetails {

    @PUT("/public/v1/users/{id}")
    Call<DataUsers> updateUser(@Path("id") Integer id, @Header("Authorization") String token, @Body User body);
}