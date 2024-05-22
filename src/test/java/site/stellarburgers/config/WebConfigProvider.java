package site.stellarburgers.config;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

public class WebConfigProvider {
    WebConfig webConfig = ConfigFactory.create(WebConfig.class, System.getProperties());

    public void setUp() {
        Configuration.browserSize = webConfig.browserSize();
        Configuration.browser = webConfig.browser();
        Configuration.browserVersion = webConfig.browserVersion();
        Configuration.baseUrl = webConfig.baseUrl();
        RestAssured.baseURI = webConfig.baseUrl();
        Configuration.pageLoadStrategy = "normal";

        if(webConfig.isRemote()){
            Configuration.remote = webConfig.remoteDriver();
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                    "enableVNC", true,
                    "enableVideo", true
            ));
            Configuration.browserCapabilities = capabilities;
        }
    }
}
