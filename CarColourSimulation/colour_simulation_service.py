import torch
from colour_simulation_interface import ColourSimulationInterface
import numpy as np
from yolo_service import YoloService
from PIL import Image
import io
import torch
import torch.nn.functional as F
import matplotlib.pyplot as plt

class ColourSimulation(ColourSimulationInterface):
    """Implementation of the ColourSimulation interface."""

    def __init__(self, model_path: str = 'colour_simulation_model.pt'):
        """
        Initialize ColourSimulation service.

        Args:
            model_path: Path to the colour simulation model file
        """
        self.model_path = model_path
        # load model
        self.model = YoloService(model_path='CarColourSimulation/models/best.pt')
    def resize_masks_to_original(self,masks, orig_h, orig_w):
        """
        Resize a mask tensor to the original image size.
      
        """
        masks_resized = []
        for mask in masks:

            if mask.ndim == 2:
                mask = mask.unsqueeze(0).unsqueeze(0) 
            elif mask.ndim == 3:
                mask = mask.unsqueeze(1) 
            mask_resized = F.interpolate(mask.float(), size=(orig_h, orig_w), mode='bilinear')
            masks_resized.append((mask_resized > 0.5).squeeze(1).bool() )
        return masks_resized
        

    def simulate_colour_on_body(self, image_bytes: bytes, colour: np.ndarray) -> bytes:
        """
        Simulate a specific colour on the provided image.

        Args:
            image_bytes: Raw image bytes
            colour: Colour to simulate (e.g., 'red', 'blue')

        Returns:
            Bytes of the modified image with the simulated colour.
        """
        #get masks 
        image = Image.open(io.BytesIO(image_bytes))
        masks,labels=self.model.get_segmentation_masks(image)
        resized_masks = self.resize_masks_to_original(masks[0], image.height, image.width) #broken only works with one image
        image_matrix = torch.tensor(np.array(image))
        
        #convert image bytes to numpy array
       
       
        labels=labels[0] #only works with one image
        group_masks=self.stitch_masks(resized_masks, labels)
        mean_colour=self.compute_mean_colour(group_masks['body_panels'], image_matrix)
        #compute transform from mean colour to target colour
        target_colour = torch.tensor(colour, dtype=torch.float32)
        colour_transform = self.compute_colour_transform(mean_colour, target_colour)
        new_image = self.apply_colour_transform(image_matrix,group_masks['body_panels'], colour_transform)
        # prep to send image back 
        plt.imsave("colour_change_image.png", new_image.numpy().astype(np.uint8))
        new_image_pil = Image.fromarray(new_image.numpy().astype(np.uint8))
        output_buffer = io.BytesIO()
        new_image_pil.save(output_buffer, format='PNG') 
        output_buffer.seek(0) 
        return output_buffer
    def apply_colour_transform(self, image: torch.Tensor, mask, transform: torch.Tensor) -> torch.Tensor:
        """
        Apply the colour transformation to the image.
        """
    
        
        # Create a copy of the original image
        result_image = image.clone().float()
        
        # Get the mask for the pixels to transform
        mask = mask.squeeze(0) if mask.dim() == 3 else mask
        
        # Create masked version for debugging
        masked_image = image.clone()
        masked_image[~mask] = 0
        
        # plt.imsave('mask_only.png', masked_image.numpy().astype(np.uint8))
        #mask out the image
        masked_pixels = result_image[mask]  
        
        # Apply the 3x3 transformation matrix to each pixel
        transformed_pixels = masked_pixels @ transform.T  
        
        
        result_image[mask] = transformed_pixels
        
        #clamp
        result_image = torch.clamp(result_image, 0, 255)
        

      
        return result_image
    

       
            
       
    def compute_colour_transform(self, mean_colour: torch.Tensor, target_colour: torch.Tensor) -> torch.Tensor:
        """
        create a colour space transformation from mean colour to target colour.
        there are better ways to do this(full 3x3 matrix) but that requires more data
        """

        #scale brightness of the colour then compute the transformation for the colours
        scale_factors = target_colour / (mean_colour + 1e-8)
        
        transform = torch.diag(scale_factors)
        return transform

    def compute_mean_colour(self, mask, image: torch.Tensor) -> torch.Tensor:
        """
        Given an image and a mask, return the mean colour of the mask.
        """
        mask = mask.bool()  # Ensure mask is boolean
        # If image is HWC, convert to CHW for masking, or flatten mask to match image shape
       
        #mask is 1,h,w change into h,w
        mask = mask.squeeze(0)  
  
      
        masked_pixels = image[mask]  # Apply mask to image
        
       

        mean_colour = masked_pixels.float().mean(dim=0)
       
        return mean_colour

    def stitch_masks(self, masks: list, labels: list) -> np.ndarray:
        """
        Stitch multiple masks into a single mask.
        """
        # Define mask categories
        categories = {
            'doors': [1, 3, 6, 9, 11, 14],  
            'body_panels': [0, 8, 16, 20, 21],  
            'glass': [2, 10], 
            'wheels': [22],    
            'lights': [4, 5, 7, 12, 13, 15]  # all lights
        }
        
        # Initialize group masks
        group_masks = {category: None for category in categories}
        
        for mask, label in zip(masks, labels):
            for category, label_list in categories.items():
                if label in label_list:
                    if group_masks[category] is None:
                        group_masks[category] = mask.clone()
                    else:
                        group_masks[category] = torch.logical_or(group_masks[category], mask)
                    break
        return group_masks
            



    

# if __name__ == "__main__":
#     image_path = "CarColourSimulation/test_data/car2_3.jpg"
#     # Example usage
#     colour_simulation = ColourSimulation(model_path='CarColourSimulation/models/best.pt')
    
#     # Load an image (for example, from a file or bytes)
#     with open(image_path, "rb") as image_file:
#         image_bytes = image_file.read()
    
#     # Simulate colour on the image
#     simulated_image_bytes = colour_simulation.simulate_colour_on_body(image_bytes,np.array([108, 148, 252]))


