package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize
public abstract class Meta {

    @JsonProperty("pagination")
    public abstract Pagination getPagination();

    @JsonCreator
    public static Meta create(
            @JsonProperty("pagination") Pagination pagination) {
        return new AutoValue_Meta(pagination);
    }
}
