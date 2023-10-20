import Config.ApiOrder;
import io.qameta.allure.*;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import static org.hamcrest.Matchers.notNullValue;

public class TestOrdersList {
    @Test
    @Feature("Список заказов")
    @Story("Получение списка")
    @Description("Получение списка заказов")
    public void testGetOrdersList() {

        ApiOrder api = new ApiOrder();
        ValidatableResponse response = api.getOrderList();

        response.statusCode(200)
                .body("orders", notNullValue());
    }
}
