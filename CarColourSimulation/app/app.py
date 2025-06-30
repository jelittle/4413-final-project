from fastapi import FastAPI, File, UploadFile, HTTPException
from fastapi.responses import JSONResponse
import logging
from typing import Dict, Any
from colour_simulation_service import ColourSimulation

#logging is always good
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

app = FastAPI(
    title="Car Colour Simulation API",
    description="REST API for car colour simulation using YOLOv11 and colour transformation",
    version="1.0.0"
)

#setup car colour simulation service
try:
    
    colour_simulation_service = ColourSimulation()
    logger.info("Colour Simulation Service initialized successfully")
except Exception as e:
    logger.error(f"Failed to initialize Colour Simulation Service: {e}")
    colour_simulation_service = None

@app.post("/simulate_colour")
async def simulate_colour(file: UploadFile ,colour:list[float]):
    """
    Simulate a specific colour on the provided image.

    """
    if colour_simulation_service is None:
        raise HTTPException(status_code=500, detail="Colour Simulation Service not available")
    
    # Validate file type
    if not file.content_type or not file.content_type.startswith('image/'):
        raise HTTPException(status_code=400, detail="File must be an image")
    
    try:
        image_bytes = await file.read()
        modified_image_bytes = colour_simulation_service.simulate_colour_on_body(image_bytes, colour)
        return JSONResponse(content={"image": modified_image_bytes})
    except Exception as e:
        logger.error(f"Error simulating colour: {e}")
        raise HTTPException(status_code=500, detail="Error simulating colour")