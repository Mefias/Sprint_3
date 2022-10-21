import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.Courier;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class PostCourierLoginTest extends QaScooterHandle {
    private static Courier myCourier;

    public PostCourierLoginTest() {
        super("/api/v1/courier/login");
    }

    @BeforeClass
    public static void setUpBeforeClass() {
        myCourier = getRandomCourier();
        myCourier.create();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        if (myCourier != null)
            myCourier.delete();
    }

    @Test
    @DisplayName("Successful login returns Code 200: ok")
    public void loginReturnsCode200() {
        Response response = getPostResponse(myCourier, handle);
        printResponseToConsole(response);
        compareResponseCodeToTarget(response, 200);
    }
    @Test
    @DisplayName("Successful login returns json with courier id")
    public void loginReturnsCourierId() {
        Response response = getPostResponse(myCourier, handle);
        printResponseToConsole(response);
        compareResponseBodyPartToTarget(response, "id", notNullValue(), "not null");
    }
    @Test
    @DisplayName("Wrong login returns Code 404: not found")
    public void wrongLoginReturnsCode404AndMessage() {
        Courier wrongCourier = myCourier.clone();
        wrongCourier.setLogin(getRandomCourier().getLogin());
        Response response = getPostResponse(wrongCourier, handle);
        printResponseToConsole(response);
        compareResponseCodeToTarget(response, 404);
        compareResponseMessageToTarget(response, "Учетная запись не найдена");
    }
    @Test
    @DisplayName("Wrong password returns Code 404: not found")
    public void wrongPasswordReturnsCode404AndMessage() {
        Courier wrongCourier = myCourier.clone();
        wrongCourier.setPassword(getRandomCourier().getPassword());
        Response response = getPostResponse(wrongCourier, handle);
        printResponseToConsole(response);
        compareResponseCodeToTarget(response, 404);
        compareResponseMessageToTarget(response, "Учетная запись не найдена");
    }
    @Test
    @DisplayName("No login returns Code 400: Bad Request")
    public void noLoginReturnsCode400AndMessage() {
        Courier wrongCourier = myCourier.clone();
        wrongCourier.setLogin(null);
        Response response = getPostResponse(wrongCourier, handle);
        printResponseToConsole(response);
        compareResponseCodeToTarget(response, 400);
        compareResponseMessageToTarget(response, "Недостаточно данных для входа");
    }
    @Test
    @DisplayName("No password returns Code 400: Bad Request")
    public void noPasswordReturnsCode400AndMessage() {
        Courier wrongCourier = myCourier.clone();
        wrongCourier.setPassword(null);
        Response response = getPostResponse(wrongCourier, handle);
        printResponseToConsole(response);
        compareResponseCodeToTarget(response, 400);
        compareResponseMessageToTarget(response, "Недостаточно данных для входа");
    }
}
