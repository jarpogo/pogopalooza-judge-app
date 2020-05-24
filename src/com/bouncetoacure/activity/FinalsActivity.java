package com.bouncetoacure.activity;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.bouncetoacure.R;
import com.bouncetoacure.webservice.HttpWebService;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class FinalsActivity extends Activity implements OnSeekBarChangeListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {
	private SeekBar sb_create1, sb_diff1, sb_var1, sb_style1, sb_create2, sb_diff2, sb_var2, sb_style2, sb_create3, sb_diff3, sb_var3, sb_style3;
	private TextView tv_create1, tv_diff1, tv_var1, tv_style1, tv_create2, tv_diff2, tv_var2, tv_style2, tv_create3, tv_diff3, tv_var3, tv_style3, tv_judge, tv_jumper, tv_event;
	private RadioGroup rg_land2, rg_land3;
	private int rg_landVal2, rg_landVal3;
	private String username, jumper, event;
	private Button btn_settings, btn_save, btn_cancel;
	private boolean jam1_flag = false, solo1_flag = false, solo2_flag = false, error;

	@Override
	protected void onResume() {
		super.onResume();
		btn_settings.setBackgroundResource(R.drawable.settings);
	}

	// popup message when trying to load activity
	@Override
	protected Dialog onCreateDialog(int id) {
		ProgressDialog dialog = new ProgressDialog(this);
		dialog.setMessage("Please wait while saving...");
		dialog.setIndeterminate(true);
		dialog.setCancelable(true);
		return dialog;
	}

	// called when the activity is first created
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finals);
		init();
		loadValues();
	}

	// update text fields when slider bars are modified
	public void onProgressChanged(SeekBar seeker, int progress, boolean fromUser) {
		switch (seeker.getId()) {
			case R.id.sb_create1:
				tv_create1.setText(String.valueOf(progress));
				break;
			case R.id.sb_diff1:
				tv_diff1.setText(String.valueOf(progress));
				break;
			case R.id.sb_var1:
				tv_var1.setText(String.valueOf(progress));
				break;
			case R.id.sb_style1:
				tv_style1.setText(String.valueOf(progress));
				break;
			case R.id.sb_create2:
				tv_create2.setText(String.valueOf(progress));
				break;
			case R.id.sb_diff2:
				tv_diff2.setText(String.valueOf(progress));
				break;
			case R.id.sb_var2:
				tv_var2.setText(String.valueOf(progress));
				break;
			case R.id.sb_style2:
				tv_style2.setText(String.valueOf(progress));
				break;
			case R.id.sb_create3:
				tv_create3.setText(String.valueOf(progress));
				break;
			case R.id.sb_diff3:
				tv_diff3.setText(String.valueOf(progress));
				break;
			case R.id.sb_var3:
				tv_var3.setText(String.valueOf(progress));
				break;
			case R.id.sb_style3:
				tv_style3.setText(String.valueOf(progress));
				break;
		}
	}

	// auto-generated | do nothing
	public void onStartTrackingTouch(SeekBar arg0) {
		// Do Nothing
	}

	// auto-generated | do nothing
	public void onStopTrackingTouch(SeekBar arg0) {
		// Do Nothing
	}

	// button event handler
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_settings:
				try {
					btn_settings.setBackgroundResource(R.drawable.settings_pressed);
					Intent intent = new Intent(FinalsActivity.this, SettingsActivity.class);
					intent.putExtra("username", username);
					FinalsActivity.this.startActivity(intent);
				} catch (Exception e) {
					Log.i(SelectEventActivity.class.getName(), "Error: " + e.getMessage());
				}
				break;
			case R.id.btn_save:
				startProgressBar(v);
				break;
			case R.id.btn_cancel:
				finish();
				break;
		}
	}

	// handler for radio buttons
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
			case R.id.rb1_land2:
				rg_landVal2 = 10;
				break;
			case R.id.rb2_land2:
				rg_landVal2 = 5;
				break;
			case R.id.rb3_land2:
				rg_landVal2 = 0;
				break;
			case R.id.rb1_land3:
				rg_landVal3 = 10;
				break;
			case R.id.rb2_land3:
				rg_landVal3 = 5;
				break;
			case R.id.rb3_land3:
				rg_landVal3 = 0;
				break;
		}
	}

	// initialize all fields
	public void init() {
		// intent variables
		username = getIntent().getStringExtra("username");
		jumper = getIntent().getStringExtra("jumper");
		event = getIntent().getStringExtra("event");
		
		// map text views
		tv_judge = (TextView) findViewById(R.id.tv_judgeName);
		tv_judge.setText(username);
		tv_jumper = (TextView) findViewById(R.id.tv_jumperName);
		tv_jumper.setText(jumper);
		tv_event = (TextView) findViewById(R.id.tv_eventName);
		tv_event.setText(event);
		
		tv_create1 = (TextView) findViewById(R.id.tv_createVal1);
		tv_diff1 = (TextView) findViewById(R.id.tv_diffVal1);
		tv_var1 = (TextView) findViewById(R.id.tv_varVal1);
		tv_style1 = (TextView) findViewById(R.id.tv_styleVal1);
		
		tv_create2 = (TextView) findViewById(R.id.tv_createVal2);
		tv_diff2 = (TextView) findViewById(R.id.tv_diffVal2);
		tv_var2 = (TextView) findViewById(R.id.tv_varVal2);
		tv_style2 = (TextView) findViewById(R.id.tv_styleVal2);
		
		tv_create3 = (TextView) findViewById(R.id.tv_createVal3);
		tv_diff3 = (TextView) findViewById(R.id.tv_diffVal3);
		tv_var3 = (TextView) findViewById(R.id.tv_varVal3);
		tv_style3 = (TextView) findViewById(R.id.tv_styleVal3);
		
		// map seekbars
		sb_create1 = (SeekBar) findViewById(R.id.sb_create1);
		sb_diff1 = (SeekBar) findViewById(R.id.sb_diff1);
		sb_var1 = (SeekBar) findViewById(R.id.sb_var1);
		sb_style1 = (SeekBar) findViewById(R.id.sb_style1);
		sb_create1.setOnSeekBarChangeListener(this);
		sb_diff1.setOnSeekBarChangeListener(this);
		sb_var1.setOnSeekBarChangeListener(this);
		sb_style1.setOnSeekBarChangeListener(this);
		
		sb_create2 = (SeekBar) findViewById(R.id.sb_create2);
		sb_diff2 = (SeekBar) findViewById(R.id.sb_diff2);
		sb_var2 = (SeekBar) findViewById(R.id.sb_var2);
		sb_style2 = (SeekBar) findViewById(R.id.sb_style2);
		sb_create2.setOnSeekBarChangeListener(this);
		sb_diff2.setOnSeekBarChangeListener(this);
		sb_var2.setOnSeekBarChangeListener(this);
		sb_style2.setOnSeekBarChangeListener(this);
		
		sb_create3 = (SeekBar) findViewById(R.id.sb_create3);
		sb_diff3 = (SeekBar) findViewById(R.id.sb_diff3);
		sb_var3 = (SeekBar) findViewById(R.id.sb_var3);
		sb_style3 = (SeekBar) findViewById(R.id.sb_style3);
		sb_create3.setOnSeekBarChangeListener(this);
		sb_diff3.setOnSeekBarChangeListener(this);
		sb_var3.setOnSeekBarChangeListener(this);
		sb_style3.setOnSeekBarChangeListener(this);
				
		// map Radio Groups
		rg_land2 = (RadioGroup) findViewById(R.id.rg_land2);
		rg_land3 = (RadioGroup) findViewById(R.id.rg_land3);
		rg_land2.setOnCheckedChangeListener(this);
		rg_land3.setOnCheckedChangeListener(this);
		
		// map Buttons
		btn_settings = (Button) findViewById(R.id.btn_settings);
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_settings.setOnClickListener(this);
		btn_save.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
	}

	// execute http Post an retrieve values for all fields
	public void loadValues() {
		String res = null;
		JSONArray scoreArray;
		// add POST data
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("Judge", username));
		postParameters.add(new BasicNameValuePair("Jumper", jumper));
		postParameters.add(new BasicNameValuePair("Event", event));
		
		// send POST
		try {
			res = HttpWebService.executeHttpPost("http://www.bouncetoacure.com/php_files/GetJudgeResults.php", postParameters);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// parse JSON response
		try {
			scoreArray = new JSONArray(res);
			for (int i = 0; i < scoreArray.length(); i++) {
				JSONObject jsonObject = scoreArray.getJSONObject(i);
				if (jsonObject.get("Type").toString().matches("jam1")) {
					setResults(sb_create1, tv_create1, Integer.valueOf(jsonObject.getString("Creativity").toString()));
					setResults(sb_diff1, tv_diff1, Integer.valueOf(jsonObject.getString("Difficulty").toString()));
					setResults(sb_var1, tv_var1, Integer.valueOf(jsonObject.getString("Variety").toString()));
					setResults(sb_style1, tv_style1, Integer.valueOf(jsonObject.getString("Style").toString()));
					jam1_flag = true;
				} else if (jsonObject.get("Type").toString().matches("solo1")) {
					setResults(sb_create2, tv_create2, Integer.valueOf(jsonObject.getString("Creativity").toString()));
					setResults(sb_diff2, tv_diff2, Integer.valueOf(jsonObject.getString("Difficulty").toString()));
					setResults(sb_var2, tv_var2, Integer.valueOf(jsonObject.getString("Variety").toString()));
					setResults(sb_style2, tv_style2, Integer.valueOf(jsonObject.getString("Style").toString()));
					rg_landVal2 = Integer.valueOf(jsonObject.getString("Landings"));
					switch (rg_landVal2) {
						case 0:
							rg_land2.check(R.id.rb3_land2);
							break;
						case 5:
							rg_land2.check(R.id.rb2_land2);
							break;
						case 10:
							rg_land2.check(R.id.rb1_land2);
							break;
					}
					solo1_flag = true;
				} else if (jsonObject.get("Type").toString().matches("solo2")) {
					setResults(sb_create3, tv_create3, Integer.valueOf(jsonObject.getString("Creativity").toString()));
					setResults(sb_diff3, tv_diff3, Integer.valueOf(jsonObject.getString("Difficulty").toString()));
					setResults(sb_var3, tv_var3, Integer.valueOf(jsonObject.getString("Variety").toString()));
					setResults(sb_style3, tv_style3, Integer.valueOf(jsonObject.getString("Style").toString()));
					rg_landVal3 = Integer.valueOf(jsonObject.getString("Landings"));
					switch (rg_landVal3) {
						case 0:
							rg_land3.check(R.id.rb3_land3);
							break;
						case 5:
							rg_land3.check(R.id.rb2_land3);
							break;
						case 10:
							rg_land3.check(R.id.rb1_land3);
							break;
					}
					solo2_flag = true;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (!jam1_flag) {
			setResults(sb_create1, tv_create1, 0);
			setResults(sb_diff1, tv_diff1, 0);
			setResults(sb_var1, tv_var1, 0);
			setResults(sb_style1, tv_style1, 0);
		}
		if (!solo1_flag) {
			setResults(sb_create2, tv_create2, 0);
			setResults(sb_diff2, tv_diff2, 0);
			setResults(sb_var2, tv_var2, 0);
			setResults(sb_style2, tv_style2, 0);
			rg_land2.check(R.id.rb1_land2);
			rg_landVal2 = 10;
		}
		if (!solo2_flag) {
			setResults(sb_create3, tv_create3, 0);
			setResults(sb_diff3, tv_diff3, 0);
			setResults(sb_var3, tv_var3, 0);
			setResults(sb_style3, tv_style3, 0);
			rg_land3.check(R.id.rb1_land3);
			rg_landVal3 = 10;
		}
	}

	// take values from screen and submit to database via web service
	public boolean setValues() {
		String res;
		ArrayList<NameValuePair> postParameters;
		
		// Jam 1 POST data
		res = null;
		postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("Judge", username));
		postParameters.add(new BasicNameValuePair("Jumper", jumper));
		postParameters.add(new BasicNameValuePair("Event", event));
		postParameters.add(new BasicNameValuePair("Type", "jam1"));
		postParameters.add(new BasicNameValuePair("Create", tv_create1.getText().toString()));
		postParameters.add(new BasicNameValuePair("Diff", tv_diff1.getText().toString()));
		postParameters.add(new BasicNameValuePair("Var", tv_var1.getText().toString()));
		postParameters.add(new BasicNameValuePair("Style", tv_style1.getText().toString()));
		postParameters.add(new BasicNameValuePair("Land", "0"));
		
		// determine if updating or inserting field
		if (jam1_flag) {
			try {
				res = HttpWebService.executeHttpPost("http://www.bouncetoacure.com/php_files/UpdateJudgeResults.php", postParameters);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				res = HttpWebService.executeHttpPost("http://www.bouncetoacure.com/php_files/SetJudgeResults.php", postParameters);
				jam1_flag = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// Solo 1 POST data
		res = null;
		postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("Judge", username));
		postParameters.add(new BasicNameValuePair("Jumper", jumper));
		postParameters.add(new BasicNameValuePair("Event", event));
		postParameters.add(new BasicNameValuePair("Type", "solo1"));
		postParameters.add(new BasicNameValuePair("Create", tv_create2.getText().toString()));
		postParameters.add(new BasicNameValuePair("Diff", tv_diff2.getText().toString()));
		postParameters.add(new BasicNameValuePair("Var", tv_var2.getText().toString()));
		postParameters.add(new BasicNameValuePair("Style", tv_style2.getText().toString()));
		postParameters.add(new BasicNameValuePair("Land", String.valueOf(rg_landVal2)));
		
		// determine if updating or inserting field
		if (solo1_flag) {
			try {
				res = HttpWebService.executeHttpPost("http://www.bouncetoacure.com/php_files/UpdateJudgeResults.php", postParameters);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				res = HttpWebService.executeHttpPost("http://www.bouncetoacure.com/php_files/SetJudgeResults.php", postParameters);
				solo1_flag = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// Solo 2 POST data
		res = null;
		postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("Judge", username));
		postParameters.add(new BasicNameValuePair("Jumper", jumper));
		postParameters.add(new BasicNameValuePair("Event", event));
		postParameters.add(new BasicNameValuePair("Type", "solo2"));
		postParameters.add(new BasicNameValuePair("Create", tv_create3.getText().toString()));
		postParameters.add(new BasicNameValuePair("Diff", tv_diff3.getText().toString()));
		postParameters.add(new BasicNameValuePair("Var", tv_var3.getText().toString()));
		postParameters.add(new BasicNameValuePair("Style", tv_style3.getText().toString()));
		postParameters.add(new BasicNameValuePair("Land", String.valueOf(rg_landVal3)));
		
		// determine if updating or inserting field
		if (solo2_flag) {
			try {				
				res = HttpWebService.executeHttpPost("http://www.bouncetoacure.com/php_files/UpdateJudgeResults.php", postParameters);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				res = HttpWebService.executeHttpPost("http://www.bouncetoacure.com/php_files/SetJudgeResults.php", postParameters);
				solo2_flag = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// check response
		if (res.contains("null")) {
			error = true;
		} else {
			return true;
		}
		
		return false;
	}

	// set the seeker bar progress
	public void setResults(SeekBar sb, TextView tv, int progress) {
		sb.setProgress(progress);
		tv.setText(String.valueOf(progress));
	}

	// dialog for setting values
	public void startProgressBar(View v) {
		showDialog(1);
		new Thread(new Runnable() {
			public void run() {
				try {
					if (setValues()) {
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
	
	// handler for dialog dismissal
	public void onDismiss(DialogInterface dialog) {
		System.out.println(error);
		if (error) {
			//new AlertMessage(context, "Incorrect username and/or password");
		}
	}
}
