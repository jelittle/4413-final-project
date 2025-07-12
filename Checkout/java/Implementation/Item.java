package Implementation;
/**
 * A representation of the items that the customer wants to check out
 */
public class Item implements ItemInterface {
	
	public String name; // the products name
	public double price; // the products price
	public int quantity; // the amount of said product being checked out

	public Item(String name, double price, int quantity) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	public String giveName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
