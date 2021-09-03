package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize
public abstract class Pagination {

    @JsonProperty("total")
    public abstract int getTotal();

    @JsonProperty("pages")
    public abstract int getPages();

    @JsonProperty("page")
    public abstract int getPage();

    @JsonProperty("limit")
    public abstract int getLimit();

    @JsonProperty("links")
    public abstract Links getLinks();

    @JsonCreator
    public static Pagination create(
            @JsonProperty("total") int total,
            @JsonProperty("pages") int pages,
            @JsonProperty("page") int page,
            @JsonProperty("limit") int limit,
            @JsonProperty("links") Links links) {
        return new AutoValue_Pagination(total, pages, page, limit, links);
    }
}
