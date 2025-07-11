package Implementation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import PaymentMethods.CheckPayment;
import PaymentMethods.Payment;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/controlls")
public class CheckoutController {
	
	public CheckoutController() {
	}
	
	public static class Transaction {
		@JsonProperty("items")
		private List<Item> items;
		@JsonProperty("paymentMethod")
	    private Payment paymentMethod;

	    public Transaction() {}

	    public List<Item> getItems() {
	        return items;
	    }

	    public void setItems(List<Item> items) {
	        this.items = items;
	    }

	    public Payment getPaymentMethod() {
	        return paymentMethod;
	    }

	    public void setPaymentMethod(Payment paymentMethod) {
	        this.paymentMethod = paymentMethod;
	    }
	}
	
	@POST
	@Path("/transaction")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response processTransaction(Transaction transaction) {
	    List<Item> items = transaction.items;
	    Payment paymentMethod = transaction.paymentMethod;
		HashMap<String, Integer> quantitiesBought = new HashMap<>();
		List<String> ids = new ArrayList<>();
		for (Item item: items) {
			ids.add(item.giveId());
			quantitiesBought.put(item.giveId(), item.giveQuantity());
		}
		
		String queryInput = "\"[";
		for (int i = 0; i < ids.size(); i++) {
			queryInput += "\\\"" + ids.get(i) + "\\\"";
			if (i != ids.size() - 1) {
		        queryInput += ", ";
		    }
		}
		
		queryInput += "]\"";
		
		try {
			String command = "curl -X GET http://localhost:8060/vehicle/list -H \"Content-Type: application/json\" -d " + queryInput;

			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
			builder.redirectErrorStream(true);

			Process process = builder.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
			    response.append(line);
			}
			
			String fullResponse = response.toString();
			int jsonStart = fullResponse.indexOf('[');
			if (jsonStart == -1) {
			    throw new RuntimeException("No JSON array found in response");
			}

			String jsonOnly = fullResponse.substring(jsonStart);  

			JsonArray jsonArray = JsonParser.parseString(jsonOnly).getAsJsonArray();
			
			ArrayList<Item> avaliableItems = new ArrayList<>();
			for (JsonElement el : jsonArray) {
			    JsonObject obj = el.getAsJsonObject();

			    String id = obj.get("id").getAsString();
			    int quantity = obj.get("quantity").getAsInt();
			    double price = obj.get("price").getAsDouble();
			    
			    for (int i = 0; i < items.size(); i++) {
			    	if (items.get(i).giveId().equals(id)) items.get(i).setPrice(price);
			    }
			    
			    avaliableItems.add(new Item(id, quantity, price));
			}
	        
			String receipt = giveReceipt(items, paymentMethod);
		    			
			queryInput = "[";
			for (int i = 0; i < avaliableItems.size(); i++) {
			    Item item = avaliableItems.get(i);
			    String itemId = item.giveId();
			    int quantity = item.giveQuantity() - quantitiesBought.get(itemId);

			    if (quantity < 0) {
			        return Response.status(200).entity("There is no car with id = " + itemId + " in stock.").build();
			    }

			    queryInput += "{\"vehicleId\": \"" + itemId + "\", \"quantity\": " + quantity + "}";

			    if (i != avaliableItems.size() - 1) {
			        queryInput += (", ");
			    }
			}
			queryInput += "]";
			
			Random random = new Random();
			if (random.nextInt(3) == 0) {
			    return Response.status(402).entity("Payment Failed.").build();
			}
		    
		    HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(new URI("http://localhost:8060/vehicle/list?change=quantity"))
	                .method("PATCH", HttpRequest.BodyPublishers.ofString(queryInput))
	                .header("Content-Type", "application/json")
	                .build();

	        client.send(request, HttpResponse.BodyHandlers.ofString());

		    return Response.status(200).entity(receipt).build();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return Response.status(500).entity("Failed to contact vehicle service").build();
	    }		
	}
	
	public String giveReceipt(List<Item> items, Payment paymentMethod) {
        String receipt = paymentMethod.pay() + "\n";
        receipt += ("---- RECEIPT ----\n"); 
        
        for (Item item : items) {
            receipt += item.giveId() + " x " + item.giveQuantity() + " @ $" + item.givePrice() + " = $ " + item.givePrice() * item.giveQuantity() + "\n";
        }
                
        double total = 0;
        for (Item item : items) {
			total += item.givePrice() * item.giveQuantity();
		}
        
        receipt += "Total Price: $" + total + "\n";
        receipt += "Thank you for shopping!\n";
        return receipt;
    }
	
//	@POST
//	@Path("/item") // no price
//	@Consumes(MediaType.TEXT_PLAIN)
//	@Produces(MediaType.TEXT_PLAIN)
//	public Response putItem(@QueryParam("itemId") String itemID, @QueryParam("price") double price, @QueryParam("quantity") int quantity) {
//		System.out.println("itemId" + itemID + ", price:" + price + ", quantity:" + quantity);
//		checkout.putItem(itemID, price, quantity);
//		System.out.println(checkout.get("1"));
//		return Response.status(200).entity("Item added sucessfully").build();
//	}
//	
//	@DELETE
//	@Path("/item")
//	@Consumes(MediaType.TEXT_PLAIN)
//	public Response removeItem(@QueryParam("itemId") String itemId) {
//		checkout.removeItem(itemId);
//		return Response.status(200).entity("Item removed").build();
//	}
//	
//	@DELETE
//	@Path("/clear")
//	@Consumes(MediaType.TEXT_PLAIN)
//	public Response removeAllItem() {
//		checkout.clearCheckout();
//		return Response.status(200).entity("All Items removed").build();
//	}
//	
//	@GET
//	@Path("/item")
//	@Consumes(MediaType.TEXT_PLAIN)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getItem(@QueryParam("itemId") String itemId) {
//		Item item = checkout.get(itemId);
//		Gson gson=new Gson();
//		return Response.status(200).entity(gson.toJson(item)).build();
//	}
//	
//	@GET
//	@Path("/items")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getAllItem() {
//		Gson gson=new Gson();
//		return Response.status(200).entity(gson.toJson(checkout.items)).build();
//	}
//	
//	@GET
//	@Path("/test") 
//	@Produces(MediaType.TEXT_PLAIN)
//	public String test() {
//	    return "REST is working!";
//	}
//
//	@GET
//	@Path("/format")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getVehicleFormatFromAnotherProject() {
//	    try {
//	        HttpClient client = HttpClient.newHttpClient();
//	        HttpRequest request = HttpRequest.newBuilder()
//	                .uri(new URI("http://localhost:8070/vehicle/list"))
//	                .GET()
//	                .build();
//
//	        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
//	        String body = httpResponse.body();
//
//	        return Response.status(httpResponse.statusCode()).entity(body).build();
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return Response.status(500).entity("Failed to contact vehicle service").build();
//	    }
//	}
}
