package PaymentMethods;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class BankTransfer extends Payment{
	
	private String accountHolderName; 
	private String bankName;
	private String accountNumber;
	private String routingNumber;
	
	@JsonCreator
	public BankTransfer(@JsonProperty("payerName") String payerName,
	        @JsonProperty("amount") double amount,
	        @JsonProperty("accountHolderName") String accountHolderName,
	        @JsonProperty("bankName") String bankName,
	        @JsonProperty("accountNumber") String accountNumber,
	        @JsonProperty("routingNumber") String routingNumber) {
		super(payerName, amount);
		
		if (payerName == null || payerName.trim().isEmpty()) {
			throw new WebApplicationException(
			        Response.status(Response.Status.BAD_REQUEST)
			            .entity("{\"error\":\"payerName can't be empty.\"}")
			            .type("application/json")
			            .build()
			    );
		}
	    
	    if (accountHolderName == null || accountHolderName.trim().isEmpty()) {
	    	throw new WebApplicationException(
			        Response.status(Response.Status.BAD_REQUEST)
			            .entity("{\"error\":\"accountHolderName can't be empty.\"}")
			            .type("application/json")
			            .build()
			    );
	    }
	    
	    if (bankName == null || bankName.trim().isEmpty()) {
	    	throw new WebApplicationException(
			        Response.status(Response.Status.BAD_REQUEST)
			            .entity("{\"error\":\"bankName can't be empty.\"}")
			            .type("application/json")
			            .build()
			    );
	    }
	    
	    if (accountNumber == null || !accountNumber.matches("\\d{8,20}")) {
	    	throw new WebApplicationException(
			        Response.status(Response.Status.BAD_REQUEST)
			            .entity("{\"error\":\"AccountNumber number must be 8–20 digits.\"}")
			            .type("application/json")
			            .build()
			    );
	    }
	    
	    if (routingNumber == null || !routingNumber.matches("\\d{9}")) {
	    	throw new WebApplicationException(
			        Response.status(Response.Status.BAD_REQUEST)
			            .entity("{\"error\":\"routingNumber must be 9 digits.\"}")
			            .type("application/json")
			            .build()
			    );
	    }
		
		this.accountHolderName = accountHolderName;
		this.bankName = bankName;
		this.accountNumber = accountNumber;
		this.routingNumber = routingNumber;
	}
	
	private String getPayerName() {
		return payerName;
	}

	private void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	
	private String getAccountHolderName() {
		return accountHolderName;
	}
	
	private void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}
	
	private String getBankName() {
		return bankName;
	}
	
	private void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	private String getAccountNumber() {
		return accountNumber;
	}
	
	private void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	private String getRoutingNumber() {
		return routingNumber;
	}
	
	private void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}
	
	@Override
	public String toString() {
		return "BankTransfer [accountHolderName=" + accountHolderName + ", bankName=" + bankName + ", accountNumber="
				+ accountNumber + ", routingNumber=" + routingNumber + ", amount=" + amount + "]";
	}

	@Override
    public String pay() {
        return "Transaction Sucessful! Customer " + payerName + " paid via bank transfer from " + accountHolderName + " using " + bankName + "'s services";
    }
}
