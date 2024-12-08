package api;

import io.restassured.response.Response;
import models.Booking;

import utils.JsonUtils;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;

public class BookingApi {

    public Booking getBooking(int bookingId) {
        Response response = given()
                .pathParam("id", bookingId)
                .when()
                .get("/booking/{id}");
                
        return JsonUtils.fromJson(response.asString(), Booking.class);
    }

    public String authBooking(String bookingAuth) {
        Response response = given()
                .header("Content-Type", "application/json")
                .body(bookingAuth)
                .when()
                .post("/auth");

        // Извлекаем token
        String token = response.path("token");
        return token;
    }

    public int createBooking(String bookingRequest) {
        Response response = given()
                .body(bookingRequest)                      // Передаем тело запроса
                .when()
                .post("/booking");                      // Выполняем POST-запрос

        assertEquals(response.getStatusCode(), 200);

        // Извлекаем bookingid
        int bookingId = response.path("bookingid");

        return bookingId;
    }

    public void updateBooking(int bookingId, String updatedBookingRequest, String authToken) {
        Response response = given()
                .header("Content-Type", "application/json")    // Указываем тип содержимого
                .header("Accept", "application/json")         // Указываем тип принимаемого ответа
                .header("Cookie", "token=" + authToken)           // Передаём токен через cookie
                .body(updatedBookingRequest)                    // Передаем обновленное тело запроса
                .when()
                .put("/booking/" + bookingId);                 // Выполняем PUT-запрос по ID

        assertEquals(response.getStatusCode(), 200);
    }

    public void patchBooking(int bookingId, String patchRequestBody, String authToken) {
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + authToken)
                .body(patchRequestBody)
                .when()
                .patch("/booking/" + bookingId);

        // Проверяем успешный статус
        response.then().statusCode(200); // Ожидаем код 200 OK
    }

    public void deleteBooking(int bookingId, String authToken)  {
        Response response = given()
                .header("Cookie", "token=" + authToken)
                .when()
                .delete("/booking/" + bookingId);   // Выполняем DELETE-запрос

        // Проверяем, что удаление прошло успешно (код 201)
        assertEquals(response.getStatusCode(), 201);
    }

    public void pingBooking() {
        Response response = given()
                .when()
                .get("/ping");

        assertEquals(response.getStatusCode(), 201);
    }
} 