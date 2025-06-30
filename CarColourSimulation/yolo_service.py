from yolo_interface import YoloInterface
from typing import Tuple, List
import numpy as np
from ultralytics import YOLO




class YoloService(YoloInterface):
    """Implementation of the YOLO image segmentation interface."""

    def __init__(self, model_path: str = 'yolo_model.pt'):
        """
        Initialize YOLO service.

        Args:
            model_path: Path to the YOLO model file
        """
        self.model_path = model_path
        # Load the YOLO model here (e.g., using ultralytics or another library)
        self.model = YOLO(model_path)

    def get_segmentation_masks(self, image) -> Tuple[np.ndarray, List[str]]:
        """
        Get segmentation masks and corresponding labels.

        Args:
            image_bytes: Raw image bytes

        Returns:
            Tuple of (combined_mask_array, list_of_labels)
        """
        # Load image from bytes
       
        
        # Run YOLO inference
        results = self.model(image)
        
        # Extract masks and labels
        masks = []
        labels = []
        
        for result in results:
            mask_set=[]
            label_set=[]
            if result.masks is not None:
                for mask, cls_id in zip(result.masks.data, result.boxes.cls):
                    mask_set.append(mask)
                    label_set.append(cls_id)
                masks.append(mask_set)
                labels.append(label_set)
        

          
        return masks, labels
   
      