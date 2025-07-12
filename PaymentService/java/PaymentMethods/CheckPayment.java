package PaymentMethods;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class CheckPayment extends Payment{
	
	private String checkNumber;
	private String bankName; 
	
	@JsonCreator
	public CheckPayment(@JsonProperty("payerName") String payerName,
	        @JsonProperty("amount") double amount,
	        @JsonProperty("checkNumber") String checkNumber,
	        @JsonProperty("bankName") String bankName
	        ) {
		super(payerName, amount);
		if (checkNumber == null || checkNumber.length() < 6 || checkNumber.length() > 12 || !checkNumber.matches("\\d+")) {
		    throw new WebApplicationException(
		        Response.status(Response.Status.BAD_REQUEST)
		            .entity("{\"error\":\"Check number must be 6–12 digits.\"}")
		            .type("application/json")
		            .build()
		    );
		}
		
		if (bankName == null || bankName.trim().isEmpty()) {		    
		    throw new WebApplicationException(
		            Response.status(Response.Status.BAD_REQUEST)
		                    .entity("{\"error\":\"Bank name cannot be empty.\"}")
		                    .type("application/json")
		                    .build()
		        );
		}
		this.checkNumber = checkNumber;
		this.bankName = bankName;
		this.payerName = payerName;
		this.amount = amount;
	}

	private String getCheckNumber() {
		return checkNumber;
	}

	private void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	private String getBankName() {
		return bankName;
	}

	private void setBankName(String bankName) {
		this.bankName = bankName;
	}

	private String getPayerName() {
		return payerName;
	}

	private void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	@Override
	public String toString() {
		return "CheckPayment [checkNumber=" + checkNumber + ", bankName=" + bankName + ", payerName=" + payerName
				+ ", amount=" + amount + "]";
	}

	@Override
    public String pay() {
        return "Transaction Sucessful! Customer " + payerName + " paid using check # " + checkNumber + " from " + bankName;
    }
	
	
}
