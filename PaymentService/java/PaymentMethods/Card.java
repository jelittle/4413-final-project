package PaymentMethods;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class Card extends Payment {
	
	private String CardNumber;
	private String expirationDate;
	private String CVV;
	private String BillingAdress;
	
	@JsonCreator
	public Card(@JsonProperty("payerName") String payerName,
	        @JsonProperty("amount") double amount,
	        @JsonProperty("CardNumber") String CardNumber,
	        @JsonProperty("expirationDate") String expirationDate,
	        @JsonProperty("CVV") String CVV,
	        @JsonProperty("BillingAdress") String BillingAdress) {
		
		super(payerName, amount);
		
		if (CardNumber.length() != 16 || !CardNumber.matches("\\d+") || !LuhnCheck(CardNumber)) {
			throw new WebApplicationException(
		            Response.status(Response.Status.BAD_REQUEST)
		                    .entity("{\"error\":\"Invalid card number. Must be 16 digits and pass Luhn check.\"}")
		                    .type("application/json")
		                    .build()
		        );
		}
		this.CardNumber = CardNumber;
		this.expirationDate = expirationDate; 
		this.CVV = CVV;
		if (!(CVV.length() == 3 || CVV.length() == 4) || !CVV.matches("\\d+")) {
			throw new WebApplicationException(
		            Response.status(Response.Status.BAD_REQUEST)
		                    .entity("{\"error\":\"Invalid CVV. Must be 3 or 4 digits.\"}")
		                    .type("application/json")
		                    .build()
		        );
		}
		this.BillingAdress = BillingAdress;
	}

	private String getName() {
		return payerName;
	}

	private void setName(String payerName) {
		this.payerName = payerName;
	}

	private String getCardNumber() {
		return CardNumber;
	}

	private void setCardNumber(String number) {
		this.CardNumber = number;
	}

	private String getExpirationDate() {
		return expirationDate;
	}

	private void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	private String getCVV() {
		return CVV;
	}

	private void setCVV(String cVV) {
		CVV = cVV;
	}

	private String getBillingAdress() {
		return BillingAdress;
	}

	private void setBillingAdress(String billingAdress) {
		BillingAdress = billingAdress;
	}

	@Override
	public String toString() {
		return "CreditOrDebit [payerName=" + payerName + ", CardNumber=" + CardNumber + ", expirationDate=" + expirationDate + ", CVV="
				+ CVV + ", BillingAdress=" + BillingAdress + ", amount=" + amount + "]";
	}

	@Override
    public String pay() {
        return "Transaction Sucessful! Customer paid using Credit Card with identification number " + CardNumber;
    }
	
	private boolean LuhnCheck(String cardNumber) {
	    int sum = 0;
	    boolean alternate = false;

	    // Process digits right to left
	    for (int i = cardNumber.length() - 1; i >= 0; i--) {
	        char c = cardNumber.charAt(i);
	        if (!Character.isDigit(c)) return false; // invalid character
	        int digit = Character.getNumericValue(c);

	        if (alternate) {
	            digit *= 2;
	            if (digit > 9) {
	                digit -= 9;
	            }
	        }

	        sum += digit;
	        alternate = !alternate;
	    }

	    return sum % 10 == 0;
	}

	
}
