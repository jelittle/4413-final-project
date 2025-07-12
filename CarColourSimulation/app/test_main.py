from fastapi.testclient import TestClient

from fastapi.testclient import TestClient
from main import app
import matplotlib as plt
import time

client = TestClient(app)
#add tests: for grpc requirement
def test_returns_image():
    test_image_path = "./app/test_data/car2_1.jpg"
    with open(test_image_path, "rb") as f:
        response = client.post(
            "/simulate_colour?r=202&g=45&b=52",
            files={"file": ("car2_1.jpg", f, "image/jpeg")}
        )
    
    #if its an error print the error
    if response.status_code != 200:
        print(f"Error: {response.status_code} - {response.text}")
    assert response.status_code == 200
    assert response.headers["content-type"].startswith("image/")
    assert len(response.content) > 0
    # Save the returned image
    with open("output_image.jpg", "wb") as f:
        f.write(response.content)
    print(f"Image saved as output_image.jpg")

 


# def test_image_is_different():
#     """Test that multiple calls return different images"""
#     pass







