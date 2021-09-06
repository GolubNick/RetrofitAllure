package resources;

import models.DataUsers;
import models.User;
import retrofit2.Call;
import retrofit2.http.*;

public interface DeleteUser {

    @DELETE("/public/v1/users/{id}")
    Call<DataUsers> deleteUser(@Path("id") Integer id, @Header("Authorization") String token);
}
