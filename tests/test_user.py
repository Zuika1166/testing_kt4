import allure
from api_testing.base_request import BaseRequest
from models.user_model import UserModel

BASE_URL = "https://petstore.swagger.io/v2"


@allure.feature("USER")
@allure.story("POST /user")
@allure.title("Создание пользователя и проверка получения")
def test_create_get_user():
    api = BaseRequest(BASE_URL)

    body = {
        "id": 3001,
        "username": "pytest_user",
        "firstName": "Ivan",
        "lastName": "Ivanov",
        "email": "ivan@test.com",
        "password": "12345",
        "phone": "12345",
        "userStatus": 0
    }

    with allure.step("POST /user — создать пользователя"):
        api.post("user", "", body)

    with allure.step("GET /user/{username} — получить пользователя"):
        response = api.get("user", body["username"])

    with allure.step("Валидация ответа через Pydantic"):
        user = UserModel(**response)

    assert user.username == body["username"]