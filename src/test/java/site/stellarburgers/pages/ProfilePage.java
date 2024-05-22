package site.stellarburgers.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ProfilePage {
    private final SelenideElement
            constructorButton = $(byXpath(".//p[text() = 'Конструктор']")),
            headerLogo = $(byXpath(".//div[@class = 'AppHeader_header__logo__2D0X2']")),
            logoutButton = $(byXpath(".//button[text() = 'Выход']"));

    @Step("Открытие страницы личного кабинета")
    public ProfilePage openPage() {
        open("/account/profile/");
        return this;
    }

    @Step("Клик на кнопку 'Выход'")
    public ProfilePage clickLogoutButton() {
        logoutButton.click();
        return this;
    }

    @Step("Клик на кнопку на лаготип в хедере")
    public ProfilePage clickHeaderLogo() {
        headerLogo.click();
        return this;
    }

    @Step("Клик на кнопку 'Конструктор'")
    public ProfilePage clickConstructorButton() {
        constructorButton.click();
        return this;
    }

    @Step("Проверка открытия личного кабинета")
    public ProfilePage isProfilePageOpen() {
        logoutButton.shouldBe(visible);
        return this;
    }
}
