import requests
login_url = 'http://localhost:8080/auth/login'
main_url = "http://localhost:8080/api/v1/"

jwt_token = None

def admin():
    login_data = {"username": "EM60000001", "password": "1234"}
    response = requests.post(login_url, json=login_data)
    if response.status_code == 200:
        global jwt_token
        # Extract the JWT token from the response
        jwt_token = response.json().get('token')  # Adjust this based on your actual response structure
        print(jwt_token)

admin()