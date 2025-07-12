package Implementation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A representation of the items that the customer wants to check out
 */
public class Item implements ItemInterface {
	
	private String itemId; // the products name
	private double price; // the products price
	private int quantity; // the amount of said product being checked out
	
	@JsonCreator
	public Item(@JsonProperty("itemId") String itemId, 
				@JsonProperty("quantity") int quantity,
				@JsonProperty("price") double price) {
		this.itemId = itemId;
		this.price = price;
		this.quantity = quantity;
	}

	public String giveId() {
		return itemId;
	}

	public void setName(String name) {
		this.itemId = name;
	}

	public double givePrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int giveQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
