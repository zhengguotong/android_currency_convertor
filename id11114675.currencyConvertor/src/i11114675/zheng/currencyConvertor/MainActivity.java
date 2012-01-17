package i11114675.zheng.currencyConvertor;

import java.text.SimpleDateFormat;
import java.util.Date;

import i11114675.zheng.currencyConvertor.adapters.CurrencyListAdapter;
import i11114675.zheng.currencyConvertor.currency.CurrencyRate;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity{
	private final static int REQUESTCODE = 1;
	private CurrencyListAdapter currencyRateAdapter;
	private CurrencyApplication app;
	private TextView lastUpdate;
	private TextView fromCurrencyCode;
	private TextView baseAmount;
	private ImageView fromCurrencyFlag;
	private SharedPreferences lastUpdatePre;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
      
        app = (CurrencyApplication)getApplication();
        currencyRateAdapter = new CurrencyListAdapter(this, app.getCurrencyRates());
        setListAdapter(currencyRateAdapter);
        setupView();
        update();
    }

	@Override
	protected void onResume() {
		super.onResume();
		currencyRateAdapter.forceReload(); // reloads currency rates adapter
		//resets base currency information 
		fromCurrencyCode.setText(app.getFromCurrency().getCurrencyCode());
		baseAmount.setText("" + app.getBaseAmount()); 
		fromCurrencyFlag.setBackgroundDrawable(app.getFromCurrency().getFlag());
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//updates currency rate when AddcurrenyActivity, ChangeBaseCurrencyAcitivy
		//and ChangeBaseCurrencyAcitivty is called
		update();
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	/**
	 * setups a view that was identified by the id attribute from the XML
	 */
	private void setupView(){
		lastUpdate = (TextView) findViewById(R.id.last_update_time);
		lastUpdatePre = getSharedPreferences("lastupdate", Context.MODE_PRIVATE);
		String updateTime = lastUpdatePre.getString("updateTime",
				"Not Update yet"); // gets last updatetime from sharepreferneces
		lastUpdate.setText("Last Update: " + updateTime);

		fromCurrencyCode = (TextView) findViewById(R.id.base_currency_code);
		baseAmount = (TextView) findViewById(R.id.base_currency_amount);
		fromCurrencyFlag = (ImageView) findViewById(R.id.base_currency_flag);

		fromCurrencyCode.setText(app.getFromCurrency().getCurrencyCode());
		baseAmount.setText("" + app.getBaseAmount());
		fromCurrencyFlag.setBackgroundDrawable(app.getFromCurrency().getFlag());
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.update_rate:
	    	update(); //updates currency rates 
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
    }
    
    /**
     * updates currency rate when the list is not empty 
     */
    private void update(){
    	//if there is not currency rate in the list 
		if (app.getCurrencyRates().size() == 0) {
			Toast.makeText(this, "Not data need to be updated",
					Toast.LENGTH_SHORT).show();
		}else {
			new UpdateCurreciesTask().execute(); 
		}
    }
    
    /**
     * saves last update time string to sharedpreference 
     * @param updatTime
     */
    private void setupLastUpdate(String updatTime){
    	Editor editor = lastUpdatePre.edit();
		editor.putString("updateTime", updatTime);
		editor.commit();
    }
    
    /**
     * starts add new currency rate activity 
     * 
     * @param target add new currency button view 
     */
	public void addCurrencyHandler(View target) {
		Intent intent = new Intent(MainActivity.this, AddCurrencyActivity.class);
		startActivityForResult(intent, REQUESTCODE);
	}
    
	/**
	 * starts chart view Activity 
	 * 
	 * @param target char view button 
	 */
	public void chartViewHandler(View target){
		if (0 != app.getCurrencyRates().size()){
			Intent intent = new Intent(MainActivity.this, LineChartActivity.class);
			startActivity(intent);
		} else {// when the currency rate list is empty
			Toast.makeText(this,
					"Please addd a currency in order to view the trend.",
					Toast.LENGTH_SHORT).show();
		}
	}
    
	/**
	 * change base currency group handler 
	 * 
	 * @param target
	 */
	public void baseCurrencyGroupHandler(View target) {
		final String[] actions = getResources().getStringArray(
				R.array.base_currency_dialog);// gets actions from xml
		//built a custom aler Dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(app.getFromCurrency().getCurrencyName());
		builder.setIcon(app.getFromCurrency().getFlag());
		//sets onlicked listener to each item 
		builder.setItems(actions, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				if (item == 0){//change base amount
					Intent intent = new Intent(MainActivity.this,
							AmountAcitivity.class);
					startActivity(intent);
				}
				else if (item == 1) {//change base currency 
					Intent intent = new Intent(MainActivity.this,
							ChangeBaseCurrencyAcitivity.class);
					startActivityForResult(intent, REQUESTCODE);
				}
			}
		});
		AlertDialog alert = builder.create();

		alert.show();
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, final int position, long id) {
		super.onListItemClick(l, v, position, id);
		final String[] actions = getResources().getStringArray(
				R.array.clicked_currency_dialog);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(app.getCurrencyRates().get(position).getToCurrency()
				.getCurrencyName());
		builder.setIcon(app.getCurrencyRates().get(position).getToCurrency()
				.getFlag());
		builder.setItems(actions, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				 if (item == 0){//changes this tocurrency 
					 startChangeCurrency(position);
				 }
			     else if (item == 1){//delete this tocurrency
					 app.deleteCurrencyRate(app.getCurrencyRates().get(position));
					 currencyRateAdapter.forceReload();
				 }
				 else if(item == 2){//move this tocurrency up 
					 moveUp(position);
				 }
				 else if(item == 3){//move this tocurrency down 
			    	moveDown(position);
				}
			}
		});
		AlertDialog alert = builder.create();

		alert.show();
	}
	
	/**
	 * moves tocurrency  up 
	 * @param position current position 
	 */
	private void moveUp(int position){
		if (position != 0) {//the currency position is not the first postion
			CurrencyRate temp = app.getCurrencyRates().get(position - 1);
			app.getCurrencyRates().set(position - 1,
					app.getCurrencyRates().get(position));
			app.getCurrencyRates().set(position, temp);
			currencyRateAdapter.forceReload(); // reloads adapter according to
												// the new order
		}
	}
	
	/**
	 * moves toCurrnecy down 
	 * @param position current position
	 */
	private void moveDown(int position) {
		// if the current position is not the last position
		if (position != app.getCurrencyRates().size() - 1) {
			CurrencyRate temp = app.getCurrencyRates().get(position + 1);
			app.getCurrencyRates().set(position + 1,
					app.getCurrencyRates().get(position));
			app.getCurrencyRates().set(position, temp);
			currencyRateAdapter.forceReload();
		}
	}
	
	/**
	 * start chnageCurrency activity 
	 * @param fromPosition
	 */
	private void startChangeCurrency(int fromPosition){
		Intent intent = new Intent(MainActivity.this, ChangeCurrencyAcitivity.class);
		// sends current tocurrncy position to child activity 
		Bundle bundle = new Bundle();
		bundle.putInt("fromRate", fromPosition);
		intent.putExtras(bundle);
		startActivityForResult(intent,REQUESTCODE);
	}
	
	/**
	 * inner class update update currency rate in background
	 * 
	 * @author zhengguotong
	 * 
	 */
	private class UpdateCurreciesTask extends AsyncTask<Void, Integer, Long> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Toast.makeText(MainActivity.this, "Loading...",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		protected Long doInBackground(Void... params) {
			try{
				app.updateCurrencyRates();
			}catch(Exception e){
				Toast.makeText(MainActivity.this, e.toString() ,Toast.LENGTH_SHORT).show();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Long result) {
			currencyRateAdapter.forceReload(); // reloads adapter
			String currentTime = SimpleDateFormat.getDateTimeInstance().format(
					new Date(System.currentTimeMillis()));// gets current time
			// save last update time to sharedpreferences
			setupLastUpdate(currentTime);
			//update last update time on the textview
			lastUpdate.setText("Last Update: " + currentTime);
			Toast.makeText(MainActivity.this, "Currecy data is up to date",
					Toast.LENGTH_SHORT).show();
	     }
    }
}