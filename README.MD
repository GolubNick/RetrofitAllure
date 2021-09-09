### The API framework description
### 1. Retrofit
The API framework is based on Retrofit library. Retrofit is a REST Client. In Retrofit, you configure which converter is used for the data serialization.
To work with Retrofit you basically need the following three classes:

- Model class which is used as a JSON model;
- Interfaces that define the possible HTTP operations;
- Retrofit.Builder class - Instance which uses the interface and the Builder API to allow defining the URL end point for the HTTP operations.

Every method of an interface represents one possible API call. It must have HTTP annotation (GET, POST, etc.) to specify the request type and the relative URL. 
The return value wraps the response in a Call object with the type of the expected result.
    
    @GET("/public/v1/users")
    Call<DataUsers> getUserById();

You can use replacement blocks and query parameters to adjust the URL. A replacement block is added to the relative URL with {}. 
With the help of the @Path annotation on the method parameter, the value of that parameter is bound to the specific replacement block.

    @GET("/public/v1/users/{id}")
    Call<DataUsers> getUserById(@Path("id") Integer id);

Query parameters are added with the @Query annotation on a method parameter. They are automatically added at the end of the URL.

    @GET("/public/v1/users")
    Call<User> getUserById(@Query("id") Integer id);

The @Body annotation on a method parameter tells Retrofit to use the object as the request body for the call.
With the help of the @Header("Authorization") annotation you tell Retrofit to add the Authorization field to the request header with the value you provide for credentials.

    @PUT("/public/v1/users/{id}")
    Call<DataUsers> updateUser(@Path("id") Integer id, @Header("Authorization") String token, @Body User body);

### 2. Allure report
Allure has been chosen to report tests result. Allure report includes every HTTP action with attachments both request and response (body, status code, headers, etc.). 
To generate Allure report you have to make 'mvn allure:serve' command. Also, you should have an allure.properties file in your project. 
This file contains a directory path with test results.

### 3. Console logs
Logs in a console and Allure report are the same. This is achieved to use custom interceptor class.

### 4. Java Aspects
Also, a project uses AspectJ library. For this it is necessary to have a 'aop-ajc.xml' file which includes a path to a class with aspects.
AspectJ allows to catch an execution of any java method and to add some logic before, after, or runtime execution. 
Thus, we were able to wrap every HTTP request in a separate Allure step.

### 5. Initializing of framework variables

You can set framework properties either as environment variables or write in a config.yaml file which is in a framework's root directory.
Before tests running environment variables join to variables from config.yaml file and after that set as framework properties.