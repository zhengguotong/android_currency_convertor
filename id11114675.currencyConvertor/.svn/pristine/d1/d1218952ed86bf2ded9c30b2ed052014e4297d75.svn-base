package i11114675.zheng.currencyConvertor;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * change tocurrency 
 * @author zhengguotong
 *
 */
public class ChangeCurrencyAcitivity extends AllCurrencyAcitivity {
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Bundle fromPositionBundle = getIntent().getExtras();
		//gets tocurrency position from called activity 
		int fromPosition = fromPositionBundle.getInt("fromRate");
		app.changeCurrency(fromPosition,adapter.getItem(position));
		finish();
	}	
}
