package site.stellarburgers.tests.apiTests;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import site.stellarburgers.api.user.User;
import site.stellarburgers.api.user.UserClient;
import site.stellarburgers.api.user.UserGenerator;
import site.stellarburgers.tests.TestBase;

import static org.hamcrest.CoreMatchers.is;

public class LoginUserTest extends TestBase {
    private UserClient userClient;
    private User user;
    private String accessToken;

    @BeforeEach
    @Step("Создание тестовго пользователя")
    public void createUser() {
        userClient = new UserClient();
        user = UserGenerator.getRandomUser();
        accessToken = userClient.createUser(user).extract().path("accessToken");
    }

    @Test
    @DisplayName("Пользователь может авторизоваться при корректных данных")
    public void loginUserTest() {
        ValidatableResponse loginUserResponse = userClient.loginUser(user);
        loginUserResponse.assertThat().statusCode(200).body("success", is(true));
    }

    @AfterEach
    @Step("Удаление тестового пользователя")
    public void deleteUser() {
        userClient.deleteUser(accessToken);
    }
}
