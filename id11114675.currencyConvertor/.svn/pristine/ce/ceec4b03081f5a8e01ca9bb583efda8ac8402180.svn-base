package i11114675.zheng.currencyConvertor;

import i11114675.zheng.currencyConvertor.adapters.AllCurrencyListAdapter;
import android.app.ListActivity;
import android.os.Bundle;

/**
 * supclass for Add currencyAcitity, changeCurrencyAcitivity and 
 * changebasecurrnecyAcitity, this class load all available currency 
 * @author zhengguotong
 *
 */
public class AllCurrencyAcitivity extends ListActivity {
	protected CurrencyApplication app;
    protected AllCurrencyListAdapter adapter;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.add_currency);
		//gets this activity application 
        app = (CurrencyApplication)getApplication();
        //gets allListAdapter 
        adapter = new AllCurrencyListAdapter(this, app.getCurrencys());
	    setListAdapter(adapter);
	}
}
