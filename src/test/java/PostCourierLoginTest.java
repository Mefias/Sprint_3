import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.Courier;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class PostCourierLoginTest extends QaScooterHandle {
    private String handle = "/api/v1/courier/login";

    @BeforeClass
    public static void setUpBeforeClass() {
        deleteMyCourier();
        createMyCourier();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        deleteMyCourier();
    }

    @Test
    @DisplayName("Successful login returns Code 200: ok")
    public void loginReturnsCode200() {
        Response response = given()
            .header("Content-type", "application/json")
            .and()
            .body(getMyCourier())
            .post(handle);
        printResponseToConsole(response);
        compareResponseCodeToTarget(response, 200);
    }
    @Test
    @DisplayName("Successful login returns json with courier id")
    public void loginReturnsCourierId() {
        Response response = given()
            .header("Content-type", "application/json")
            .and()
            .body(getMyCourier())
            .post(handle);
        printResponseToConsole(response);
        compareResponseCodeToTarget(response, 404);
        compareResponseBodyPartToTarget(response, "id", notNullValue(), "not null");
    }
    @Test
    @DisplayName("Wrong login returns Code 404: not found")
    public void wrongLoginReturnsCode404AndMessage() {
        Courier myCourier = getMyCourier();
        myCourier.setLogin(nonExistingLogin);
        Response response = given()
            .header("Content-type", "application/json")
            .and()
            .body(myCourier)
            .post(handle);
        printResponseToConsole(response);
        compareResponseCodeToTarget(response, 404);
        compareResponseMessageToTarget(response, "Учетная запись не найдена");
    }
    @Test
    @DisplayName("Wrong login returns Code 404: not found")
    public void wrongPasswordReturnsCode404AndMessage() {
        Courier myCourier = getMyCourier();
        myCourier.setPassword(myPassword+"1234");
        Response response = given()
            .header("Content-type", "application/json")
            .and()
            .body(myCourier)
            .post(handle);
        printResponseToConsole(response);
        compareResponseCodeToTarget(response, 404);
        compareResponseMessageToTarget(response, "Учетная запись не найдена");
    }
    @Test
    @DisplayName("No login returns Code 400: Bad Request")
    public void noLoginReturnsCode400AndMessage() {
        Courier myCourier = getMyCourier();
        myCourier.setLogin(null);
        Response response = given()
            .header("Content-type", "application/json")
            .and()
            .body(myCourier)
            .post(handle);
        printResponseToConsole(response);
        compareResponseCodeToTarget(response, 400);
        compareResponseMessageToTarget(response, "Недостаточно данных для входа");
    }    @Test
    @DisplayName("No password returns Code 400: Bad Request")
    public void noPasswordReturnsCode400AndMessage() {
        Courier myCourier = getMyCourier();
        myCourier.setPassword(null);
        Response response = given()
            .header("Content-type", "application/json")
            .body(myCourier)
            .post(handle);
        printResponseToConsole(response);
        compareResponseCodeToTarget(response, 400);
        compareResponseMessageToTarget(response, "Недостаточно данных для входа");
    }
}
