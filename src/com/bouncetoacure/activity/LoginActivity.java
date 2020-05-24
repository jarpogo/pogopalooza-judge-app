package com.bouncetoacure.activity;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.bouncetoacure.R;
import com.bouncetoacure.components.AlertMessage;
import com.bouncetoacure.webservice.HttpWebService;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;

public class LoginActivity extends Activity implements View.OnClickListener, OnDismissListener {
	// declare views to access later
	EditText et_username, et_password;
	Button btn_login, btn_cancel;
	Context context = this;
	boolean error;
	String eventArray="", jumperArray="";

	// popup message when trying to load activity
	@Override
	protected Dialog onCreateDialog(int id) {
		ProgressDialog dialog = new ProgressDialog(this);
		dialog.setMessage("Please wait while loading...");
		dialog.setIndeterminate(true);
		dialog.setCancelable(true);
		dialog.setOnDismissListener(this);
		return dialog;
	}

	// called when the activity is first created
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		init();
	}

	// initialize all fields
	public void init() {
		// edit text mapping
		et_username = (EditText) findViewById(R.id.et_un);
		et_password = (EditText) findViewById(R.id.et_pw);
		
		// button mapping
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_login.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
	}

	// button event handler
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_login:
				startProgressBar(v);
				break;
			case R.id.btn_cancel:
				finish();
				break;
		}
	}

	// dialog for setting values
	public void startProgressBar(View v) {
		error = false;
		showDialog(1);
		new Thread(new Runnable() {
			public void run() {
				try {
					if (beginLogin()) {
						dismissDialog(1);
						openNewActivity();
					} else {
						dismissDialog(1);
					}
				} catch (Exception e) {
					System.out.println("Found an exception: " + e);
				}
			}
		}).start();
	}

	// log in with user provided username/password
	public boolean beginLogin() {
		String res = null;
		try {
			// grab the password and encrypt with MD5
			String s = et_password.getText().toString();
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(s.getBytes(), 0, s.length());
			String signature = new BigInteger(1, md5.digest()).toString(16);
			
			// add POST data
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("Username", et_username.getText().toString()));
			postParameters.add(new BasicNameValuePair("Password", signature));
			
			// get response from POST
			res = HttpWebService.executeHttpPost("http://www.bouncetoacure.com/php_files/Auth.php", postParameters);
			if (res.contains("true")) {
				if(populateArrays()) {
					return true;
				}
			} else {
				error = true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	// open activity and pass variables
	public void openNewActivity() {		
		Intent intent = new Intent(LoginActivity.this, SelectEventActivity.class);
		intent.putExtra("username", et_username.getText().toString());
		intent.putExtra("eventArray", eventArray);
		intent.putExtra("jumperArray", jumperArray);
		
		LoginActivity.this.startActivity(intent);
	}
	
	// update arrays based on database
	public boolean populateArrays() {		
		try {
			jumperArray = HttpWebService.executeHttpGet("http://www.bouncetoacure.com/php_files/GetJumpers.php");
			eventArray = HttpWebService.executeHttpGet("http://www.bouncetoacure.com/php_files/GetEvents.php");		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	// handler for dialog dismissal
	public void onDismiss(DialogInterface dialog) {
		System.out.println(error);
		if (error) {
			new AlertMessage(context, "Incorrect username and/or password");
		}
	}
}