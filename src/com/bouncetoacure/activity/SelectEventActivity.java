package com.bouncetoacure.activity;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import com.bouncetoacure.R;
import com.bouncetoacure.components.AlertMessage;
import com.bouncetoacure.webservice.HttpWebService;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class SelectEventActivity extends Activity implements View.OnClickListener, OnDismissListener {
	private Spinner event_Spinner, jumper_Spinner;
	private Button btn_select, btn_settings, btn_cancel;
	private TextView judge;
	private String username;
	private Context context = this;
	private JSONArray eventArray, jumperArray;
	private String eArray, jArray;
	private ArrayAdapter<String> m_adapterForSpinner;
	boolean error;

	
	@Override
	protected void onResume() {
		super.onResume();
		btn_settings.setBackgroundResource(R.drawable.settings);
	}

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
		setContentView(R.layout.selectevent);
		init();
		populateSpinners();
	}

	// event handler for buttons
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_settings:
				try {
					btn_settings.setBackgroundResource(R.drawable.settings_pressed);
					Intent intent = new Intent(SelectEventActivity.this, SettingsActivity.class);
					intent.putExtra("username", username);
					SelectEventActivity.this.startActivity(intent);
				} catch (Exception e) {
					Log.i(SelectEventActivity.class.getName(), "Error: " + e.getMessage());
				}
				break;
			case R.id.btn_select:
				startProgressBar(v);
				break;
			case R.id.btn_cancel:
				finish();
				break;
		}
	}

	// map variables to fields
	public void init() {
		// intent variables
		username = getIntent().getStringExtra("username");
		eArray = getIntent().getStringExtra("eventArray");
		jArray = getIntent().getStringExtra("jumperArray");
		
		// text views
		judge = (TextView) findViewById(R.id.tv_judgeName);
		judge.setText(username);
		
		// Populate Spinners
		event_Spinner = (Spinner) findViewById(R.id.spinner_event);
		event_Spinner.setAdapter(null);
		jumper_Spinner = (Spinner) findViewById(R.id.spinner_jumper);
		jumper_Spinner.setAdapter(null);
		
		// Buttons
		btn_settings = (Button) findViewById(R.id.btn_settings);
		btn_select = (Button) findViewById(R.id.btn_select);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_settings.setOnClickListener(this);
		btn_select.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
	}

	
	public boolean submitDetails() {
		String res = null;
		
		// Add POST data
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("Jumper", jumper_Spinner.getSelectedItem().toString()));
		postParameters.add(new BasicNameValuePair("Event", event_Spinner.getSelectedItem().toString()));
		
		// send post data
		try {
			res = HttpWebService.executeHttpPost("http://www.bouncetoacure.com/php_files/IsParticipating.php", postParameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// check response
		if (res.contains("null")) {
			error = true;
		} else {
			return true;
		}
		return false;
	}

	// open new activity depending on the item in the spinner
	public void openNewActivity() {
		event_Spinner = (Spinner) findViewById(R.id.spinner_event);
		if (event_Spinner.getSelectedItem().toString().contains("Qual")) {
			try {
				Intent intent = new Intent(SelectEventActivity.this, QualifyingActivity.class);
				intent.putExtra("username", judge.getText().toString());
				intent.putExtra("jumper", jumper_Spinner.getSelectedItem().toString());
				intent.putExtra("event", event_Spinner.getSelectedItem().toString());
				SelectEventActivity.this.startActivity(intent);
			} catch (Exception e) {
				Log.i(SelectEventActivity.class.getName(), "Error: " + e.getMessage());
			}
		} else if (event_Spinner.getSelectedItem().toString().contains("Final")) {
			try {
				Intent intent = new Intent(SelectEventActivity.this, FinalsActivity.class);
				intent.putExtra("username", judge.getText().toString());
				intent.putExtra("jumper", jumper_Spinner.getSelectedItem().toString());
				intent.putExtra("event", event_Spinner.getSelectedItem().toString());
				SelectEventActivity.this.startActivity(intent);
			} catch (Exception e) {
				Log.i(SelectEventActivity.class.getName(), "Error: " + e.getMessage());
			}
		}
	}

	// populate the spinner with appropriate values from DB
	public void populateSpinners() {
		// Populate Event Spinner
		try {
			eventArray = new JSONArray(eArray);
			m_adapterForSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
			m_adapterForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			event_Spinner.setAdapter(m_adapterForSpinner);
			for (int i = 0; i < eventArray.length(); i++) {
				JSONObject jsonObject = eventArray.getJSONObject(i);
				m_adapterForSpinner.add(jsonObject.getString("Event"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Populate Jumper Spinner
		try {
			jumperArray = new JSONArray(jArray);
			m_adapterForSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
			m_adapterForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			jumper_Spinner.setAdapter(m_adapterForSpinner);
			for (int i = 0; i < jumperArray.length(); i++) {
				JSONObject jsonObject = jumperArray.getJSONObject(i);
				m_adapterForSpinner.add(jsonObject.getString("Jumper"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// begin status bar for opening new activity
	public void startProgressBar(View v) {
		error = false;
		showDialog(1);
		new Thread(new Runnable() {
			public void run() {
				try {
					if (submitDetails()) {
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

	// handler for dialog dismissal
	public void onDismiss(DialogInterface dialog) {
		if (error) {
			new AlertMessage(context, "The Jumper selected is not participating in that Event");
		}
	}
}
