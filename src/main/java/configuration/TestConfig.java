package configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

@JsonDeserialize()
@AutoValue
public abstract class TestConfig {

    @JsonProperty("retrofitConfig")
    public abstract RetrofitConfig getRetrofitConfig();

    @JsonProperty("generalConfig")
    public abstract GeneralConfig getGeneralConfig();

    @JsonCreator
    public static TestConfig create(
            @JsonProperty("retrofitConfig") RetrofitConfig  retrofitConfig,
            @JsonProperty("generalConfig") GeneralConfig  generalConfig
    ) {
        return new AutoValue_TestConfig(retrofitConfig, generalConfig);
    }
}