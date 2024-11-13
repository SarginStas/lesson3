package api;

import io.restassured.response.Response;
import models.Booking;
import utils.JsonUtils;

import static io.restassured.RestAssured.given;

public class BookingApi {

    public Booking getBooking(int bookingId) {
        Response response = given()
                .pathParam("id", bookingId)
                .when()
                .get("/booking/{id}");
                
        return JsonUtils.fromJson(response.asString(), Booking.class);
    }
} 