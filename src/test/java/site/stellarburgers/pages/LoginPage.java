package site.stellarburgers.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import site.stellarburgers.api.user.User;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {

    private final SelenideElement
            emailFieldInput = $(byXpath(".//label[text() = 'Email']/../input[@name = 'name']")), // поле ввода email))
            passwordFieldInput = $("input[name='Пароль']"),
            loginButton = $(byText("Войти")),
            registrationLinkButton = $(byXpath(".//a[contains(@href, '/register') and text() = 'Зарегистрироваться']")),
            recoverPasswordLinkButton = $(byXpath(".//a[contains(@href, '/forgot-password') and text() = 'Восстановить пароль']")); //Ссылка на восстановление пароля)

    @Step("Открытие страницы логина")
    public LoginPage openPage() {
        open("/login");
        return this;
    }

    @Step("Заполнение поля email")
    public LoginPage setEmailField(String email) {
        emailFieldInput.setValue(email);
        return this;
    }

    @Step("Заполнение поля пароль")
    public LoginPage setPasswordField(String password) {
        passwordFieldInput.setValue(password);
        return this;
    }

    @Step("Клик на кнопку 'Войти'")
    public LoginPage clickLoginButton() {
        loginButton.click();
        return this;
    }

    @Step("Клик на кнопку 'Зарегистрироваться'")
    public LoginPage clickRegistrationLinkButton() {
        registrationLinkButton.click();
        return this;
    }

    @Step("Проверка что открылась страница авторизации")
    public LoginPage isLoginPageOpen() {
        loginButton.shouldBe(visible);
        return this;
    }

    @Step("Польный логин пользователя")
    public LoginPage loginUser(User user) {
        open("/login");
        setEmailField(user.getEmail());
        setPasswordField(user.getPassword());
        clickLoginButton();
        return this;
    }
}
