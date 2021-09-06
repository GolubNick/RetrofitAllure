package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;

@AutoValue
@JsonDeserialize
public abstract class DataCreateUser {

    @Nullable
    @JsonProperty("meta")
    public abstract Meta getMeta();

    @JsonProperty("data")
    public abstract User getData();

    @JsonCreator
    public static DataCreateUser create(
            @JsonProperty("meta") Meta meta,
            @JsonProperty("data") User data
    ) {
        return new AutoValue_DataCreateUser(meta, data);
    }
}