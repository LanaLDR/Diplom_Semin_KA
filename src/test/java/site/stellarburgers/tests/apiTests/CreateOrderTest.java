package site.stellarburgers.tests.apiTests;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import site.stellarburgers.api.order.Order;
import site.stellarburgers.api.order.OrderClient;
import site.stellarburgers.api.order.OrderGenerator;
import site.stellarburgers.api.user.User;
import site.stellarburgers.api.user.UserClient;
import site.stellarburgers.api.user.UserGenerator;
import site.stellarburgers.tests.TestBase;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;

public class CreateOrderTest extends TestBase {
    private User user;
    private UserClient userClient;
    private Order order;
    private OrderClient orderClient;
    private String accessToken;

    @BeforeEach
    @Step("Создание тестового пользователя и заказа")
    public void createUserAndOrder() {
        userClient = new UserClient();
        orderClient = new OrderClient();
        user = UserGenerator.getRandomUser();
        order = OrderGenerator.getDefaultOrder();
        accessToken = userClient.createUser(user).extract().path("accessToken");
    }

    @Test
    @DisplayName("Можно создать заказ с авторизацией")
    public void createOrderWithAuthTest() {
        ValidatableResponse createOrderClient = orderClient.createOrder(accessToken, order);
        createOrderClient.assertThat().statusCode(200).body("success", is(true));
    }

    @Test
    @DisplayName("Можно создать заказ без авторизациий")
    public void createOrderWithoutAuthTest() {
        ValidatableResponse createOrderClient = orderClient.createOrder("", order);
        createOrderClient.assertThat().statusCode(200).body("success", is(true));
    }

    @Test
    @DisplayName("Нельзя создать заказ без ингредиентов с авторизацией")
    public void notCreateOrderWithoutIngredientsWithAuthTest() {
        order = new Order();
        ValidatableResponse createOrderClient = orderClient.createOrder(accessToken, order);
        createOrderClient.assertThat().statusCode(400).body("success", is(false));
    }

    @Test
    @DisplayName("Нельзя создать заказ без ингредиентов без авторизации")
    public void notCreateOrderWithoutIngredientsWithoutAuthTest() {
        order = new Order();
        ValidatableResponse createOrderClient = orderClient.createOrder("", order);
        createOrderClient.assertThat().statusCode(400).body("success", is(false));
    }

    @Test
    @DisplayName("Нельзя создать заказ c неверными ингредиетами с авторизацией")
    public void notCreateOrderWithFakeIngredientsWithAuthTest() {
        order = new Order(List.of("123kek321"));
        ValidatableResponse createOrderClient = orderClient.createOrder(accessToken, order);
        createOrderClient.assertThat().statusCode(500);
    }

    @Test
    @DisplayName("Нельзя создать заказ с неверными ингредиентами без авторизации")
    public void notCreateOrderWithFakeIngredientsWithoutAuthTest() {
        order = new Order(List.of("123kek321"));
        ValidatableResponse createOrderClient = orderClient.createOrder("", order);
        createOrderClient.assertThat().statusCode(500);
    }

    @AfterEach
    @Step("Удаление тестового пользователя")
    public void deleteUser() {
        userClient.deleteUser(accessToken);
    }
}
