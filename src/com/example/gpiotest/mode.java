package com.example.gpiotest;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class mode extends Activity {

	  @Override
		public void onBackPressed() {
			Intent intent = new Intent(this, HomeActivity.class);
			startActivity(intent);
			finish();
			//android.os.Process.killProcess(android.os.Process.myPid());

		}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mode);

				
		
		//tombol internet
		((Button) findViewById(R.id.internet))
				.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						mode.this.startActivity(new Intent(
								mode.this, MainActivity2.class));
					}
				});
		
		//tombol lokal
		((Button) findViewById(R.id.lokal))
				.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						mode.this.startActivity(new Intent(
								mode.this, MainActivity.class));
					}
				});
		
		//tombol sms
				((Button) findViewById(R.id.pesan))
						.setOnClickListener(new OnClickListener() {

							public void onClick(View v) {
								mode.this.startActivity(new Intent(
										mode.this, MainActivity3.class));
								Toast.makeText(mode.this, "Pastikan Pulsa Anda Cukup Untuk Lakukan SMS", Toast.LENGTH_LONG).show();
							}
						});
				
		
		((Button) findViewById(R.id.logpesan))
				.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						Intent click = new Intent(mode.this,
								DataPesan.class);
						click.putExtra("tipepesan", "inbox");
						startActivity(click);
					}
				});


		((Button) findViewById(R.id.exit))
				.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						Intent intent = new Intent(Intent.ACTION_MAIN);
						intent.addCategory(Intent.CATEGORY_HOME);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
						System.exit(0);
					}
				});
		
	}
	

}
