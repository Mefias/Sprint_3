import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasSize;

public class GetOrdersTest extends QaScooterHandle {
    private String handle = "/api/v1/orders";
    @Test
    @DisplayName("Get orders returns non-empty order list")
    public void getOrdersReturnsList() {
        Response response = given()
                .header("Content-type", "application/json")
                .get(handle);
        printResponseToConsole(response);
        compareResponseCodeToTarget(response, 200);
        compareResponseBodyPartToTarget(response, "orders", hasSize(not(0)), "non-empty array");
    }
}
