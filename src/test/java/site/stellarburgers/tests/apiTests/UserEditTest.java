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

public class UserEditTest extends TestBase {
    private User user;
    private UserClient userClient;
    private String accessToken;

    @BeforeEach
    @Step("Создание тестовго пользователя")
    public void createUser() {
        userClient = new UserClient();
        user = UserGenerator.getRandomUser();
        accessToken = userClient.createUser(user).extract().path("accessToken");
    }

    @Test
    @DisplayName("Можно изменить пользователя при корректной авторизации")
    public void editUserWithAuthTest() {
        ValidatableResponse editUserResponse =
                userClient.editUser(accessToken, "new@yandex.ru", "newPassword", "newName");
        editUserResponse.assertThat().statusCode(200).body("success", is(true));
    }

    @Test
    @DisplayName("Нельзя изменить данные пользователя при некорректном авторизации")
    public void editUserWithoutAuthTest() {
        ValidatableResponse editUserResponse =
                userClient.editUser("", "new@yandex.ru", "newPassword", "newName");
        editUserResponse.assertThat().statusCode(401).body("success", is(false));
    }

    @AfterEach
    @Step("Удаление тестового пользователя")
    public void deleteUser() {
        userClient.deleteUser(accessToken);
    }
}
