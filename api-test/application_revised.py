import requests

main_url = "http://localhost:8080/api/v1/"

# Login to get the JWT token
login_url = 'http://localhost:8080/auth/login'
login_data = {
    'username': 'EM60000001',
    'password': '1234'
}

# Send POST request to the login endpoint
response = requests.post(login_url, json=login_data)

# Check if the login was successful
if response.status_code == 200:
    print("Login successful")
    jwt_token = response.json().get('token')  # Adjust this based on your actual response structure

    # Set the Authorization header for subsequent requests
    headers = {
        "Authorization": f"Bearer {jwt_token}",
        "Content-Type": "application/json"
    }

    # Function to create a new application
    def create_application(app_data):
        r = requests.post(main_url + "applications/new", json=app_data)
        print("Application creation response:", r.status_code, r.content)

    # Function to approve an application
    def approve_application(application_id):
        r = requests.post(main_url + f"applications/{application_id}/status", headers=headers, json={
            "status": "APPROVED"
        })
        print(f"Approval response for {application_id}:", r.content)

    # List of applications to create
    applications = [
{
    "name": "Rohini Mehta",
    "email": "rohini.mehta123@gmail.com",
    "nationality": "Indian",
    "gender": "Female",
    "address": "23 Juhu Beach Road, Mumbai",
    "dateOfBirth": "09/11/2001",
    "username": "rohinimehta01",
    "primaryPhone": "+918877665544",
    "secondaryPhone": "+918877665543",
    "guardianName": "Suresh Mehta",
    "guardianPhone": "08877665544",
    "sscSchool": "Mumbai Central School",
    "sscYearOfPassing": 2018,
    "sscMarks": 90,
    "intermediateCollege": "St. Xavier's College",
    "intermediateYearOfPassing": 2020,
    "intermediateMarks": 83,
    "degreeCourse": "BIOTECH",
    "secondLanguage": "Marathi",
    "caste": "General",
    "religion": "Hindu",
    "studentAadhaar": "445566778899",
    "motherAadhaar": "445566778900"
  },        {
            "name": "John Doe",
            "email": "john.doe@example.com",
            "nationality": "American",
            "gender": "Male",
            "address": "123 Main St, Springfield",
            "dateOfBirth": "01/01/2000",
            "username": "johndoe01",
            "primaryPhone": "+1234567890",
            "secondaryPhone": "+1234567891",
            "guardianName": "Jane Doe",
            "guardianPhone": "+1234567892",
            "sscSchool": "Springfield High School",
            "sscYearOfPassing": 2018,
            "sscMarks": 85,
            "intermediateCollege": "Springfield College",
            "intermediateYearOfPassing": 2020,
            "intermediateMarks": 88,
            "degreeCourse": "BCA",
            "secondLanguage": "Hindi",
            "caste": "General",
            "religion": "Christian",
            "studentAadhaar": "123456789012",
            "motherAadhaar": "123456789013"
        },
        {
            "name": "Alice Smith",
            "email": "alice.smith@example.com",
            "nationality": "British",
            "gender": "Female",
            "address": "456 Elm St, London",
            "dateOfBirth": "02/02/2001",
            "username": "alicesmith01",
            "primaryPhone": "+1987654321",
            "secondaryPhone": "+1987654322",
            "guardianName": "Bob Smith",
            "guardianPhone": "+1987654323",
            "sscSchool": "London Academy",
            "sscYearOfPassing": 2019,
            "sscMarks": 92,
            "intermediateCollege": "London College",
            "intermediateYearOfPassing": 2021,
            "intermediateMarks": 90,
            "degreeCourse": "BBA",
            "secondLanguage": "Hindi",
            "caste": "General",
            "religion": "Atheist",
            "studentAadhaar": "987654321098",
            "motherAadhaar": "987654321099"
        }
    ]

    # Create applications and approve them
    for app in applications:
        create_application(app)

    # Approve applications (assuming you know the application IDs)
    application_ids = ["SGDCAP1001", "SGDCAP1002","SGDCAP1003"]  # Replace with actual IDs after creation
    for app_id in application_ids:
        approve_application(app_id)

else:
    print("Login failed:", response.status_code, response.content)

