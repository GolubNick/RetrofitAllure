package configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;


@JsonDeserialize()
@AutoValue
public abstract class GeneralConfig {

    @JsonProperty("token")
    @Nullable
    public abstract String getToken();

    @JsonCreator
    public static GeneralConfig create(
            @JsonProperty("token") String token
    ) {
        return new AutoValue_GeneralConfig(token);
    }
}