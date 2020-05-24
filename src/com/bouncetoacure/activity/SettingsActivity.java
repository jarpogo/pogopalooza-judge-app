package com.bouncetoacure.activity;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.bouncetoacure.R;
import com.bouncetoacure.components.AlertMessage;
import com.bouncetoacure.exceptions.EmptyFieldException;
import com.bouncetoacure.exceptions.MatchException;
import com.bouncetoacure.exceptions.PasswordException;
import com.bouncetoacure.webservice.HttpWebService;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends Activity {
	private Button submit, cancel;
	private TextView judge;
	private EditText oldpw, newpw, confirmpw;
	private String username;
	private Context context = this;

	// called when the activity is first created
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		init();
		
		// Set Click Listener
		submit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					checkEmptyFields();
					checkPasswordMatch();
					checkOldPassword();
					updatePassword();
				} catch (MatchException e) {
					Log.e(SettingsActivity.class.getName(), "Password Match exception: " + e.getMessage());
					new AlertMessage(context, e.getMessage());
				} catch (EmptyFieldException e) {
					Log.e(SettingsActivity.class.getName(), "Empty Field Exception: " + e.getMessage());
					new AlertMessage(context, e.getMessage());
				} catch (PasswordException e) {
					Log.e(SettingsActivity.class.getName(), "Password Exception: " + e.getMessage());
					new AlertMessage(context, e.getMessage());
				} catch (SQLException e) {
					Log.e(SettingsActivity.class.getName(), "SQL Exception: " + e.getMessage());
					new AlertMessage(context, e.getMessage());
				}
			}
		});
		// Set Click Listener
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	// initialize variables and map to fields
	private void init() {
		// get intent variables
		username = getIntent().getStringExtra("username");
		
		// map text views
		judge = (TextView) findViewById(R.id.tv_judgeName);
		judge.setText(username);
		
		// map text fields
		oldpw = (EditText) findViewById(R.id.et_oldpw);
		newpw = (EditText) findViewById(R.id.et_newpw);
		confirmpw = (EditText) findViewById(R.id.et_confirmpw);
		
		// map buttons
		submit = (Button) findViewById(R.id.btn_submit);
		cancel = (Button) findViewById(R.id.btn_cancel);
	}

	// verify both password text fields match
	private void checkPasswordMatch() throws MatchException {
		newpw = (EditText) findViewById(R.id.et_newpw);
		confirmpw = (EditText) findViewById(R.id.et_confirmpw);
		String newpass = newpw.getText().toString();
		String confirmpass = confirmpw.getText().toString();
		if (!newpass.matches(confirmpass)) {
			throw new MatchException("New passwords entered do not match");
		}
	}

	// verify password fields are not empty
	private void checkEmptyFields() throws EmptyFieldException {
		String newpass = newpw.getText().toString();
		String confirmpass = confirmpw.getText().toString();
		if (newpass.matches("") || confirmpass.matches("")) {
			throw new EmptyFieldException("New Password fields cannot be empty");
		}
	}

	// confirm old password entered is correct
	private void checkOldPassword() throws PasswordException {
		String response = null;
		String un = username;
		String pw = oldpw.getText().toString();
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md5.update(pw.getBytes(), 0, pw.length());
		String signature = new BigInteger(1, md5.digest()).toString(16);
		// Add your data
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("Username", un));
		postParameters.add(new BasicNameValuePair("Password", signature));
		try {
			response = HttpWebService.executeHttpPost("http://www.bouncetoacure.com/php_files/Auth.php", postParameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (response.contains("true")) {
			// do nothing
		} else {
			throw new PasswordException("Old password entered is incorrect");
		}
	}

	// update the password to the DB via http POST
	private void updatePassword() throws SQLException {
		String response = null;
		String un = username;
		String pw = newpw.getText().toString();
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md5.update(pw.getBytes(), 0, pw.length());
		String signature = new BigInteger(1, md5.digest()).toString(16);
		// Add your data
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("Username", un));
		postParameters.add(new BasicNameValuePair("Password", signature));
		try {
			response = HttpWebService.executeHttpPost("http://www.bouncetoacure.com/php_files/SetPassword.php", postParameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (response.contains("true")) {
			new AlertMessage(context, "Your new password has been saved");
		} else {
			throw new SQLException("Failed to Update the database");
		}
	}
}
