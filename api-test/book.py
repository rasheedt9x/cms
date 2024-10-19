import requests
import json

# URL of the Spring Boot API
url = "http://localhost:8080/api/v1/book"

login_url = 'http://localhost:8080/auth/login'

logout_url = 'http://localhost:8080/auth/logout'
# Sample BookDto data to send in the request
book_data = {
    "title": "The Great Gatsby",
    "isbn": "9780743273565",
    "availableCopies": 5,
    "totalCopies": 10
}

#Librarian login
#login_data = {"username": "EM60000003","password": "1234"}

#Student Login
login_data = {"username": "ST70000001", "password": "SGDC@123"}
response = requests.post(login_url, json=login_data)
print(response.json())
jwt_token = response.json().get('token')
    
headers = {
        "Authorization": f"Bearer {jwt_token}",
        "Content-Type": "application/json"
}


# Send a POST request
# Accesible only by Librarian or admin
response = requests.post(url + "/new", data=json.dumps(book_data), headers=headers)


#accesible by authenticated people
#response = requests.get(url + "/all", headers=headers)



# Check the response
if response.status_code == 201:
    print("Book registered successfully!")
    print("Response:", response.json())
else:
    print("Failed to register book.")
    print("Status Code:", response.status_code)
    print("Response:", response.text)

print(response.content)
