package i11114675.zheng.currencyConvertor;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AmountAcitivity extends Activity {
    private static final String TAG ="AmountAcitivity";
    private TextView amount;
    private CurrencyApplication app;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.amount);
		
		app = (CurrencyApplication)getApplication();
		amount = (TextView) findViewById(R.id.a_amount);
	}
	
	/**
	 * append "1" to the amount text
	 * 
	 * @param target the number one button view 
	 */
	public void oneHandler(View target) {
		amount.append("1");
	}
	
	/**
	 * append "2" to the amount text
	 * 
	 * @param target the number two button view 
	 */
    public void twoHandler(View target) {
    	amount.append("2");
	}
    
    /**
	 * append "3" to amount text
	 * 
	 * @param target the number three button view 
	 */
	public void threeHandler(View target) {
		amount.append("3");
	}
	
	/**
	 * append "4" to amount text
	 * 
	 * @param target the number four button view
	 */
	public void fourHandler(View target) {
		amount.append("4");
	}
	
	/**
	 * append "5" to amount text
	 * 
	 * @param target the number five button view 
	 */
	public void fiveHandler(View target) {
		amount.append("5");
	}
	
	/**
	 * append "6" to amount text
	 * 
	 * @param target the number six button view 
	 */
	public void sixHandler(View target) {
		amount.append("6");
	}
	
	/**
	 * append "7" to amount text
	 * 
	 * @param target the number seven button view 
	 */
	public void sevenHandler(View target) {
		amount.append("7");
	}
	
	/**
	 * append "8" to amount text
	 * 
	 * @param target the number eight button view
	 */
	public void eightHandler(View target) {
		amount.append("8");
	}
	
	/**
	 * append "9" to amount text
	 * 
	 * @param target the number nine button view 
	 */
	public void nineHandler(View target) {
		amount.append("9");
	}
	
	/**
	 * append "0" to amount text
	 * 
	 * @param target the number zero button view 
	 */
	public void zeroHandler(View target) {
		amount.append("0");
	}
	
	/**
	 * append "00" to amount text
	 * 
	 * @param target the hundred symbol button view 
	 */
	public void hundredHandler(View target) {
		amount.append("00");
	}
	
	/**
	 * append "." to amount text
	 * 
	 * @param target the  decimal point button view 
	 */
	public void pointHandler(View target) {
		amount.append(".");
	}
	
	/**
	 * delete last character on the amount text
	 * 
	 * @param target the  delete button view 
	 */
	public void delHandler(View target) {
		if (amount.length() > 0) {// if the text is not empty
			String oldText = amount.getText().toString();
			amount.setText(oldText.substring(0, oldText.length() - 1));
		}
	}
	
	/**
	 * clear all amount text
	 * 
	 * @param target the  ac button view 
	 */
	public void acHandler(View target) {
		amount.setText("");
	}
	
	/**
	 *
	 * 
	 * @param target the ok button view 
	 */
	public void okHandler(View target) {
		try{
			double newAmount = Double.parseDouble(amount.getText().toString());
			app.setBaseAmount(newAmount);
			finish();
		}catch(NumberFormatException e){
			Toast.makeText(this, "You must enter a valid  number", Toast.LENGTH_SHORT).show();
			amount.setText("");
			Log.e(TAG, e.toString());
		}
	}
}
