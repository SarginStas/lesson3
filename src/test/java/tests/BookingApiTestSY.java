//package tests;
//
//import api.BookingApi;
//import config.TestConfig;
//import io.qameta.allure.*;
//import models.*;
//import org.testng.annotations.Test;
//
//import static org.testng.Assert.assertEquals;
//import static org.testng.Assert.assertNotNull;
//
//@Epic("REST API Tests")
//@Feature("Booking Management")
//public class BookingApiTestSY extends TestConfig {
//
//    private final BookingApi bookingApi = new BookingApi();
//
//    @Test
//    @Story("Get Booking")
//    @Description("Verify that a booking can be retrieved")
//    @Severity(SeverityLevel.CRITICAL)
//    public void getBooking() {
//        Booking booking = bookingApi.getBooking(100);
//
//        System.out.println(booking.getBookingdates());
//
//        assertNotNull(booking.getFirstname());
//        assertEquals(booking.getFirstname(), "John");
//        assertEquals(booking.getLastname(), "Smith");
//        assertEquals(booking.getTotalprice(), 111);
//        assertEquals(booking.isDepositpaid(), true);
//        assertEquals(booking.getBookingdates().getCheckin(),"2018-01-01");
//        assertEquals(booking.getBookingdates().getCheckout(),"2019-01-01");
//        assertEquals(booking.getAdditionalneeds(),"Breakfast");
//    }
//}