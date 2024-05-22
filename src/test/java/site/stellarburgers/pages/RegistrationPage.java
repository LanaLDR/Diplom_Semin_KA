package site.stellarburgers.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import site.stellarburgers.api.user.User;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class RegistrationPage {
    private final SelenideElement
            nameFiledInput = $(byXpath(".//label[text() = 'Имя']/../input[@name = 'name']")), // поле ввода email))
            emailFieldInput = $(byXpath(".//label[text() = 'Email']/../input[@name = 'name']")),
            passwordFieldInput = $("input[name='Пароль']"),
            registrationButton = $(byXpath(".//button[text() = 'Зарегистрироваться']")),
            incorrectPasswordText = $(byXpath(".//p[text() = 'Некорректный пароль']")),
            loginLinkButton = $(byXpath(".//a[contains(@href, '/login') and text() = 'Войти']"));

    @Step("Открытие страницы регистрации")
    public RegistrationPage openPage() {
        open("/register");
        return this;
    }

    @Step("Заполние поля 'имя'")
    public RegistrationPage setNameFiled(String name) {
        nameFiledInput.setValue(name);
        return this;
    }

    @Step("Заполнение поля 'email'")
    public RegistrationPage setEmailField(String email) {
        emailFieldInput.setValue(email);
        return this;
    }

    @Step("Заполнение поля 'пароль'")
    public RegistrationPage setPasswordField(String password) {
        passwordFieldInput.setValue(password);
        return this;
    }

    @Step("Клик на кнопку 'Зарегистрироваться'")
    public RegistrationPage clickRegistrationButton() {
        registrationButton.click();
        return this;
    }

    @Step("Клик на кнопку 'Войти'")
    public RegistrationPage clickLoginButton() {
        loginLinkButton.click();
        return this;
    }

    @Step("Сообщение о некорректном пароле отображается")
    public RegistrationPage isIncorrectPasswordDisplayed() {
        incorrectPasswordText.shouldBe(visible);
        return this;
    }

    @Step("Полная регистрация пользователя")
    public RegistrationPage registrationUser(User user) {
        setNameFiled(user.getName());
        setEmailField(user.getEmail());
        setPasswordField(user.getPassword());
        clickRegistrationButton();
        return this;
    }
}
