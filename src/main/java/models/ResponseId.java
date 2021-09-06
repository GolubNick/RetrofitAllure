package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize
public abstract class ResponseId {

    @JsonProperty("id")
    public abstract int getId();

    @JsonCreator
    public static ResponseId create(int id) { return new AutoValue_ResponseIds(id); }
}
