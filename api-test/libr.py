import requests
login_url = 'http://localhost:8080/auth/login'
logout_url = 'http://localhost:8080/auth/logout'
main_url = "http://localhost:8080/api/v1/"
jwt_token = None

def library():
    login_data = {"username": "EM60000003", "password": "1234"}
    response = requests.post(login_url, json=login_data)
    print(response.json())
    jwt_token = response.json().get('token')
    
    headers = {
        "Authorization": f"Bearer {jwt_token}",
        "Content-Type": "application/json"
    }

    dto = {
        "bookId" : 1,
    }

    r1 = requests.post(main_url + "bookloan/request",headers=headers,json=dto)
    print(r1.content)

    r1 = requests.get(main_url + "bookloan/all",headers=headers)
    print(r1.content)

    r1 = requests.post(main_url + "bookloan/approve",headers=headers)
    print(r1.content)
    
library()
