package tests;

import api.BookingApi;
import config.TestConfig;
import io.qameta.allure.*;
import io.restassured.response.Response;
import models.*;
import models.BookingId;
import utils.JsonUtils;

import org.testng.annotations.Test;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

@Epic("REST API Tests")
@Feature("Booking Management")
public class BookingApiTestDK extends TestConfig {

    private final BookingApi bookingApi = new BookingApi();
//
//    @Test
//    @Story("Get Booking")
//    @Description("Verify that a booking can be retrieved")
//    @Severity(SeverityLevel.CRITICAL)
//    public void getBooking() {
//        Booking booking = bookingApi.getBooking(100);
//
//        System.out.println(booking.getFirstname());
//
//        assertNotNull(booking.getFirstname());
//        assertEquals(booking.getFirstname(), "Josh");
//        assertEquals(booking.getLastname(), "Allen");
//        assertEquals(booking.getTotalprice(), 111);
//        assertEquals(booking.isDepositpaid(), true);
//        assertEquals(booking.getBookingdates().getCheckout(), "2019-01-01");
//        assertEquals(booking.getBookingdates().getCheckin(), "2018-01-01");
//        assertEquals(booking.getAdditionalneeds(), "midnight snack");
//    }

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
        System.out.println(Arrays.toString(bookingIds));

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
                    "First name mismatch for booking ID: " + id);
            assertEquals(booking.getLastname(), lastName,
                    "Last name mismatch for booking ID: " + id);

            System.out.println("Validated booking with ID: " + id);
        }
    }
}