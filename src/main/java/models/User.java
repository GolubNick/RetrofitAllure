package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class User {

//    @JsonProperty("id")
//    public abstract int getId();

    @JsonProperty("name")
    public abstract String getName();

    @JsonProperty("email")
    public abstract String getEmail();

    @JsonProperty("gender")
    public abstract String getGender();

    @JsonProperty("status")
    public abstract String getStatus();

    @JsonCreator
    public static User create(
//            @JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("email") String email,
            @JsonProperty("gender") String gender,
            @JsonProperty("status") String status) {
        return new AutoValue_User(name, email, gender, status);
    }
}