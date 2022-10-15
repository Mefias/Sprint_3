import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.Courier;
import org.hamcrest.Matcher;
import org.junit.BeforeClass;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class QaScooterHandle {
    public String handle;
    public static final String myLogin = "k-nosov";
    public static final String myPassword = "1234";
    public static final String nonExistingLogin = "k-nosov123456789";

    @BeforeClass
    public static void SetUpBaseUri() {
        RestAssured.baseURI= "https://qa-scooter.praktikum-services.ru";
    }
    public static Courier getMyCourier() {
        return new Courier(myLogin, myPassword, "Kirill");
    }
    public static void deleteMyCourier() {
        Courier courier = getMyCourier();
        courier.delete();
    }
    public static void createMyCourier() {
        Courier courier = getMyCourier();
        courier.create();
    }
    @Step("Compare response message to {message}")
    public void compareResponseMessageToTarget(Response response, String message) {
        response.then().assertThat().body("message", equalTo(message));
    }
    @Step("Compare response code to {code}")
    public void compareResponseCodeToTarget(Response response, int code) {
        response.then().assertThat().statusCode(code);
    }
    @Step("Check response part '{part}' is {matcherDescription}")
    public void compareResponseBodyPartToTarget(Response response, String part, Matcher matcher, String matcherDescription) {
        response.then().assertThat().body(part, matcher);
    }
    @Step("Print response code and body to console")
    public void printResponseToConsole(Response response) {
        System.out.println(response.statusCode() +": "+response.body().asString());
    }
}
