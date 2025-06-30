from fastapi.testclient import TestClient

from fastapi.testclient import TestClient
from .app import app
import pytest
import time

client = TestClient(app)
#add tests: for grpc requirement
def test_returns_image():
    """Test that the endpoint returns an image"""
    response = client.get("/generate-car")
    assert response.status_code == 200
    assert response.headers["content-type"].startswith("image/")

def test_image_is_different():
    """Test that multiple calls return different images"""
    response1 = client.get("/generate-car")
    response2 = client.get("/generate-car")
    assert response1.status_code == 200
    assert response2.status_code == 200
    assert response1.content != response2.content







