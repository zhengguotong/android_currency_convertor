package i11114675.zheng.currencyConvertor.webServices;

import i11114675.zheng.currencyConvertor.utils.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class CurrencyInfoService {
	private final static String URLADDRESS = "http://www.webservicex.net/CurrencyConvertor.asmx?WSDL";
	private final static String REQUESTMETHOD = "POST";
	private final static int TIMEOUT = 5000;
	private final static String CONTENTTYPE = "application/soap+xml; charset=utf-8";
	private final static String CONVERSIONRATERESULT = "ConversionRateResult";
	
	/**
	 * gets currency rate from web service 
	 * 
	 * @param inStream
	 * @param fromCurrency
	 *            the currency code that base currency eg: AUD for Australian
	 *            Dollar
	 * @param toCurrency
	 *            the currency code that will be converted eg: USD for U.S.
	 *            Dollar
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentCurrency(InputStream inStream, String fromCurrency,
			String toCurrency) {
		//reads soapFile and replace the fromCurrency and toCurrency in 
		//the soapFile
		String soap = readSoapFile(inStream, fromCurrency, toCurrency);
		byte[] data = soap.getBytes();
		//Connect to the currency convert web service
		URL url;
		try {
			url = new URL(URLADDRESS);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(REQUESTMETHOD);
			conn.setConnectTimeout(TIMEOUT);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", CONTENTTYPE);
			conn.setRequestProperty("Content-Length", String.valueOf(data.length));
			
			OutputStream outStream = conn.getOutputStream(); //gets outStream 
			outStream.write(data); 
			outStream.flush();
			outStream.close();
			// when the HTTP Connect response code is equal
			// to Numeric status code, 200: OK
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				//gets currency value from responseXML
				return parseResponseXML(conn.getInputStream());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return null;
	}
    /**
     * reads soap file
     * 
     * @param inStream
     * @param fromCurrency
	 *            the currency code that base currency eg: AUD for Australian
	 *            Dollar
	 * @param toCurrency
	 *            the currency code that will be converted eg: USD for U.S.
	 *            Dollar
     * @return
     * @throws Exception
     */
	public static String readSoapFile(InputStream inStream,
			String fromCurrency, String toCurrency) {
		// gets input stream as a byte array
		byte[] data;
		try {
			data = StreamTool.readInputStream(inStream);
			String soapxml = new String(data);// gets soap XML
			Map<String, String> params = new HashMap<String, String>();
			params.put("toCurrency", toCurrency);
			params.put("fromCurrency", fromCurrency);
			return replace(soapxml, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	
	}
    /**
     * replaces fromCurruency and oldCurrency in the currency_soap.xm
     * 
     * @param xml
     * @param params
     * @return
     * @throws Exception
     */
    public static String replace(String xml, Map <String, String> params){
    	String result = xml;
    	if (params != null && !params.isEmpty()){
    		for(Map.Entry<String, String> entry: params.entrySet()){
    			String name = "\\$"+entry.getKey();
    			Pattern pattern = Pattern.compile(name);
    			Matcher matcher = pattern.matcher(result);
    			if (matcher.find()){
    				result = matcher.replaceAll(entry.getValue());
    			}
    		}
    	}
    	return result;
    }
    
    /**
     * uses pullParser to parse a xml from web service response
     * 
     * @param inStream
     * @return
     * @throws Exception
     */
    private static String parseResponseXML(InputStream inStream){
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(inStream, "UTF-8");
			int eventType = parser.getEventType();// Gets first event
			while (eventType != XmlPullParser.END_DOCUMENT) {// when not end Xml
																// documet
				switch (eventType) {	
				case XmlPullParser.START_TAG:
					// gets parser current pointing position
					String name = parser.getName();
					if(CONVERSIONRATERESULT.equals(name)){
						return parser.nextText();
					}
					break;
				}
				eventType = parser.next();// moves this parser to next event
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
