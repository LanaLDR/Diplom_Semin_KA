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

public class NotCreateUserTest extends TestBase {
    private UserClient userClient;

    private static Stream<Arguments> badUserCredentials() {
        return Stream.of(
                Arguments.of(null, UserGenerator.getDefaultPassword(), UserGenerator.getDefaultName()),
                Arguments.of(UserGenerator.getDefaultEmail(), null, UserGenerator.getDefaultName()),
                Arguments.of(UserGenerator.getDefaultEmail(), UserGenerator.getDefaultPassword(), null),
                Arguments.of(null, null, null)
        );
    }

    @BeforeEach
    public void setUp() {
        userClient = new UserClient();
    }

    @ParameterizedTest(name = "Проверка создания пользователя с данными: {0} {1} {2}")
    @MethodSource("badUserCredentials")
    @DisplayName("Нельзя создать пользователя без обязательных полей")
    public void notCreateUserWithoutRequiredFieldsTest(String email, String password, String name) {
        User user = new User(email, password, name);
        ValidatableResponse createUserResponse = userClient.createUser(user);
        createUserResponse.assertThat().statusCode(403).body("success", is(false));
    }
}
