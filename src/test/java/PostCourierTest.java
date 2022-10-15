import io.restassured.response.Response;
import org.example.Courier;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName; // импорт DisplayName

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasSize;

public class PostCourierTest extends QaScooterHandle {
    private String handle = "/api/v1/courier/";
    @Before
    public void setUp() {
        Courier courierForDelete = new Courier(myLogin, myPassword, null);
        courierForDelete.delete();
    }

    @Test
    @DisplayName("Successful add courier returns Code 201: created")
    public void addReturnsSucceed() {
        Courier courier = new Courier(myLogin, myPassword, "Kirill");
        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .post(handle);
        printResponseToConsole(response);
        compareResponseCodeToTarget(response, 201);
        compareResponseBodyPartToTarget(response, "ok", equalTo(true), "is true");
    }
    @Test
    @DisplayName("Adding existing courier returns Code 409: already used")
    public void addExistingLoginCauseError409() {
        Courier courier = new Courier(myLogin, myPassword, "Kirill");
        given()
                .header("Content-type", "application/json")
                .body(courier)
                .post(handle);
        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .post(handle);
        printResponseToConsole(response);
        compareResponseCodeToTarget(response, 409);
        compareResponseMessageToTarget(response,"Этот логин уже используется");
    }
    @Test
    @DisplayName("Adding courier without login returns Code 400: not enough data")
    public void addWithoutLoginCauseError400() {
        Courier courier = new Courier(null, "password12345", "name12345");
        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .post(handle);
        printResponseToConsole(response);
        compareResponseCodeToTarget(response, 400);
        compareResponseMessageToTarget(response,"Недостаточно данных для создания учетной записи");
    }
    @Test
    @DisplayName("Adding courier without password returns Code 400: not enough data")
    public void addWithoutPasswordCauseError400() {
        Courier courier = new Courier("login12345", null, "name12345");
        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .post(handle);
        printResponseToConsole(response);
        compareResponseCodeToTarget(response, 400);
        compareResponseMessageToTarget(response,"Недостаточно данных для создания учетной записи");
    }
    @Test
    @DisplayName("Adding courier without name returns Code 400: not enough data")
    public void addWithoutNameCauseError400() {
        Courier courier = new Courier("login12345", "password12345", null);
        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .post(handle);
        printResponseToConsole(response);
        compareResponseCodeToTarget(response, 400);
        compareResponseMessageToTarget(response,"Недостаточно данных для создания учетной записи");
    }
}
