package com.bouncetoacure.activity;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.bouncetoacure.R;
import com.bouncetoacure.webservice.HttpWebService;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class EmceeActivity extends Activity implements View.OnClickListener, OnDismissListener {
	TableLayout tl_results;
	Spinner s_event;
	Button btn_submit, btn_cancel;
	Context context = this;
	boolean error;

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
		setContentView(R.layout.mcscreen);
		init();
	}

	// initialize text and button variables
	public void init() {
		// map buttons
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_submit.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		
		// map table layout
		tl_results = (TableLayout) findViewById(R.id.tl_results);
		
		// map spinner
		s_event = (Spinner) findViewById(R.id.s_event);
	}

	// handler for buttons
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_submit:
				getResults();
				//startProgressBar(v);
				break;
			case R.id.btn_cancel:
				finish();
				break;
		}
	}
	
	// progress dialog bar for obtaining results
	public void startProgressBar(View v) {
		error = false;
		showDialog(1);
		new Thread(new Runnable() {
			public void run() {
				try {
					if (getResults()) {
						dismissDialog(1);
					} else {
						dismissDialog(1);
					}
				} catch (Exception e) {
					System.out.println("Found an exception: " + e);
				}
			}
		}).start();
	}

	// grab the data from the DB via http
	public boolean getResults() {
		String res = null;
		try {
			// add POST data
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("Event", s_event.getSelectedItem().toString()));
			
			// get response
			res = HttpWebService.executeHttpPost("http://www.bouncetoacure.com/php_files/GetResults.php", postParameters);
			
			cleanTable();
			fillInTable(res);
			
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}
		 return false;
	}

	// clear the table
	public void cleanTable() {
		int count = tl_results.getChildCount();
		for (int i = 0; i < count; i++) {
			View child = tl_results.getChildAt(i);
			if (child instanceof TableRow)
				((ViewGroup) child).removeAllViews();
		}
	}

	// fill the table 
	public void fillInTable(String results) {
		int INITIAL_TEXTSIZE = 18;
		String name, total, element;
		int num = results.length();
		results = results.substring(1, num - 2);
		results = results.replace("\"", "");
		String delims = ",";
		String[] tokens = results.split(delims);
		for (int i = 0; i < tokens.length; i++) {
			element = tokens[i];
			int index = element.indexOf(':');
			name = element.substring(0, index);
			total = element.substring(index + 1, element.length());
			if (!name.equals("")) {
				TableRow tableRow = new TableRow(this);
				tableRow.setBackgroundResource(android.R.color.black);
				
				TextView nameText = new TextView(this);
				nameText.setText(name);
				nameText.setTextSize(INITIAL_TEXTSIZE);
				tableRow.addView(nameText);
				
				TextView totalText = new TextView(this);
				totalText.setText(total);
				totalText.setTextSize(INITIAL_TEXTSIZE);
				tableRow.addView(totalText);
				tl_results.addView(tableRow);
			}
		}
	}
	
	// handler for dialog dismissal
	public void onDismiss(DialogInterface dialog) {
		System.out.println(error);
		if (error) {
			//new AlertMessage(context, "Incorrect username and/or password");
		}
	}
}
