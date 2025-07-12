/**
 * The interface detailing checkout class's implementation
 */
package Implementation;

import PaymentMethods.Payment;

public interface CheckoutInterface {
	/**
	 * Submits an item for checking out
	 * @param itemID - the item's product id
	 * @param name - the products name
	 * @param price - the products price
	 * @param quantity - the amount of said product being checked out
	 */
	 void putItem(String itemID, String name, double price, int quantity);
	 
	 /**
	  * Submits an item for checking out
	  * @param itemID - the item's product id 
	  * @param item - An object representing the product being checkout
	  */
	 void putItem(String itemID, Item item);
	 
	 /**
	  * Removes an item from being checked out
	  * @param itemId - the item's product id
	  */
	 void removeItem(String itemId);

	 /**
	  * Returns the total price of all the items being checked out by the customer
	  * @return
	  */
	 double calculateTotal();
	 
	 /**
	  * Get the payment method the customer will use to pay for their items.
	  * @param method
	  */ // stragety desgine patterns and factory desgine patterns
	 Payment ChoosePaymentMethod(String method, String payerName, double amount);  // processPaymentMethod, make a processPaymentClass for each type of payment method, 1 in 3 chance of rejecting class
	 
	 /**
	  * Return a receipt of all the items being bought by the customer
	  * @return
	  */
	 String giveReceipt();
	 
	 /**
	  * remove all items from being checked out
	  */
	 void clearCheckout();  
	 
	 /**
	  * I frgt what this did again but I think you know what it dose, if not discord me to discuss.
	  */
	 void getCart();
}
