package tests;

import api.BookingApi;
import config.TestConfig;
import io.qameta.allure.*;
import models.*;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Epic("REST API Tests")
@Feature("Booking Management")
public class BookingApiTestMN extends TestConfig {
    
    private final BookingApi bookingApi = new BookingApi();

    @Test
    @Story("Get Booking")
    @Description("Verify that a booking can be retrieved")
    @Severity(SeverityLevel.CRITICAL)
    public void getBooking() {
        Booking booking = bookingApi.getBooking(111);

        System.out.println(booking.getBookingdates());

        assertNotNull(booking.getFirstname());
        assertEquals(booking.getFirstname(), "John");
        assertEquals(booking.getLastname(), "Smith");
        assertEquals(booking.getTotalprice(), 111);
        assertTrue(booking.isDepositpaid());
        assertEquals(booking.getBookingdates().getCheckin(),"2018-01-01");
        assertEquals(booking.getBookingdates().getCheckout(),"2019-01-01");
        assertEquals(booking.getAdditionalneeds(), "Breakfast");
    }
} 