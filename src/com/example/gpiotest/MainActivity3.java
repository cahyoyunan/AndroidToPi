package com.example.gpiotest;

import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
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
import android.telephony.SmsManager;


public class MainActivity3 extends Activity  {

  private GPIO gpioPort;
	protected static final int REQUEST_OK = 1;
	
	

	//Custom method buat ngirim sms masbro
	  private void SMSbro(String isi, String nohp, String norelay, String status) {
		  try {
				// proses kirim sms
				SmsManager sms = SmsManager.getDefault();
				sms.sendTextMessage(nohp, null, isi, null, null);

				// proses simpan sms yang terkirim
				ContentValues values = new ContentValues();
				values.put("address", nohp);
				values.put("body", isi);
				getContentResolver().insert(
						Uri.parse("content://sms/sent"), values);

				Toast.makeText(MainActivity3.this,
						"Relay "+ norelay+ " Telah "+ status + " Melalui SMS ", Toast.LENGTH_LONG).show();
				//finish();
			} catch (Exception e) {
				Toast.makeText(MainActivity3.this, "Pesan gagal dikirim cek kembali pulsa anda",
						Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
			   	  }

	  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main3);
      
    final ImageButton button1 = (ImageButton) findViewById(R.id.button1);
    final ToggleButton tb = (ToggleButton) findViewById(R.id.btnPort);
    final ToggleButton tb2 = (ToggleButton) findViewById(R.id.btnPort2);
    final ToggleButton tb3 = (ToggleButton) findViewById(R.id.ToggleButton02);
    final ToggleButton tb4 = (ToggleButton) findViewById(R.id.ToggleButton01);
    //final String pesan = "cek";
	final String nomor = "085720080200" ;
	
    tb.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
    	  // Only change port value if the port is an "output"
    	  
    		  if(tb.isChecked()) {
    			  SMSbro("Nyalakan lampu 1", nomor,"1", "Dinyalakan");
    			
  				
    		  } else {
    			  SMSbro("Matikan lampu 1", nomor,"1", "Dimatikan");
    		  }
    	  
      }
    });
    
    tb2.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
      	  // Only change port value if the port is an "output"
      	  
        	 if(tb2.isChecked()) {
   			  SMSbro("Nyalakan lampu 2", nomor,"2", "Dinyalakan");
 				
   		  } else {
   			  SMSbro("Matikan lampu 2", nomor,"2", "Dimatikan");
   		  }
   	  
     }
   });
    
    tb3.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
      	  // Only change port value if the port is an "output"
      	  
        	 if(tb3.isChecked()) {
   			  SMSbro("Nyalakan beban 1", nomor,"3", "Dinyalakan");
 				
   		  } else {
   			  SMSbro("Matikan beban 1", nomor,"3", "Dimatikan");
   		  }
   	  
     }
   });
    
    tb4.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
      	  // Only change port value if the port is an "output"
        	 if(tb4.isChecked()) {
   			  SMSbro("Nyalakan beban 2", nomor,"4", "Dinyalakan");
 				
   		  } else {
   			  SMSbro("Matikan beban 2", nomor,"4", "Dimatikan");
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
  }
  
  
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      if (requestCode==REQUEST_OK  && resultCode==RESULT_OK) {
      	ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
      	((TextView)findViewById(R.id.text1)).setText(thingsYouSaid.get(0));
      
      	String S1="turn on 1";
      	String S2="turn on 2";
      	String S3="turn off 1";
      	String S4="turn off 2";
      	TextView theFact = (TextView) findViewById(R.id.text1);
        
        String shareFact = theFact.getText().toString();
        if(shareFact.equals(S1)) {
  		  
        	 gpioPort.setValue(18, 1);
        	 ((ToggleButton) findViewById(R.id.btnPort)).setChecked(true);
    		 }
        
        if(shareFact.equals(S2)) {
    		  
       	 gpioPort.setValue(2, 1);
       	 ((ToggleButton) findViewById(R.id.btnPort2)).setChecked(true);
   		 }
        
        if(shareFact.equals(S3)) {
  		  
          	 gpioPort.setValue(18, 0);
          	 ((ToggleButton) findViewById(R.id.btnPort2)).setChecked(false);
      		 }
		 
        if(shareFact.equals(S4)) {
  		  
          	 gpioPort.setValue(2, 0);
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


  


}
