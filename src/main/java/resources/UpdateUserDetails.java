package resources;

import models.DataCreateUser;
import models.User;
import retrofit2.Call;
import retrofit2.http.*;

public interface UpdateUserDetails {

    @PUT("/public/v1/users")
    Call<DataCreateUser> updateUser(@Query("id") Integer id, @Header("Authorization") String token, @Body User body);
}