package com.bouncetoacure.components;

import android.app.AlertDialog;
import android.content.Context;

public class AlertMessage {
	
	// alert message that creates custom dialog
	public AlertMessage(Context context, String msg) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setMessage(msg);
		dialog.setCancelable(false);
		dialog.setNeutralButton("OK", null);
    	dialog.create().show();
	}
}
