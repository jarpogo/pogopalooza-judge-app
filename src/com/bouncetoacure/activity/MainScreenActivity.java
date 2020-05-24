package com.bouncetoacure.activity;

import com.bouncetoacure.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainScreenActivity extends Activity implements View.OnClickListener{
	
	Button btn_judge, btn_mc, btn_exit;
	
	// called when the activity is first created
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainscreen);
		init();
	}

	// initialize variables 
	public void init() {
		// map buttons
		btn_judge = (Button) findViewById(R.id.btn_judge);
		btn_mc = (Button) findViewById(R.id.btn_mc);
		btn_exit = (Button) findViewById(R.id.btn_exit);
		btn_judge.setOnClickListener(this);
		btn_mc.setOnClickListener(this);
		btn_exit.setOnClickListener(this);
	}
	
	// handler for buttons
	public void onClick(View v) {
		Intent intent;
		
		switch (v.getId()) {
			case R.id.btn_judge:
				intent = new Intent(MainScreenActivity.this, LoginActivity.class);
				MainScreenActivity.this.startActivity(intent);
				break;
			case R.id.btn_mc:
				intent = new Intent(MainScreenActivity.this, EmceeActivity.class);
				MainScreenActivity.this.startActivity(intent);
				break;
			case R.id.btn_exit:
				finish();
				break;
		}
		
	}
}
