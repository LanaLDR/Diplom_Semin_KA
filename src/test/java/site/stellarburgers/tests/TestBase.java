package site.stellarburgers.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInfo;
import site.stellarburgers.config.WebConfig;
import site.stellarburgers.config.WebConfigProvider;
import site.stellarburgers.helpers.AttachmentAllure;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {

    WebConfig webConfig = ConfigFactory.create(WebConfig.class, System.getProperties());
    static final WebConfigProvider provider = new WebConfigProvider();

    @BeforeAll
    static void beforeAll() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        provider.setUp();
    }

    @AfterEach
    void addAttachments(TestInfo testInfo) {
        if (testInfo.getTags().contains("ui")) {
            AttachmentAllure.screenshotAs("Last screenshot");
            AttachmentAllure.pageSource();
            AttachmentAllure.browserConsoleLogs();
        }
        if (webConfig.isRemote()) {
            AttachmentAllure.addVideo();
        }
        closeWebDriver();
    }
}
