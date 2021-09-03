package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;

@AutoValue
@JsonDeserialize
public abstract class Links {

    @Nullable
    @JsonProperty("previous")
    public abstract String getPrevious();

    @Nullable
    @JsonProperty("current")
    public abstract String getCurrent();

    @Nullable
    @JsonProperty("next")
    public abstract String getNext();

    @JsonCreator
    public static Links create(
            @JsonProperty("previous") String previous,
            @JsonProperty("current") String current,
            @JsonProperty("next") String next) {
        return new AutoValue_Links(previous, current, next);
    }
}
