package i11114675.zheng.currencyConvertor.adapters;

import java.util.ArrayList;

import i11114675.zheng.currencyConvertor.R;
import i11114675.zheng.currencyConvertor.currency.CurrencyRate;
import i11114675.zheng.currencyConvertor.views.CurrencyListItem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CurrencyListAdapter extends BaseAdapter {
	private ArrayList<CurrencyRate> currencyRates;
	private Context context;

	public CurrencyListAdapter(Context context, ArrayList<CurrencyRate> currencyRates) {
		this.currencyRates = currencyRates;
		this.context = context;
	}

	@Override
	public int getCount() {
		return currencyRates.size();
	}

	@Override
	public CurrencyRate getItem(int position) {
		return (null == currencyRates) ? null : currencyRates.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CurrencyListItem currencyLli;
		if (null == convertView) {
			currencyLli = (CurrencyListItem) View.inflate(context,
					R.layout.currency_list_item, null);
		} else {
			currencyLli = (CurrencyListItem) convertView;
		}
		currencyLli.setCurrencyRate(currencyRates.get(position));
		return currencyLli;
	}
	
	/**
	 * fore reload this adapter 
	 */
	public void forceReload() {
		notifyDataSetChanged();
	}
}
