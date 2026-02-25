import allure
import httpx


class AsyncRequest:
    def __init__(self, base_url: str):
        self.client = httpx.AsyncClient(base_url=base_url)

    @allure.step("HTTP {method} {endpoint}")
    async def request(self, method: str, endpoint: str, json: dict | None = None):
        resp = await self.client.request(method, endpoint, json=json)
        return resp

    async def close(self):
        await self.client.aclose()