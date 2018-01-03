package com.example.gpiotest;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import co.teubi.raspberrypi.io.*;
import android.content.Intent;
import android.speech.RecognizerIntent;


public class MainActivity extends Activity implements GPIO.PortUpdateListener,GPIO.ConnectionEventListener {

  private GPIO gpioPort;
	protected static final int REQUEST_OK = 1;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    this.gpioPort = new GPIO(new GPIO.ConnectionInfo("192.168.100.1", 8000,
        "webiopi", "raspberry"));
    

    final CheckBox cb = (CheckBox) findViewById(R.id.chkIsInput);
    final CheckBox cb2 = (CheckBox) findViewById(R.id.chkIsInput2);
    final CheckBox cb3 = (CheckBox) findViewById(R.id.CheckBox01);
    final CheckBox cb4 = (CheckBox) findViewById(R.id.CheckBox02);
    final ImageButton button1 = (ImageButton) findViewById(R.id.button1);

    
    
    cb.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
    	  if(cb.isChecked()) {
    		  gpioPort.setFunction(18, PORTFUNCTION.INPUT);
    	  } else {
    		  gpioPort.setFunction(18, PORTFUNCTION.OUTPUT);
    	  }
      }
    });
    
    cb2.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
      	  if(cb2.isChecked()) {
      		  gpioPort.setFunction(23, PORTFUNCTION.INPUT);
      	  } else {
      		  gpioPort.setFunction(23, PORTFUNCTION.OUTPUT);
      	  }
        }
      });
    
    cb3.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
      	  if(cb3.isChecked()) {
      		  gpioPort.setFunction(24, PORTFUNCTION.INPUT);
      	  } else {
      		  gpioPort.setFunction(24, PORTFUNCTION.OUTPUT);
      	  }
        }
      });
    
    cb4.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
      	  if(cb4.isChecked()) {
      		  gpioPort.setFunction(25, PORTFUNCTION.INPUT);
      	  } else {
      		  gpioPort.setFunction(25, PORTFUNCTION.OUTPUT);
      	  }
        }
      });
    

    final ToggleButton tb = (ToggleButton) findViewById(R.id.btnPort);
    final ToggleButton tb2 = (ToggleButton) findViewById(R.id.btnPort2);
    final ToggleButton tb3 = (ToggleButton) findViewById(R.id.ToggleButton01);
    final ToggleButton tb4 = (ToggleButton) findViewById(R.id.ToggleButton02);
    final Button button3=(Button)findViewById(R.id.button3);

    tb.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
    	  // Only change port value if the port is an "output"
    	  if(!cb.isChecked()) {
    		  if(!tb.isChecked()) {
    			  gpioPort.setValue(18,0);
    		  } else {
    			  gpioPort.setValue(18,1);
    		  }
    	  }
      }
    });
    
    tb2.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
      	  // Only change port value if the port is an "output"
      	  if(!cb2.isChecked()) {
      		  if(!tb2.isChecked()) {
      			  gpioPort.setValue(23,0);
      		  } else {
      			  gpioPort.setValue(23,1);
      		  }
      	  }
        }
      });
    
    tb3.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
      	  // Only change port value if the port is an "output"
      	  if(!cb3.isChecked()) {
      		  if(!tb3.isChecked()) {
      			  gpioPort.setValue(24,0);
      		  } else {
      			  gpioPort.setValue(24,1);
      		  }
      	  }
        }
      });
    
    tb4.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
      	  // Only change port value if the port is an "output"
      	  if(!cb4.isChecked()) {
      		  if(!tb4.isChecked()) {
      			  gpioPort.setValue(25,0);
      		  } else {
      			  gpioPort.setValue(25,1);
      		  }
      	  }
        }
      });
    
    button1.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
        	 Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
	         i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
	      
	          
	         try {
	             startActivityForResult(i, REQUEST_OK);
	              		            
	         } catch (Exception e) {
	        	// Toast.makeText(this, "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
	        	 Toast.makeText(getBaseContext(), "Voice Recognition Belum Terinstall", Toast.LENGTH_SHORT).show();
	         }
        }
      });
    
   
    button3.setOnClickListener(new View.OnClickListener() {
    	  public void onClick(View bebek) {
    		  Intent myIntent = new
    		  Intent(bebek.getContext(), camera.class);
    		  startActivityForResult(myIntent, 0);
    		  
    		

    	  } });
    
        
    this.gpioPort.addPortUpdateListener(this);
    (new Thread(this.gpioPort)).start();
  }
  
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, mode.class);
		startActivity(intent);
		finish();
		//android.os.Process.killProcess(android.os.Process.myPid());

	}
  
  
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      if (requestCode==REQUEST_OK  && resultCode==RESULT_OK) {
      	ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
      	((TextView)findViewById(R.id.text1)).setText(thingsYouSaid.get(0));
      
      	String S1="nyalakan lampu 1";
      	String S11="nyalakan lampu satu";
      	
      	String S2="matikan lampu satu";
       	String S22="matikan lampu 1";
       	
      	String S3="nyalakan lampu 2";
      	String S33="nyalakan lampu dua";
      	
      	String S4="matikan lampu 2";
      	String S44="matikan lampu dua";
      	
      	TextView theFact = (TextView) findViewById(R.id.text1);
        
        String shareFact = theFact.getText().toString();
        
        
        //lampu 1 nyala
        if(shareFact.equals(S1)) {
  		  
        	 gpioPort.setValue(18, 1);
        	 ((ToggleButton) findViewById(R.id.btnPort)).setChecked(true);
    		 }
        
        if(shareFact.equals(S11)) {
    		  
       	 gpioPort.setValue(18, 1);
       	 ((ToggleButton) findViewById(R.id.btnPort)).setChecked(true);
   		 }
        
        
        //lampu 1 mati
        
        if(shareFact.equals(S2)) {
    		  
       	 gpioPort.setValue(18, 0);
       	 ((ToggleButton) findViewById(R.id.btnPort)).setChecked(false);
   		 }
        
        if(shareFact.equals(S22)) {
  		  
          	 gpioPort.setValue(18, 0);
          	 ((ToggleButton) findViewById(R.id.btnPort)).setChecked(false);
      		 }
        
        
        //lampu 2 nyala
        if(shareFact.equals(S3)) {
  		  
          	 gpioPort.setValue(23, 1);
          	 ((ToggleButton) findViewById(R.id.btnPort2)).setChecked(true);
      		 }
        
        if(shareFact.equals(S33)) {
    		  
         	 gpioPort.setValue(23, 1);
         	 ((ToggleButton) findViewById(R.id.btnPort2)).setChecked(true);
     		 }
		 
        
        
        //lampu 2 mati
        if(shareFact.equals(S4)) {
  		  
          	 gpioPort.setValue(23, 0);
          	 ((ToggleButton) findViewById(R.id.btnPort2)).setChecked(false);
      		 }
        
        if(shareFact.equals(S44)) {
    		  
         	 gpioPort.setValue(23, 0);
         	 ((ToggleButton) findViewById(R.id.btnPort2)).setChecked(false);
     		 }
		  }
      
      
          }
  
  

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.activity_main, menu);
    return true;
  }

  @Override
  public void onPortUpdated(final GPIOStatus stat) {
	  runOnUiThread(new Runnable() {
		 public void run() {
			 // First check if the port is configured
			 // as an input or output
			 if(stat.ports.get(18).function==PORTFUNCTION.INPUT) {
				 
				 // Check the checkbox
				 ((CheckBox) findViewById(R.id.chkIsInput)).setChecked(true);
				 

				 // If is an Input disable the button
				 ((ToggleButton) findViewById(R.id.btnPort)).setEnabled(false);
				 
				 // Set the checked state based on the current port value
				 ((ToggleButton) findViewById(R.id.btnPort)).setChecked(stat.ports.get(18).value.toBool());
			 } else if (stat.ports.get(18).function==PORTFUNCTION.OUTPUT) {
				 
				 // Un-check the checkbox
				 ((CheckBox) findViewById(R.id.chkIsInput)).setChecked(false);
				 

				 // If is an Output enable the button
				 ((ToggleButton) findViewById(R.id.btnPort)).setEnabled(true);
				 
				 // Set the checked state based on the current port value
				 ((ToggleButton) findViewById(R.id.btnPort)).setChecked(stat.ports.get(18).value.toBool());
				 
			 } else {
			 }
		 }
	  });
  }

  
  @Override
  public void onConnectionFailed(String message) {
	// TODO Auto-generated method stub
	int a = 5;
  }

}
