import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.Courier;
import org.example.Order;
import org.hamcrest.Matcher;
import org.junit.BeforeClass;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class QaScooterHandle {
    public static final String baseURI = "https://qa-scooter.praktikum-services.ru";
    public static final Faker faker = new Faker();
    public final String handle;

    public QaScooterHandle(String handle) {
        this.handle = handle;
    }

    @BeforeClass
    public static void setUpBaseUri() {
        RestAssured.baseURI= baseURI;
    }
    public static Courier getRandomCourier() {
        String login = faker.internet().emailAddress();
        String password = faker.internet().password();
        String firstName = faker.name().firstName();

        return new Courier(login, password, firstName);
    }
    @Step("Post request with courier to {handle}")
    public Response getPostResponse(Courier courier, String handle) {
        return getPostResponse((Object) courier, handle);
    }
    @Step("Post request with order to {handle}")
    public Response getPostResponse(Order order, String handle) {
        return getPostResponse((Object) order, handle);
    }
    private Response getPostResponse(Object body, String handle) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(body)
                .post(handle);
        return response;
    }
    @Step("Get request with no body to {handle}")
    public Response getGetResponse(String handle) {
        Response response = given()
                .header("Content-type", "application/json")
                .get(handle);
        return response;
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
