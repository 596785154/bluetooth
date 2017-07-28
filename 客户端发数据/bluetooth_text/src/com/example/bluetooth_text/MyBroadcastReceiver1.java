package com.example.bluetooth_text;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver1 extends BroadcastReceiver{

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO 自动生成的方法存根
		String msg=arg1.getStringExtra("message");
		//Toast.makeText(arg0,"不能重复定时：", Toast.LENGTH_SHORT).show();	
		Toast.makeText(arg0,msg, Toast.LENGTH_SHORT).show();	
	}

}
