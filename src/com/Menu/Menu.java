package com.Menu;

/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//import com.example.android.apis.Shakespeare;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;


import android.R.string;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.TabActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.ParseException;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Demonstration of using fragments to implement different activity layouts.
 * This sample provides a different layout (and activity flow) when run in
 * landscape.
 */
public class Menu extends  TabActivity {
	private class RowData {
	       protected int mId;
	       protected String mTitle;
	       protected String mDetail;
	       protected String mTotal;
	       RowData(int id,String title,String detail,String total){
		       mId=id;
		       mTitle = title;
		       mDetail=detail;
		       mTotal=total;
		       
	       }
	       @Override
	       public String toString() {
	               return mId+" "+mTitle+" "+mDetail;
	       }
	}
	public class CustomAdapter extends ArrayAdapter<RowData> {
		public CustomAdapter(Context context, int resource,int textViewResourceId, List<RowData> objects) {               
			super(context, resource, textViewResourceId, objects);
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {   
		       ViewHolder holder = null;
		       TextView title = null;
		       TextView detail = null;
		       TextView total = null;
		       ImageView i11=null;
		       RowData rowData= getItem(position);
		       if(null == convertView){
		            convertView = mInflater.inflate(R.layout.list, null);
		            holder = new ViewHolder(convertView);
		            convertView.setTag(holder);
		       }
		       holder = (ViewHolder) convertView.getTag();
		       title = holder.gettitle();
		       title.setText(rowData.mTitle);
		       detail = holder.getdetail();
		       detail.setText(rowData.mDetail);                                                     
		       total = holder.getdetail();
		       total.setText(rowData.mTotal);
		       //i11=holder.getImage();
		       //i11.setImageResource(imgid[rowData.mId]);
		       return convertView;
		}
		private class ViewHolder {
			private View mRow;
			private TextView title = null;
			private TextView detail = null;
			private TextView total = null;
			private ImageView i11=null; 
			public ViewHolder(View row) {
				mRow = row;
			}
			public TextView gettitle() {
				if(null == title){
					title = (TextView) mRow.findViewById(R.id.title);
				}
				return title;             
			}     
			public TextView getdetail() {
			    if(null == detail){
			        detail = (TextView) mRow.findViewById(R.id.detail);
			    }
			    return detail;
			}
			public TextView gettotal() {
			    if(null == total){
			    	total = (TextView) mRow.findViewById(R.id.totally);
			    }
			    return total;
			}
			//public ImageView getImage() {
			//    if(null == i11){
			//        i11 = (ImageView) mRow.findViewById(R.id.img);
			//    }
			//    return i11;
			//}        
		}
	}
	private LayoutInflater mInflater;
	private Vector<RowData> data;
	RowData rd;
	static  String[] title = new String[1000];	
	static  String[] detail = new String[1000];
	static  String[] total = new String[1000]; 
	private Integer[] imgid = {
	  //R.drawable.bsfimg,R.drawable.bsfimg4,R.drawable.bsfimg2,
	  //R.drawable.bsfimg5
	};	
	//////////////////////
	private TransactionDbAdapter mDbHelper;
	private Cursor mTransactionCursor;
	private MenuDbAdapter mMenuDbHelper;
	private Cursor mMenuCursor;
	Bundle bundle;
	Intent intent;
	TextView editmeal;
	TextView edittotal;
	TextView edittype;
	TextView single;
	ListView order;
	String order_cost="0";
	String order_list="";
	String order_list_backup;
	String order_cost_backup;
	int cost,newitem,guestnum=0,checkoutflag=0;
	Button In,Out,CheckClear;
	Button Smallcount,Clear;
	int frontcode;
	
	Spinner number;
	int counter=0;
	String follow,count;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);     
		setContentView(R.layout.fragment_textlayout);
		Resources res = getResources(); // Resource object to get Drawables		
		//number = (Spinner) findViewById(R.id.spinner1);
		//ArrayAdapter<String> Spinneradapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,new String[]{"1","2","3","4","5","6","7","8","9","10"});		        //設定下拉選單的樣式
		//Spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//number.setAdapter(Spinneradapter);		
		//Spinner.OnItemSelectedListener spinnerListener= new Spinner.OnItemSelectedListener()
		//{
		//如果被選擇
		//public void onItemSelected(AdapterView<?>adapterView, View v, int position, long id){
		//	count =  adapterView.getSelectedItem().toString();
		//}
		//若是沒有選擇任何項目
		//public void onNothingSelected(AdapterView<?>adapterView){
		//	count ="1";
		//} 
		//};
		mDbHelper = new TransactionDbAdapter(this);
		mDbHelper.open();
		mMenuDbHelper = new MenuDbAdapter(this);
		mMenuDbHelper.open();
		//number.setOnItemSelectedListener(spinnerListener);
		In = (Button) findViewById(R.id.in);
		Out = (Button) findViewById(R.id.out);
		CheckClear = (Button) findViewById(R.id.CheckClear);
		order = (ListView) findViewById(R.id.guestlistview);
		editmeal = (TextView) findViewById(R.id.editmeal);
		edittotal = (TextView) findViewById(R.id.edittotal);
		edittype = (TextView) findViewById(R.id.edittype);
		//single = (TextView) findViewById(R.id.single);
		//Smallcount = (Button) findViewById(R.id.Smallcount);
		//Clear = (Button) findViewById(R.id.Clear);
		
		// Some parameters for tab
		String tab1_name="主食";
		String tab2_name="飲料";
	
		String tab1_spec="Main";
		String tab2_spec="Drink";
		
		/*** insert all data to db once start ***/
		//mMenuDbHelper.createAllProduct();
		/*** insert all data to db once end ***/
		
		mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		data = new Vector<RowData>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMMdd");  
	    String now = formatter.format(new Date());
		mTransactionCursor = mDbHelper.fetchTransaction(now);
		startManagingCursor(mTransactionCursor);
		int num_rows=mTransactionCursor.getCount();
		int i=0;
		mTransactionCursor.moveToFirst();
		for(i=0;i<num_rows;++i){
			System.out.println(num_rows);
			// KEY_XXXX 可參考 db_detail.txt
			//int tid = mTransactionCursor.getInt(mTransactionCursor.getColumnIndex(TransactionDbAdapter.KEY_ID));
			int rowid = mTransactionCursor.getInt(mTransactionCursor.getColumnIndex(TransactionDbAdapter.KEY_ID));
			String itemlist = mTransactionCursor.getString(mTransactionCursor.getColumnIndex(TransactionDbAdapter.KEY_ITEMLIST));
			//int price = mTransactionCursor.getInt(mTransactionCursor.getColumnIndex(TransactionDbAdapter.KEY_PRICE));
			String price = mTransactionCursor.getString(mTransactionCursor.getColumnIndex(TransactionDbAdapter.KEY_PRICE));
			String time = mTransactionCursor.getString(mTransactionCursor.getColumnIndex(TransactionDbAdapter.KEY_TIME));
			System.out.println(itemlist);
			System.out.println(price);
			System.out.println("jdddjjjd");
			try {
			 	rd = new RowData(rowid,price,price,itemlist);
			} catch (ParseException e) {
			   	e.printStackTrace();
			}
			data.add(0,rd);	
			guestnum=rowid;
			mTransactionCursor.moveToNext();
		}
		mTransactionCursor.close();
		
		System.out.println("777777777777777777");
		/*for(int i=0;i<guestnum+1;i++){
		try {
		 	rd = new RowData(i,title[i],detail[i],total[i]);
		} catch (ParseException e) {
		   	e.printStackTrace();
		}
		   data.add(0,rd);
		}*/
		CustomAdapter adapter = new CustomAdapter(this, R.layout.list,R.id.title, data);
		order.setAdapter(adapter);
		order.setTextFilterEnabled(true);
		// The activity TabHost
		TabHost tabHost = getTabHost();	    
		// Resusable TabSpec for each tab
		TabHost.TabSpec spec;	  	 	    
		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost.newTabSpec(tab1_spec).setIndicator(tab1_name,
				res.getDrawable(R.drawable.ic_tab_hamburger))
				.setContent(R.id.hamburgerLayout);
		tabHost.addTab(spec);
		
		// Create drink tab
		spec = tabHost.newTabSpec(tab2_spec).setIndicator(tab2_name,
				res.getDrawable(R.drawable.ic_tab_drinks))
				.setContent(R.id.drinksLayout);
		tabHost.addTab(spec);
		
		tabHost.setCurrentTab(0);
		
		
		In.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               				
							System.out.println("111111111");
							edittype.setText("內用");
							checkoutflag=1;
							System.out.println(guestnum);
							RowData rd = new RowData(guestnum,"("+edittype.getText().toString()+") 總計:"+" "+edittotal.getText().toString() , edittotal.getText().toString() , editmeal.getText().toString());
							data.add(0,rd);
							SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMMdd");  
						    String now = formatter.format(new Date());
							mDbHelper.createTransaction(editmeal.getText().toString(),"("+edittype.getText().toString()+") 總計:"+" "+edittotal.getText().toString(), now);
							CustomAdapter adapter2 = new CustomAdapter(Menu.this, R.layout.list,R.id.title, data);
							order.setAdapter(adapter2);								
							order.setTextFilterEnabled(true);
							checkoutflag=0;
							guestnum++;
							//////////
							edittype.setText("");
							editmeal.setText("");
							edittotal.setText("0"); 
							order_cost="0";
							order_list="";
							////////////

					}					
				}
		);
		Out.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               				
							System.out.println("111111111");
							edittype.setText("外帶");
							System.out.println(guestnum);
							RowData rd = new RowData(guestnum,"("+edittype.getText().toString()+") 總計:"+" "+edittotal.getText().toString() , edittotal.getText().toString() , editmeal.getText().toString());
							data.add(0,rd);
							SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMMdd");  
						    String now = formatter.format(new Date());
							mDbHelper.createTransaction(editmeal.getText().toString(),"("+edittype.getText().toString()+") 總計:"+" "+edittotal.getText().toString(), now);
							CustomAdapter adapter2 = new CustomAdapter(Menu.this, R.layout.list,R.id.title, data);
							order.setAdapter(adapter2);								
							order.setTextFilterEnabled(true);
							checkoutflag=0;
							guestnum++;
							//////////
							edittype.setText("");
							editmeal.setText("");
							edittotal.setText("0"); 
							order_cost="0";
							order_list="";
							////////////			
					}
				}
		); 
		CheckClear.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {						
						edittype.setText("");
						editmeal.setText(order_list_backup);
						edittotal.setText(order_cost_backup); 
						
					}
				}
		); 									
		/*Smallcount.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               			
						order_list=order_list +" "+ single.getText()+"x"+count;
						//newitem= Integer.parseInt(getResources().getString(R.string.cost_50));
						cost = Integer.parseInt(order_cost);
						newitem= counter*Integer.parseInt(count);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);	
						number.setSelection(0);

						
					}					
				}
		);
		Clear.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               			
						single.setText("");
						counter=0;
						number.setSelection(0);
					}					
				}
		);*/
		maindish();
		drinks();
	}

	// hamburger page
	public void maindish(){
		Button SpicyChicken,KaraChicken,Beef,PorkStack,Chicken,PorkFillet,ChickenBar,
		   	   Dragon,DoubleShi,Tuna,Cheese,Vegetable,Pork,Ham,Bacon,MacChicken,Egg,
		       HotSpicyChicken,PorkFloss,Corn,Original,Peanut,Chocolate,All,EggBurger,CheeseCake,WestPancake,ThickToast,
		       toast,Pancake,HotPancake,Croissants,CreamSu,Cream,Garlic,GrapeMilk,DainMy,GuaBao,EggToast,GuKau,Spaghetti,
		       PaperNoodle,MushroomNoodle,FriedRadish,FriedDumpling,HotDog,HashBrown,FrenchFries;
		
		//複合式前按鈕
		SpicyChicken = (Button) findViewById(R.id.SpicyChicken);
		KaraChicken = (Button) findViewById(R.id.KaraChicken);Beef = (Button) findViewById(R.id.Beef);
		PorkStack = (Button) findViewById(R.id.PorkStack);Chicken = (Button) findViewById(R.id.Chicken);
		PorkFillet = (Button) findViewById(R.id.PorkFillet);ChickenBar = (Button) findViewById(R.id.ChickenBar);	
		Dragon = (Button) findViewById(R.id.Dragon);
		DoubleShi = (Button) findViewById(R.id.DoubleShi);Tuna = (Button) findViewById(R.id.Tuna);
		Cheese = (Button) findViewById(R.id.Cheese);Vegetable = (Button) findViewById(R.id.Vegetable);
		Pork = (Button) findViewById(R.id.Pork);Ham = (Button) findViewById(R.id.Ham);
		Bacon = (Button) findViewById(R.id.Bacon);MacChicken = (Button) findViewById(R.id.MacChicken);
		HotSpicyChicken = (Button) findViewById(R.id.HotSpicyChicken);
		PorkFloss = (Button) findViewById(R.id.PorkFloss);Corn = (Button) findViewById(R.id.Corn);
		Peanut = (Button) findViewById(R.id.Peanut);
		Chocolate = (Button) findViewById(R.id.Chocolate);All = (Button) findViewById(R.id.All);
		CreamSu = (Button) findViewById(R.id.CreamSu);Cream = (Button) findViewById(R.id.Cream);
		GrapeMilk = (Button) findViewById(R.id.GrapeMilk);Garlic = (Button) findViewById(R.id.Garlic);
		//////////////
		
		//more
		
		//////////////
		//複合式後按鈕
		EggBurger = (Button) findViewById(R.id.EggBurger);toast = (Button) findViewById(R.id.Toast);
		Pancake = (Button) findViewById(R.id.Pancake);HotPancake = (Button) findViewById(R.id.HotPancake);
		Croissants = (Button) findViewById(R.id.Croissants);DainMy = (Button) findViewById(R.id.DainMy);			
		GuaBao = (Button) findViewById(R.id.GuaBao);EggToast = (Button) findViewById(R.id.EggToast);		
		GuKau = (Button) findViewById(R.id.GuKau);CheeseCake = (Button) findViewById(R.id.CheeseCake);		
		WestPancake = (Button) findViewById(R.id.WestPancake);ThickToast = (Button) findViewById(R.id.ThickToast);
		//單品餐點
		Egg = (Button) findViewById(R.id.Egg);Spaghetti = (Button) findViewById(R.id.Spaghetti); 
	    PaperNoodle = (Button) findViewById(R.id.PaperNoodle);MushroomNoodle = (Button) findViewById(R.id.MushroomNoodle);
	    FriedRadish = (Button) findViewById(R.id.FriedRadish);FriedDumpling = (Button) findViewById(R.id.FriedDumpling);
	    HotDog = (Button) findViewById(R.id.HotDog);HashBrown = (Button) findViewById(R.id.HashBrown);
	    FrenchFries = (Button) findViewById(R.id.FrenchFries);
	    //////////////
		
		//more
		
		//////////////
	    
	    SpicyChicken.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               			
						frontcode = 0x01ffff;										
					}					
				}
		);
		KaraChicken.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						frontcode = 0x02ffff;
											
					}					
				}
		);
		Beef.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						frontcode = 0x03ffff;
											
					}					
				}
		);
		PorkStack.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub						
						frontcode = 0x04ffff;
											
					}					
				}
		);
		
		Chicken.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						frontcode = 0x05ffff;
											
					}					
				}
		);
		PorkFillet.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						frontcode = 0x06ffff;
												
					}					
				}
		);
		ChickenBar.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						frontcode = 0x07ffff;
											
					}					
				}
		);
		Dragon.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						frontcode = 0x08ffff;
											
					}					
				}
		);
		DoubleShi.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						frontcode = 0x09ffff;
											
					}					
				}
		);
		Tuna.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						frontcode = 0x0affff;
											
					}					
				}
		);
		
		
		/*
		CreamSu.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               			
						int backcode = 0xffff0006;
						int code = frontcode & backcode;
						//single.setText(follow+getString(R.string.Croissants));												
					}					
				}
		);
		Cream.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               			
						int backcode = 0xffff0007;
						int code = frontcode & backcode;
						//single.setText(follow+getString(R.string.Croissants));												
					}					
				}
		);
		Garlic.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               			
						int backcode = 0xffff0008;
						int code = frontcode & backcode;
						//single.setText(follow+getString(R.string.Croissants));												
					}					
				}
		);
		GrapeMilk.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               			
						int backcode = 0xffff0009;
						int code = frontcode & backcode;
						//single.setText(follow+getString(R.string.Croissants));												
					}					
				}
		);
		
		*/
		////////////////////////////////////////
		
		//more menu
		
		////////////////////////////////////////
		GuKau.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               							
						int backcode = 0xffff0001;
						int code = frontcode & backcode;
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 1);
						startManagingCursor(mMenuCursor);						
						int num_rows=mMenuCursor.getCount();
						if(num_rows>0){
						mMenuCursor.moveToFirst();
							int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
							String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
							///rrr
							System.out.println(price+" "+name);	
							order_list_backup=order_list;
							order_cost_backup=order_cost;
							order_list=order_list +" "+name;							
							cost = Integer.parseInt(order_cost);
							newitem= price;
							order_cost = Integer.toString(cost+newitem);
							editmeal.setText(order_list);
							edittotal.setText(order_cost);
							
							System.out.println(order_list+":"+order_cost);
						}
						else{
							Toast.makeText(getApplicationContext(), "查無此商品",Toast.LENGTH_LONG).show();
							frontcode = 0;
						}
					}					
				}
		);
		GuaBao.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               							
						int backcode = 0xffff0002;
						int code = frontcode & backcode;
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 1);
						startManagingCursor(mMenuCursor);						
						int num_rows=mMenuCursor.getCount();
						if(num_rows>0){
						mMenuCursor.moveToFirst();
							int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
							String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
							///rrr
							System.out.println(price+" "+name);
							order_list_backup=order_list;
							order_cost_backup=order_cost;
							order_list=order_list +" "+name;							
							cost = Integer.parseInt(order_cost);
							newitem= price;
							order_cost = Integer.toString(cost+newitem);
							editmeal.setText(order_list);
							edittotal.setText(order_cost);
							
							System.out.println(order_list+":"+order_cost);
						}
						else{
							Toast.makeText(getApplicationContext(), "查無此商品",Toast.LENGTH_LONG).show();
							frontcode = 0;
						}
					}					
				}
		);
		EggBurger.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               							
						int backcode = 0xffff0003;
						int code = frontcode & backcode;
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 1);
						startManagingCursor(mMenuCursor);						
						int num_rows=mMenuCursor.getCount();
						if(num_rows>0){
						mMenuCursor.moveToFirst();
							int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
							String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
							///rrr
							System.out.println(price+" "+name);
							order_list_backup=order_list;
							order_cost_backup=order_cost;
							order_list=order_list +" "+name;							
							cost = Integer.parseInt(order_cost);
							newitem= price;
							order_cost = Integer.toString(cost+newitem);
							editmeal.setText(order_list);
							edittotal.setText(order_cost);
							
							System.out.println(order_list+":"+order_cost);
						}
						else{
							Toast.makeText(getApplicationContext(), "查無此商品",Toast.LENGTH_LONG).show();
							frontcode = 0;
						}
					}					
				}
		);	
		Pancake.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub  
						int backcode = 0xffff0004;
						int code = frontcode & backcode;
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 1);
						startManagingCursor(mMenuCursor);						
						int num_rows=mMenuCursor.getCount();
						if(num_rows>0){
						mMenuCursor.moveToFirst();
							int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
							String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
							System.out.println(price+" "+name);	
							order_list_backup=order_list;
							order_cost_backup=order_cost;
							order_list=order_list +" "+name;							
							cost = Integer.parseInt(order_cost);
							newitem= price;
							order_cost = Integer.toString(cost+newitem);
							editmeal.setText(order_list);
							edittotal.setText(order_cost);
							
							System.out.println(order_list+":"+order_cost);
						}
						else{
							Toast.makeText(getApplicationContext(), "查無此商品",Toast.LENGTH_LONG).show();
							frontcode = 0;
						}										
					}					
				}
		);
		Croissants.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               			
						int backcode = 0xffff0005;
						int code = frontcode & backcode;
						//single.setText(follow+getString(R.string.Croissants));
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 1);
						startManagingCursor(mMenuCursor);						
						int num_rows=mMenuCursor.getCount();
						if(num_rows>0){
						mMenuCursor.moveToFirst();
							int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
							String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
							System.out.println(price+" "+name);	
							order_list_backup=order_list;
							order_cost_backup=order_cost;
							order_list=order_list +" "+name;							
							cost = Integer.parseInt(order_cost);
							newitem= price;
							order_cost = Integer.toString(cost+newitem);
							editmeal.setText(order_list);
							edittotal.setText(order_cost);
							
							System.out.println(order_list+":"+order_cost);
						}
						else{
							Toast.makeText(getApplicationContext(), "查無此商品",Toast.LENGTH_LONG).show();
							frontcode = 0;
						}
					}					
				}
		);
		HotPancake.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               			
						int backcode = 0xffff0006;
						int code = frontcode & backcode;
						//single.setText(follow+getString(R.string.HotPancake));
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 1);
						startManagingCursor(mMenuCursor);						
						int num_rows=mMenuCursor.getCount();
						if(num_rows>0){
						mMenuCursor.moveToFirst();
							int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
							String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
							System.out.println(price+" "+name);	
							order_list_backup=order_list;
							order_cost_backup=order_cost;
							order_list=order_list +" "+name;							
							cost = Integer.parseInt(order_cost);
							newitem= price;
							order_cost = Integer.toString(cost+newitem);
							editmeal.setText(order_list);
							edittotal.setText(order_cost);
							
							System.out.println(order_list+":"+order_cost);
						}
						else{
							Toast.makeText(getApplicationContext(), "查無此商品",Toast.LENGTH_LONG).show();
							frontcode = 0;
						}
					}					
				}
		);
		EggToast.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               			
						int backcode = 0xffff0007;
						int code = frontcode & backcode;
						//single.setText(follow+getString(R.string.Croissants));
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 1);
						startManagingCursor(mMenuCursor);						
						int num_rows=mMenuCursor.getCount();
						if(num_rows>0){
						mMenuCursor.moveToFirst();
							int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
							String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
							System.out.println(price+" "+name);	
							order_list_backup=order_list;
							order_cost_backup=order_cost;
							order_list=order_list +" "+name;							
							cost = Integer.parseInt(order_cost);
							newitem= price;
							order_cost = Integer.toString(cost+newitem);
							editmeal.setText(order_list);
							edittotal.setText(order_cost);
							
							System.out.println(order_list+":"+order_cost);
						}
						else{
							Toast.makeText(getApplicationContext(), "查無此商品",Toast.LENGTH_LONG).show();
							frontcode = 0;
						}
					}					
				}
		);
		CheeseCake.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               			
						int backcode = 0xffff0008;
						int code = frontcode & backcode;
						//single.setText(follow+getString(R.string.Croissants));
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 1);
						startManagingCursor(mMenuCursor);						
						int num_rows=mMenuCursor.getCount();
						if(num_rows>0){
						mMenuCursor.moveToFirst();
							int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
							String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
							System.out.println(price+" "+name);	
							order_list_backup=order_list;
							order_cost_backup=order_cost;
							order_list=order_list +" "+name;							
							cost = Integer.parseInt(order_cost);
							newitem= price;
							order_cost = Integer.toString(cost+newitem);
							editmeal.setText(order_list);
							edittotal.setText(order_cost);
							
							System.out.println(order_list+":"+order_cost);
						}
						else{
							Toast.makeText(getApplicationContext(), "查無此商品",Toast.LENGTH_LONG).show();
							frontcode = 0;
						}
					}					
				}
		);
		WestPancake.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               			
						int backcode = 0xffff0009;
						int code = frontcode & backcode;
						//single.setText(follow+getString(R.string.Croissants));
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 1);
						startManagingCursor(mMenuCursor);						
						int num_rows=mMenuCursor.getCount();
						if(num_rows>0){
						mMenuCursor.moveToFirst();
							int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
							String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
							System.out.println(price+" "+name);	
							order_list_backup=order_list;
							order_cost_backup=order_cost;
							order_list=order_list +" "+name;							
							cost = Integer.parseInt(order_cost);
							newitem= price;
							order_cost = Integer.toString(cost+newitem);
							editmeal.setText(order_list);
							edittotal.setText(order_cost);
					
							System.out.println(order_list+":"+order_cost);
						}
						else{
							Toast.makeText(getApplicationContext(), "查無此商品",Toast.LENGTH_LONG).show();
							frontcode = 0;
						}
					}					
				}
		);		
		toast.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               			
						int backcode = 0xffff000a;
						int code = frontcode & backcode;
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 1);
						startManagingCursor(mMenuCursor);						
						int num_rows=mMenuCursor.getCount();
						if(num_rows>0){
						mMenuCursor.moveToFirst();
							int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
							String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
							System.out.println(price+" "+name);	
							order_list_backup=order_list;
							order_cost_backup=order_cost;
							order_list=order_list +" "+name;							
							cost = Integer.parseInt(order_cost);
							newitem= price;
							order_cost = Integer.toString(cost+newitem);
							editmeal.setText(order_list);
							edittotal.setText(order_cost);
							
							System.out.println(order_list+":"+order_cost);
						}
						else{
							Toast.makeText(getApplicationContext(), "查無此商品",Toast.LENGTH_LONG).show();
							frontcode = 0;
						}												
					}					
				}
		);
		ThickToast.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               			
						int backcode = 0xffff000b;
						int code = frontcode & backcode;
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 1);
						startManagingCursor(mMenuCursor);						
						int num_rows=mMenuCursor.getCount();
						if(num_rows>0){
						mMenuCursor.moveToFirst();
							int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
							String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
							System.out.println(price+" "+name);	
							order_list_backup=order_list;
							order_cost_backup=order_cost;
							order_list=order_list +" "+name;							
							cost = Integer.parseInt(order_cost);
							newitem= price;
							order_cost = Integer.toString(cost+newitem);
							editmeal.setText(order_list);
							edittotal.setText(order_cost);
							
							System.out.println(order_list+":"+order_cost);
						}
						else{
							Toast.makeText(getApplicationContext(), "查無此商品",Toast.LENGTH_LONG).show();
							frontcode = 0;
						}												
					}					
				}
		);				
		DainMy.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               			
						int backcode = 0xffff000c;
						int code = frontcode & backcode;
						//single.setText(follow+getString(R.string.Croissants));
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 1);
						startManagingCursor(mMenuCursor);						
						int num_rows=mMenuCursor.getCount();
						if(num_rows>0){
						mMenuCursor.moveToFirst();
							int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
							String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
							System.out.println(price+" "+name);	
							order_list_backup=order_list;
							order_cost_backup=order_cost;
							order_list=order_list +" "+name;							
							cost = Integer.parseInt(order_cost);
							newitem= price;
							order_cost = Integer.toString(cost+newitem);
							editmeal.setText(order_list);
							edittotal.setText(order_cost);
							
							System.out.println(order_list+":"+order_cost);
						}
						else{
							Toast.makeText(getApplicationContext(), "查無此商品",Toast.LENGTH_LONG).show();
							frontcode = 0;
						}
					}					
				}
		);
		Egg.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               									
						int code = 0x00000001;
						//single.setText(follow+getString(R.string.Croissants));
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 1);
						startManagingCursor(mMenuCursor);						
						mMenuCursor.moveToFirst();
						int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
						String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
						System.out.println(price+" "+name);	
						order_list_backup=order_list;
						order_cost_backup=order_cost;
						order_list=order_list +" "+name;							
						cost = Integer.parseInt(order_cost);
						newitem= price;
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);							
						System.out.println(order_list+":"+order_cost);						
					}					
				}
		);
		Spaghetti.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               									
						int code = 0x00000002;
						//single.setText(follow+getString(R.string.Croissants));
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 1);
						startManagingCursor(mMenuCursor);						
						mMenuCursor.moveToFirst();
						int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
						String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
						System.out.println(price+" "+name);	
						order_list_backup=order_list;
						order_cost_backup=order_cost;
						order_list=order_list +" "+name;							
						cost = Integer.parseInt(order_cost);
						newitem= price;
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);							
						System.out.println(order_list+":"+order_cost);						
					}					
				}
		);
	    PaperNoodle.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               									
						int code = 0x00000003;
						//single.setText(follow+getString(R.string.Croissants));
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 1);
						startManagingCursor(mMenuCursor);						
						mMenuCursor.moveToFirst();
						int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
						String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
						System.out.println(price+" "+name);	
						order_list_backup=order_list;
						order_cost_backup=order_cost;
						order_list=order_list +" "+name;							
						cost = Integer.parseInt(order_cost);
						newitem= price;
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);							
						System.out.println(order_list+":"+order_cost);						
					}					
				}
		);
	    MushroomNoodle.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               									
						int code = 0x00000004;
						//single.setText(follow+getString(R.string.Croissants));
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 1);
						startManagingCursor(mMenuCursor);						
						mMenuCursor.moveToFirst();
						int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
						String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
						System.out.println(price+" "+name);	
						order_list_backup=order_list;
						order_cost_backup=order_cost;
						order_list=order_list +" "+name;							
						cost = Integer.parseInt(order_cost);
						newitem= price;
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);							
						System.out.println(order_list+":"+order_cost);						
					}					
				}
		);
	    FriedRadish.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               									
						int code = 0x00000005;
						//single.setText(follow+getString(R.string.Croissants));
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 1);
						startManagingCursor(mMenuCursor);						
						mMenuCursor.moveToFirst();
						int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
						String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
						System.out.println(price+" "+name);	
						order_list_backup=order_list;
						order_cost_backup=order_cost;
						order_list=order_list +" "+name;							
						cost = Integer.parseInt(order_cost);
						newitem= price;
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);							
						System.out.println(order_list+":"+order_cost);						
					}					
				}
		);
	    FriedDumpling.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               									
						int code = 0x00000006;
						//single.setText(follow+getString(R.string.Croissants));
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 1);
						startManagingCursor(mMenuCursor);						
						mMenuCursor.moveToFirst();
						int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
						String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
						System.out.println(price+" "+name);	
						order_list_backup=order_list;
						order_cost_backup=order_cost;
						order_list=order_list +" "+name;							
						cost = Integer.parseInt(order_cost);
						newitem= price;
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);							
						System.out.println(order_list+":"+order_cost);						
					}					
				}
		);
	    HotDog.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               									
						int code = 0x00000007;
						//single.setText(follow+getString(R.string.Croissants));
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 1);
						startManagingCursor(mMenuCursor);						
						mMenuCursor.moveToFirst();
						int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
						String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
						System.out.println(price+" "+name);	
						order_list_backup=order_list;
						order_cost_backup=order_cost;
						order_list=order_list +" "+name;							
						cost = Integer.parseInt(order_cost);
						newitem= price;
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);							
						System.out.println(order_list+":"+order_cost);						
					}					
				}
		);
	    HashBrown.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               									
						int code = 0x00000008;
						//single.setText(follow+getString(R.string.Croissants));
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 1);
						startManagingCursor(mMenuCursor);						
						mMenuCursor.moveToFirst();
						int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
						String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
						System.out.println(price+" "+name);	
						order_list_backup=order_list;
						order_cost_backup=order_cost;
						order_list=order_list +" "+name;							
						cost = Integer.parseInt(order_cost);
						newitem= price;
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);							
						System.out.println(order_list+":"+order_cost);						
					}					
				}
		);
	    FrenchFries.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               									
						int code = 0x00000009;
						//single.setText(follow+getString(R.string.Croissants));
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 1);
						startManagingCursor(mMenuCursor);						
						mMenuCursor.moveToFirst();
						int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
						String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
						System.out.println(price+" "+name);	
						order_list_backup=order_list;
						order_cost_backup=order_cost;
						order_list=order_list +" "+name;							
						cost = Integer.parseInt(order_cost);
						newitem= price;
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);							
						System.out.println(order_list+":"+order_cost);						
					}					
				}
		);
		
	}

	// drinks page
	public void drinks(){
		Button BigPearlBarley,MiddlePearlBarley,SmallPearlBarley,BigSoyBean,MiddleSoyBean,SmallSoyBean,BidRice,
	    MiddleRice,SmallRice,Milk,Hot,Warm,Ice,NoIce,NoSuger,LowSuger;
		
		BigPearlBarley = (Button) findViewById(R.id.BigPearlBarley);
		MiddlePearlBarley = (Button) findViewById(R.id.MiddlePearlBarley);
	    SmallPearlBarley = (Button) findViewById(R.id.SmallPearlBarley);
	    BigSoyBean = (Button) findViewById(R.id.BigSoyBean);
	    MiddleSoyBean = (Button) findViewById(R.id.MiddleSoyBean);
	    SmallSoyBean = (Button) findViewById(R.id.SmallSoyBean);
	    BidRice = (Button) findViewById(R.id.BidRice);
	    MiddleRice = (Button) findViewById(R.id.MiddleRice);
	    SmallRice = (Button) findViewById(R.id.SmallRice);
	    Milk = (Button) findViewById(R.id.Milk);
	    
	    Ice = (Button) findViewById(R.id.Ice);
	    Hot = (Button) findViewById(R.id.Hot);
		Warm = (Button) findViewById(R.id.Warm);
		NoIce = (Button) findViewById(R.id.NoIce);
		NoSuger = (Button) findViewById(R.id.NoSuger);
		LowSuger = (Button) findViewById(R.id.LowSuger);
	    
	    
	    BigPearlBarley.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               									
						int code = 0x00000001;
						//single.setText(follow+getString(R.string.Croissants));
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 2);
						startManagingCursor(mMenuCursor);						
						mMenuCursor.moveToFirst();
						int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
						String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
						System.out.println(price+" "+name);	
						order_list_backup=order_list;
						order_cost_backup=order_cost;
						order_list=order_list +" "+name;							
						cost = Integer.parseInt(order_cost);
						newitem= price;
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);							
						System.out.println(order_list+":"+order_cost);						
					}					
				}
		);
	    MiddlePearlBarley.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               									
						int code = 0x00000002;
						//single.setText(follow+getString(R.string.Croissants));
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 2);
						startManagingCursor(mMenuCursor);						
						mMenuCursor.moveToFirst();
						int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
						String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
						System.out.println(price+" "+name);	
						order_list_backup=order_list;
						order_cost_backup=order_cost;
						order_list=order_list +" "+name;							
						cost = Integer.parseInt(order_cost);
						newitem= price;
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);							
						System.out.println(order_list+":"+order_cost);						
					}					
				}
		);
	    SmallPearlBarley.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               									
						int code = 0x00000003;
						//single.setText(follow+getString(R.string.Croissants));
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 2);
						startManagingCursor(mMenuCursor);						
						mMenuCursor.moveToFirst();
						int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
						String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
						System.out.println(price+" "+name);	
						order_list_backup=order_list;
						order_cost_backup=order_cost;
						order_list=order_list +" "+name;							
						cost = Integer.parseInt(order_cost);
						newitem= price;
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);							
						System.out.println(order_list+":"+order_cost);						
					}					
				}
		);
	    BigSoyBean.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               									
						int code = 0x00000004;
						//single.setText(follow+getString(R.string.Croissants));
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 2);
						startManagingCursor(mMenuCursor);						
						mMenuCursor.moveToFirst();
						int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
						String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
						System.out.println(price+" "+name);	
						order_list_backup=order_list;
						order_cost_backup=order_cost;
						order_list=order_list +" "+name;							
						cost = Integer.parseInt(order_cost);
						newitem= price;
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);							
						System.out.println(order_list+":"+order_cost);						
					}					
				}
		);
	    MiddleSoyBean.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               									
						int code = 0x00000005;
						//single.setText(follow+getString(R.string.Croissants));
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 2);
						startManagingCursor(mMenuCursor);						
						mMenuCursor.moveToFirst();
						int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
						String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
						System.out.println(price+" "+name);	
						order_list_backup=order_list;
						order_cost_backup=order_cost;
						order_list=order_list +" "+name;							
						cost = Integer.parseInt(order_cost);
						newitem= price;
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);							
						System.out.println(order_list+":"+order_cost);						
					}					
				}
		);
	    SmallSoyBean.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               									
						int code = 0x00000006;
						//single.setText(follow+getString(R.string.Croissants));
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 2);
						startManagingCursor(mMenuCursor);						
						mMenuCursor.moveToFirst();
						int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
						String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
						System.out.println(price+" "+name);	
						order_list_backup=order_list;
						order_cost_backup=order_cost;
						order_list=order_list +" "+name;							
						cost = Integer.parseInt(order_cost);
						newitem= price;
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);							
						System.out.println(order_list+":"+order_cost);						
					}					
				}
		);
	    BidRice.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               									
						int code = 0x00000007;
						//single.setText(follow+getString(R.string.Croissants));
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 2);
						startManagingCursor(mMenuCursor);						
						mMenuCursor.moveToFirst();
						int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
						String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
						System.out.println(price+" "+name);	
						order_list_backup=order_list;
						order_cost_backup=order_cost;
						order_list=order_list +" "+name;							
						cost = Integer.parseInt(order_cost);
						newitem= price;
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);							
						System.out.println(order_list+":"+order_cost);						
					}					
				}
		);
	    MiddleRice.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               									
						int code = 0x00000008;
						//single.setText(follow+getString(R.string.Croissants));
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 2);
						startManagingCursor(mMenuCursor);						
						mMenuCursor.moveToFirst();
						int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
						String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
						System.out.println(price+" "+name);	
						order_list_backup=order_list;
						order_cost_backup=order_cost;
						order_list=order_list +" "+name;							
						cost = Integer.parseInt(order_cost);
						newitem= price;
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);							
						System.out.println(order_list+":"+order_cost);						
					}					
				}
		);
	    SmallRice.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               									
						int code = 0x00000009;
						//single.setText(follow+getString(R.string.Croissants));
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 2);
						startManagingCursor(mMenuCursor);						
						mMenuCursor.moveToFirst();
						int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
						String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
						System.out.println(price+" "+name);	
						order_list_backup=order_list;
						order_cost_backup=order_cost;
						order_list=order_list +" "+name;							
						cost = Integer.parseInt(order_cost);
						newitem= price;
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);							
						System.out.println(order_list+":"+order_cost);						
					}					
				}
		);
	    Milk.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               									
						int code = 0x0000000a;
						//single.setText(follow+getString(R.string.Croissants));
						mMenuCursor=mMenuDbHelper.fetchProduct(code, 2);
						startManagingCursor(mMenuCursor);						
						mMenuCursor.moveToFirst();
						int price = mMenuCursor.getInt(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_PRICE));
						String name = mMenuCursor.getString(mMenuCursor.getColumnIndex(MenuDbAdapter.KEY_NAME));
						System.out.println(price+" "+name);	
						order_list_backup=order_list;
						order_cost_backup=order_cost;
						order_list=order_list +" "+name;							
						cost = Integer.parseInt(order_cost);
						newitem= price;
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);							
						System.out.println(order_list+":"+order_cost);						
					}					
				}
		);
	    Hot.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               									
						order_list=order_list +"-熱";
						editmeal.setText(order_list);
					}					
				}
		);
		Warm.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               									
						order_list=order_list +"-溫";
						editmeal.setText(order_list);
					}					
				}
		);
		Ice.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               									
						order_list=order_list +"-冰";
						editmeal.setText(order_list);
					}					
				}
		);
		NoIce.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               									
						order_list=order_list +"-去冰";
						editmeal.setText(order_list);
					}					
				}
		);
		NoSuger.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               									
						order_list=order_list +"-無糖";
						editmeal.setText(order_list);
					}					
				}
		);
		LowSuger.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               									
						order_list=order_list +"-少糖";
						editmeal.setText(order_list);
					}					
				}
		);
	}
	
	
}
