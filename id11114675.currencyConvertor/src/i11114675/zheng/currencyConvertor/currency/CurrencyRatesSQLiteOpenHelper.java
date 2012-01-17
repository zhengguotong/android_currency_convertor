package i11114675.zheng.currencyConvertor.currency;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CurrencyRatesSQLiteOpenHelper extends SQLiteOpenHelper {
	
	public static final int version = 1;
	public static final String DB_NAME = "currecy_rates_db.sqlite";
	public static final String CURRENCY_TABLE = "currency_rates";
	public static final String CURRENCY_ID = "id";
	public static final String FROM_CURRENCY = "from_currnecy";
	public static final String TO_CURRENCY = "to_currency";
	public static final String CURRENCY_RATE = "rate";
	
	public CurrencyRatesSQLiteOpenHelper(Context context) {
		super(context, DB_NAME, null, version);	
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		createTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}
	
	/**
	 * create currency rate database table 
	 * @param db
	 */
	private void createTable(SQLiteDatabase db){
		db.execSQL(
				"create table " + CURRENCY_TABLE +" (" +
				CURRENCY_ID + " integer primary key autoincrement not null," +
				FROM_CURRENCY + " integer," +
				TO_CURRENCY + " integer, " +
				CURRENCY_RATE + " real" +
				");" 
			);
	}

}
