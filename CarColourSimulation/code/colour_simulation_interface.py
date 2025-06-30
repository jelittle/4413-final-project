from abc import ABC, abstractmethod

class ColourSimulationInterface(ABC):
    """Abstract interface for colour simulation services."""

    @abstractmethod
    def simulate_colour_on_body(self, image_bytes: bytes, colour: str) -> bytes:
        """
        Simulate a specific colour on the provided image.

        """
        pass
