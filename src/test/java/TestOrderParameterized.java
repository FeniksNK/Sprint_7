import config.ApiOrder;
import order.OrderGenerator;
import order.OrderRequest;
import io.qameta.allure.*;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class TestOrderParameterized {

    private final String[] color;

    public TestOrderParameterized(String[] color) {
        this.color = color;
    }
    @Parameterized.Parameters
    public static Collection<Object[]> getColorOptions() {
        return Arrays.asList(new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}}
        });
    }

    @Test
    @Feature("Создание заказа")
    @Story("Успешное создание")
    @Description("Создание заказа с разными вариантами цветов")
    public void testCreateOrderWithColors() {

        OrderRequest orderRequest = OrderGenerator.getRandomData();
        orderRequest.setColor(color);

        ApiOrder api = new ApiOrder();
        ValidatableResponse response = api.createNewOrder(orderRequest);

        response
                .statusCode(201)
                .body("track", notNullValue());
    }
}
