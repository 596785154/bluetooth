package com.example.bluetooth_text;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver1 extends BroadcastReceiver{

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO �Զ����ɵķ������
		String msg=arg1.getStringExtra("message");
		//Toast.makeText(arg0,"�����ظ���ʱ��", Toast.LENGTH_SHORT).show();	
		Toast.makeText(arg0,msg, Toast.LENGTH_SHORT).show();	
	}

}
