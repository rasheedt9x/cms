import requests, base64
login_url = 'http://localhost:8080/auth/login'

logout_url = 'http://localhost:8080/auth/logout'
main_url = "http://localhost:8080/api/v1/"

jwt_token = None
imageBase64 = None
def image():
## emp login    
#    login_data = {"username": "EM60000004", "password": "SGDC@123"}

   
    ## student login
    login_data = {"username": "ST70000004", "password": "SGDC@123"}
    response = requests.post(login_url, json=login_data)
    print(response.json())
    jwt_token = response.json().get('token')
    
    headers = {
        "Authorization": f"Bearer {jwt_token}",
        "Content-Type": "application/json"
    }

    IMAGE_NAME = "book.jpg"
    
    with open(IMAGE_NAME, "rb") as image_file:
        encoded_image = base64.b64encode(image_file.read()).decode('utf-8')
        imageBase64 = encoded_image  # Add the encoded image to book_data

    dto = {
        "imageBase64": imageBase64
    }

    ## Updating employee Image
    # r1 = requests.post(main_url + "employees/get/updateImage",headers=headers, json=dto)
    # print(r1.content)


    ## Fetching employee image
    # r1 = requests.get(main_url + "employees/get/self/image",headers=headers)
    # image = base64.b64decode(r1.json()["imageBase64"])
    # with open("emp.jpg", "wb") as f:
    #     f.write(image)




    # Updating students Image
    r1 = requests.post(main_url + "students/get/updateImage",headers=headers, json=dto)
    print(r1.content)


    # Fetching students image
    r1 = requests.get(main_url + "students/get/self/image",headers=headers)
    image = base64.b64decode(r1.json()["imageBase64"])
    with open("stud.jpg", "wb") as f:
        f.write(image)

    
image()
