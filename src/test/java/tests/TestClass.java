package tests;

import models.DataCreateUser;
import models.DataUsers;
import models.User;
import org.testng.annotations.Test;
import resources.CreateNewUsers;
import resources.GetUserDetails;
import resources.UpdateUserDetails;
import retrofit2.Call;
import retrofit2.Response;
import utils.JsonSchemaHelper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.UUID;

public class TestClass extends TestBase{

    private final GetUserDetails getUserDetails = retrofitResourceFactory.createResource(GetUserDetails.class);
    private final CreateNewUsers createNewUserFactory = retrofitResourceFactory.createResource(CreateNewUsers.class);
    private final UpdateUserDetails updateUserFactory = retrofitResourceFactory.createResource(UpdateUserDetails.class);

    @Test
    public void getUserByCorrectId() throws IOException {
        Call<DataUsers> request = getUserDetails.getUserById(123);
        Response<DataUsers> response = request.execute();
        softAssertions.assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_OK);
//        softAssertions.assertThat(JsonSchemaHelper.isMatchingSchema("/json-schema/get_user.json", response)).isTrue();
    }

    @Test
    public void createNewUser() throws IOException {
        User body = User.create("John Doe", "JohnDoe@" + UUID.randomUUID() + ".com", "male", "active");
        Call<DataCreateUser> request = createNewUserFactory.createNewUser(TOKEN, body);
        Response<DataCreateUser> response = request.execute();
        softAssertions.assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_CREATED);
        softAssertions.assertThat(JsonSchemaHelper.isMatchingSchema("/json-schema/create_user.json", response)).isTrue();
    }

    @Test
    public void updateUser() throws IOException {
        User body = User.create("John Doe", "JohnDoe@" + UUID.randomUUID() + ".com", "male", "active");
        Call<DataCreateUser> request = updateUserFactory.updateUser(53, TOKEN, body);
        Response<DataCreateUser> response = request.execute();
        softAssertions.assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_OK);
//        softAssertions.assertThat(JsonSchemaHelper.isMatchingSchema("/json-schema/createuser.json", response)).isTrue();
    }
}