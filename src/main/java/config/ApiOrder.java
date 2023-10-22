package config;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import order.OrderRequest;
import static io.restassured.RestAssured.given;

public class ApiOrder extends App {
    private static final String ORDER_LIST_URL = URL + "api/v1/orders/";

    @Step("Получение списка заказов")
    public ValidatableResponse getOrderList() {
        return given().log().all()
                .spec(getHeader())
                .when()
                .get(ORDER_LIST_URL)
                .then().log().all();
    }

    @Step("Создание нового заказа")
    public ValidatableResponse createNewOrder(OrderRequest orderRequest) {
        return given().log().all()
                .spec(getHeader())
                .body(orderRequest)
                .when()
                .post(ORDER_LIST_URL)
                .then().log().all();
    }
}
