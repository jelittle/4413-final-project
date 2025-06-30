from abc import ABC, abstractmethod
from typing import List, Tuple, Dict, Any
import numpy as np

class YoloInterface(ABC):
    """Abstract interface for YOLO image segmentation services."""
    


    
    @abstractmethod
    def get_segmentation_masks(self, image_bytes: bytes) -> Tuple[np.ndarray, List[str]]:
        """
        Get segmentation masks and corresponding labels.
        
        Args:
            image_bytes: Raw image bytes
            
        Returns:
            Tuple of (combined_mask_array, list_of_labels)
        """
        pass
    

