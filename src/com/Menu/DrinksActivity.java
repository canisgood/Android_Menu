package com.Menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DrinksActivity extends Activity {
    /** Called when the activity is first created. */
	
    int pos=-1;
    String order_cost;
    String order_list;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drinks);

        TextView textview;
        TextView textview2;
        Button button_d_cocs;
        Button button_d_ipbm;
        Button button_d_hpbm;
        Button button_d_ism;
        Button button_d_hsm;
        
        textview = (TextView) findViewById(R.id.textview);
        textview2 = (TextView) findViewById(R.id.textview2);
        textview.setText("This is the Drinks tab...");
        textview2.setText("What do you want to order?");
        
        button_d_cocs = (Button) findViewById(R.id.button_d_cocs);
        button_d_ipbm = (Button) findViewById(R.id.button_d_ipbm);
        button_d_hpbm = (Button) findViewById(R.id.button_d_hpbm);
        button_d_ism = (Button) findViewById(R.id.button_d_ism);
        button_d_hsm = (Button) findViewById(R.id.button_d_hsm);
        
        button_d_cocs.setOnClickListener(
           	new Button.OnClickListener(){
           		@Override
           		public void onClick(View v) {
           			// TODO Auto-generated method stub	
           			++pos;
           			order_list=getResources().getString(R.string.d_cocs);
           			order_cost=getResources().getString(R.string.cost_d_cocs);
           			System.out.println(order_list+":"+order_cost);
           		}
           	}
        );
        button_d_ipbm.setOnClickListener(
               	new Button.OnClickListener(){
               		@Override
               		public void onClick(View v) {
               			// TODO Auto-generated method stub	
               			++pos;
               			order_list=getResources().getString(R.string.d_ipbm);
               			order_cost=getResources().getString(R.string.cost_d_ipbm);
               			System.out.println(order_list+":"+order_cost);
               		}
               	}
            );
        button_d_hpbm.setOnClickListener(
               	new Button.OnClickListener(){
               		@Override
               		public void onClick(View v) {
               			// TODO Auto-generated method stub	
               			++pos;
               			order_list=getResources().getString(R.string.d_hpbm);
               			order_cost=getResources().getString(R.string.cost_d_hpbm);
               			System.out.println(order_list+":"+order_cost);
               		}
               	}
            );
        button_d_ism.setOnClickListener(
               	new Button.OnClickListener(){
               		@Override
               		public void onClick(View v) {
               			// TODO Auto-generated method stub	
               			++pos;
               			order_list=getResources().getString(R.string.d_ism);
               			order_cost=getResources().getString(R.string.cost_d_ism);
               			System.out.println(order_list+":"+order_cost);
               		}
               	}
            );
        button_d_hsm.setOnClickListener(
               	new Button.OnClickListener(){
               		@Override
               		public void onClick(View v) {
               			// TODO Auto-generated method stub	
               			++pos;
               			order_list=getResources().getString(R.string.d_hsm);
               			order_cost=getResources().getString(R.string.cost_d_hsm);
               			System.out.println(order_list+":"+order_cost);
               		}
               	}
            );
    }
}