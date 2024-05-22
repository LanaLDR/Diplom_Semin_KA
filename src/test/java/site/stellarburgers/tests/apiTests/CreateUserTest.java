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

public class CreateUserTest extends TestBase {
    private UserClient userClient;
    private User user;
    private String accessToken;


    @BeforeEach
    @Step("Создание тестовых данных")
    public void createRandomCredentials() {
        userClient = new UserClient();
        user = UserGenerator.getRandomUser();
    }

    @Test
    @DisplayName("Пользователь создается при корректных данных")
    public void createUniqueUserTest() {
        ValidatableResponse createUserResponse = userClient.createUser(user);
        createUserResponse.assertThat().statusCode(200).body("success", is(true));
        accessToken = createUserResponse.extract().path("accessToken");
    }

    @Test
    @DisplayName("Нельзя создать существующего пользователя")
    public void createNotUniqueUserTest() {
        accessToken = userClient.createUser(user).extract().path("accessToken");
        ValidatableResponse createUserResponse = userClient.createUser(user);
        createUserResponse.assertThat().statusCode(403).body("success", is(false));
    }

    @AfterEach
    @Step("Удаление тестового пользователя")
    public void deleteUser() {
        userClient.deleteUser(accessToken);
    }
}
