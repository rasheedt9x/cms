import requests
import json
import base64

# URL of the Spring Boot API
url = "http://localhost:8080/api/v1/book"

login_url = 'http://localhost:8080/auth/login'

logout_url = 'http://localhost:8080/auth/logout'

# Sample BookDto data to send in the request
book_data = {
    "title": "The Great Gatsby",
    "isbn": "9780743273565",
    "availableCopies": 5,
    "totalCopies": 10,
    "imageBase64": None  # This will hold the base64-encoded image
}

# Encode the image file to base64
with open("book.jpg", "rb") as image_file:
    encoded_image = base64.b64encode(image_file.read()).decode('utf-8')
    book_data["imageBase64"] = encoded_image  # Add the encoded image to book_data

# Librarian login
login_data = {"username": "EM60000003", "password": "1234"}

# Student Login
# login_data = {"username": "ST70000001", "password": "SGDC@123"}

# Log in and get JWT token
response = requests.post(login_url, json=login_data)
print(response.json())
jwt_token = response.json().get('token')

# Set headers for the authorized request
headers = {
    "Authorization": f"Bearer {jwt_token}",
    "Content-Type": "application/json"
}

# Send a POST request to register a new book with image
#response = requests.post(url + "/new", data=json.dumps(book_data), headers=headers)

response = requests.get(url + "/all",headers=headers)
# Check the responsbbbe
if response.status_code == 200:
    print("Book registered successfully!")
    print(len(response.json()))
    print("Response:", response.json()[2]["imageBase64"])

    with open("img.jpg","wb") as f:
        f.write(base64.b64decode(response.json()[2]["imageBase64"]))
    
else:
    print("Failed to register book.")
    print("Status Code:", response.status_code)
    print("Response:", response.text)

#print(response.content)
