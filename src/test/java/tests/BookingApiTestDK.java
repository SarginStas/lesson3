package tests;

import api.BookingApi;
import config.TestConfig;
import io.qameta.allure.*;
import io.restassured.response.Response;
import models.*;
import models.BookingId;
import utils.JsonUtils;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

@Epic("REST API Tests")
@Feature("Booking Management")
public class BookingApiTestDK extends TestConfig {

    private final BookingApi bookingApi = new BookingApi();

    @Test
    @Story("Get Bookings")
    @Description("Verify that all bookings can be retrieved")
    @Severity(SeverityLevel.CRITICAL)
    @Override
    public void testGetAllBookings() {

        String firstName = "Jane";
        String lastName = "Doe";

        Response response = given()
                .queryParam("firstname", firstName)
                .queryParam("lastname", lastName)
                .when()
                .get("/booking");

        assertEquals(response.getStatusCode(), 200);

        // Deserialize the response into an array of BookingId objects
        BookingId[] bookingIds = JsonUtils.fromJson(response.asString(), BookingId[].class);

        assertNotNull(bookingIds);
        assertTrue(bookingIds.length > 0);

        // Check that each bookingId is not null
        for (BookingId bookingId : bookingIds) {

            int id = bookingId.getBookingid();

            assertNotNull(id);

            Booking booking = bookingApi.getBooking(id);
            assertNotNull(booking, "Booking should not be null for ID: " + id);

            // Проверяем соответствие имени и фамилии
            assertEquals(booking.getFirstname(), firstName,
                    "Firstname mismatch for booking ID: " + id);
            assertEquals(booking.getLastname(), lastName,
                    "Lastname mismatch for booking ID: " + id);
            System.out.println("Validated booking with ID: " + id);
        }
    }
}