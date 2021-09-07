package configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

@JsonDeserialize()
@AutoValue
public abstract class RetrofitConfig {

    @JsonProperty("baseUrl")
    public abstract String getBaseUrl();

    @JsonCreator
    public static RetrofitConfig create(
            @JsonProperty("baseUrl") String  baseUrl
    ) {
        return new AutoValue_RetrofitConfig(baseUrl);
    }
}
