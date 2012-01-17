package i11114675.zheng.currencyConvertor.webServices;

import i11114675.zheng.currencyConvertor.utils.StreamTool;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import android.util.Log;


public class HistoricalCurrencyRate {
	private static final String TAG = "HistroricalCurrencyRate";
	//historical web service url address
	private static final String URLADDRESS = "http://currencies.apps.grandtrunk.net/getrange";
	
	/**
	 * get historical rates from web service
	 * @param startdate
	 * @param endDate
	 * @param fromCurrency
	 * @param toCurrency
	 * @return
	 */
	public static byte[] getHistroiaclStream(String startdate, String endDate,
			String fromCurrency, String toCurrency) {
		URL url;
		try {
			//create new url according to the startdate, endate
			//from currenycode and tocurreny code 
			url = new URL(URLADDRESS + "/"
					    + startdate + "/" // eg.01-11-2011
					    + endDate + "/"   // eg. 08-11-2011
					    + fromCurrency + "/" // from currency code eg.AUD
					    + toCurrency); // toCurrency code eg.CNY
			//open http connction 
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			//get inputstream from the connection 
			InputStream inStream = conn.getInputStream();
			return StreamTool.readInputStream(inStream); // parse input stream 
		} catch (MalformedURLException e) {
			Log.e(TAG, e.toString());
			return null;
		} catch (ProtocolException e) {
			Log.e(TAG, e.toString());
			return null;
		} catch (IOException e) {
			Log.e(TAG, e.toString());
			return null;
		} catch (Exception e) {
			Log.e(TAG, e.toString());
			return null;
		}
	}

}
