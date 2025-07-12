package Implementation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import PaymentMethods.BankTransfer;
import PaymentMethods.Card;
import PaymentMethods.CheckPayment;
import PaymentMethods.Payment;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Concrete checkout implementation object for customers to check out items they want to buy
 */
public class Checkout implements CheckoutInterface{
	public HashMap<String, Item> items = new HashMap<>(); // the list of all the items the customer wants to checkout
	public String userID;  // the customers user id
	public String id;
	
	public Checkout(String userName, String id) {
		this.userID = userName;
		this.id = id;
	}

	@Override
	public void putItem(String itemID, String name, double price, int quantity) {
		items.put(itemID, new Item (name, price, quantity));
	}
	
	@Override
	public void putItem(String itemID, Item item) {
		items.put(itemID, item);
	}

	@Override
	public void removeItem(String itemId) {
		items.remove(itemId);
	}
	
	@Override
	public double calculateTotal() {
		double total = 0;
		/* goes through all the items checked out by the customer in the items list and returns the sum of all items checked out 
		 * by multiplying each individual  items price by the quantity they were bought. */  
		for (String itemId : items.keySet()) {
			total += items.get(itemId).price * items.get(itemId).quantity;
		}
		return total;
	}

	@Override
	public Payment ChoosePaymentMethod(String method, String payerName, double amount) {
		Payment output = null;
		
//		if (method.equals("Card")) {
//			output = new Card(payerName, amount);
//		}
//		else if (method.equals("Check")) {
//			output = new CheckPayment(payerName, amount);
//		}
//		else if (method.equals("Bank")) {
//			output = new BankTransfer(payerName, amount);
//		}
		
		return output;
	}
	
	@Override
	public String giveReceipt() {
        String receipt = "";
        receipt += ("---- RECEIPT ----\n"); // start of the receipt
        
        // Append a list of item details to the RECEIPT in following format: itemId xQuantity @ UnitPrice = TotalPrice
        //System.out.println(items.keySet());
        for (String itemId : items.keySet()) {
            receipt += items.get(itemId).name + " x " + items.get(itemId).quantity + " @ $" + items.get(itemId).price + " = $ " + items.get(itemId).price * items.get(itemId).quantity + "\n";
        }
        
        // add total price of all checked out items into the receipt 
        receipt += "Total Price: $" + this.calculateTotal() + "\n";
        // ending message for the receipt
        receipt += "Thank you for shopping!\n";
        //System.out.println(receipt);
        return receipt;
    }

	@Override
	public void clearCheckout() {
		items.clear();
	}

	@Override
	public void getCart() {
		// TODO Auto-generated method stub
		
	}
	
	public String giveID() {
		return this.id;
	}
	
	public void setID (String id) {
		this.id = id;
	}

}
