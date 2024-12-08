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

    private String authToken; // Глобальная переменная для хранения токена
    private int addBooking;

    @Test
    @Story("Auth Bookings")
    @Severity(SeverityLevel.CRITICAL)
    public void testAuthBooking() {
        // Создаем JSON-запрос
        String requestBody = """
                {
                    "username" : "admin",
                    "password" : "password123"
                }
                """;

        // Отправляем POST-запрос
        String authBooking = bookingApi.authBooking(requestBody);

        // Сохраняем токен в глобальную переменную
        this.authToken = authBooking;
    }

    @Test
    @Story("Get Bookings")
    @Description("Verify that all bookings can be retrieved")
    @Severity(SeverityLevel.CRITICAL)
    @Override
    public void testGetAllBookings() {

        String firstName = "Susan";
        String lastName = "Ericsson";

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

        for (BookingId bookingId : bookingIds) {
            int id = bookingId.getBookingid();
            assertNotNull(id);
            Booking booking = bookingApi.getBooking(id);

            // Проверяем соответствие имени и фамилии
            assertEquals(booking.getFirstname(), firstName,
                    "Firstname mismatch for booking ID: " + id);
            assertEquals(booking.getLastname(), lastName,
                    "Lastname mismatch for booking ID: " + id);
            System.out.println("Validated booking with ID: " + id);
        }
    }

    @Test
    @Story("Add Bookings")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddBooking() {
        // Создаем JSON-запрос
        String requestBody = """
            {
                "firstname" : "Jim1",
                "lastname" : "Brown1",
                "totalprice" : 1111,
                "depositpaid" : true,
                "bookingdates" : {
                    "checkin" : "2024-01-01",
                    "checkout" : "2024-02-01"
                },
                "additionalneeds" : "Breakfast1"
            }
            """;
        int createdBooking = bookingApi.createBooking(requestBody);
        this.addBooking = createdBooking;
        Booking booking = bookingApi.getBooking(createdBooking);
    }

    @Test
    @Story("Update Bookings")
    @Severity(SeverityLevel.CRITICAL)
    public void testUpdatedBooking() {

        int bookingId = 10;

        // Создаем JSON-запрос
        String updatedRequestBody = """
            {
                "firstname" : "Дима",
                "lastname" : "Казаков",
                "totalprice" : 1111,
                "depositpaid" : true,
                "bookingdates" : {
                    "checkin" : "2024-01-01",
                    "checkout" : "2024-02-01"
                },
                "additionalneeds" : "Breakfast1"
            }
            """;

        bookingApi.updateBooking(bookingId, updatedRequestBody, authToken);
        Booking booking = bookingApi.getBooking(bookingId);
    }

    @Test
    @Story("Update BookingsName")
    @Severity(SeverityLevel.CRITICAL)
    public void testUpdatedBookingNames() {

        String patchRequestBody = """
        {
            "firstname": "Dmitriy",
            "lastname": "Smith"
        }
        """;

        int bookingId = 10;

        bookingApi.patchBooking(bookingId, patchRequestBody, authToken);
    }

    @Test
    @Story("Delete Booking")
    public void testDeleteBooking() {

        bookingApi.deleteBooking(addBooking, authToken);
    }

    @Test
    @Story("Ping Booking")
    public void testPingBooking() {

        bookingApi.pingBooking();
    }
}