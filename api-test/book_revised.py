import requests
import json

# Constants for API URLs
BASE_URL = "http://localhost:8080"
BOOK_URL = f"{BASE_URL}/api/v1/book"
LOGIN_URL = f"{BASE_URL}/auth/login"
LOGOUT_URL = f"{BASE_URL}/auth/logout"

# Sample BookDto data to send in the request
BOOKS_DATA = [
    {
        "title": "The Great Gatsby",
        "isbn": "9780743273565",
        "availableCopies": 5,
        "totalCopies": 10
    },
    {
        "title": "To Kill a Mockingbird",
        "isbn": "9780061120084",
        "availableCopies": 3,
        "totalCopies": 7
    },
    {
        "title": "1984",
        "isbn": "9780451524935",
        "availableCopies": 4,
        "totalCopies": 10
    },
    {
        "title": "Pride and Prejudice",
        "isbn": "9781503290563",
        "availableCopies": 2,
        "totalCopies": 5
    },
    {
        "title": "The Catcher in the Rye",
        "isbn": "9780316769488",
        "availableCopies": 6,
        "totalCopies": 8
    }
]

# Function to log in and retrieve JWT token
def login(username, password):
    login_data = {"username": username, "password": password}
    response = requests.post(LOGIN_URL, json=login_data)
    
    if response.status_code == 200:
        print("Login successful!")
        return response.json().get('token')
    else:
        print("Login failed.")
        print("Status Code:", response.status_code)
        print("Response:", response.text)
        return None

# Function to register a new book
def register_book(jwt_token, book_data):
    headers = {
        "Authorization": f"Bearer {jwt_token}",
        "Content-Type": "application/json"
    }
    
    response = requests.post(f"{BOOK_URL}/new", json=book_data, headers=headers)
    
    if response.status_code == 201:
        print(f"Book '{book_data['title']}' registered successfully!")
        print("Response:", response.json())
    else:
        print(f"Failed to register book '{book_data['title']}'.")
        print("Status Code:", response.status_code)
        print("Response:", response.text)

# Main function to execute the script
def main():
    
    # Choose the appropriate credentials for login
    # Student login
    # username = "ST70000001"
    # password = "SGDC@123"

    #Library login
    username = "EM60000003"
    password = "1234"

    
    
    # Log in and get the JWT token
    jwt_token = login(username, password)
    
    if jwt_token:
        # Register each book in the BOOKS_DATA list
        for book in BOOKS_DATA:
            register_book(jwt_token, book)

# Entry point of the script
if __name__ == "__main__":
    main()

