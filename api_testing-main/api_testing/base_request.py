import requests
import pprint


class BaseRequest:
    def __init__(self, base_url):
        self.base_url = base_url

    def _request(self, url, request_type, data=None, expected_error=False):

        if request_type == 'GET':
            response = requests.get(url)

        elif request_type == 'POST':
            response = requests.post(url, json=data)
        
        elif request_type == 'PUT':
            response = requests.put(url, json=data)

        else:
            response = requests.delete(url)

        pprint.pprint(f'{request_type} example')
        pprint.pprint(response.url)
        pprint.pprint(response.status_code)
        pprint.pprint(response.reason)
        pprint.pprint(response.text)
        try:
            pprint.pprint(response.json())
        except:
            pprint.pprint("No JSON in response")
        pprint.pprint('**********')

        return response
    def _safe_json(self, response):
        try:
            return response.json()
        except:
            return {}
    def get(self, endpoint, endpoint_id, expected_error=False):
        url = f'{self.base_url}/{endpoint}/{endpoint_id}'
        response = self._request(url, 'GET', expected_error=expected_error)
        return response.json()

    def post(self, endpoint, endpoint_id, body):
        url = f'{self.base_url}/{endpoint}/{endpoint_id}'
        response = self._request(url, 'POST', data=body)
        return self._safe_json(response).get('message')

    def delete(self, endpoint, endpoint_id):
        url = f'{self.base_url}/{endpoint}/{endpoint_id}'
        response = self._request(url, 'DELETE')
        return self._safe_json(response).get('message')
    def put(self, endpoint, endpoint_id, body):
        url = f'{self.base_url}/{endpoint}/{endpoint_id}'
        response = self._request(url, 'PUT', data=body)
        return response


BASE_URL_PETSTORE = 'https://petstore.swagger.io/v2'
base_request = BaseRequest(BASE_URL_PETSTORE)

# =========================
# 🐶 PET (пример из задания)
# =========================

pet_info = base_request.get('pet', 1)

data = {
    "id": 1,
    "name": "Barsic",
    "photoUrls": ["string"],
    "status": "available"
}

base_request.post('pet', '', data)

pet_info = base_request.get('pet', 1)

assert data['name'] == pet_info['name']

request_id = base_request.delete('pet', 1)
base_request.get('pet', request_id, expected_error=True)

# =========================
# 👤 USER — 4 запроса
# =========================

user_body = {
    "id": 1001,
    "username": "test_user_1",
    "firstName": "Ivan",
    "lastName": "Ivanov",
    "email": "ivan@test.com",
    "password": "12345",
    "phone": "12345",
    "userStatus": 0
}

# 1. Create user
base_request.post('user', '', user_body)

# 2. Get user
user_info = base_request.get('user', 'test_user_1')
assert user_info['username'] == 'test_user_1'

# 3. Update user
user_body["firstName"] = "Petr"
base_request.put('user', 'test_user_1', user_body)
user_info = base_request.get('user', 'test_user_1')
assert user_info["firstName"] == "Petr"

# 4. Delete user
base_request.delete('user', 'test_user_1')
base_request.get('user', 'test_user_1', expected_error=True)


# =========================
# 🏪 STORE — 4 запроса
# =========================

order_body = {
    "id": 5001,
    "petId": 1,
    "quantity": 1,
    "shipDate": "2026-02-25T10:00:00.000Z",
    "status": "placed",
    "complete": True
}

# 1. Create order
base_request.post('store/order', '', order_body)

# 2. Get order
order_info = base_request.get('store/order', 5001)
assert order_info["id"] == 5001

# 3. Delete order
base_request.delete('store/order', 5001)
base_request.get('store/order', 5001, expected_error=True)

# 4. Get inventory
inventory = base_request.get('store/inventory', '')
pprint.pprint(inventory)