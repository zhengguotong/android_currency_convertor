package i11114675.zheng.currencyConvertor.currency;

public class CurrencyRate {
	private long id;
	private Currency fromCurrency;
	private Currency toCurrency;
	private double currencyRate = 0.0;
	private String toAmount = "0.0";
	
	public CurrencyRate(Currency toCurrency){
		this.toCurrency = toCurrency;
	}
	
	public CurrencyRate(long id, Currency fromCurreny, Currency toCurrency,
			double currencyRate) {
		this.id = id;
		this.fromCurrency = fromCurreny;
		this.toCurrency = toCurrency;
		this.currencyRate = currencyRate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Currency getFromCurrency() {
		return fromCurrency;
	}

	public void setFromCurrency(Currency fromCurrency) {
		this.fromCurrency = fromCurrency;
	}

	public Currency getToCurrency() {
		return toCurrency;
	}

	public void setToCurrency(Currency toCurreency) {
		this.toCurrency = toCurreency;
	}

	public double getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(double currencyRate) {
		this.currencyRate = currencyRate;
	}
	
	public String getFullCode(){
		return fromCurrency.getCurrencyCode() + "/"
				+ toCurrency.getCurrencyCode();
	}

	public String getToAmount() {
		return toAmount;
	}

	public void setToAmount(String toAmount) {
		this.toAmount = toAmount;
	}
}
