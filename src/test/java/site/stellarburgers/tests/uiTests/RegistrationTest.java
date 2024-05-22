package site.stellarburgers.tests.uiTests;

import io.qameta.allure.Step;
import org.junit.jupiter.api.*;
import site.stellarburgers.api.user.User;
import site.stellarburgers.api.user.UserClient;
import site.stellarburgers.api.user.UserGenerator;
import site.stellarburgers.pages.LoginPage;
import site.stellarburgers.pages.MainPage;
import site.stellarburgers.pages.RegistrationPage;
import site.stellarburgers.tests.TestBase;

public class RegistrationTest extends TestBase {
    private String accessToken;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegistrationPage registrationPage;
    private User user = new User();
    private UserClient userClient = new UserClient();

    @Test
    @Tag("ui")
    @DisplayName("Пользователь может зарегистрироваться начиная со страницы логина")
    public void userCanBeRegisteredFromLoginPage() {
        user = UserGenerator.getRandomUser();
        loginPage = new LoginPage();
        loginPage.openPage()
            .clickRegistrationLinkButton();

        registrationPage = new RegistrationPage();
        registrationPage.registrationUser(user);

        loginPage.loginUser(user);

        mainPage = new MainPage();
        mainPage.isMainPageOpen();
    }

    @Test
    @Tag("ui")
    @DisplayName("Пользователь не может зарегистрироваться с коротким паролем")
    public void userCantBeRegisteredWithWrongPassword() {
        user = UserGenerator.getRandomUserWithWrongPassword();
        registrationPage = new RegistrationPage();
        registrationPage.openPage()
                .registrationUser(user)
                .isIncorrectPasswordDisplayed();
    }

    @AfterEach
    @Step("Удаление тестового пользователя")
    public void deleteUser() {
        accessToken = userClient.loginUser(user).extract().path("accessToken");
        if (accessToken != null) userClient.deleteUser(accessToken);
    }
}
