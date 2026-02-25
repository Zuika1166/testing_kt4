import allure
import pytest
from api_testing.async_request import AsyncRequest
from models.user_model import UserModel
import pytest
BASE_URL = "https://petstore.swagger.io/v2"
@pytest.mark.anyio("asyncio")
@allure.feature("USER")
@allure.story("ASYNC POST+GET")
@allure.title("Async: создание и получение пользователя")
async def test_async_create_get_user():
    api = AsyncRequest(BASE_URL)

    body = {
        "id": 4001,
        "username": "async_user",
        "firstName": "Ivan",
        "lastName": "Ivanov",
        "email": "ivan@test.com",
        "password": "12345",
        "phone": "12345",
        "userStatus": 0
    }

    with allure.step("Create user"):
        r1 = await api.request("POST", "/user", json=body)
        assert r1.status_code == 200

    with allure.step("Get user"):
        r2 = await api.request("GET", f"/user/{body['username']}")
        assert r2.status_code == 200
        user = UserModel(**r2.json())
        assert user.firstName == "Ivan"

    await api.close()