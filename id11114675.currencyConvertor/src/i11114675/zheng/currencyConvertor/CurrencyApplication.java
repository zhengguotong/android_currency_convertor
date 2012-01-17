package i11114675.zheng.currencyConvertor;

import i11114675.zheng.currencyConvertor.currency.Currency;
import i11114675.zheng.currencyConvertor.currency.CurrencyRate;
import i11114675.zheng.currencyConvertor.currency.CurrencyRatesSQLiteOpenHelper;
import i11114675.zheng.currencyConvertor.webServices.CurrencyInfoService;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static i11114675.zheng.currencyConvertor.currency.CurrencyRatesSQLiteOpenHelper.*;


public class CurrencyApplication extends Application {
	
	public static final String TAG = "CurrencyApplication";
    private ArrayList<Currency> currencys;
    private ArrayList<CurrencyRate> currencyRates;
    private Currency fromCurrency;
    private double baseAmount;
    private SQLiteDatabase database; 
    private Resources res;
	private TypedArray flags; 
	private TypedArray currencyCodes;
	private TypedArray currencyName;
    
    @Override
	public void onCreate() {
		super.onCreate();
		setupResource();
		
		try {
			CurrencyRatesSQLiteOpenHelper helper = new CurrencyRatesSQLiteOpenHelper(
					this);
			database = helper.getWritableDatabase();// create database
		} catch (Exception e) {
			Log.e(TAG, "Data table not found");
		}

		setFromCurrency();// sets base currency information
		loadAllCurrency();// loads all currency
	    
	    if(null == currencyRates){
			loadCurrencyRates();// load currency rate from database
	    }
	}
    
	/**
	 * gets currency code, currency flag and currency name from xml
	 */
    private void setupResource(){
    	res = getResources();
		flags = res.obtainTypedArray(R.array.flags);
		currencyCodes = res.obtainTypedArray(R.array.currecny_codes);
	    currencyName = res.obtainTypedArray(R.array.currecny_names); 
    }
    
    /**
     * sets from currency information from sharedpreferences
     */
    public void setFromCurrency() {
		SharedPreferences fromCurrencyPre = getSharedPreferences(
				"fromCurrency", Context.MODE_PRIVATE);
		baseAmount = fromCurrencyPre.getFloat("baseAmount", 1);
		int fromCurrecyId = fromCurrencyPre.getInt("currencyId", 1);
		fromCurrency = new Currency(fromCurrecyId,
				flags.getDrawable(fromCurrecyId),
				currencyCodes.getString(fromCurrecyId),
				currencyName.getString(fromCurrecyId));
    }
	
    /**
     * loads all available currency from xml and 
     * saves to currencies arraylist 
     */
    private void loadAllCurrency() {
    	if (null == currencys){
			currencys = new ArrayList<Currency>();
			
			Currency currency;

			for (int i = 0; i < flags.length(); i++) {
				currency = new Currency(i,flags.getDrawable(i),
						currencyCodes.getString(i), currencyName.getString(i));
				currencys.add(currency);
			}
    	}
	}
	
    /**
     * load currency rates from database and stores to currencyrates arraylist 
     */
    private void loadCurrencyRates(){
    	currencyRates = new ArrayList<CurrencyRate>();

		Cursor ratesCursor = database.query(CURRENCY_TABLE, new String[] {
				CURRENCY_ID, FROM_CURRENCY, TO_CURRENCY, CURRENCY_RATE }, null,
				null, null, null, String.format("%s", CURRENCY_ID));

    	ratesCursor.moveToFirst(); // sets the cursor point to the first row
    	CurrencyRate rate;
    	
    	if (!ratesCursor.isAfterLast()) { 
			do{
				int id = ratesCursor.getInt(0);
				int fcId = ratesCursor.getInt(1);
				Currency fromCurrency = new Currency(fcId,flags.getDrawable(fcId),
						currencyCodes.getString(fcId),
						currencyName.getString(fcId));
				int tcId = ratesCursor.getInt(2);
				Currency toCurrency = new Currency(tcId,flags.getDrawable(tcId),
						currencyCodes.getString(tcId),
						currencyName.getString(tcId));
				double exchangeRate = ratesCursor.getDouble(3);
				rate = new CurrencyRate(id,fromCurrency, toCurrency,exchangeRate);
				rate.setToAmount(toAmount(exchangeRate));
				currencyRates.add(rate);
			} while (ratesCursor.moveToNext());
		} 
		ratesCursor.close(); // closes cursor
    }
    
    /**
     * updates currency rate from web service 
     */
	public void updateCurrencyRates() {
		for (CurrencyRate rate : currencyRates) {
			// gets input stream from xml
			InputStream inStream = this.getClass().getClassLoader()
					.getResourceAsStream("currency_soap.xml");
			try {
				// gets currency rates form web service
				rate.setCurrencyRate(Double.parseDouble(CurrencyInfoService
						.getCurrentCurrency(inStream, rate.getFromCurrency()
								.getCurrencyCode(), rate.getToCurrency()
								.getCurrencyCode())));
				rate.setToAmount(toAmount(rate.getCurrencyRate()));
				updateDatabase(rate); // updates new currency rate to database
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }
    
	/**
	 * updates database 
	 * @param rate
	 */
    private void updateDatabase(CurrencyRate rate){
    	ContentValues values = new ContentValues();
    	//gets new currency rate information 
		values.put(FROM_CURRENCY, rate.getFromCurrency().getPosition());
		values.put(TO_CURRENCY, rate.getToCurrency().getPosition());
		values.put(CURRENCY_RATE, rate.getCurrencyRate());

		long id = rate.getId();
		//finds place in databses 
		String where = String.format("%s = %s", CURRENCY_ID, id);
		//confirms changes
		database.update(CURRENCY_TABLE, values, where, null);
    }
    
    /**
     * add new currency rate to the list
     * @param rate
     */
	public void addCurrencyRate(CurrencyRate rate) {
		assert (null != currencyRates);
        
		//sets fromcurrency 
		rate.setFromCurrency(fromCurrency);
		currencyRates.add(rate);// add new currency to arraylist 
		
		ContentValues values = new ContentValues();
		//gets currency rate information 
		values.put(FROM_CURRENCY, rate.getFromCurrency().getPosition());
		values.put(TO_CURRENCY, rate.getToCurrency().getPosition());
		values.put(CURRENCY_RATE, rate.getCurrencyRate());
		//inserts new cureny into datbase
		rate.setId(database.insert(CURRENCY_TABLE, null, values));
	}
    /**
     * deletes currency rate from the list 
     * @param rate
     */
	public void deleteCurrencyRate(CurrencyRate rate){
		currencyRates.remove(rate);//removes list from arraylists
		//finds place in the database 
		String where = String.format("%s = %s", CURRENCY_ID, rate.getId() );
		//delete from database 
		database.delete(CURRENCY_TABLE, where, null);
	}
	
	/**
	 * updates base currency inform to sharedpreferences 
	 * @param fromCurrency
	 */
	public void setFromCurrency(Currency fromCurrency){
		this.fromCurrency = fromCurrency;
		//changes basecurrnecy for each currency rate in arrarylist
		changeBaseCurrecy();
		//open sharedpreference 
		SharedPreferences fromCurrencyPre = getSharedPreferences(
				"fromCurrency", Context.MODE_PRIVATE);
		Editor editor = fromCurrencyPre.edit();
		//write new base currency id to editor 
		editor.putInt("currencyId", fromCurrency.getPosition());
		editor.commit();//confirms changes 
	}
	
	/**
	 * changes basecurrnecy for each currency rate in arrarylist
	 */
	private void changeBaseCurrecy(){
		for(CurrencyRate rate: currencyRates){
			rate.setFromCurrency(fromCurrency);
		}
	}
	/**
	 * change toCurrency 
	 * @param index  toCurrency position
	 * @param newCurrency  new toCurrency information 
	 */
	public void changeCurrency(int index, Currency newCurrency){
		CurrencyRate rate = currencyRates.get(index);
		rate.setToCurrency(newCurrency);
	}
	
	public Currency getFromCurrency(){
		return fromCurrency;
	}
	
	/**
	 * updates new baseAmount value 
	 * @param newValue new baseAmount value 
	 */
	public void setBaseAmount(double newValue){
		baseAmount = newValue;
		
		//calculates toAmoutn for each currency rate
		for(CurrencyRate rate: currencyRates){
			rate.setToAmount(toAmount(rate.getCurrencyRate()));
		}
		
		//updates new baseAmout to sharepreference 
		SharedPreferences fromCurrencyPre = getSharedPreferences(
				"fromCurrency", Context.MODE_PRIVATE);
		Editor editor = fromCurrencyPre.edit();
		editor.putFloat("baseAmount", (float) newValue);
		editor.commit();	
	}
	
	public double getBaseAmount(){
		return baseAmount;
	}
	
	public ArrayList<Currency> getCurrencys() {
		return currencys;
	}

	public void setCurrencys(ArrayList<Currency> currencys) {
		this.currencys = currencys;
	}
	
	public ArrayList<CurrencyRate> getCurrencyRates() {
		return currencyRates;
	}
	
	public void setCurrencyRates(ArrayList<CurrencyRate> currencyRates) {
		this.currencyRates = currencyRates;
	}
	
	/**
	 * Calculates and formats toAmount 
	 */
	public  String toAmount(double rate){
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(baseAmount * rate);
	}
}
