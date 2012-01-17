package i11114675.zheng.currencyConvertor;

import i11114675.zheng.currencyConvertor.currency.CurrencyRate;
import android.view.View;
import android.widget.ListView;

/**
 * adds new currency to currency rate list 
 * 
 * @author zhengguotong
 *
 */
public class AddCurrencyActivity extends AllCurrencyAcitivity{
    
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		try {
			app.addCurrencyRate(new CurrencyRate(adapter
					.getItem(position)));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			finish();
		}
	}
}
