package site.stellarburgers.api;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.with;
import static site.stellarburgers.helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.http.ContentType.JSON;

public class RestClient {
    public static RequestSpecification getBaseSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().method()
            .log().headers()
            .log().body()
            .contentType(JSON);
}
