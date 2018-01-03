package com.example.gpiotest;



import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class HomeActivity extends Activity 
{
//	private TextView textView2;
	Button btnSignIn,btnSignUp,button3;
	LoginDataBaseAdapter loginDataBaseAdapter;
	MainActivity MainActivity;
	 private static final String TAG = "bluetooth voice";
	private BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.main);
	     
	     /**
	     TextView tx = (TextView)findViewById(R.id.coy);
	     TextView tx2 = (TextView)findViewById(R.id.coy2);
	      Typeface custom_font = Typeface.createFromAsset(getAssets(),"fonts/moonhouse.ttf");
	      tx.setTypeface(custom_font);
	      tx2.setTypeface(custom_font);
	      **/
	     // create a instance of SQLite Database
	     loginDataBaseAdapter=new LoginDataBaseAdapter(this);
	     loginDataBaseAdapter=loginDataBaseAdapter.open();
	     
	     // Get The Refference Of Buttons
	     btnSignIn=(Button)findViewById(R.id.buttonSignIN);
	     btnSignUp=(Button)findViewById(R.id.buttonSignUP);
	     button3=(Button)findViewById(R.id.button3);
	   
			
	    // Set OnClick Listener on SignUp button 
	    btnSignUp.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			/// Create Intent for SignUpActivity  and Start The Activity
			Intent intentSignUP=new Intent(getApplicationContext(),SignUPActivity.class);
			startActivity(intentSignUP);
			}
		});
	  
	/**    button3.setOnClickListener(new OnClickListener() {
	    	  public void onClick(View bebek) {
	    		  Intent myIntent = new
	    		  Intent(bebek.getContext(), Activity2.class);
	    		  startActivityForResult(myIntent, 0);
	    		  }

	    		  });**/
		}
	
	/**
	 private void checkBTState() {
		    // Check for Bluetooth support and then check to make sure it is turned on
		    // Emulator doesn't support Bluetooth and will return null
		    //if(btAdapter==null) { 
		     //errorExit("Fatal Error", "Bluetooth not support");
		    //} else {
		      if (btAdapter.isEnabled()) {
		        Log.d(TAG, "...Bluetooth ON...");
		      } else {
		        //Prompt user to turn on Bluetooth
		        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		        startActivityForResult(enableBtIntent, 1);
		      //}
		    }
		  }**/
	
	
	// Methos to handleClick Event of Sign In Button
	public void signIn(View V)
	   {
		//cek kondisi bluetooth
		//checkBTState();
		//memunculkan dialog sign in
			final Dialog dialog = new Dialog(HomeActivity.this);
			dialog.setContentView(R.layout.login);
		    dialog.setTitle("Login");
		 
		    

			// get Instance  of Database Adapter
			loginDataBaseAdapter=new LoginDataBaseAdapter(this);
			loginDataBaseAdapter=loginDataBaseAdapter.open();
	
		    // get the Refferences of views
		    final  EditText editTextUserName=(EditText)dialog.findViewById(R.id.editTextUserNameToLogin);
		    final  EditText editTextPassword=(EditText)dialog.findViewById(R.id.editTextPasswordToLogin);
		    
			Button btnSignIn=(Button)dialog.findViewById(R.id.buttonSignIn);
				
			// Set On ClickListener
			btnSignIn.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// get The User name and Password
					String userName=editTextUserName.getText().toString();
					String password=editTextPassword.getText().toString();
					
					
					
					// fetch the Password form database for respective user name
					String storedPassword=loginDataBaseAdapter.getSinlgeEntry(userName);
					String signin="Log in";
					loginDataBaseAdapter.masukEntry(userName,signin);
					// check if the Stored password matches with  Password entered by user
					if(password.equals(storedPassword))
					{
						Toast.makeText(HomeActivity.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
						dialog.dismiss();
						
						 Intent myIntent = new Intent(v.getContext(), mode.class);
						 	myIntent.putExtra("userName", userName);
					    		  startActivityForResult(myIntent, 0);
					    	  

					}
					else
					{
						Toast.makeText(HomeActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
					}
				}
			});
			
			dialog.show();
	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		System.exit(0);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	    // Close The Database
		loginDataBaseAdapter.close();
	}
}
