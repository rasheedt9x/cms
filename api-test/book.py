import requests
import json

# URL of the Spring Boot API
url = "http://localhost:8080/api/v1/book/new"

# Sample BookDto data to send in the request
book_data = {
    "title": "The Great Gatsby",
    "isbn": "9780743273565",
    "availableCopies": 5,
    "totalCopies": 10
}

# Convert the book data to JSON
headers = {'Content-Type': 'application/json'}

# Send a POST request
response = requests.post(url, data=json.dumps(book_data), headers=headers)

# Check the response
if response.status_code == 201:
    print("Book registered successfully!")
    print("Response:", response.json())
else:
    print("Failed to register book.")
    print("Status Code:", response.status_code)
    print("Response:", response.text)
