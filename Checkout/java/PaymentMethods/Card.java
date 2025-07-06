package PaymentMethods;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Card extends Payment {
	
	private String CardNumber;
	private String expirationDate;
	private String CVV;
	private String BillingAdress;
	
//	public Card(String payerName, double amount) {
//		super(payerName, amount);
//		this.payerName = payerName;
//		this.amount = amount;
//	}
	
	@JsonCreator
	public Card(@JsonProperty("payerName") String payerName,
	        @JsonProperty("amount") double amount,
	        @JsonProperty("CardNumber") String CardNumber,
	        @JsonProperty("expirationDate") String expirationDate,
	        @JsonProperty("CVV") String CVV,
	        @JsonProperty("BillingAdress") String BillingAdress) {
		super(payerName, amount);
		this.CardNumber = CardNumber;
		this.expirationDate = expirationDate; 
		this.CVV = CVV;
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

	
}
