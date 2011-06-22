package com.Menu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MenuDbAdapter {
    /* Table: Product */
    public static final String KEY_ID = "_productId";		// primary key: product id
    public static final String KEY_CODE = "_code";			// product code
    public static final String KEY_PRICE = "_price";		// product price
    public static final String KEY_NAME = "_name";			// product name
    public static final String KEY_TYPE = "_type";			// product type: 
    														// 1 eats food, 2 drinks
    
    private static final String TAG = "MenuDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    
    /**
     * Database creation sql statement
     */
    /* Table: Product */
    private static final String DATABASE_CREATE1 =
            "create table Product (_productId integer primary key autoincrement, "
                    + "_code integer DEFAULT 0, "
                    + "_price integer DEFAULT 0, "
                    + "_name text not null, "
                    + "_type integer DEFAULT 1);";
    
    private static final String DATABASE_NAME = "MENU";
    private static final String DATABASE_TABLE1 = "Product";
    private static final int DATABASE_VERSION = 2;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        	db.execSQL(DATABASE_CREATE1);;
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_CREATE1);
            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
    public MenuDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the "MENU" database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public MenuDbAdapter open() throws SQLException {
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
     * Table: Product
     * Create a new "product". If the product is
     * successfully created return the new productId for that product, otherwise return
     * a -1 to indicate failure.
     * 
     * @param code the integer code(32 bits) of this product
     * @param price the price of this product
     * @param name the name of this product
     * @return productId or -1 if failed
     */
    public long createProduct(int code, int price, String name, int type) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CODE, code);
        initialValues.put(KEY_PRICE, price);
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_TYPE, type);

        return mDb.insert(DATABASE_TABLE1, null, initialValues);
    }
    public void createAllProduct() {
    	System.out.println("Insert all products into the MENU database");
    	
    	/*** 
         * �ƦX���\�I
        this.createProduct(0x00010001, 0, "xxxx�K�N" ,1);
        this.createProduct(0x00010002, 0, "xxxx�Υ]" ,1);
        this.createProduct(0x00010003, 0, "xxxx�J��" ,1);
        this.createProduct(0x00010004, 0, "xxxx�J��" ,1);
        this.createProduct(0x00010005, 0, "xxxx�i�|" ,1);
        this.createProduct(0x00010006, 0, "xxxx�J�N��" ,1);
        this.createProduct(0x00010007, 0, "xxxx�J�R�q" ,1);
        this.createProduct(0x00010008, 0, "xxxx�ŹT��" ,1);
        this.createProduct(0x00010009, 0, "xxxx�P��" ,1);
        this.createProduct(0x0001000a, 0, "xxxx�g�q" ,1);
        this.createProduct(0x0001000b, 0, "xxxx�p��" ,1);
        this.createProduct(0x0001000c, 0, "xxxx������" ,1);
         * ***/
        /*** 0x0001 ***/
        this.createProduct(0x00010003, 50, "�l�����L�J��" ,1);
        this.createProduct(0x00010004, 50, "�l�����L�J��" ,1);
        this.createProduct(0x00010005, 55, "�l�����L�i�|" ,1);
        this.createProduct(0x00010006, 55, "�l�����L�J�N��" ,1);
        this.createProduct(0x00010007, 50, "�l�����L�J�R�q" ,1);

        /*** 0x0002 ***/
        this.createProduct(0x00020003, 45, "�d�����L�J��" ,1);
        this.createProduct(0x00020004, 45, "�d�����L�J��" ,1);
        this.createProduct(0x00020005, 50, "�d�����L�i�|" ,1);
        this.createProduct(0x00020006, 50, "�d�����L�J�N��" ,1);
        this.createProduct(0x00020007, 45, "�d�����L�J�R�q" ,1);

        /*** 0x0003 ***/
        this.createProduct(0x00030003, 55, "�W�p���׳J��" ,1);
        this.createProduct(0x00030004, 55, "�W�p���׳J��" ,1);
        this.createProduct(0x00030005, 60, "�W�p���ץi�|" ,1);
        this.createProduct(0x00030006, 60, "�W�p���׳J�N��" ,1);
        this.createProduct(0x00030007, 55, "�W�p���׳J�R�q" ,1);

        /*** 0x0004 ***/
        this.createProduct(0x00040002, 35, "�ޱƳΥ]" ,1);
        this.createProduct(0x00040003, 40, "�ޱƳJ��" ,1);
        this.createProduct(0x00040004, 40, "�ޱƳJ��" ,1);
        this.createProduct(0x00040005, 45, "�ޱƥi�|" ,1);
        this.createProduct(0x00040006, 45, "�ޱƳJ�N��" ,1);
        this.createProduct(0x00040007, 40, "�ޱƳJ�R�q" ,1);

        /*** 0x0005 ***/
        this.createProduct(0x00050003, 35, "�����J��" ,1);
        this.createProduct(0x00050004, 35, "�����J��" ,1);
        this.createProduct(0x00050005, 40, "�����i�|" ,1);
        this.createProduct(0x00050006, 40, "�����J�N��" ,1);
        this.createProduct(0x00050007, 35, "�����J�R�q" ,1);
        
        /*** 0x0006 ***/
        this.createProduct(0x00060003, 40, "���ٳJ��" ,1);
        this.createProduct(0x00060004, 40, "���ٳJ��" ,1);
        this.createProduct(0x00060005, 45, "���٥i�|" ,1);
        this.createProduct(0x00060006, 45, "���ٳJ�N��" ,1);
        this.createProduct(0x00060007, 40, "���ٳJ�R�q" ,1);
        this.createProduct(0x00060008, 45, "���٨ŹT��" ,1);
        this.createProduct(0x00060009, 45, "�����P��" ,1);

        /*** 0x0007 ***/
        this.createProduct(0x00070003, 40, "���h�J��" ,1);
        this.createProduct(0x00070004, 40, "���h�J��" ,1);
        this.createProduct(0x00070005, 45, "���h�i�|" ,1);
        this.createProduct(0x00070006, 45, "���h�J�N��" ,1);
        this.createProduct(0x00070007, 40, "���h�J�R�q" ,1);

        /*** 0x0008 ***/
        this.createProduct(0x00080003, 40, "�s��J��" ,1);
        this.createProduct(0x00080004, 40, "�s��J��" ,1);
        this.createProduct(0x00080005, 45, "�s��i�|" ,1);
        this.createProduct(0x00080006, 45, "�s��J�N��" ,1);
        this.createProduct(0x00080007, 40, "�s��J�R�q" ,1);

        /*** 0x0009 ***/
        this.createProduct(0x00090003, 40, "���߳J��" ,1);
        this.createProduct(0x00090004, 40, "���߳J��" ,1);
        this.createProduct(0x00090005, 45, "���ߥi�|" ,1);
        this.createProduct(0x00090006, 45, "���߳J�N��" ,1);
        this.createProduct(0x00090007, 40, "���߳J�R�q" ,1);

        /*** 0x000a ***/
        this.createProduct(0x000a0001, 50, "�C���K�N" ,1);
        this.createProduct(0x000a0003, 35, "�C���J��" ,1);
        this.createProduct(0x000a0004, 30, "�C���J��" ,1);
        this.createProduct(0x000a0005, 40, "�C���i�|" ,1);
        this.createProduct(0x000a0006, 40, "�C���J�N��" ,1);
        this.createProduct(0x000a0007, 30, "�C���J�R�q" ,1);
        this.createProduct(0x000a0008, 40, "�C���ŹT��" ,1);
        this.createProduct(0x000a0009, 40, "�C���P��" ,1);

        /*** 0x000b ***/
        this.createProduct(0x000b0003, 30, "�_�q�J��" ,1);
        this.createProduct(0x000b0004, 25, "�_�q�J��" ,1);
        this.createProduct(0x000b0005, 35, "�_�q�i�|" ,1);
        this.createProduct(0x000b0006, 35, "�_�q�J�N��" ,1);
        this.createProduct(0x000b0007, 25, "�_�q�J�R�q" ,1);
        this.createProduct(0x000b0008, 35, "�_�q�ŹT��" ,1);

        /*** 0x000c ***/
        this.createProduct(0x000c0003, 35, "����J��" ,1);
        this.createProduct(0x000c0004, 30, "����J��" ,1);
        this.createProduct(0x000c0005, 35, "����i�|" ,1);
        this.createProduct(0x000c0006, 35, "����J�N��" ,1);
        this.createProduct(0x000c0007, 30, "����J�R�q" ,1);

        /*** 0x000d ***/;
        this.createProduct(0x000d0003, 30, "�ަ׳J��" ,1);
        this.createProduct(0x000d0004, 30, "�ަ׳J��" ,1);
        this.createProduct(0x000d0005, 35, "�ަץi�|" ,1);
        this.createProduct(0x000d0006, 35, "�ަ׳J�N��" ,1);
        this.createProduct(0x000d0007, 30, "�ަ׳J�R�q" ,1);

        /*** 0x000e ***/
        this.createProduct(0x000e0003, 30, "���L�J��" ,1);
        this.createProduct(0x000e0004, 25, "���L�J��" ,1);
        this.createProduct(0x000e0005, 35, "���L�i�|" ,1);
        this.createProduct(0x000e0006, 35, "���L�J�N��" ,1);
        this.createProduct(0x000e0007, 25, "���L�J�R�q" ,1);
        this.createProduct(0x000e0008, 35, "���L�ŹT��" ,1);
        this.createProduct(0x000e0009, 35, "���L�P��" ,1);
        this.createProduct(0x000e000c, 40, "���L������" ,1);

        /*** 0x000f ***/
        this.createProduct(0x000f0001, 50, "�����K�N" ,1);
        this.createProduct(0x000f0002, 35, "���ڳΥ]" ,1);
        this.createProduct(0x000f0003, 30, "���ڳJ��" ,1);
        this.createProduct(0x000f0004, 30, "���ڳJ��" ,1);
        this.createProduct(0x000f0005, 35, "���ڥi�|" ,1);
        this.createProduct(0x000f0006, 35, "���ڳJ�N��" ,1);
        this.createProduct(0x000f0007, 30, "���ڳJ�R�q" ,1);
        this.createProduct(0x000f0008, 40, "���ڨŹT��" ,1);

        /*** 0x0010 ***/
        this.createProduct(0x00100003, 35, "���J���J��" ,1);

        /*** 0x0011 ***/
        this.createProduct(0x00110003, 35, "�������J��" ,1);

        /*** 0x0012 ***/
        this.createProduct(0x00120004, 25, "���P�J��" ,1);
        this.createProduct(0x00120005, 35, "���P�i�|" ,1);
        this.createProduct(0x00120006, 35, "���P�J�N��" ,1);
        this.createProduct(0x00120007, 25, "���P�J�R�q" ,1);
        this.createProduct(0x00120008, 35, "���P�ŹT��" ,1);

        /*** 0x0013 ***/
        this.createProduct(0x00130004, 25, "�ɦ̳J��" ,1);
        this.createProduct(0x00130008, 35, "�ɦ̨ŹT��" ,1);

        /*** 0x0014 ***/
        this.createProduct(0x00140007, 45, "�`�׳J�R�q" ,1);

        /*** 0x0015 ***/
        this.createProduct(0x00150005, 25, "��ͥi�|" ,1);
        this.createProduct(0x00150008, 25, "��ͨŹT��" ,1);
        this.createProduct(0x0015000a, 15, "��ͤg�q" ,1);
        this.createProduct(0x0015000b, 20, "��ͫp��" ,1);

        /*** 0x0016 ***/
        this.createProduct(0x00160005, 25, "���J�O�i�|" ,1);
        this.createProduct(0x00160008, 25, "���J�O�ŹT��" ,1);
        this.createProduct(0x00160009, 25, "���J�O�P��" ,1);
        this.createProduct(0x0016000a, 15, "���J�O�g�q" ,1);
        this.createProduct(0x0016000b, 20, "���J�O�p��" ,1);

        /*** 0x0017 ***/
        this.createProduct(0x00170008, 25, "����ŹT��" ,1);
        this.createProduct(0x0017000a, 15, "����g�q" ,1);
        this.createProduct(0x0017000b, 20, "����p��" ,1);

        /*** 0x0018 ***/
        this.createProduct(0x00180008, 25, "���p�ŹT��" ,1);
        this.createProduct(0x0018000a, 15, "���p�g�q" ,1);
        this.createProduct(0x0018000b, 20, "���p�p��" ,1);

        /*** 0x0019 ***/
        this.createProduct(0x0019000a, 15, "���o�g�q" ,1);
        this.createProduct(0x0019000b, 20, "���o�p��" ,1);

        /*** 0x001a ***/
        this.createProduct(0x001a000a, 15, "���[�g�q" ,1);
        this.createProduct(0x001a000b, 20, "���[�p��" ,1);

        /*** 0x001b ***/
        this.createProduct(0x001b000a, 20, "���奤�p�g�q" ,1);
        this.createProduct(0x001b000b, 25, "���奤�p�p��" ,1);

        /*** 0x001c ***/
        this.createProduct(0x001c0003, 20, "����J��" ,1);
        this.createProduct(0x001c0004, 20, "����J��" ,1);
        this.createProduct(0x001c0006, 30, "����J�N��" ,1);
        this.createProduct(0x001c0007, 20, "����J�R�q" ,1);

        /*** 0x001d ***/
        this.createProduct(0x001d0008, 30, "�γJ�ŹT��" ,1);


        /***
         * ��~�\�I
        this.createProduct(0x00000001, 0, "", 1);
         * ***/
        this.createProduct(0x00000001, 10, "���]�J" ,1);
        this.createProduct(0x00000002, 45, "�q�j�Q��" ,1);
        this.createProduct(0x00000003, 35, "�­J���K�O��" ,1);
        this.createProduct(0x00000004, 35, "Ĩۣ�K�O��" ,1);
        this.createProduct(0x00000005, 25, "�ڽ��|" ,1);
        this.createProduct(0x00000006, 25, "�λ�" ,1);
        this.createProduct(0x00000007, 15, "����" ,1);
        this.createProduct(0x00000008, 25, "����" ,1);
        this.createProduct(0x00000009, 25, "����" ,1);
        this.createProduct(0x0000000a, 25, "����" ,1);
        this.createProduct(0x0000000b, 30, "�J���" ,1);
        this.createProduct(0x0000000c, 25, "���{" ,1);
        this.createProduct(0x0000000d, 30, "�D��" ,1);
        this.createProduct(0x0000000e, 25, "����" ,1);
        this.createProduct(0x0000000f, 25, "�׺�" ,1);
        this.createProduct(0x00000010, 15, "���]" ,1);
        this.createProduct(0x00000011, 15, "�ץ]" ,1);
        this.createProduct(0x00000012, 10, "�C�Y", 1);
        

        /*** 
    	 * ��~���~ 
    	this.createProduct(0x00000001, 0, "", 2);
    	 * ***/
        this.createProduct(0x00000001, 25, "������(�j)", 2);
        this.createProduct(0x00000002, 20, "������(��)", 2);
        this.createProduct(0x00000003, 15, "������(�p)", 2);
        this.createProduct(0x00000004, 20, "����(�j)", 2);
        this.createProduct(0x00000005, 15, "����(��)", 2);
        this.createProduct(0x00000006, 10, "����(�p)", 2);
        this.createProduct(0x00000007, 20, "�̼�(�j)", 2);
        this.createProduct(0x00000008, 15, "�̼�(��)", 2);
        this.createProduct(0x00000009, 10, "�̼�(�p)", 2);
        this.createProduct(0x0000000a, 20, "�A��", 2);
        this.createProduct(0x0000000b, 20, "����(�j)", 2);
        this.createProduct(0x0000000c, 15, "����(��)", 2);
        this.createProduct(0x0000000d, 10, "����(�p)", 2);
        this.createProduct(0x0000000e, 20, "����(�j)", 2);
        this.createProduct(0x0000000f, 15, "����(��)", 2);
        this.createProduct(0x00000010, 10, "����(�p)", 2);
        this.createProduct(0x00000011, 20, "���(�j)", 2);
        this.createProduct(0x00000012, 15, "���(��)", 2);
        this.createProduct(0x00000013, 10, "���(�p)", 2);
        this.createProduct(0x00000014, 20, "�������", 2);
        this.createProduct(0x00000015, 25, "�񨧨F����(�j)", 2);
        this.createProduct(0x00000016, 20, "�񨧨F����(��)", 2);
        this.createProduct(0x00000017, 15, "�񨧨F����(�p)", 2);
        this.createProduct(0x00000018, 25, "�ʭ�����(�j)", 2);
        this.createProduct(0x00000019, 20, "�ʭ�����(��)", 2);
        this.createProduct(0x0000001a, 15, "�ʭ�����(�p)", 2);
        this.createProduct(0x0000001b, 25, "�ʭ����(�j)", 2);
        this.createProduct(0x0000001c, 20, "�ʭ����(��)", 2);
        this.createProduct(0x0000001d, 15, "�ʭ����(�p)", 2);
        this.createProduct(0x0000001e, 25, "�h���(�j)", 2);
        this.createProduct(0x0000001f, 20, "�h���(��)", 2);
        this.createProduct(0x00000020, 15, "�h���(�p)", 2);
        this.createProduct(0x00000021, 30, "��i�@��(�j)", 2);
        this.createProduct(0x00000022, 25, "��i�@��(��)", 2);
        this.createProduct(0x00000023, 20, "��i�@��(�p)", 2);
        this.createProduct(0x00000024, 25, "���V�����(�j)", 2);
        this.createProduct(0x00000025, 20, "���V�����(��)", 2);
        this.createProduct(0x00000026, 15, "���V�����(�p)", 2);
        this.createProduct(0x00000027, 20, "�i��(�j)", 2);
        this.createProduct(0x00000028, 15, "�i��(��)", 2);
        this.createProduct(0x00000029, 40, "�ɦ̿@��(�j)", 2);
        this.createProduct(0x0000002a, 30, "�ɦ̿@��(��)", 2);
        this.createProduct(0x0000002b, 20, "�ɦ̿@��(�p)", 2);
        
        System.out.println("Insert completely.");
        return;
    }
    
    
    
    /******
     * Delete Data Functions
     ******/
    
    /**
     * Table: Product
     * Delete the "product" with the given product code
     * 
     * @param code the integer code of the product to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteProduct(int code, int type) {

        return mDb.delete(DATABASE_TABLE1, KEY_CODE + "=" + code + " and " + KEY_TYPE + "=" + type, null) > 0;
    }
    /**
     * Table: Product
     * Delete all the "product"s
     * 
     * @return true if deleted, false otherwise
     */
    public boolean deleteAllProduct() {

        return mDb.delete(DATABASE_TABLE1, KEY_ID + "!=0", null) > 0;
    }
    

    
    /******
     * Select Data Functions
     ******/
    
    /**
     * Table: Product
     * Return a Cursor over the list of all "product"s in the database
     * 
     * @return Cursor over all "product"
     */
    public Cursor fetchAllProduct() {
    	
        return mDb.query(DATABASE_TABLE1, new String[] {KEY_ID, KEY_CODE, KEY_PRICE, KEY_NAME }, 
                null , 
                null, null, null, null, null);
    }
    /**
     * Table: Product
     * Return a Cursor positioned at the "product" that matches the given product code
     * 
     * @param code the integer code of "product" to retrieve
     * @return Cursor positioned to matching "product", if found
     * @throws SQLException if "product" could not be found/retrieved
     */
    public Cursor fetchProduct(int code, int type) throws SQLException {
        Cursor mCursor =

                mDb.query(true, DATABASE_TABLE1, new String[] {KEY_ID, KEY_CODE, KEY_PRICE, KEY_NAME },
                	KEY_CODE + "='" + code + "' and " + KEY_TYPE + "='" + type + "'", 
                    null, null, null, null, null);
        return mCursor;
    }
    


    /******
     * Update Data Functions
     ******/
    
    /**
     * Table: Product
     * Update the "product" using the details provided. The "product" to be updated is
     * specified using the id, and it is altered to use the parameters'
     * values passed in
     * 
     * @param productId the id of product to update
     * @return true if the product was successfully updated, false otherwise
     */
    public boolean updateProduct(int productId, int code, int price, String name, int type) {
        ContentValues args = new ContentValues();
        args.put(KEY_CODE, code);
        args.put(KEY_PRICE, price);
        args.put(KEY_NAME, name);
        args.put(KEY_TYPE, type);
        
        return mDb.update(DATABASE_TABLE1, args, KEY_ID + "='" + productId + "'" , null) > 0;
    }
}
