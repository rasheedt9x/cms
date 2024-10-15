import requests

# Set the login URL
login_url = 'http://localhost:8090/auth/login'  # Change to your Spring Boot URL
main_url = "http://localhost:8090/api/v1/"
# Define your login credentials
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

    # Fetch all applications
    r1 = requests.get(main_url + "applications/all", headers=headers)
    print("r1", r1.content)

    # # Create a new group
    # grp_req = requests.post(main_url + "groups/new", headers=headers, json={
    #     "name": "bcom"
    # })
    # print("grp: ", grp_req.content)

    # Create a new application
    r2 = requests.post(main_url + "applications/new", headers=headers, json={
        "name": "joe",
        "email": "he",
        "nationality": "india",
        "gender": "male",
        "address": "mpl",
        "dateOfBirth": "01/06/20"
    })
    print("r2", r2.status_code, r2.content)

    # # Create a new student
    # r3 = requests.post(main_url + "students/new", headers=headers, json={
    #     "name": "hello",
    #     "username": "hello12345",
    #     "password": "pass",
    #     "group": "bcom",
    #     "yearOfStudy": 1,
    #     "email": "example.com"
    # })
    # print("r3", r3.content)

    # # Create a new department
    # r4 = requests.post(main_url + "dept/new", headers=headers, json={
    #     "name": "Computer Science"
    # })
    # print("r4", r4.status_code, r4.content)

    # # Create a new employee
    # r5 = requests.post(main_url + "employees/new", headers=headers, json={
    #     "name": "John Doe",
    #     "username": "johndoe",
    #     "email": "johndoe@example.com",
    #     "departmentName": "Computer Science",
    # })
    # print("r5", r5.status_code, r5.content)

    # # Log in as the new student
    # login = requests.post(login_url, json={"username": "ST70000001", "password": "pass"})
    # if login.status_code == 200:
    #     login_token = login.json().get('token')  # Adjust this based on your actual response structure
    #     login_headers = {
    #         "Authorization": f"Bearer {login_token}",
    #         "Content-Type": "application/json"
    #     }
    #     r6 = requests.get(main_url + "students/get/ST70000001", headers=login_headers)
    #     print(r6.content)
    # else:
    #     print("Student login failed")
    #     print(login.json())  # Print error details
else:
    print("Login failed")
    print(response.json())  # Print error details

