package tests;

import models.DataUsers;
import models.User;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import resources.CreateNewUsers;
import resources.DeleteUser;
import resources.GetUserDetails;
import resources.UpdateUserDetails;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.UUID;

import static utils.JsonSchemaHelper.matchSchema;

public class TestClass extends TestBase{

    private final GetUserDetails getUserDetails = retrofitResourceFactory.createResource(GetUserDetails.class);
    private final CreateNewUsers createNewUserFactory = retrofitResourceFactory.createResource(CreateNewUsers.class);
    private final UpdateUserDetails updateUserFactory = retrofitResourceFactory.createResource(UpdateUserDetails.class);
    private final DeleteUser deleteUserFactory = retrofitResourceFactory.createResource(DeleteUser.class);

    private final String NAME = "John Doe";
    private final String EMAIL = "JohnDoe@" + UUID.randomUUID() + ".com";
    private final String GENDER = "male";
    private final String STATUS = "active";

    private ThreadLocal<Response<DataUsers>> responseDataUser = new ThreadLocal<>();

    @BeforeMethod(onlyForGroups = "create")
    private void createUser() throws IOException {
        User body = User.create(null, NAME, EMAIL, GENDER, STATUS);
        Call<DataUsers> request = createNewUserFactory.createNewUser(TOKEN, body);
        Response<DataUsers> response = request.execute();
        responseDataUser.set(response);
    }

    @AfterMethod(onlyForGroups = "delete")
    private Response<DataUsers> deleteExistingUser() throws IOException {
        Call<DataUsers> request = deleteUserFactory.deleteUser(responseDataUser.get().body().getData().getId(), TOKEN);
        return request.execute();
    }

    @Test(groups = {"create", "delete"})
    public void getUserByCorrectId() throws IOException {
        Call<DataUsers> request = getUserDetails.getUserById(responseDataUser.get().body().getData().getId());
        Response<DataUsers> response = request.execute();
        softAssertions.assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_OK);
        matchSchema("/json-schema/user.json", response);
    }

    @Test(groups = {"delete"})
    public void createNewUser() throws IOException {
        createUser();
        Response<DataUsers> response = responseDataUser.get();
        softAssertions.assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_CREATED);
        matchSchema("/json-schema/user.json", response);
    }

    @Test(groups = {"create", "delete"})
    public void updateUser() throws IOException {
        User body = User.create(null, NAME, EMAIL, GENDER, STATUS);
        Call<DataUsers> request = updateUserFactory.updateUser(responseDataUser.get().body().getData().getId(), TOKEN, body);
        Response<DataUsers> response = request.execute();
        softAssertions.assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_OK);
        matchSchema("/json-schema/user.json", response);
    }

    @Test(groups = "create")
    public void deleteUser() throws IOException {
        Response<DataUsers> response = deleteExistingUser();
        softAssertions.assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_NO_CONTENT);
    }
}
