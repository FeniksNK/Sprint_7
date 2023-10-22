package config;
import courier.Courier;
import courier.CourierLogin;


import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class ApiCourier extends App {
    private static final String PATH = URL + "api/v1/courier/";
    private static final String LOGIN_PATH = URL + "api/v1/courier/login";

    @Step("Создание нового курьера")
    public ValidatableResponse create(Courier courier) {
        return given().log().all()
                .spec(getHeader())
                .body(courier)
                .when()
                .post(PATH)
                .then().log().all();
    }
    @Step("Авторизация курьера")
    public ValidatableResponse login(CourierLogin courierLogin) {
        return given().log().all()
                .spec(getHeader())
                .body(courierLogin)
                .when()
                .post(LOGIN_PATH)
                .then().log().all();
    }

    @Step("Удаление созданного курьера")
    public void delete(int id) {
        given().log().all()
                .spec(getHeader())
                .when()
                .delete(PATH + id)
                .then().log().all();
    }
}
