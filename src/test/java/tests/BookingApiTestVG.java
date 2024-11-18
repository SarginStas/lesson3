package tests;

import api.BookingApi;
import config.TestConfig;
import io.qameta.allure.*;
import models.Booking;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Epic("REST API Tests")
@Feature("Booking Management")
public class BookingApiTest extends TestConfig {

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
        assertEquals(booking.isDepositpaid(), true);
        assertEquals(booking.getBookingdates().getCheckin(),"2013-02-23");
        assertEquals(booking.getBookingdates().getCheckout(),"2014-10-23");
        assertEquals(booking.getAdditionalneeds(),"Breakfast");
    }
}