package com.Menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OmeletActivity extends Activity {
    /** Called when the activity is first created. */
	
    int pos=-1;
    String order_cost;
    String order_list;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.omelet);

        TextView textview;
        TextView textview2;
        Button button_o_scco = new Button(this);
        Button button_o_ccco = new Button(this);
        Button button_o_tbco = new Button(this);
        Button button_o_pco = new Button(this);
        Button button_o_cco = new Button(this);
        
        textview = (TextView) findViewById(R.id.textview);
        textview2 = (TextView) findViewById(R.id.textview2);
        textview.setText("This is the Omelte tab...");
        textview2.setText("What do you want to order?");
        
        button_o_scco = (Button) findViewById(R.id.button_o_scco);
        button_o_ccco = (Button) findViewById(R.id.button_o_ccco);
        button_o_tbco = (Button) findViewById(R.id.button_o_tbco);
        button_o_pco = (Button) findViewById(R.id.button_o_pco);
        button_o_cco = (Button) findViewById(R.id.button_o_cco);
        
        button_o_scco.setOnClickListener(
           	new Button.OnClickListener(){
           		@Override
           		public void onClick(View v) {
           			// TODO Auto-generated method stub
           			++pos;
           			order_list=getResources().getString(R.string.o_scco);
           			order_cost=getResources().getString(R.string.cost_o_scco);
           			System.out.println(order_list+":"+order_cost);
           		}
           	}
        );
        button_o_ccco.setOnClickListener(
               	new Button.OnClickListener(){
               		@Override
               		public void onClick(View v) {
               			// TODO Auto-generated method stub
               			++pos;
               			order_list=getResources().getString(R.string.o_ccco);
               			order_cost=getResources().getString(R.string.cost_o_ccco);
               			System.out.println(order_list+":"+order_cost);
               		}
               	}
            );
        button_o_tbco.setOnClickListener(
               	new Button.OnClickListener(){
               		@Override
               		public void onClick(View v) {
               			// TODO Auto-generated method stub
               			++pos;
               			order_list=getResources().getString(R.string.o_tbco);
               			order_cost=getResources().getString(R.string.cost_o_tbco);
               			System.out.println(order_list+":"+order_cost);
               		}
               	}
            );
        button_o_pco.setOnClickListener(
               	new Button.OnClickListener(){
               		@Override
               		public void onClick(View v) {
               			// TODO Auto-generated method stub
               			++pos;
               			order_list=getResources().getString(R.string.o_pco);
               			order_cost=getResources().getString(R.string.cost_o_pco);
               			System.out.println(order_list+":"+order_cost);
               		}
               	}
            );
        button_o_cco.setOnClickListener(
               	new Button.OnClickListener(){
               		@Override
               		public void onClick(View v) {
               			// TODO Auto-generated method stub
               			++pos;
               			order_list=getResources().getString(R.string.o_cco);
               			order_cost=getResources().getString(R.string.cost_o_cco);
               			System.out.println(order_list+":"+order_cost);
               		}
               	}
            );
    }
}