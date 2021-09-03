package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
@JsonDeserialize
public abstract class DataUsers {

    @JsonProperty("meta")
    public abstract Meta getMeta();

    @JsonProperty("data")
    public abstract List<User> getData();

    @JsonCreator
    public static DataUsers create(
            @JsonProperty("meta") Meta meta,
            @JsonProperty("data") List<User> data
    ) {
        return new AutoValue_DataUsers(meta, data);
    }
}
