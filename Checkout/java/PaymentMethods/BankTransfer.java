package PaymentMethods;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BankTransfer extends Payment{
	
	private String accountHolderName; 
	private String bankName;
	private String accountNumber;
	private String routingNumber;
	
//	public BankTransfer(String payerName, double amount) {
//		super(payerName, amount);
//		this.payerName = payerName;
//		this.amount = amount;
//	}
	
	@JsonCreator
	public BankTransfer(@JsonProperty("payerName") String payerName,
	        @JsonProperty("amount") double amount,
	        @JsonProperty("accountHolderName") String accountHolderName,
	        @JsonProperty("bankName") String bankName,
	        @JsonProperty("accountNumber") String accountNumber,
	        @JsonProperty("routingNumber") String routingNumber) {
		super(payerName, amount);
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
