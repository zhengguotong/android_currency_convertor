package i11114675.zheng.currencyConvertor.adapters;

import i11114675.zheng.currencyConvertor.R;
import i11114675.zheng.currencyConvertor.currency.Currency;
import i11114675.zheng.currencyConvertor.views.AllCurrencyListItem;

import java.util.ArrayList;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class AllCurrencyListAdapter extends BaseAdapter {
	private ArrayList<Currency> currencys;
	private Context context;
	
    public AllCurrencyListAdapter(Context context,ArrayList<Currency> currencys){
    	this.currencys = currencys;
    	this.context = context;
    }

	@Override
	public int getCount() {
		return currencys.size();
	}

	@Override
	public Currency getItem(int position) {
		return (null == currencys) ? null : currencys.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AllCurrencyListItem allCurrencyLli;
		if (null == convertView) {// if the list view is empty 
			// add currency view to currencies adapter s
			allCurrencyLli = (AllCurrencyListItem) View.inflate(context,
					R.layout.all_currency_list_item, null);
		} else {
			allCurrencyLli = (AllCurrencyListItem) convertView;
		}
        allCurrencyLli.setCurrency(currencys.get(position));
		return allCurrencyLli;
	}
}
