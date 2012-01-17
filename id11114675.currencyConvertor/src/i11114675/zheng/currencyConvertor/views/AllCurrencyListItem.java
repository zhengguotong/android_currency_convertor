package i11114675.zheng.currencyConvertor.views;

import i11114675.zheng.currencyConvertor.R;
import i11114675.zheng.currencyConvertor.currency.Currency;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * All available currencies view 
 * @author zhengguotong
 *
 */
public class AllCurrencyListItem extends LinearLayout {
	private Currency currency;
    private ImageView currencyFlag;
    private TextView currencyCode;
    private TextView currencyName;
    
	public AllCurrencyListItem(Context context,AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		currencyFlag = (ImageView) findViewById(R.id.acl_currency_flag);
		currencyCode = (TextView) findViewById(R.id.acl_currency_code);
		currencyName = (TextView) findViewById(R.id.acl_currency_name);
	}
	
	/**
	 * sets corresponding flag, currency code and currency name for 
	 * this currencies list view 
	 * @param currency
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
		
		currencyFlag.setBackgroundDrawable(currency.getFlag());
		currencyCode.setText(currency.getCurrencyCode());
		currencyName.setText(currency.getCurrencyName());
	}
	
	
	public Currency getCurrency(){
		return currency;
	}
}
