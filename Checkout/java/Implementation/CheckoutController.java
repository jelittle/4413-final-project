package Implementation;

import java.util.Random;

import com.google.gson.Gson;

import PaymentMethods.CheckPayment;
import PaymentMethods.Payment;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/controlls")
public class CheckoutController {
	public static Checkout checkout = new Checkout(null, null);;
	
	public CheckoutController() {
	}
	
	@POST
	@Path("/payment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response setPayment(Payment payment) {
		Random rand = new Random();
        boolean approved = (rand.nextInt(3) < 2);
        
        payment.amount = checkout.calculateTotal();
        System.out.println(checkout.giveReceipt());
        if (approved) {
            return Response.ok(payment.pay() + '\n' + checkout.giveReceipt()).build();
        } else {
            return Response.status(402).entity("Payment rejected").build();
        }
	}
	
	@POST
	@Path("/item") 
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public Response putItem(@QueryParam("itemId") String itemID, @QueryParam("name") String name, @QueryParam("price") double price, @QueryParam("quantity") int quantity) {
		System.out.println("itemId" + itemID + ", name: " + name + ", price:" + price + ", quantity:" + quantity);
		checkout.putItem(itemID, name, price, quantity);
		System.out.println(checkout.items.get("1"));
		return Response.status(200).entity("Item added sucessfully").build();
	}
	
	@DELETE
	@Path("/item")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response removeItem(@QueryParam("itemId") String itemId) {
		checkout.removeItem(itemId);
		return Response.status(200).entity("Item removed").build();
	}
	
	@DELETE
	@Path("/clear")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response removeAllItem() {
		checkout.clearCheckout();
		return Response.status(200).entity("All Items removed").build();
	}
	
	@GET
	@Path("/item")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItem(@QueryParam("itemId") String itemId) {
		Item item = checkout.items.get(itemId);
		Gson gson=new Gson();
		return Response.status(200).entity(gson.toJson(item)).build();
	}
	
	@GET
	@Path("/items")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllItem() {
		Gson gson=new Gson();
		return Response.status(200).entity(gson.toJson(checkout.items)).build();
	}
	
	@GET
	@Path("/test") 
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
	    return "REST is working!";
	}

}
