package config;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utils.JsonUtils;

public abstract class TestConfig {
    
    protected static RequestSpecification requestSpec;
    
    @BeforeSuite
    public void setup() {
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(
            ObjectMapperConfig.objectMapperConfig()
                .jackson2ObjectMapperFactory((type, s) -> JsonUtils.getObjectMapper())
        );

        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
        
        RestAssured.requestSpecification = requestSpec;
    }

    @Test
    @Story("Get Bookings")
    @Description("Verify that all bookings can be retrieved")
    @Severity(SeverityLevel.CRITICAL)
    public abstract void testGetAllBookings();
}