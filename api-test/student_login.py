import requests
login_url = 'http://localhost:8080/auth/login'

logout_url = 'http://localhost:8080/auth/logout'
main_url = "http://localhost:8080/api/v1/"

jwt_token = None

def stud(username):
    login_data = {"username": username, "password": "SGDC@123"}
    response = requests.post(login_url, json=login_data)
    print(response.json())
    jwt_token = response.json().get('token')
    
    headers = {
        "Authorization": f"Bearer {jwt_token}",
        "Content-Type": "application/json"
    }

    
    #All applications
    r1 = requests.get(main_url + "students/ping", headers=headers)
    print(r1.content)

    
    r1 = requests.get(main_url + "students/get/self", headers=headers)
    print(r1.content)

    # r2 = requests.post(logout_url,headers=headers)
    # print(r2.content)
    
stud("ST70000001")
stud("ST70000002")
stud("ST70000003")
