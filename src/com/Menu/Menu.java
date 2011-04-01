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




import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * Demonstration of using fragments to implement different activity layouts.
 * This sample provides a different layout (and activity flow) when run in
 * landscape.
 */
public class Menu extends TabActivity {
	Bundle bundle;
	Intent intent;
	TextView editmeal;
    TextView edittotal;
    String order_cost="0";
    String order_list="";
    int cost,newitem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     
        setContentView(R.layout.fragment_textlayout);
        Resources res = getResources(); // Resource object to get Drawables
        
        Button Clear;
        Clear = (Button) findViewById(R.id.Clear);
        editmeal = (TextView) findViewById(R.id.editmeal);
        edittotal = (TextView) findViewById(R.id.edittotal);
	    // Some parameters for tab
	    String tab1_name="º~³ù";
	    String tab2_name="³J»æ";
	    String tab3_name="¶¼®Æ";
	    String tab1_spec="Hamburger";
	    String tab2_spec="Omelet";
	    String tab3_spec="Drinks";	
	    
	    
	    Clear.setOnClickListener(
               	new Button.OnClickListener(){
               		@Override
               		public void onClick(View v) {
               			// TODO Auto-generated method stub               			
               			editmeal.setText("");
               			edittotal.setText("0"); 
               			order_cost="0";
               		    order_list="";
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
	    tabHost.setCurrentTab(0);
	    hamburger();
	    drinks();
	    Omelet();
    }
    
    public void hamburger(){
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
           			order_list=order_list + " "+ getResources().getString(R.string.h_scef);
           			newitem= Integer.parseInt(getResources().getString(R.string.cost_h_scef));
           			cost = Integer.parseInt(order_cost);
           			order_cost = Integer.toString(cost+newitem);
               		editmeal.setText(order_list);
               		edittotal.setText(order_cost);
               		System.out.println(order_list+":"+order_cost);	

           		}
           	}
        );
        button_h_cceb.setOnClickListener(
            new Button.OnClickListener(){
               	@Override
               	public void onClick(View v) {
               		// TODO Auto-generated method stub
               		order_list=order_list + " "+ getResources().getString(R.string.h_cceb);
           			newitem= Integer.parseInt(getResources().getString(R.string.cost_h_cceb));
           			cost = Integer.parseInt(order_cost);
           			order_cost = Integer.toString(cost+newitem);
               		editmeal.setText(order_list);
               		edittotal.setText(order_cost);
               		System.out.println(order_list+":"+order_cost);	
               	}
            }
        );
        button_h_tbcb.setOnClickListener(
              	new Button.OnClickListener(){
               		@Override
               		public void onClick(View v) {
               			// TODO Auto-generated method stub
               			order_list=order_list + " "+ getResources().getString(R.string.h_tbcb);
               			newitem= Integer.parseInt(getResources().getString(R.string.cost_h_tbcb));
               			cost = Integer.parseInt(order_cost);
               			order_cost = Integer.toString(cost+newitem);
               			editmeal.setText(order_list);
               			edittotal.setText(order_cost);
               			System.out.println(order_list+":"+order_cost);
               		}
               	}
            );
        button_h_peb.setOnClickListener(
               	new Button.OnClickListener(){
               		@Override
               		public void onClick(View v) {
               			// TODO Auto-generated method stub
               			order_list=order_list + " "+ getResources().getString(R.string.h_peb);
               			newitem= Integer.parseInt(getResources().getString(R.string.cost_h_peb));
               			cost = Integer.parseInt(order_cost);
               			order_cost = Integer.toString(cost+newitem);
               			editmeal.setText(order_list);
               			edittotal.setText(order_cost);
               			System.out.println(order_list+":"+order_cost);
               		}
               	}
            );
        button_h_ceb.setOnClickListener(
               	new Button.OnClickListener(){
               		@Override
               		public void onClick(View v) {
               			// TODO Auto-generated method stub
               			order_list=order_list + " "+ getResources().getString(R.string.h_ceb);
               			newitem= Integer.parseInt(getResources().getString(R.string.cost_h_ceb));
               			cost = Integer.parseInt(order_cost);
               			order_cost = Integer.toString(cost+newitem);
               			editmeal.setText(order_list);
               			edittotal.setText(order_cost);
               			System.out.println(order_list+":"+order_cost);
               		}
               	}
            );
    }
    public void drinks(){
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
           			order_list=order_list + " "+ getResources().getString(R.string.d_cocs);
           			newitem= Integer.parseInt(getResources().getString(R.string.cost_d_cocs));
           			cost = Integer.parseInt(order_cost);
           			order_cost = Integer.toString(cost+newitem);
           			editmeal.setText(order_list);
           			edittotal.setText(order_cost);
           			System.out.println(order_list+":"+order_cost);
           		}
           	}
        );
        button_d_ipbm.setOnClickListener(
               	new Button.OnClickListener(){
               		@Override
               		public void onClick(View v) {
               			// TODO Auto-generated method stub	
               			order_list=order_list + " "+ getResources().getString(R.string.d_ipbm);
               			newitem= Integer.parseInt(getResources().getString(R.string.cost_d_ipbm));
               			cost = Integer.parseInt(order_cost);
               			order_cost = Integer.toString(cost+newitem);
               			editmeal.setText(order_list);
               			edittotal.setText(order_cost);
               			System.out.println(order_list+":"+order_cost);
               		}
               	}
            );
        button_d_hpbm.setOnClickListener(
               	new Button.OnClickListener(){
               		@Override
               		public void onClick(View v) {
               			// TODO Auto-generated method stub	
               			order_list=order_list + " "+ getResources().getString(R.string.d_hpbm);
               			newitem= Integer.parseInt(getResources().getString(R.string.cost_d_hpbm));
               			cost = Integer.parseInt(order_cost);
               			order_cost = Integer.toString(cost+newitem);
               			editmeal.setText(order_list);
               			edittotal.setText(order_cost);
               			System.out.println(order_list+":"+order_cost);
               		}
               	}
            );
        button_d_ism.setOnClickListener(
               	new Button.OnClickListener(){
               		@Override
               		public void onClick(View v) {
               			// TODO Auto-generated method stub	
               			order_list=order_list + " "+ getResources().getString(R.string.d_ism);
               			newitem= Integer.parseInt(getResources().getString(R.string.cost_d_ism));
               			cost = Integer.parseInt(order_cost);
               			order_cost = Integer.toString(cost+newitem);
               			editmeal.setText(order_list);
               			edittotal.setText(order_cost);
               			System.out.println(order_list+":"+order_cost);
               		}
               	}
            );
        button_d_hsm.setOnClickListener(
               	new Button.OnClickListener(){
               		@Override
               		public void onClick(View v) {
               			// TODO Auto-generated method stub	
               			order_list=order_list + " "+ getResources().getString(R.string.d_hsm);
               			newitem= Integer.parseInt(getResources().getString(R.string.cost_d_hsm));
               			cost = Integer.parseInt(order_cost);
               			order_cost = Integer.toString(cost+newitem);
               			editmeal.setText(order_list);
               			edittotal.setText(order_cost);
               			System.out.println(order_list+":"+order_cost);
               		}
               	}
            );
    }
    public void Omelet(){
        TextView textview;
        TextView textview2;       
        Button button_o_scco;
        Button button_o_ccco;
        Button button_o_tbco;
        Button button_o_pco;
        Button button_o_cco;
        textview = (TextView) findViewById(R.id.textview);
        textview2 = (TextView) findViewById(R.id.textview2);
        textview.setText("This is the Omelet tab...");
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
           			order_list=order_list + " "+ getResources().getString(R.string.o_scco);
           			newitem= Integer.parseInt(getResources().getString(R.string.cost_o_scco));
           			cost = Integer.parseInt(order_cost);
           			order_cost = Integer.toString(cost+newitem);
               		editmeal.setText(order_list);
               		edittotal.setText(order_cost);
               		System.out.println(order_list+":"+order_cost);	

           		}
           	}
        );
        button_o_ccco.setOnClickListener(
            new Button.OnClickListener(){
               	@Override
               	public void onClick(View v) {
               		// TODO Auto-generated method stub
               		order_list=order_list + " "+ getResources().getString(R.string.o_ccco);
           			newitem= Integer.parseInt(getResources().getString(R.string.cost_o_ccco));
           			cost = Integer.parseInt(order_cost);
           			order_cost = Integer.toString(cost+newitem);
               		editmeal.setText(order_list);
               		edittotal.setText(order_cost);
               		System.out.println(order_list+":"+order_cost);	
               	}
            }
        );
        button_o_tbco.setOnClickListener(
              	new Button.OnClickListener(){
               		@Override
               		public void onClick(View v) {
               			// TODO Auto-generated method stub
               			order_list=order_list + " "+ getResources().getString(R.string.o_tbco);
               			newitem= Integer.parseInt(getResources().getString(R.string.cost_o_tbco));
               			cost = Integer.parseInt(order_cost);
               			order_cost = Integer.toString(cost+newitem);
               			editmeal.setText(order_list);
               			edittotal.setText(order_cost);
               			System.out.println(order_list+":"+order_cost);
               		}
               	}
            );
        button_o_pco.setOnClickListener(
               	new Button.OnClickListener(){
               		@Override
               		public void onClick(View v) {
               			// TODO Auto-generated method stub
               			order_list=order_list + " "+ getResources().getString(R.string.o_pco);
               			newitem= Integer.parseInt(getResources().getString(R.string.cost_o_pco));
               			cost = Integer.parseInt(order_cost);
               			order_cost = Integer.toString(cost+newitem);
               			editmeal.setText(order_list);
               			edittotal.setText(order_cost);
               			System.out.println(order_list+":"+order_cost);
               		}
               	}
            );
        button_o_cco.setOnClickListener(
               	new Button.OnClickListener(){
               		@Override
               		public void onClick(View v) {
               			// TODO Auto-generated method stub
               			order_list=order_list + " "+ getResources().getString(R.string.o_cco);
               			newitem= Integer.parseInt(getResources().getString(R.string.cost_o_cco));
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
