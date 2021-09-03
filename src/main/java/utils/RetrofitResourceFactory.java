package utils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Factory to create several resource clients.
 */
public class RetrofitResourceFactory {

    private final Retrofit retrofit;

    public RetrofitResourceFactory(String baseUrl) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new CustomLoggingInterceptor())
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();
    }

    /**
     * Creates resource instance.
     *
     * @param resourceClass instance of Retrofit client interface
     * @return Resource instance of type S
     */
    public <S> S createResource(Class<S> resourceClass) {
        return retrofit.create(resourceClass);
    }
}
