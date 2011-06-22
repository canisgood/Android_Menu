package com.Menu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TransactionDbAdapter {
    /* Table: TransactionList */
    public static final String KEY_ID = "_rowid";				// primary key: transaction id
    public static final String KEY_ITEMLIST = "_itemlist";		// item list
    public static final String KEY_PRICE = "_price";			// total price
    public static final String KEY_TIME = "_time";				// time
    
    /* Table: TransactionItem */
    /*
    public static final String KEY_ID = "_rowid";				// primary key
    public static final String KEY_PRICE = "_price";			// unit price
    public static final String KEY_TIME = "_time";				// time
    */
    public static final String KEY_TID = "_tid";				// transaction id
    public static final String KEY_ITEM = "_item";				// item
    public static final String KEY_AMOUNT = "_amount";			// amount
    
    private static final String TAG = "TransactionDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    
    /**
     * Database creation sql statement
     */
    /* Table: TransactionList */
    private static final String DATABASE_CREATE1 =
            "create table TransactionList (_rowid integer primary key autoincrement, "
                    + "_itemlist text not null, "
                    + "_price integer DEFAULT 0, "
                    + "_time text not null);";
    
    /* Table: TransactionItem */
    private static final String DATABASE_CREATE2 =
        	"create table TransactionItem (_rowid integer primary key autoincrement, "
				+ "_tid integer DEFAULT 0, "
                + "_item text not null, "
                + "_price integer DEFAULT 0, "
                + "_amount integer DEFAULT 0, "
                + "_time text not null);";
    
    private static final String DATABASE_NAME = "TRANSACTION";
    private static final String DATABASE_TABLE1 = "TransactionList";
    private static final String DATABASE_TABLE2 = "TransactionItem";
    private static final int DATABASE_VERSION = 2;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        	db.execSQL(DATABASE_CREATE1);
        	db.execSQL(DATABASE_CREATE2);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_CREATE1);
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_CREATE2);
            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
    public TransactionDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the "TRANSACTION" database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public TransactionDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
    
    public void close() {
    	
        mDbHelper.close();
    }

    
    /******
     * Insert Data Functions
     ******/
    
    /**
     * Table: TransactionList
     * Create a new "transaction". If the search is
     * successfully created return the new rowId for that transaction, otherwise return
     * a -1 to indicate failure.
     * 
     * @param itemlist the items of this transaction
     * @param price the total price of this transaction
     * @param time the time of this transaction
     * @return rowId or -1 if failed
     */
    public long createTransaction(String itemlist, String price, String time) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ITEMLIST, itemlist);
        initialValues.put(KEY_PRICE, price);
        initialValues.put(KEY_TIME, time);

        return mDb.insert(DATABASE_TABLE1, null, initialValues);
    }
    
    /**
     * Table: TransactionItem
     * Create a new "transaction item". If the search is
     * successfully created return the new rowId for that transaction, otherwise return
     * a -1 to indicate failure.
     * 
     * @param tid the transaction id of the item
     * @param item the an item of this transaction
     * @param price the unit price of the item
     * @param amount the amount of the item
     * @param time the time of transaction
     * @return rowId or -1 if failed
     */
    public long createTransactionItem(String tid, String item, String price, String amount, String time) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TID, tid);
        initialValues.put(KEY_ITEM, item);
        initialValues.put(KEY_PRICE, price);
        initialValues.put(KEY_AMOUNT, amount);
        initialValues.put(KEY_TIME, time);

        return mDb.insert(DATABASE_TABLE2, null, initialValues);
    }
    
    
    /******
     * Delete Data Functions
     ******/
    
    /**
     * Table: TransactionList
     * Delete the "transaction" with the given tid
     * 
     * @param id the transaction id of the transaction to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteTransaction(long id) {

        return mDb.delete(DATABASE_TABLE1, KEY_ID + "=" + id, null) > 0;
    }
    /**
     * Table: TransactionList
     * Delete all the "transaction"s
     * 
     * @return true if deleted, false otherwise
     */
    public boolean deleteAllTransaction() {

        return mDb.delete(DATABASE_TABLE1, KEY_ID + "!=0", null) > 0;
    }
    
    /**
     * Table: TransactionItem
     * Delete the "transaction item" with the given rowId
     * 
     * @param id the rowid of the item to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteTransactionItem(long id) {

        return mDb.delete(DATABASE_TABLE2, KEY_ID + "=" + id, null) > 0;
    }
    /**
     * Table: TransactionItem
     * Delete the "transaction items" with the given tid
     * 
     * @param tid the items with tid of the transaction to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteTransactionItemlist(long tid) {

        return mDb.delete(DATABASE_TABLE2, KEY_TID + "=" + tid, null) > 0;
    }
    /**
     * Table: TransactionItem
     * Delete all the "transactions' items"
     * 
     * @return true if deleted, false otherwise
     */
    public boolean deleteAllTransactionItem() {

        return mDb.delete(DATABASE_TABLE2, KEY_ID + "!=0", null) > 0;
    }

    
    /******
     * Select Data Functions
     ******/
    
    /**
     * Table: TransactionList
     * Return a Cursor over the list of all "transaction" in the database
     * 
     * @return Cursor over all "transaction"
     */
    public Cursor fetchAllTransaction(int bytime) {
    	
    	String order="";
    	if(bytime==0){
    		order=KEY_ID+" ASC";
    	}
    	else{
    		order=KEY_ID+" DESC";
    	}
    	
        return mDb.query(DATABASE_TABLE1, new String[] {KEY_ID, KEY_ITEMLIST, KEY_PRICE, KEY_TIME }, 
                null , 
                null, null, null, order, null);
    }
    /**
     * Table: TransactionList
     * Return a Cursor positioned at the "transaction" that matches the given rowId
     * 
     * @param time the time of "transaction" to retrieve
     * @return Cursor positioned to matching "transaction", if found
     * @throws SQLException if "transaction" could not be found/retrieved
     */
    public Cursor fetchTransaction(String time) throws SQLException {
    	String order=KEY_ID+" ASC";
        Cursor mCursor =

                mDb.query(true, DATABASE_TABLE1, new String[] {KEY_ID, KEY_ITEMLIST, KEY_PRICE, KEY_TIME },
                	KEY_TIME + "='" + time +"'", 
                    null, null, null, order, null);
        return mCursor;

    }
    
    /**
     * Table: TransactionItem
     * Return a Cursor over the list of all "transaction items" in the database
     * 
     * @return Cursor over all "transaction items"
     */
    public Cursor fetchAllTransactionItemlist(int bytime) {
    	
    	String order="";
    	if(bytime==0){
    		order=KEY_TID+" ASC";
    	}
    	else{
    		order=KEY_TID+" DESC";
    	}
    	
        return mDb.query(DATABASE_TABLE2, new String[] {KEY_ID, KEY_TID, KEY_ITEM, KEY_PRICE, KEY_AMOUNT, KEY_TIME }, 
                null , 
                null, null, null, order, null);
    }
    /**
     * Table: TransactionItem
     * Return a Cursor positioned at the "transaction items" that matches the given tid
     * 
     * @param tid items with the transaction id of transaction to retrieve
     * @return Cursor positioned to matching "transaction items", if found
     * @throws SQLException if "transaction" could not be found/retrieved
     */
    public Cursor fetchTransactionItemlist(String tid) throws SQLException {
        Cursor mCursor =

                mDb.query(true, DATABASE_TABLE2, new String[] {KEY_ID, KEY_TID, KEY_ITEM, KEY_PRICE, KEY_AMOUNT, KEY_TIME },
                	KEY_TID + "='" + tid +"'", 
                    null, null, null, null, null);
        return mCursor;

    }
    /**
     * Table: TransactionItem
     * Return a Cursor positioned at the "transaction item" that matches the given rowid
     * 
     * @param rowid rowid of an item to retrieve
     * @return Cursor positioned to matching "transaction item", if found
     * @throws SQLException if "transaction item" could not be found/retrieved
     */
    public Cursor fetchTransactionItem(String id) throws SQLException {
        Cursor mCursor =

                mDb.query(true, DATABASE_TABLE2, new String[] {KEY_ID, KEY_TID, KEY_ITEM, KEY_PRICE, KEY_AMOUNT, KEY_TIME },
                	KEY_ID + "='" + id +"'", 
                    null, null, null, null, null);
        return mCursor;

    }


    /******
     * Update Data Functions
     ******/
    
    /**
     * Table: TransactionList
     * Update the "transaction" using the details provided. The "transaction" to be updated is
     * specified using the id, and it is altered to use the parameters'
     * values passed in
     * 
     * @param id the rowid of transaction to update
     * @return true if the transaction was successfully updated, false otherwise
     */
    public boolean updateTransaction(String id, String itemlist, String price) {
        ContentValues args = new ContentValues();
        args.put(KEY_ITEMLIST, itemlist);
        args.put(KEY_PRICE, price);

        return mDb.update(DATABASE_TABLE1, args, KEY_ID + "='" + id + "'" , null) > 0;
    }
    
    /**
     * Table: TransactionItem
     * Update the "transaction item" using the details provided. The "transaction item" to be updated is
     * specified using the id, and it is altered to use the parameters'
     * values passed in
     * 
     * @param id the rowid of item to update
     * @return true if the item was successfully updated, false otherwise
     */
    public boolean updateTransactionItem(String id, String item, String price, String amount) {
        ContentValues args = new ContentValues();
        args.put(KEY_ITEM, item);
        args.put(KEY_PRICE, price);
        args.put(KEY_AMOUNT, amount);

        return mDb.update(DATABASE_TABLE2, args, KEY_ID + "='" + id + "'" , null) > 0;
    }
}
