package tests;

import models.DataUsers;
import models.User;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import resources.CreateNewUsers;
import resources.DeleteUser;
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
    private final DeleteUser deleteUserFactory = retrofitResourceFactory.createResource(DeleteUser.class);

    private final String NAME = "John Doe";
    private final String EMAIL = "JohnDoe@" + UUID.randomUUID() + ".com";
    private final String GENDER = "male";
    private final String STATUS = "active";

    @Test
    public void getUserByCorrectId() throws IOException {
        Response<DataUsers> responseData = createUser();
        Call<DataUsers> request = getUserDetails.getUserById(responseData.body().getData().getId());
        Response<DataUsers> response = request.execute();
        softAssertions.assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_OK);
        softAssertions.assertThat(JsonSchemaHelper.isMatchingSchema("/json-schema/user.json", response)).isTrue();
        deleteExistingUser(responseData.body().getData().getId());
    }

    @Test
    public void createNewUser() throws IOException {
        Response<DataUsers> responseData = createUser();
        softAssertions.assertThat(responseData.code()).isEqualTo(HttpURLConnection.HTTP_CREATED);
        softAssertions.assertThat(JsonSchemaHelper.isMatchingSchema("/json-schema/user.json", responseData)).isTrue();
        deleteExistingUser(responseData.body().getData().getId());
    }

    @Test
    public void updateUser() throws IOException {
        Response<DataUsers> responseData = createUser();
        User body = User.create(NAME, EMAIL, GENDER, STATUS);
        Call<DataUsers> request = updateUserFactory.updateUser(responseData.body().getData().getId(), TOKEN, body);
        Response<DataUsers> response = request.execute();
        softAssertions.assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_OK);
        softAssertions.assertThat(JsonSchemaHelper.isMatchingSchema("/json-schema/user.json", response)).isTrue();
        deleteExistingUser(responseData.body().getData().getId());
    }

    @Test
    public void deleteUser() throws IOException {
        Response<DataUsers> responseData = createUser();
        Response<DataUsers> response = deleteExistingUser(responseData.body().getData().getId());
        softAssertions.assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_NO_CONTENT);
    }

    private Response<DataUsers> createUser() throws IOException {
        User body = User.create(NAME, EMAIL, GENDER, STATUS);
        Call<DataUsers> request = createNewUserFactory.createNewUser(TOKEN, body);
        Response<DataUsers> response = request.execute();
        return response;
    }

    private Response<DataUsers> deleteExistingUser(int id) throws IOException {
        Call<DataUsers> request = deleteUserFactory.deleteUser(id, TOKEN);
        Response<DataUsers> response = request.execute();
        return response;
    }
}