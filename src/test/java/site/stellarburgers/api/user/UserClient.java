package site.stellarburgers.api.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import site.stellarburgers.api.RestClient;

import static io.restassured.RestAssured.given;

public class UserClient extends RestClient {
    private static final String CREATE_USER_PATH = "api/auth/register/";
    private static final String LOGIN_USER_PATH = "api/auth/login/";
    private static final String DELETE_USER_PATH = "api/auth/user/";
    private static final String EDIT_USER_PATH = "api/auth/user/";


    @Step("Создание пользователя")
    public ValidatableResponse createUser(User user) {
        return given(getBaseSpec)
                .body(user)
                .when()
                .post(CREATE_USER_PATH)
                .then();
    }

    @Step("Логин пользователя")
    public ValidatableResponse loginUser(User user) {
        UserCredentials userCredentials = UserCredentials.from(user);
        return given(getBaseSpec)
                .body(userCredentials)
                .when()
                .post(LOGIN_USER_PATH)
                .then();
    }

    @Step("Изменение пользователя")
    public ValidatableResponse editUser(String accessToken, String email, String password, String name) {
        User user = new User(email, password, name);
        return given(getBaseSpec)
                .header("authorization", accessToken)
                .body(user)
                .when()
                .patch(EDIT_USER_PATH)
                .then();
    }

    @Step("Удаление пользователя")
    public void deleteUser(String accessToken) {
                given(getBaseSpec)
                .header("authorization", accessToken)
                .when()
                .delete(DELETE_USER_PATH)
                .then();
    }
}
