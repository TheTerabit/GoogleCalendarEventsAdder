package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.api.client.util.DateTime;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventModel {

        private final String name;
        private final DateTime start;
        private final Long duration;
        private final String description;
        private final String location;
        private final DateTime reminder;

        public EventModel(@JsonProperty("name") String name,
                          @JsonProperty("start") DateTime start,
                          @JsonProperty("duration") Long duration,
                          @JsonProperty("description") String description,
                          @JsonProperty("location") String location,
                          @JsonProperty("reminder") DateTime reminder) {
            this.name = name;
            this.start = start;
            this.duration = duration;
            this.description = description;
            this.location = location;
            this.reminder = reminder;
        }

}
