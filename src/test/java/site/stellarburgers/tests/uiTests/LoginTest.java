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


public class LoginTest extends TestBase {

    private UserClient userClient;
    private User user;
    private String accessToken;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegistrationPage registrationPage;

    @BeforeEach
    @Step("Создание тестового пользователя")
    public void createUser() {
        userClient = new UserClient();
        user = UserGenerator.getRandomUser();
        accessToken = userClient.createUser(user).extract().path("accessToken");
    }

    @Test
    @Tag("ui")
    @DisplayName("Пользователь может авторизоваться начиная с главной страницы через личный кабинет")
    public void userCanBeLoginFromAccountButton() {
        mainPage = new MainPage();
        mainPage.openPage()
                .clickPersonalAccountButton();
        loginPage = new LoginPage();
        loginPage.loginUser(user);
        mainPage.isCreateOrderButtonDisplayed();
    }

    @Test
    @Tag("ui")
    @DisplayName("Пользователь может авторизоваться начиная с главной страницы через кнопку логина")
    public void userCanBeLoginFromMainPage() {
        mainPage = new MainPage();
        mainPage.openPage()
                .clickLoginButton();
        loginPage = new LoginPage();
        loginPage.loginUser(user);
        mainPage.isCreateOrderButtonDisplayed();
    }

    @Test
    @Tag("ui")
    @DisplayName("Пользователь может авторизоваться начиная со страницы регистрации")
    public void userCanBeLoginFromRegisterPage() {
        registrationPage = new RegistrationPage();
        registrationPage.openPage()
                .clickLoginButton();
        loginPage = new LoginPage();
        loginPage.loginUser(user);
        mainPage = new MainPage();
        mainPage.isCreateOrderButtonDisplayed();
    }

    @AfterEach
    @Step("Удаление тестового пользователя")
    public void deleteUser() {
        userClient.deleteUser(accessToken);
    }
}
