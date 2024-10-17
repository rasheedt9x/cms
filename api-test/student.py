import requests

# Log in as the new student
login_url = 'http://localhost:8080/auth/login'

logout_url = 'http://localhost:8080/auth/logout'
main_url = "http://localhost:8080/api/v1/"
login = requests.post(login_url, json={"username": "ST70000001", "password": "SGDC@123"})
if login.status_code == 200:
    login_token = login.json().get('token')  # Adjust this based on your actual response structure
    login_headers = {
        "Authorization": f"Bearer {login_token}",
        "Content-Type": "application/json"
    }
    r6 = requests.get(main_url + "students/get/ST70000001", headers=login_headers)
    print(r6.content)
else:
    print("Student login failed")
    print(login.json())  # Print error details