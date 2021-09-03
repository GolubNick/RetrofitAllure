package tests;

import models.DataUsers;
import org.testng.annotations.Test;
import resources.GetUserDetails;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.net.HttpURLConnection;

public class TestClass extends TestBase{

    private final GetUserDetails getUserDetails
            = resourceFactory.createResource(GetUserDetails.class);

    @Test
    public void getUserById() throws IOException {
        Call<DataUsers> request = getUserDetails.getUserById(123);
        Response<DataUsers> response = request.execute();
        softAssertions.assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_OK);
    }
}
