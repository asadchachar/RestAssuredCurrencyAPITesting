package io.fixer.test.stepdef.model;

public class FixerRequest {

	private String from;
	private String to;
	private Double amount;

	public FixerRequest() {
	}

	public FixerRequest(String from, String to, Double amount) {
		super();
		this.from = from;
		this.to = to;
		this.amount = amount;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "ConvertCurrencyResponse [from=" + from + ", to=" + to + ", amount=" + amount + "]";
	}

}
