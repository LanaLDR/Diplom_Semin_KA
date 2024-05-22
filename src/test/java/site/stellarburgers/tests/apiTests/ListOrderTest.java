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

import static org.hamcrest.CoreMatchers.is;

public class ListOrderTest {
    private UserClient userClient;
    private OrderClient orderClient;
    private Order order;
    private User user;
    private String accessToken;

    @BeforeEach
    @Step("Создание тестового пользователя и заказа")
    public void createUserAndOrder() {
        userClient = new UserClient();
        orderClient = new OrderClient();
        user = UserGenerator.getRandomUser();
        order = OrderGenerator.getDefaultOrder();
        accessToken = userClient.createUser(user).extract().path("accessToken");
        orderClient.createOrder(accessToken, order);
    }

    @Test
    @DisplayName("Можно получить список заказов пользователя с авторизацией")
    public void getOrderListUserWithAuthTest() {
        ValidatableResponse getUserListOrder = orderClient.getListOrdersUser(accessToken);
        getUserListOrder.assertThat().statusCode(200).body("success", is(true));
    }

    @Test
    @DisplayName("Можно получить список заказов пользователя без авторизации")
    public void getOrderListUserWithoutAuthTest() {
        ValidatableResponse getUserListOrder = orderClient.getListOrdersUser("");
        getUserListOrder.assertThat().statusCode(401).body("success", is(false));
    }

    @AfterEach
    @Step("Удаление тестового пользователя")
    public void deleteUser() {
        userClient.deleteUser(accessToken);
    }
}
