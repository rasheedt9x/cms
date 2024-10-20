import requests
login_url = 'http://localhost:8080/auth/login'

logout_url = 'http://localhost:8080/auth/logout'
main_url = "http://localhost:8080/api/v1/"

jwt_token = None

def admin():
    
    login_data = {"username": "EM60000001", "password": "1234"}
#    login_data = {"username": "ST70000001", "password": "SGDC@123"}
    response = requests.post(login_url, json=login_data)
    print(response.json())
    jwt_token = response.json().get('token')
    
    headers = {
        "Authorization": f"Bearer {jwt_token}",
        "Content-Type": "application/json"
    }

    
    # #All applications
    # r1 = requests.get(main_url + "students/ping", headers=headers)
    # print(r1.content)

    
    r1 = requests.get(main_url + "employees/get/self", headers=headers)
    print(r1.content)

    print("JWT Token: ", jwt_token)

    # r2 = requests.post(logout_url,headers=headers)
    # print(r2.content)
    
    # r3 = requests.get(main_url + "applications/all", headers=headers)
    # print(r3.content)
    
admin()
