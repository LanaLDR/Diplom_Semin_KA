package site.stellarburgers.tests.apiTests;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import site.stellarburgers.api.user.User;
import site.stellarburgers.api.user.UserClient;
import site.stellarburgers.api.user.UserGenerator;
import site.stellarburgers.tests.TestBase;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;

public class NotLoginUserTest extends TestBase {

    private UserClient userClient;

    private static Stream<Arguments> badUserData() {
        return Stream.of(
                Arguments.of(UserGenerator.getDefaultEmail(), "fakePass"),
                Arguments.of("fakeEmail@yandex.ru", UserGenerator.getDefaultPassword()),
                Arguments.of(null, UserGenerator.getDefaultPassword()),
                Arguments.of(UserGenerator.getDefaultEmail(), null),
                Arguments.of(null, null)
        );
    }

    @BeforeEach
    public void setUp() {
        userClient = new UserClient();
    }

    @ParameterizedTest(name = "Проверка авторизации пользователя с данными: {0} {1}")
    @MethodSource("badUserData")
    @DisplayName("Пользователь не может авторизоваться при некорректных данных")
    public void loginUserWithFakeEmailTest(String email, String password) {
        User user = new User(email, password, null);
        ValidatableResponse loginUserResponse = userClient.loginUser(user);
        loginUserResponse.assertThat().statusCode(401).body("success", is(false));
    }
}
