package PaymentMethods;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonTypeInfo(
 use = JsonTypeInfo.Id.NAME,
 include = JsonTypeInfo.As.PROPERTY,
 property = "type"
)
@JsonSubTypes({
 @JsonSubTypes.Type(value = CheckPayment.class, name = "check"),
 @JsonSubTypes.Type(value = Card.class, name = "card"),
 @JsonSubTypes.Type(value = BankTransfer.class, name = "bank")
})

public abstract class Payment {
	public double amount;
	public String payerName;
	
	public Payment (String payerName, double amount) {
		this.payerName = payerName;
		this.amount = amount;
	}

	public abstract String pay ();
	
	
}