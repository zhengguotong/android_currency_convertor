package i11114675.zheng.currencyConvertor.views;

import i11114675.zheng.currencyConvertor.R;
import i11114675.zheng.currencyConvertor.currency.CurrencyRate;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CurrencyListItem extends LinearLayout {
	private CurrencyRate currencyRate;
    private TextView toCurrencyAmount;
    private TextView toCurrencyCode;
    private TextView fromCurrency;
    private TextView toCurrencyRate;
    private TextView toCurrency;
    private ImageView currencyFlag;
    
	public CurrencyListItem(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		
		toCurrencyAmount = (TextView) findViewById(R.id.cl_currency_amount);
		toCurrencyCode = (TextView) findViewById(R.id.cl_currency_code);
		fromCurrency = (TextView) findViewById(R.id.cl_from_currency);
		toCurrencyRate = (TextView) findViewById(R.id.cl_currency_rate);
		toCurrency = (TextView) findViewById(R.id.cl_to_currency);
		currencyFlag = (ImageView) findViewById(R.id.cl_currency_flag);
	}
	
	/**
	 * sets corresponding toAmout, to currencyCode, fromCurrency,
	 * toCurrencyRate, toCurrency and currencyFlag for this currency rate view
	 * 
	 * @param currencyRate
	 */
	public void setCurrencyRate(CurrencyRate currencyRate) {
		this.currencyRate = currencyRate;
		
		toCurrencyAmount.setText(currencyRate.getToAmount());
		toCurrencyCode.setText(currencyRate.getFullCode());
		fromCurrency.setText(currencyRate.getFromCurrency().getCurrencyCode());
		toCurrencyRate.setText("" + currencyRate.getCurrencyRate());
		toCurrency.setText(currencyRate.getToCurrency().getCurrencyCode());
		currencyFlag.setBackgroundDrawable(currencyRate.getToCurrency()
				.getFlag());
	}
	
	public CurrencyRate getCurrencyRate(){
		return currencyRate;
	}
	
}
