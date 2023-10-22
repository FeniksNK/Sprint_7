import config.ApiCourier;
import courier.CourierGenerator;
import courier.CourierLogin;
import courier.Courier;
import io.qameta.allure.*;
import io.restassured.response.ValidatableResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.Matchers.*;
@RunWith(JUnit4.class)
public class TestLoginCourier {

    Courier courier;
    ApiCourier api = new ApiCourier();
    private int createdCourierId;
    private ApiCourier courierLogin;

    private int statusCode;

    @Before
    public void setUp() {
        courierLogin = new ApiCourier();
    }

    @Test
    @Feature("Авторизация курьера")
    @Story("Успешная авторизация созданного курьера")
    @Description("Проверка авторизации для созданного курьера")
    public void checkLoginWithCreatedCourier() {
        courier = CourierGenerator.getRandomData();
        ValidatableResponse createResponse = courierLogin.create(courier);
        statusCode = createResponse.extract().statusCode();

        ValidatableResponse response = courierLogin.login(CourierLogin.courierLogin(courier));
        statusCode = response.extract().statusCode();
        createdCourierId = response.extract().path("id");

        response.statusCode(200)
                .body("id", equalTo(createdCourierId));
    }

    @Test
    @Feature("Авторизация курьера")
    @Story("Авторизация без указания обязательных полей")
    @Description("Проверка, что нельзя авторизоваться без указания обязательных полей")
    public void loginRequiresAllFields() {
        CourierLogin courierLogin = new CourierLogin("", "");

        ValidatableResponse response = api.login(courierLogin);

        response
                .statusCode(400)
                .body("message", containsString("Недостаточно данных"));
    }

    @Test
    @Feature("Авторизация курьера")
    @Story("Авторизация с неверным логином/паролем")
    @Description("Проверка, что нельзя авторизоваться с неверным логином/паролем")
    public void loginWithWrongCredentials() {
        Courier wrongCourier = CourierGenerator.getRandomLoginAndPassword();
        CourierLogin wrongLogin = CourierLogin.courierLogin(wrongCourier);

        ValidatableResponse response = api.login(wrongLogin);

        response
                .statusCode(404)
                .body("message", containsString("Учетная запись не найдена"));
    }

    @Test
    @Feature("Авторизация курьера")
    @Story("Авторизация без логина")
    @Description("Проверка, что нельзя авторизоваться без логина")
    public void loginWithoutLogin() {
        CourierLogin courierLogin = new CourierLogin("", "password");

        ValidatableResponse response = api.login(courierLogin);

        response
                .statusCode(400)
                .body("message", containsString("Недостаточно данных"));
    }

    @Test
    @Feature("Авторизация курьера")
    @Story("Авторизация без пароля")
    @Description("Проверка, что нельзя авторизоваться без пароля")
    public void loginWithoutPassword() {
        CourierLogin courierLogin = new CourierLogin("login", "");

        ValidatableResponse response = api.login(courierLogin);

        response
                .statusCode(400)
                .body("message", containsString("Недостаточно данных"));
    }

    @Test
    @Feature("Авторизация курьера")
    @Story("Авторизация несуществующего пользователя")
    @Description("Проверка, что нельзя авторизоваться несуществующим пользователем")
    public void loginNonExistingCourier() {
        Courier nonExisting = CourierGenerator.getRandomLoginAndPassword();
        CourierLogin nonExistingLogin = CourierLogin.courierLogin(nonExisting);

        ValidatableResponse response = api.login(nonExistingLogin);

        response
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void deleteCourier() {
        api.delete(createdCourierId);
    }

}