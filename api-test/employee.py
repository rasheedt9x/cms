import requests

# Set the login URL
login_url = 'http://localhost:8080/auth/login'  # Change to your Spring Boot URL
main_url = "http://localhost:8080/api/v1/"

# Define your login credentials
login_data = {
    'username': 'EM60000002',
    'password': '1234'
}

# Send POST request to the login endpoint
response = requests.post(login_url, json=login_data)

# Check if the login was successful
if response.status_code == 200:
    print("Login successful!")
    # Extract the JWT token from the response
    jwt_token = response.json().get('token')  # Adjust this based on your actual response structure

    # Set the Authorization header for subsequent requests
    headers = {
        "Authorization": f"Bearer {jwt_token}",
        "Content-Type": "application/json"
    }

    # List of employees to create with valid departments
    employees = [
        {
            "name": "John Doe",
            "username": "johndoe",
            "email": "johndoe@example.com",
            "departmentName": "Computer Science",  # Valid department
            "nationality": "American",
            "gender": "Male",
            "address": "123 Main St, Anytown, USA",
            "dateOfBirth": "01/01/1981",
            "dateOfJoining": "01/01/2020",
            "dateOfLeaving": None,
            "employed": True
        },
        {
            "name": "Jane Smith",
            "username": "janesmith",
            "email": "janesmith@example.com",
            "departmentName": "Humanities",  # Valid department
            "nationality": "British",
            "gender": "Female",
            "address": "456 Elm St, Othertown, UK",
            "dateOfBirth": "15/05/1985",
            "dateOfJoining": "15/05/2019",
            "dateOfLeaving": None,
            "employed": True
        },
        {
            "name": "Alice Johnson",
            "username": "alicejohnson",
            "email": "alicejohnson@example.com",
            "departmentName": "MANAGEMENT",  # Valid department
            "nationality": "Canadian",
            "gender": "Female",
            "address": "789 Oak St, Sometown, CA",
            "dateOfBirth": "20/03/1992",
            "dateOfJoining": "20/03/2021",
            "dateOfLeaving": None,
            "employed": True
        },
        {
            "name": "Bob Brown",
            "username": "bobbrown",
            "email": "bobbrown@example.com",
            "departmentName": "Mathematics",  # Valid department
            "nationality": "Australian",
            "gender": "Male",
            "address": "321 Pine St, Yourtown, AU",
            "dateOfBirth": "30/07/1988",
            "dateOfJoining": "30/07/2022",
            "dateOfLeaving": None,
            "employed": True
        }
    ]

    # Create each employee
    for employee in employees:
        r = requests.post(main_url + "employees/new", headers=headers, json=employee)
        print(f"Creating employee {employee['name']}: Status Code: {r.status_code}, Response: {r.content}")


    

else:
    print("Login failed")
    print(response.json())  # Print error details

