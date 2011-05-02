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
import java.util.List;
import java.util.Vector;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.TabActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ParseException;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
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
	Bundle bundle;
	Intent intent;
	TextView editmeal;
	TextView edittotal;
	TextView edittype;
	ListView order;
	String order_cost="0";
	String order_list="";
	int cost,newitem,guestnum=0,checkoutflag=0;
	Button In,Out,Checkout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);     
		setContentView(R.layout.fragment_textlayout);
		Resources res = getResources(); // Resource object to get Drawables		
		In = (Button) findViewById(R.id.in);
		Out = (Button) findViewById(R.id.out);
		Checkout = (Button) findViewById(R.id.Checkout);
		order = (ListView) findViewById(R.id.guestlistview);
		editmeal = (TextView) findViewById(R.id.editmeal);
		edittotal = (TextView) findViewById(R.id.edittotal);
		edittype = (TextView) findViewById(R.id.edittype);
		// Some parameters for tab
		String tab1_name="漢堡";
		String tab2_name="蛋餅";
		String tab3_name="飲料";
		String tab4_name="可頌堡";
		String tab5_name="全麥蛋燒餅";
		String tab6_name="現烤(蛋)吐司";
		String tab1_spec="Hamburger";
		String tab2_spec="Omelet";
		String tab3_spec="Drinks";
		String tab4_spec="Croissant";
		String tab5_spec="Shaobing";
		String tab6_spec="Toast";
		mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		data = new Vector<RowData>();
		for(int i=0;i<guestnum+1;i++){
		try {
		 	rd = new RowData(i,title[i],detail[i],total[i]);
		    } catch (ParseException e) {
		    	e.printStackTrace();
		   }
		   data.add(rd);
		}
		CustomAdapter adapter = new CustomAdapter(this, R.layout.list,R.id.title, data);
		order.setAdapter(adapter);
		order.setTextFilterEnabled(true);
		In.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               			
						if(checkoutflag==0){	
							System.out.println("111111111");
							edittype.setText("內用");
							/*title[guestnum]="內用";
							Vector<RowData> data;
							data = new Vector<RowData>();
							for(int i=0;i<guestnum+1;i++){
								try {
									RowData rd = new RowData(i,title[i],detail[i],total[i]);
								    } catch (ParseException e) {
								    	e.printStackTrace();
								   }
								   data.add(rd);
							}						
							CustomAdapter adapter1 = new CustomAdapter(Menu.this, R.layout.list,R.id.title, data);
							order.setAdapter(adapter1);								
							order.setTextFilterEnabled(true);*/
							checkoutflag=1;
							System.out.println(guestnum);
							//System.out.println(title[guestnum]);
						}
						
					}					
				}
		);
		Out.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub               			
						if(checkoutflag==0){	
							System.out.println("111111111");
							edittype.setText("外帶");
							/*Vector<RowData> data;
							data = new Vector<RowData>();
							title[guestnum]="外帶";
							for(int i=0;i<guestnum+1;i++){
								try {
									RowData rd = new RowData(i,title[i],detail[i],total[i]);
								    } catch (ParseException e) {
								    	e.printStackTrace();
								   }
								   data.add(rd);
							}							
							CustomAdapter adapter2 = new CustomAdapter(Menu.this, R.layout.list,R.id.title, data);
							order.setAdapter(adapter2);								
							order.setTextFilterEnabled(true);*/
							checkoutflag=1;
							System.out.println(guestnum);
							//System.out.println(title[guestnum]);
						}
						
					}
				}
		); 
		Checkout.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						if(checkoutflag==1){						
						System.out.println("checkout");
						// TODO Auto-generated method stub               								
						RowData rd = new RowData(guestnum,"("+edittype.getText().toString()+") 總計:"+" "+edittotal.getText().toString() ,edittotal.getText().toString(),editmeal.getText().toString());
						data.add(rd);
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
				}
		); 									
		// The activity TabHost
		TabHost tabHost = getTabHost();	    
		// Resusable TabSpec for each tab
		TabHost.TabSpec spec;	  	 	    
		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost.newTabSpec(tab1_spec).setIndicator(tab1_name,
				res.getDrawable(R.drawable.ic_tab_hamburger))
				.setContent(R.id.hamburgerLayout);
		tabHost.addTab(spec);	  

		// Create omelet tab
		spec = tabHost.newTabSpec(tab2_spec).setIndicator(tab2_name,
				res.getDrawable(R.drawable.ic_tab_omelet))
				.setContent(R.id.omeletLayout);
		tabHost.addTab(spec);    

		// Create drinks tab	    
		spec = tabHost.newTabSpec(tab3_spec).setIndicator(tab3_name,
				res.getDrawable(R.drawable.ic_tab_drinks))
				.setContent(R.id.drinksLayout);
		tabHost.addTab(spec);

		// Create croissant tab	    
		spec = tabHost.newTabSpec(tab4_spec).setIndicator(tab4_name,
				res.getDrawable(R.drawable.ic_tab_croissant))
				.setContent(R.id.croissantLayout);
		tabHost.addTab(spec);

		// Create shaobing tab	    
		spec = tabHost.newTabSpec(tab5_spec).setIndicator(tab5_name,
				res.getDrawable(R.drawable.ic_tab_shaobing))
				.setContent(R.id.shaobingLayout);
		tabHost.addTab(spec);

		// Create toast tab	    
		spec = tabHost.newTabSpec(tab6_spec).setIndicator(tab6_name,
				res.getDrawable(R.drawable.ic_tab_toast))
				.setContent(R.id.toastLayout);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
		hamburger();
		drinks();
		omelet();
		croissant();
		shaobing();
		toast();
	}

	// hamburger page
	public void hamburger(){
		TextView textview;
		TextView textview2;       
		Button button_h_1;
		Button button_h_2;
		Button button_h_3;
		Button button_h_4;
		Button button_h_5;
		Button button_h_6;
		Button button_h_7;
		Button button_h_8;
		Button button_h_9;
		Button button_h_10;
		Button button_h_11;
		Button button_h_12;
		Button button_h_13;
		Button button_h_14;
		Button button_h_15;
		Button button_h_16;
		Button button_h_17;
		Button button_h_18;
		textview = (TextView) findViewById(R.id.textview);
		textview2 = (TextView) findViewById(R.id.textview2);          
		button_h_1 = (Button) findViewById(R.id.button_h_1);
		button_h_2 = (Button) findViewById(R.id.button_h_2);
		button_h_3 = (Button) findViewById(R.id.button_h_3);
		button_h_4 = (Button) findViewById(R.id.button_h_4);
		button_h_5 = (Button) findViewById(R.id.button_h_5);
		button_h_6 = (Button) findViewById(R.id.button_h_6);
		button_h_7 = (Button) findViewById(R.id.button_h_7);
		button_h_8 = (Button) findViewById(R.id.button_h_8);
		button_h_9 = (Button) findViewById(R.id.button_h_9);
		button_h_10 = (Button) findViewById(R.id.button_h_10);
		button_h_11 = (Button) findViewById(R.id.button_h_11);
		button_h_12 = (Button) findViewById(R.id.button_h_12);
		button_h_13 = (Button) findViewById(R.id.button_h_13);
		button_h_14 = (Button) findViewById(R.id.button_h_14);
		button_h_15 = (Button) findViewById(R.id.button_h_15);
		button_h_16 = (Button) findViewById(R.id.button_h_16);
		button_h_17 = (Button) findViewById(R.id.button_h_17);
		button_h_18 = (Button) findViewById(R.id.button_h_18);

		button_h_1.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.h_1);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_50));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);	
					}
				}
		);
		button_h_2.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.h_2);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_45));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);	
					}
				}
		);
		button_h_3.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.h_3);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_55));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_h_4.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.h_4);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_40));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_h_5.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.h_5);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_35));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_h_6.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.h_6);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_40));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_h_7.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.h_7);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_40));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_h_8.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.h_8);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_40));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_h_9.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.h_9);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_40));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_h_10.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.h_10);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_35));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_h_11.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.h_11);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_30));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);	
					}
				}
		);
		button_h_12.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.h_12);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_35));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);	
					}
				}
		);
		button_h_13.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.h_13);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_30));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_h_14.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.h_14);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_30));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_h_15.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.h_15);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_30));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_h_16.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.h_16);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_35));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_h_17.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.h_17);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_20));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_h_18.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.h_18);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_35));
						cost = Integer.parseInt(order_cost);
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
		TextView textview;
		TextView textview2;
		Button button_d_1;
		Button button_d_2;
		Button button_d_3;
		Button button_d_4;
		Button button_d_5;
		Button button_d_6;
		Button button_d_7;
		Button button_d_8;
		Button button_d_9;
		Button button_d_10;
		Button button_d_11;
		Button button_d_12;
		Button button_d_13;
		Button button_d_14;
		Button button_d_15;
		Button button_d_16;
		textview = (TextView) findViewById(R.id.textview);
		textview2 = (TextView) findViewById(R.id.textview2);

		button_d_1 = (Button) findViewById(R.id.button_d_1);
		button_d_2 = (Button) findViewById(R.id.button_d_2);
		button_d_3 = (Button) findViewById(R.id.button_d_3);
		button_d_4 = (Button) findViewById(R.id.button_d_4);
		button_d_5 = (Button) findViewById(R.id.button_d_5);
		button_d_6 = (Button) findViewById(R.id.button_d_6);
		button_d_7 = (Button) findViewById(R.id.button_d_7);
		button_d_8 = (Button) findViewById(R.id.button_d_8);
		button_d_9 = (Button) findViewById(R.id.button_d_9);
		button_d_10 = (Button) findViewById(R.id.button_d_10);
		button_d_11 = (Button) findViewById(R.id.button_d_11);
		button_d_12 = (Button) findViewById(R.id.button_d_12);
		button_d_13 = (Button) findViewById(R.id.button_d_13);
		button_d_14 = (Button) findViewById(R.id.button_d_14);
		button_d_15 = (Button) findViewById(R.id.button_d_15);
		button_d_16 = (Button) findViewById(R.id.button_d_16);
		button_d_1.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.d_1);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_40));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_d_2.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub	
						order_list=order_list + " "+ getResources().getString(R.string.d_2);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_25));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_d_3.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub	
						order_list=order_list + " "+ getResources().getString(R.string.d_3);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_20));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_d_4.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub	
						order_list=order_list + " "+ getResources().getString(R.string.d_4);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_20));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_d_5.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub	
						order_list=order_list + " "+ getResources().getString(R.string.d_5);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_20));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_d_6.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.d_6);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_20));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_d_7.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.d_7);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_20));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_d_8.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.d_8);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_20));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_d_9.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.d_9);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_25));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_d_10.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.d_10);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_25));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_d_11.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.d_11);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_30));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);	
					}
				}
		);
		button_d_12.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.d_12);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_25));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);	
					}
				}
		);
		button_d_13.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.d_13);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_20));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_d_14.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.d_14);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_20));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_d_15.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.d_15);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_25));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_d_16.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.d_16);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_20));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
	}

	// omelet page
	public void omelet(){
		TextView textview;
		TextView textview2;       
		Button button_o_1;
		Button button_o_2;
		Button button_o_3;
		Button button_o_4;
		Button button_o_5;
		Button button_o_6;
		Button button_o_7;
		Button button_o_8;
		Button button_o_9;
		Button button_o_10;
		Button button_o_11;
		Button button_o_12;
		Button button_o_13;
		Button button_o_14;
		Button button_o_15;
		Button button_o_16;
		Button button_o_17;
		Button button_o_18;
		textview = (TextView) findViewById(R.id.textview);
		textview2 = (TextView) findViewById(R.id.textview2);         
		button_o_1 = (Button) findViewById(R.id.button_o_1);
		button_o_2 = (Button) findViewById(R.id.button_o_2);
		button_o_3 = (Button) findViewById(R.id.button_o_3);
		button_o_4 = (Button) findViewById(R.id.button_o_4);
		button_o_5 = (Button) findViewById(R.id.button_o_5);
		button_o_6 = (Button) findViewById(R.id.button_o_6);
		button_o_7 = (Button) findViewById(R.id.button_o_7);
		button_o_8 = (Button) findViewById(R.id.button_o_8);
		button_o_9 = (Button) findViewById(R.id.button_o_9);
		button_o_10 = (Button) findViewById(R.id.button_o_10);
		button_o_11 = (Button) findViewById(R.id.button_o_11);
		button_o_12 = (Button) findViewById(R.id.button_o_12);
		button_o_13 = (Button) findViewById(R.id.button_o_13);
		button_o_14 = (Button) findViewById(R.id.button_o_14);
		button_o_15 = (Button) findViewById(R.id.button_o_15);
		button_o_16 = (Button) findViewById(R.id.button_o_16);
		button_o_17 = (Button) findViewById(R.id.button_o_17);
		button_o_18 = (Button) findViewById(R.id.button_o_18);
		button_o_1.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.o_1);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_50));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);	

					}
				}
		);
		button_o_2.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.o_2);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_45));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);	
					}
				}
		);
		button_o_3.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.o_3);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_55));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_o_4.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.o_4);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_40));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_o_5.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.o_5);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_35));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_o_6.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.o_6);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_40));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_o_7.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.o_7);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_40));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_o_8.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.o_8);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_40));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_o_9.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.o_9);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_40));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_o_10.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.o_10);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_30));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_o_11.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.o_11);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_25));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);	
					}
				}
		);
		button_o_12.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.o_12);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_30));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);	
					}
				}
		);
		button_o_13.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.o_13);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_30));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_o_14.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.o_14);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_25));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_o_15.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.o_15);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_30));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_o_16.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.o_16);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_25));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_o_17.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.o_17);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_25));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_o_18.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.o_18);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_20));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
	}

	// croissant page
	public void croissant(){
		TextView textview;
		TextView textview2;       
		Button button_c_1;
		Button button_c_2;
		Button button_c_3;
		Button button_c_4;
		Button button_c_5;
		Button button_c_6;
		Button button_c_7;
		Button button_c_8;
		Button button_c_9;
		Button button_c_10;
		Button button_c_11;
		Button button_c_12;
		Button button_c_13;
		Button button_c_14;
		Button button_c_15;
		Button button_c_16;
		Button button_c_17;
		Button button_c_18;
		textview = (TextView) findViewById(R.id.textview);
		textview2 = (TextView) findViewById(R.id.textview2);         
		button_c_1 = (Button) findViewById(R.id.button_c_1);
		button_c_2 = (Button) findViewById(R.id.button_c_2);
		button_c_3 = (Button) findViewById(R.id.button_c_3);
		button_c_4 = (Button) findViewById(R.id.button_c_4);
		button_c_5 = (Button) findViewById(R.id.button_c_5);
		button_c_6 = (Button) findViewById(R.id.button_c_6);
		button_c_7 = (Button) findViewById(R.id.button_c_7);
		button_c_8 = (Button) findViewById(R.id.button_c_8);
		button_c_9 = (Button) findViewById(R.id.button_c_9);
		button_c_10 = (Button) findViewById(R.id.button_c_10);
		button_c_11 = (Button) findViewById(R.id.button_c_11);
		button_c_12 = (Button) findViewById(R.id.button_c_12);
		button_c_13 = (Button) findViewById(R.id.button_c_13);
		button_c_14 = (Button) findViewById(R.id.button_c_14);
		button_c_15 = (Button) findViewById(R.id.button_c_15);
		button_c_16 = (Button) findViewById(R.id.button_c_16);
		button_c_17 = (Button) findViewById(R.id.button_c_17);
		button_c_18 = (Button) findViewById(R.id.button_c_18);
		button_c_1.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.c_1);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_55));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);	

					}
				}
		);
		button_c_2.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.c_2);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_50));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);	
					}
				}
		);
		button_c_3.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.c_3);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_60));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_c_4.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.c_4);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_45));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_c_5.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.c_5);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_40));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_c_6.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.c_6);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_45));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_c_7.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.c_7);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_45));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_c_8.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.c_8);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_45));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_c_9.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.c_9);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_45));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_c_10.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.c_10);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_40));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_c_11.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.c_11);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_35));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);	
					}
				}
		);
		button_c_12.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.c_12);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_35));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);	
					}
				}
		);
		button_c_13.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.c_13);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_35));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_c_14.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.c_14);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_35));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_c_15.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.c_15);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_35));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_c_16.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.c_16);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_35));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_c_17.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.c_17);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_25));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_c_18.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.c_18);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_25));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
	}

	// shaobing page
	public void shaobing(){
		TextView textview;
		TextView textview2;
		Button button_s_1;
		Button button_s_2;
		Button button_s_3;
		Button button_s_4;
		Button button_s_5;
		Button button_s_6;
		Button button_s_7;
		Button button_s_8;
		Button button_s_9;
		Button button_s_10;
		Button button_s_11;
		Button button_s_12;
		Button button_s_13;
		Button button_s_14;
		Button button_s_15;
		Button button_s_16;
		Button button_s_17;
		textview = (TextView) findViewById(R.id.textview);
		textview2 = (TextView) findViewById(R.id.textview2);

		button_s_1 = (Button) findViewById(R.id.button_s_1);
		button_s_2 = (Button) findViewById(R.id.button_s_2);
		button_s_3 = (Button) findViewById(R.id.button_s_3);
		button_s_4 = (Button) findViewById(R.id.button_s_4);
		button_s_5 = (Button) findViewById(R.id.button_s_5);
		button_s_6 = (Button) findViewById(R.id.button_s_6);
		button_s_7 = (Button) findViewById(R.id.button_s_7);
		button_s_8 = (Button) findViewById(R.id.button_s_8);
		button_s_9 = (Button) findViewById(R.id.button_s_9);
		button_s_10 = (Button) findViewById(R.id.button_s_10);
		button_s_11 = (Button) findViewById(R.id.button_s_11);
		button_s_12 = (Button) findViewById(R.id.button_s_12);
		button_s_13 = (Button) findViewById(R.id.button_s_13);
		button_s_14 = (Button) findViewById(R.id.button_s_14);
		button_s_15 = (Button) findViewById(R.id.button_s_15);
		button_s_16 = (Button) findViewById(R.id.button_s_16);
		button_s_17 = (Button) findViewById(R.id.button_s_17);
		button_s_1.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.s_1);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_55));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_s_2.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub	
						order_list=order_list + " "+ getResources().getString(R.string.s_2);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_50));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_s_3.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub	
						order_list=order_list + " "+ getResources().getString(R.string.s_3);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_60));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_s_4.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub	
						order_list=order_list + " "+ getResources().getString(R.string.s_4);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_45));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_s_5.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub	
						order_list=order_list + " "+ getResources().getString(R.string.s_5);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_40));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_s_6.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.s_6);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_45));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_s_7.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.s_7);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_45));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_s_8.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.s_8);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_45));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_s_9.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.s_9);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_45));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_s_10.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.s_10);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_40));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_s_11.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.s_11);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_35));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);	
					}
				}
		);
		button_s_12.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.s_12);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_35));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);	
					}
				}
		);
		button_s_13.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.s_13);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_35));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_s_14.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.s_14);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_35));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_s_15.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.s_15);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_35));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_s_16.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.s_16);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_35));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_s_17.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.s_17);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_30));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
	}

	// toast page
	public void toast(){
		TextView textview;
		TextView textview2;       
		Button button_t_1;
		Button button_t_2;
		Button button_t_3;
		Button button_t_4;
		Button button_t_5;
		Button button_t_6;
		Button button_t_7;
		Button button_t_8;
		Button button_t_9;
		Button button_t_10;
		Button button_t_11;
		Button button_t_12;
		Button button_t_13;
		Button button_t_14;
		Button button_t_15;
		Button button_t_16;
		Button button_t_17;
		Button button_t_18;
		textview = (TextView) findViewById(R.id.textview);
		textview2 = (TextView) findViewById(R.id.textview2);        
		button_t_1 = (Button) findViewById(R.id.button_t_1);
		button_t_2 = (Button) findViewById(R.id.button_t_2);
		button_t_3 = (Button) findViewById(R.id.button_t_3);
		button_t_4 = (Button) findViewById(R.id.button_t_4);
		button_t_5 = (Button) findViewById(R.id.button_t_5);
		button_t_6 = (Button) findViewById(R.id.button_t_6);
		button_t_7 = (Button) findViewById(R.id.button_t_7);
		button_t_8 = (Button) findViewById(R.id.button_t_8);
		button_t_9 = (Button) findViewById(R.id.button_t_9);
		button_t_10 = (Button) findViewById(R.id.button_t_10);
		button_t_11 = (Button) findViewById(R.id.button_t_11);
		button_t_12 = (Button) findViewById(R.id.button_t_12);
		button_t_13 = (Button) findViewById(R.id.button_t_13);
		button_t_14 = (Button) findViewById(R.id.button_t_14);
		button_t_15 = (Button) findViewById(R.id.button_t_15);
		button_t_16 = (Button) findViewById(R.id.button_t_16);
		button_t_17 = (Button) findViewById(R.id.button_t_17);
		button_t_18 = (Button) findViewById(R.id.button_t_18);
		button_t_1.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.t_1);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_50));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);	

					}
				}
		);
		button_t_2.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.t_2);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_45));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);	
					}
				}
		);
		button_t_3.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.t_3);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_55));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_t_4.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.t_4);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_40));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_t_5.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.t_5);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_35));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_t_6.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.t_6);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_40));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_t_7.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.t_7);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_40));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_t_8.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.t_8);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_40));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_t_9.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.t_9);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_40));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_t_10.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.t_10);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_30));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_t_11.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.t_11);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_25));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);	
					}
				}
		);
		button_t_12.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.t_12);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_30));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);	
					}
				}
		);
		button_t_13.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.t_13);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_30));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_t_14.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.t_14);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_25));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_t_15.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.t_15);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_30));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_t_16.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.t_16);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_25));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_t_17.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.t_17);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_20));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
		button_t_18.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						order_list=order_list + " "+ getResources().getString(R.string.t_18);
						newitem= Integer.parseInt(getResources().getString(R.string.cost_45));
						cost = Integer.parseInt(order_cost);
						order_cost = Integer.toString(cost+newitem);
						editmeal.setText(order_list);
						edittotal.setText(order_cost);
						System.out.println(order_list+":"+order_cost);
					}
				}
		);
	}
}
