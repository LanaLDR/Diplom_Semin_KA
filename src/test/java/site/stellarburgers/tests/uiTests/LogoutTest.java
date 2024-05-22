package site.stellarburgers.tests.uiTests;

import io.qameta.allure.Step;
import org.junit.jupiter.api.*;
import site.stellarburgers.api.user.User;
import site.stellarburgers.api.user.UserClient;
import site.stellarburgers.api.user.UserGenerator;
import site.stellarburgers.pages.LoginPage;
import site.stellarburgers.pages.MainPage;
import site.stellarburgers.pages.ProfilePage;
import site.stellarburgers.tests.TestBase;

public class LogoutTest extends TestBase {
    private UserClient userClient;
    private User user;
    private String accessToken;
    private MainPage mainPage;
    private LoginPage loginPage;
    private ProfilePage profilePage;

    @BeforeEach
    @Step("Создание тестового пользователя")
    public void createUser() {
        userClient = new UserClient();
        user = UserGenerator.getRandomUser();
        accessToken = userClient.createUser(user).extract().path("accessToken");
    }

    @Test
    @Tag("ui")
    @DisplayName("Можно разлогиниться по кнопке «Выйти» в личном кабинете")
    public void userCanBeLoginFromAccountButton() {
        loginPage = new LoginPage();
        loginPage.openPage()
                .loginUser(user);

        mainPage = new MainPage();
        mainPage.clickPersonalAccountButton();

        profilePage = new ProfilePage();
        profilePage.clickLogoutButton();

        loginPage.isLoginPageOpen();
    }

    @AfterEach
    @Step("Удаление тестового пользователя")
    public void deleteUser() {
        userClient.deleteUser(accessToken);
    }
}
