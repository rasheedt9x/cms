import requests

login_url = 'http://localhost:8080/auth/login'
logout_url = 'http://localhost:8080/auth/logout'
main_url = "http://localhost:8080/api/v1/"

# List of student credentials
students = [
    {"username": "ST70000001", "password": "SGDC@123"},
    {"username": "ST70000002", "password": "SGDC@123"},
    {"username": "ST70000003", "password": "SGDC@123"},
    # Add more students as needed
]

def student_login(student):
    login_data = student
    response = requests.post(login_url, json=login_data)
    
    if response.status_code == 200:
        print(f"Login successful for {student['username']}")
        jwt_token = response.json().get('token')

        headers = {
            "Authorization": f"Bearer {jwt_token}",
            "Content-Type": "application/json"
        }

        # Ping the student endpoint
        r1 = requests.get(main_url + "students/ping", headers=headers)
        print(f"Ping response for {student['username']}: {r1.content}")

        # Get student details
        r1 = requests.get(main_url + "students/get/self", headers=headers)
        print(f"Student details for {student['username']}: {r1.content}")

        # Logout
        r2 = requests.post(logout_url, headers=headers)
        print(f"Logout response for {student['username']}: {r2.content}")
    else:
        print(f"Login failed for {student['username']}: {response.status_code}, {response.content}")

# Iterate through the list of students and log them in
for student in students:
    student_login(student)

