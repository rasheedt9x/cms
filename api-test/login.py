import requests
login_url = 'http://localhost:8080/auth/login'
main_url = "http://localhost:8080/api/v1/"

jwt_token = None

def admin():
    login_data = {"username": "EM60000001", "password": "1234"}
    response = requests.post(login_url, json=login_data)
    print(response.json())

admin()