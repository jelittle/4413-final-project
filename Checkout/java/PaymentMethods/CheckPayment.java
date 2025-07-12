package PaymentMethods;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
