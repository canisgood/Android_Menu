package com.Menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SongsActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.songs);
        TextView textview = new TextView(this);
        textview = (TextView) findViewById(R.id.textview);
        textview.setText("This is the Songs tab");
        Button button = new Button(this);
        //button = (Button) findViewById(R.id.button);
        //button.setText("This Songs");
        
        //button.setOnClickListener(
        //   	new Button.OnClickListener(){
        //   		@Override
        //   		public void onClick(View v) {
        //   			// TODO Auto-generated method stub	
        //   		}
        //   	}
        //);
    }
}