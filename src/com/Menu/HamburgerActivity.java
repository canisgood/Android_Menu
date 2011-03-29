package com.Menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HamburgerActivity extends Activity {
    /** Called when the activity is first created. */
	
    int pos=-1;
    String order_cost;
    String order_list;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hamburger);
        
        TextView textview;
        TextView textview2;
        Button button_h_scef;
        Button button_h_cceb;
        Button button_h_tbcb;
        Button button_h_peb;
        Button button_h_ceb;
        
        textview = (TextView) findViewById(R.id.textview);
        textview2 = (TextView) findViewById(R.id.textview2);
        textview.setText("This is the Hamburger tab...");
        textview2.setText("What do you want to order?");
           
        button_h_scef = (Button) findViewById(R.id.button_h_scef);
        button_h_cceb = (Button) findViewById(R.id.button_h_cceb);
        button_h_tbcb = (Button) findViewById(R.id.button_h_tbcb);
        button_h_peb = (Button) findViewById(R.id.button_h_peb);
        button_h_ceb = (Button) findViewById(R.id.button_h_ceb);
        
        button_h_scef.setOnClickListener(
           	new Button.OnClickListener(){
           		@Override
           		public void onClick(View v) {
           			// TODO Auto-generated method stub
           			++pos;
           			order_list=getResources().getString(R.string.h_scef);
           			order_cost=getResources().getString(R.string.cost_h_scef);
           			System.out.println(order_list+":"+order_cost);
           			//order_cost[pos]=getResources().getString(R.string.cost_h_scef);
           			//order_list[pos]=getResources().getString(R.string.h_scef);
           			//System.out.println(order_list[pos]+":"+order_cost[pos]);
           			//textview1.setText(getResources().getString(R.string.h_scef));
           	        //textview2.setText(getResources().getString(R.string.cost_h_scef));
           		}
           	}
        );
        button_h_cceb.setOnClickListener(
               	new Button.OnClickListener(){
               		@Override
               		public void onClick(View v) {
               			// TODO Auto-generated method stub
               			++pos;
               			order_list=getResources().getString(R.string.h_cceb);
               			order_cost=getResources().getString(R.string.cost_h_cceb);
               			System.out.println(order_list+":"+order_cost);
               		}
               	}
            );
        button_h_tbcb.setOnClickListener(
               	new Button.OnClickListener(){
               		@Override
               		public void onClick(View v) {
               			// TODO Auto-generated method stub
               			++pos;
               			order_list=getResources().getString(R.string.h_tbcb);
               			order_cost=getResources().getString(R.string.cost_h_tbcb);
               			System.out.println(order_list+":"+order_cost);
               		}
               	}
            );
        button_h_peb.setOnClickListener(
               	new Button.OnClickListener(){
               		@Override
               		public void onClick(View v) {
               			// TODO Auto-generated method stub
               			++pos;
               			order_list=getResources().getString(R.string.h_peb);
               			order_cost=getResources().getString(R.string.cost_h_peb);
               			System.out.println(order_list+":"+order_cost);
               		}
               	}
            );
        button_h_ceb.setOnClickListener(
               	new Button.OnClickListener(){
               		@Override
               		public void onClick(View v) {
               			// TODO Auto-generated method stub
               			++pos;
               			order_list=getResources().getString(R.string.h_ceb);
               			order_cost=getResources().getString(R.string.cost_h_ceb);
               			System.out.println(order_list+":"+order_cost);
               		}
               	}
            );
    }
}