package site.stellarburgers.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {
    private final SelenideElement
            loginButton = $(byText("Войти в аккаунт")),
            createOrderButton = $(byText("Оформить заказ")),
            personalAccountButton = $(byText("Личный Кабинет")),
            mainPageTitle = $(byText("Соберите бургер")),
            currentConstructorType = $(".tab_tab__1SPyG.tab_tab_type_current__2BEPc"),
            constructorTypeBun = $(byText("Булки")),
            constructorTypeSauces = $(byText("Соусы")),
            constructorTypeFillings = $(byText("Начинки"));

    @Step("Открытие главной страницы")
    public MainPage openPage() {
        open("/");
        return this;
    }

    @Step("Проверка что кнопка офрмления заказа отображается")
    public MainPage isCreateOrderButtonDisplayed() {
        createOrderButton.shouldBe(visible);
        return this;
    }

    @Step("Клик на кнопку 'Войти в аккаунт'")
    public MainPage clickLoginButton() {
        loginButton.click();
        return this;
    }

    @Step("Проверка что главная страница открылась")
    public MainPage isMainPageOpen() {
        mainPageTitle.shouldBe(visible);
        return this;
    }

    @Step("Клик на кнопку 'Личный кабинет'")
    public MainPage clickPersonalAccountButton() {
        personalAccountButton.click();
        return this;
    }

    @Step("Получение текста выбранного типа конструктора")
    public String getCurrentConstructorTypeText() {
        return currentConstructorType.getText();
    }

    @Step("Выбор типа конструктора 'Булки'")
    public MainPage clickConstructorTypeBun() {
        constructorTypeBun.click();
        return this;
    }

    @Step("Проверяем, что выделен нужный тип конструктора")
    public MainPage isNeededConstructorTypeSelected(String constructorType) {
        currentConstructorType.shouldHave(text(constructorType));
        return this;
    }

    @Step("Выбор типа конструктора 'Соусы'")
    public MainPage clickConstructorTypeSauces() {
        constructorTypeSauces.click();
        return this;
    }

    @Step("Выбор типа конструктора 'Начинки'")
    public MainPage clickConstructorTypeFillings() {
        constructorTypeFillings.click();
        return this;
    }
}
