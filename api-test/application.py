import requests
main_url = "http://localhost:8080/api/v1/"
# headers = {
#         "Content-Type": "application/json"
# }

r2 = requests.post(main_url + "applications/new",  json={
    "name": "joe",
    "email": "he1@gmail.com",
    "nationality": "india",
    "gender": "male",
    "address": "mpl",
    "dateOfBirth": "01/06/20"
})
print("r2", r2.status_code, r2.content)