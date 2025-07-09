/*
Covers the functionality of the catalogue page.
Has the functionality for the model year range slider.
*/

// Model Year Range Slider functionality.
document.addEventListener('DOMContentLoaded', function() {
    const yearMinSlider = document.getElementById('year-min');
    const yearMaxSlider = document.getElementById('year-max');
    const yearMinDisplay = document.getElementById('year-min-display');
    const yearMaxDisplay = document.getElementById('year-max-display');

    // Update display values when sliders change
    function updateYearDisplay() {
        yearMinDisplay.textContent = yearMinSlider.value;
        yearMaxDisplay.textContent = yearMaxSlider.value;
    }

    // Ensure min slider doesn't exceed max slider. If so, move both sliders to the same value.
    yearMinSlider.addEventListener('input', function() {
        if (parseInt(yearMinSlider.value) > parseInt(yearMaxSlider.value)) {
            yearMaxSlider.value = yearMinSlider.value;
        }
        updateYearDisplay();
    });

    // Ensure max slider doesn't go below min slider. If so, move both sliders to the same value.
    yearMaxSlider.addEventListener('input', function() {
        if (parseInt(yearMaxSlider.value) < parseInt(yearMinSlider.value)) {
            yearMinSlider.value = yearMaxSlider.value;
        }
        updateYearDisplay();
    });

    // Initialize display
    updateYearDisplay();
});