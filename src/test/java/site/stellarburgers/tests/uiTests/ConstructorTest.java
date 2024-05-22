package site.stellarburgers.tests.uiTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import site.stellarburgers.pages.MainPage;
import site.stellarburgers.tests.TestBase;


public class ConstructorTest extends TestBase {

    MainPage mainPage = new MainPage();

    @Test
    @Tag("ui")
    @DisplayName("Переход к разделу 'Булки' кликабелен")
    void constructorTypeBunAreClickable() {
        mainPage.openPage()
                .clickConstructorTypeSauces()
                .clickConstructorTypeBun()
                .isNeededConstructorTypeSelected("Булки");
    }

    @Test
    @Tag("ui")
    @DisplayName("Переход к разделу 'Соусы' кликабелен")
    void constructorTypeSaucesAreClickable() {
        mainPage.openPage()
                .clickConstructorTypeSauces()
                .isNeededConstructorTypeSelected("Соусы");
    }

    @Test
    @Tag("ui")
    @DisplayName("Переход к разделу 'Начинки' кликабелен")
    void constructorTypeFillingsAreClickable() {
        mainPage.openPage()
                .clickConstructorTypeFillings()
                .isNeededConstructorTypeSelected("Начинки");
    }
}
