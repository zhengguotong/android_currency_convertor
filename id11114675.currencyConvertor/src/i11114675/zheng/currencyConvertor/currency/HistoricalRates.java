package i11114675.zheng.currencyConvertor.currency;

import i11114675.zheng.currencyConvertor.views.GraphView.GraphViewData;

public class HistoricalRates {
	private GraphViewData[] historicalRate;
	private CurrencyRate rate;

	public HistoricalRates(GraphViewData[] historicalRate, CurrencyRate rate) {
		this.historicalRate = historicalRate;
		this.setRate(rate);
	}

	public GraphViewData[] getHistoricalRate() {
		return historicalRate;
	}

	public void setRate(CurrencyRate rate) {
		this.rate = rate;
	}

	public CurrencyRate getRate() {
		return rate;
	}
}
