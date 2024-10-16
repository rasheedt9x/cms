import requests
main_url = "http://localhost:8080/api/v1/"
# jwt_token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1fYWRtaW4iLCJpYXQiOjE3MjkwNTIxOTgsImV4cCI6MTczMTY0NDE5OH0.Iwj8yNN20X7nhRFN5GK30JJnAVFnhcycA6qLV29ubiU'


# headers = {
#         "token" :f"Bearer {jwt_token}",
#         "Content-Type": "application/json"
# }

# r2 = None
# for i in range(200):
#     email = f"he{i}@gmail.com"
#     username = f"heMan{i}"
#     r2 = requests.post(main_url + "applications/new",  json={
#     "name": "joe",
#     "email": email,
#     "nationality": "india",
#     "gender": "male",
#     "address": "mpl",
#     "dateOfBirth": "01/06/2020",
#     "username" : username
#     })
#     print("r2", r2.status_code, r2.content)
    
    
login_url = 'http://localhost:8080/auth/login'
login_data = {
    'username': 'EM60000001',
    'password': '1234'
}

# Send POST request to the login endpoint
response = requests.post(login_url, json=login_data)

# Check if the login was successful
if response.status_code == 200:
    print(response.json())
    # Extract the JWT token from the response
    jwt_token = response.json().get('token')  # Adjust this based on your actual response structure

    # Set the Authorization header for subsequent requests
    headers = {
        "Authorization": f"Bearer {jwt_token}",
        "Content-Type": "application/json"
    }
    
    #All applications
    # r1 = requests.get(main_url + "applications/all", headers=headers)
    # print(r1.json())
    
    #Get Application by Id
    
    r1 = requests.get(main_url + "applications/SGDCAP1001", headers=headers)
    print(r1.json())
    
    # #All applications by Page
    # r1 = requests.get(main_url + "applications/all/1", headers=headers)
    # print(r1.json())
    
    

    # #Approve applications
    # r1 = requests.post(main_url + "applications/SGDCAP1002/status", headers=headers, json={
    #     "status": "APPROVED"
    # })
    # print("r1", r1.content)
    
    # #Get applications by Status
    # r1 = requests.get(main_url + "applications/all/status", headers=headers, json={
    #     "status": "APPROVED"
    # })
    # print("r1", r1.json())
    
    
    
    


# login = requests.get(main_url + "applications/all", headers=headers)
# print(login.content)