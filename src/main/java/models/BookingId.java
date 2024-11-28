package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonDeserialize(builder = models.BookingId.BookingIdBuilder.class)
public class BookingId {
    @JsonProperty("bookingid")
    private int bookingid;
}
