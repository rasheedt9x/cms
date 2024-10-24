import requests
login_url = 'http://localhost:8080/auth/login'
logout_url = 'http://localhost:8080/auth/logout'
main_url = "http://localhost:8080/api/v1/"
libr_jwt_token = None

def library():
    stud_login_data = {"username": "ST70000001", "password": "SGDC@123"}
    libr_login_data = {"username": "EM60000003", "password": "1234"}
    
    response = requests.post(login_url, json=libr_login_data)
    print(response.json())
    libr_jwt_token = response.json().get('token')

    
    response = requests.post(login_url, json=stud_login_data)
    print(response.json())
    stud_jwt_token = response.json().get('token')

    stud_headers = {
        "Authorization": f"Bearer {stud_jwt_token}",
        "Content-Type": "application/json"
    }
    
    headers = {
        "Authorization": f"Bearer {libr_jwt_token}",
        "Content-Type": "application/json"
    }

#    print(headers,stud_headers)

    dto = {
        "bookId" : 1,
    }

    
    # REQUEST A BOOK BY ITS ID
    r1 = requests.post(main_url + "bookloan/request",headers=stud_headers,json=dto)
    print(r1.content)

    ### GET ALL BOOKLOANS (LIBRARIAN ONLY)
    r1 = requests.get(main_url + "bookloan/all",headers=headers)
    print(r1.content)
    
    ### APPROVE A BOOK LOAN (LIBRARIAN) ONLY  
    # r1 = requests.post(main_url + "bookloan/approve/1",headers=headers)
    # print(r1.content)

    ### REQUEST BOOK RETURN BY AN USER
    # r1 = requests.post(main_url + "bookloan/return/1",headers=stud_headers)
    # print(r1.content)

    ### APPROVE THE BOOK RETURN (LIBRARIAN ONLY)
    # r1 = requests.post(main_url + "bookloan/approveReturn/1",headers=headers)
    # print(r1.content)


    ### GET ALL BOOK LOANS OF THE CURRENT USER
    # r1 = requests.get(main_url + "bookloan/self",headers=stud_headers)
    # print(r1.content)

    
    # r1 = requests.post(main_url + "bookloan/renew/1",headers=stud_headers)
    # print(r1.content)
    
    
library()
