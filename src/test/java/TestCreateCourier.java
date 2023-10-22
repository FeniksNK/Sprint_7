import config.ApiCourier;
import courier.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import courier.CourierGenerator;

import static org.hamcrest.CoreMatchers.equalTo;

public class TestCreateCourier {
    private final ApiCourier apiCourier = new ApiCourier();
    private Courier validCourier;
    private int createdCourierId;
    private int statusCode;
    private boolean isCourierCreated;

    @Before
    public void setUp() {

        validCourier = CourierGenerator.getRandomData();
    }

    @Test
    @Feature("Создание курьера")
    @Story("Успешное создание нового курьера")
    @Description("Проверка успешного создания нового курьера с валидными данными")
    public void createCourierSuccess() {
        ValidatableResponse response = apiCourier.create(validCourier);

        statusCode = response.extract().statusCode();
        isCourierCreated = response.extract().path("ok");
        createdCourierId = response.extract().path("id");

        response.statusCode(200)
                .body("ok", equalTo(true));

    }

    @Test
    @Feature("Создание курьера")
    @Story("Создание дубликата курьера")
    @Description("Проверка, что нельзя создать двух одинаковых курьеров")
    public void createCourierDuplicate() {
        Courier duplicate = new Courier();
        duplicate.setLogin(validCourier.getLogin());

        ValidatableResponse response = apiCourier.create(duplicate);

        response.statusCode(400);
    }
    @Test
    @Feature("Создание курьера")
    @Story("Создание без обязательных полей")
    @Description("Проверка, что нельзя создать курьера без указания обязательных полей")
    public void createCourierWithoutRequiredFields() {
        Courier courierNoLogin = CourierGenerator.getRandomPassword();

        ValidatableResponse response = apiCourier.create(courierNoLogin);

        response.statusCode(400);
    }
    @Test
    @Feature("Создание курьера")
    @Story("Создание курьера с уже занятым логином")
    @Description("Проверка, что нельзя создать курьера с логином, который уже используется")
    public void createCourierWithExistingLogin() {
        Courier first = CourierGenerator.getRandomData();
        apiCourier.create(first);

        Courier second = CourierGenerator.getRandomData();
        second.setLogin(first.getLogin());

        ValidatableResponse response = apiCourier.create(second);

        response.statusCode(409);
    }


    @After
    public void tearDown() {
        apiCourier.delete(createdCourierId);
    }

    }
