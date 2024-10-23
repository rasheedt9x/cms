import requests
login_url = 'http://localhost:8080/auth/login'

logout_url = 'http://localhost:8080/auth/logout'
main_url = "http://localhost:8080/api/v1/"

jwt_token = None

def admin():
## emp login    
#    login_data = {"username": "EM60000001", "password": "12345"}

   
    ## student login
    login_data = {"username": "ST70000001", "password": "SGDC@123"}
    response = requests.post(login_url, json=login_data)
    print(response.json())
    jwt_token = response.json().get('token')
    
    headers = {
        "Authorization": f"Bearer {jwt_token}",
        "Content-Type": "application/json"
    }

    

    # r1 = requests.get(main_url + "students/ping", headers=headers)
    # print(r1.content)

    ## Get emp details (self with token not by Id and others)
    # r1 = requests.get(main_url + "employees/get/self", headers=headers)
    # print(r1.content)

    ## same as get students details but works with only self
    # r1 = requests.get(main_url + "students/get/self", headers=headers)
    # print(r1.content)

    # print("JWT Token: ", jwt_token)

    # r2 = requests.post(logout_url,headers=headers)
    # print(r2.content)
    
    # r3 = requests.get(main_url + "applications/all", headers=headers)
    # print(r3.content)






    ## Edit employee details (only mail and password)
    
    # ## pass change dto
    # dto = {
    #     "oldPassword": "12345", 
    #     "newPassword": "12345",
    #     "confirmPassword": "12345"
    # }
    
    
    # ## email change dto
    # dto = {
    #     "oldPassword": "12345", 
    #     "email": "hellow1@gmail.com"
    # }
    
    # # update profile (only works on self login)
    # r1 = requests.post(main_url + "employees/get/updateProfile", headers=headers,json=dto)
    # print(r1.content, r1.status_code)


    
    # r1 = requests.get(main_url + "employees/get/self", headers=headers)
    # print(r1.content)








    

    # Edit student details (only mail and password)
    
     ## pass change dto 
    # dto = {
    #      "oldPassword": "SGDC@123", 
    #      "newPassword": "SGDC@1234",
    #  }
    
    
     ## email change dto
    # dto = {
    #      "oldPassword": "SGDC@1234", 
    #      "email": "hellow1@gmail.com"
    #  }
    
    # # previous profile deets
    # r1 = requests.get(main_url + "students/get/self", headers=headers)
    # print(r1.content, r1.status_code)


    #  # update profile (works only on self login)
    # r1 = requests.post(main_url + "students/get/updateProfile", headers=headers,json=dto)
    # print(r1.content, r1.status_code)


    # # updated profile deets
    # r1 = requests.get(main_url + "students/get/self", headers=headers)
    # print(r1.content, r1.status_code)





    
admin()
