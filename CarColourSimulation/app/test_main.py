from fastapi.testclient import TestClient

from fastapi.testclient import TestClient
from .app import app

import time

client = TestClient(app)
#add tests: for grpc requirement
def test_returns_image():
    """Test that the endpoint returns an image"""
    test_image_path = "CarColourSimulation/app/test_data/car2_1.jpg"
    
    with open(test_image_path, "rb") as f:
        response = client.post(
            "/simulate_colour",
            files={"file": ("car2_1.jpg", f, "image/jpeg")},
            data={"colour": "[108, 148, 252]"}  # Red color as JSON string
        )
    
    assert response.status_code == 200
    assert "image" in response.json()


def test_image_is_different():
    """Test that multiple calls return different images"""
    pass







