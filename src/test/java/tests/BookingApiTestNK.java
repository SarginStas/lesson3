package tests;

import api.BookingApi;
import config.TestConfig;
import io.qameta.allure.*;
import io.restassured.response.Response;
import models.*;
import org.testng.annotations.Test;
import utils.JsonUtils;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;
import static org.testng.AssertJUnit.assertTrue;

@Epic("REST API Tests")
@Feature("Booking Management")
public class BookingApiTestNK extends TestConfig {

    private final BookingApi bookingApi = new BookingApi();

    @Test
    @Story("Get Booking")
    @Description("Verify that a booking can be retrieved")
    @Severity(SeverityLevel.CRITICAL)
    public void getBooking() {
        Booking booking = bookingApi.getBooking(100);

        System.out.println(booking.getBookingdates());

        assertNotNull(booking.getFirstname());
        assertEquals(booking.getFirstname(), "Josh");
        assertEquals(booking.getLastname(), "Allen");
        assertEquals(booking.getTotalprice(), 111);
        assertTrue(booking.isDepositpaid());
        assertEquals(booking.getBookingdates().getCheckin(),"2018-01-01");
        assertEquals(booking.getBookingdates().getCheckout(), "2019-01-01");
        assertEquals(booking.getAdditionalneeds(), "super bowls");
    }

    public class Counter {
        private int count;

        public Counter() {
            count = 0;
        }

        public int getCount() {
            return count;
        }

        public void increment() {
            count++;
        }
    }

    @Test
    @Story("Get Bookings")
    @Description("Verify that all bookings can be retrieved")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAllBookings() {
        Response response = given()
                .when()
                .queryParam("firstname","Jane")
                .queryParam("lastname","Doe")
                .get("/booking");



        assertEquals(200, response.getStatusCode());

        // Deserialize the response into an array of BookingId objects
        BookingId[] bookingIds = JsonUtils.fromJson(response.asString(), BookingId[].class);

        assertNotNull(bookingIds);
        assertTrue(bookingIds.length > 0);

        // Check that each bookingId is not null
        for (BookingId bookingId : bookingIds) {
            assertNotNull(bookingId.getBookingid());
        }
        Counter c = new Counter();

        for (BookingId bookingId : bookingIds) {
            c.increment();
        }
        System.out.println(c.getCount());

    }
}