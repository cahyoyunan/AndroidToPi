package com.example.gpiotest;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

public class camera2 extends Activity implements OnClickListener {
	
	private static final String URL = "http://admin:admin@219.83.55.230:8088";
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera);
		mWebView = (WebView) findViewById(R.id.webview); 
		//mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebChromeClient(new WebChromeClient());
	
		refreshWebView();}
	
	
	private void refreshWebView() {
		mWebView.loadUrl(URL);
	}

	@Override
	public void onClick(View v) {
	//refreshWebView();
	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, MainActivity2.class);
		startActivity(intent);
		//finish();
		android.os.Process.killProcess(android.os.Process.myPid());

	}
	

}
