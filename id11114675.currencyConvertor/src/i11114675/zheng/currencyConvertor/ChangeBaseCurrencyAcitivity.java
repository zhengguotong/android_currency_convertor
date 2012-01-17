package i11114675.zheng.currencyConvertor;

import android.view.View;
import android.widget.ListView;

/**
 * changes Base Currency activity 
 * 
 * @author zhengguotong
 *
 */
public class ChangeBaseCurrencyAcitivity extends AllCurrencyAcitivity {
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		//sets change fromCurrency to clicked item 
		app.setFromCurrency(adapter.getItem(position)); 
		finish();
	}	
}
