/* 
JS File to handle the quantity functionality of the new vehicle full details page.
*/

document.addEventListener('DOMContentLoaded', function() {
    const decreaseBtn = document.getElementById('decrease-quantity-button');
    const increaseBtn = document.getElementById('increase-quantity-button');
    const quantityDisplay = document.getElementById('quantity-number');
    
    let currentQuantity = 1;
    // Max quantity is 5
    const maxQuantity = 5;
    
    // Function to update the quantity display
    function updateQuantityDisplay() {
        quantityDisplay.textContent = currentQuantity;
    }
    
    // Decrease quantity button
    decreaseBtn.addEventListener('click', function() {
        if (currentQuantity > 1) {
            currentQuantity--;
            updateQuantityDisplay();
        }
    });
    
    // Increase quantity button
    increaseBtn.addEventListener('click', function() {
        if (currentQuantity < maxQuantity) {
            currentQuantity++;
            updateQuantityDisplay();
        }
    });
});