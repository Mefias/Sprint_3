import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class PostOrdersTest extends QaScooterHandle {
    private String handle = "/api/v1/orders";
    private Order order;

    public PostOrdersTest(String name, String[] color) {
        Allure.description(name);
        order = new Order();
        order.setFirstName("Name123");
        order.setLastName("LastName123");
        order.setAddress("address123");
        order.setMetroStation("4");
        order.setPhone("+7 800 355 35 35");
        order.setRentTime(5);
        order.setDeliveryDate("2022-11-01");
        order.setComment("some comment");
        order.setColor(color);
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {"BLACK", new String[] {"BLACK"}},
                {"GREY", new String[] {"GREY"}},
                {"BLACK and GREY", new String[] {"BLACK", "GREY"}},
                {"no color", null}
        };
    }
    @Test
    @DisplayName("All color variants return code 200 and track number")
    public void postOrderReturnsCode200AndTrack() {
        Response response = given()
                .header("Content-type", "application/json")
                .body(order)
                .post(handle);
        printResponseToConsole(response);
        compareResponseCodeToTarget(response, 201);
        compareResponseBodyPartToTarget(response, "track", notNullValue(), "not null");
    }
}
