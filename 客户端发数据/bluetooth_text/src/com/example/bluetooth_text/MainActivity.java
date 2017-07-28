package com.example.bluetooth_text;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button time_task, time_equal, time_cancle;
	private EditText edit1, edit2, edit3, edit4;
	private BluetoothSocket clientSocket;
	String inMsg;
	InputStream inputStream;
	DataInputStream data;
	OutputStream output;
	DataOutputStream dataoutput;
	byte[] a={0x30, 0x30 };
	byte[] b={0x30, 0x30 , 0x30, 0x30};
	byte[] time={0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30 };
	byte[] timeTask={0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30 , 0x30, 0x30, 0x30, 0x30, 0x30};
    byte mark=0x2c;
	byte[] off = { 0x6f, 0x66, 0x66 };
	boolean boo=true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		time_task = (Button) findViewById(R.id.time_task);
		time_equal = (Button) findViewById(R.id.time_equal);
		time_cancle = (Button) findViewById(R.id.time_cancle);
		edit1 = (EditText) findViewById(R.id.edit1);
		edit2 = (EditText) findViewById(R.id.edit2);
		edit3 = (EditText) findViewById(R.id.edit3);
		edit4 = (EditText) findViewById(R.id.edit4);
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		if (adapter != null) {
			if (!adapter.isEnabled()) {
				Intent intent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivity(intent);
			}
			Set<BluetoothDevice> devices = adapter.getBondedDevices();
			if (devices.size() > 0) {
				for (Iterator<BluetoothDevice> it = devices.iterator(); it
						.hasNext();) {
					BluetoothDevice device = (BluetoothDevice) it.next();
					System.out.println("aaaaaaa");
					System.out.println(device.getAddress());
					boolean bool_one = (device.getName().equals("Galaxy Nexus na2"));// 判断要连接的远程主机的名字
					System.out.println("bbbbbbbbbbb");
					System.out.println("远程主机的名字：" + device.getName());
					if (bool_one) {
						try {
							System.out.println("cccccccccc");
							clientSocket = device
									.createRfcommSocketToServiceRecord(UUID
											.fromString("00001101-0000-1000-8000-00805F9B34FB"));
							System.out.println("dddddddddd");
							System.out.println("zzzzzzz" + clientSocket);
							clientSocket.connect();
							System.out.println("eeeeeeeee");
						} catch (IOException e) {
							System.out.println("fffffffffff");
						}
					}
				}
			} else {
				System.out.println("还没有已配对的远程蓝牙设备！");
			}
		} else {
			System.out.println("本机没有蓝牙设备！");
		}
		time_task.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				System.out.println("开始boo："+boo);
				if(boo){
				System.out.println("执行boo："+boo);
				int hour, minute, second, mesc;
				/*String str1 = edit1.getText().toString().trim();//这里trim()作用是去掉首位空格，防止不必要的错误  
				if(TextUtils.isEmpty(str1)){  
				//为空  
				}  */
				String hourStr=edit1.getText().toString().trim();
				String minuteStr=edit2.getText().toString().trim();
				String secondStr=edit3.getText().toString().trim();
				String mescStr=edit4.getText().toString().trim();
				if(TextUtils.isEmpty(hourStr)||TextUtils.isEmpty(minuteStr)
						||TextUtils.isEmpty(secondStr)||TextUtils.isEmpty(mescStr)){
					System.out.println("数值不能为空：");
					Intent intent=new Intent();  
	                //设置Intent的Action属性  
	                intent.setAction("com.song.124");  
	                //如果只传一个bundle的信息，可以不包bundle，直接放在intent里  
	                intent.putExtra("message","数值不能为空："); 
	                //发送广播  
	                sendBroadcast(intent);  
					
				}
				else{
				hour = Integer.parseInt(edit1.getText().toString());
				minute = Integer.parseInt(edit2.getText().toString());
				second = Integer.parseInt(edit3.getText().toString());
				mesc = Integer.parseInt(edit4.getText().toString());
				if (hour >= 0 && hour <= 24) {
					if (minute >= 0 && minute <= 60) {
						if (second >= 0 && second <= 60) {
							if(mesc>=0&&mesc<=9999){
							try {
								// 发送数据
								output = clientSocket.getOutputStream();
								System.out.println("121212121212");
								dataoutput = new DataOutputStream(output);
								System.out.println("获得Dataoutputstream："
										+ dataoutput);
								byte[] hours=exchange(hour);
								timeTask[0]=hours[0];
								timeTask[1]=hours[1];
								timeTask[2]=mark;
								System.out.println("我是timeTask[1,2,3]");
								byte[] minutes=exchange(minute);
								timeTask[3]=minutes[0];
								timeTask[4]=minutes[1];
								timeTask[5]=mark;
								System.out.println("我是timeTask[4,5,6]");
								byte[] seconds=exchange(second);
								timeTask[6]=seconds[0];
								timeTask[7]=seconds[1];
								timeTask[8]=mark;
								System.out.println("我是timeTask[7,8,9]");
								byte[] mescs=exchanges(mesc);
								timeTask[9]=mescs[0];
								timeTask[10]=mescs[1];
								timeTask[11]=mescs[2];
								timeTask[12]=mescs[3];
								System.out.println("我是timeTask[10,11,12,13]");
								dataoutput.write(timeTask);
								String str=new String(timeTask);
								System.out.println("向流里写数据："+timeTask);
								System.out.println("向流里写数据："+str);
								dataoutput.flush();
								System.out.println("刷新数据：");
								boo=false;
								System.out.println("结束boo："+boo);
								// dataoutput.close();
								// clientSocket.close();
							} catch (IOException e) {
								e.printStackTrace();
								System.out.println("kkkkkkkkkk");
							}
						}else{edit4.setText("数字范围：0-9999");}
						} else {
							edit3.setText("数字范围：0-60");
						}
					} else {
						edit2.setText("数字范围：0-60");
					}
				} else {
					edit1.setText("数字范围：0-24");
				}
				}
			}
			else{
				System.out.println("不能重复定时：");
				/*Intent intent=new Intent("com.example.MainActivity");
				intent.putExtra("message","不能重复定时");
				sendBroadcast(intent);*/
				Intent intent=new Intent();  
                //设置Intent的Action属性  
                intent.setAction("com.song.123");  
                //如果只传一个bundle的信息，可以不包bundle，直接放在intent里  
                intent.putExtra("message","不能重复定时"); 
                //发送广播  
                sendBroadcast(intent);  
			}
		}
		});
		time_equal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time
										// Zone资料。
				t.setToNow(); // 取得系统时间。
				int year = t.year;
				System.out.println("现在是：" + year + "年");
				int month = t.month;
				System.out.println("现在是：" + month + "月");
				int date = t.monthDay;
				System.out.println("现在是：" + date + "号");
				int hour = t.hour;
				System.out.println("现在是：" + hour + "时");
				int minute = t.minute;
				System.out.println("现在是：" + minute + "分");
				int second = t.second;
				System.out.println("现在是：" + second + "秒");
				try {
					output = clientSocket.getOutputStream();
					System.out.println("121212121212");
					dataoutput = new DataOutputStream(output);
					System.out.println("获得Dataoutputstream：" + dataoutput);
					System.out.println("准备调用转换函数");
					byte[] hours=exchange(hour);
					 time[0]=hours[0];
					 System.out.println("byte数组："+time[0]);
					 time[1]=hours[1];
					 System.out.println("byte数组："+time[1]);
					 time[2]=mark;
					 System.out.println("byte数组："+time[2]);
					 byte[] minutes=exchange(minute);
					 time[3]=minutes[0];
					 time[4]=minutes[1];
					 time[5]=mark;
					 byte[] seconds=exchange(second);
					 time[6]=seconds[0];
					 time[7]=seconds[1];
					dataoutput.write(time);
					String str=new String(time);
					System.out.println("向流里写数据："+time);
					System.out.println("向流里写数据："+str);
					System.out.println("向流里写数据：");
					dataoutput.flush();
					System.out.println("刷新数据：");
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("kkkkkkkkkk");
				}
			}

		});
		time_cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				try {
					output = clientSocket.getOutputStream();
					System.out.println("121212121212");
					dataoutput = new DataOutputStream(output);
					System.out.println("获得Dataoutputstream：" + dataoutput);
					dataoutput.write(off);
					String str=new String(off);
					System.out.println("向流里写数据："+off);
					System.out.println("向流里写数据："+str);
					dataoutput.flush();
					System.out.println("刷新数据：");
					boo=true;
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("kkkkkkkkkk");
				}
			}

		});
	}
	public byte[] exchange(int x){
		System.out.println("int b,c");
		int b,c;
		System.out.println("int b,c");
		b=x/10;
		System.out.println("b=x/10"+b);
		c=x%10;
		System.out.println("c=x%10"+c);
		if(b==0){
			a[0]=0x30;System.out.println("a[0]=0x30:"+a[0]+","+0x30);
		}else if(b==1){
			a[0]=0x31;System.out.println("a[0]=0x31:"+a[0]+","+0x31);
		}else if(b==2){
			a[0]=0x32;System.out.println("a[0]=0x32:"+a[0]+","+0x32);
		}else if(b==3){
			a[0]=0x33;System.out.println("a[0]=0x33:"+a[0]+","+0x33);
		}else if(b==4){
			a[0]=0x34;System.out.println("a[0]=0x34:"+a[0]+","+0x34);
		}else if(b==5){
			a[0]=0x35;System.out.println("a[0]=0x35:"+a[0]+","+0x35);
		}else if(b==6){
			a[0]=0x36;System.out.println("a[0]=0x36:"+a[0]+","+0x36);
		}else if(b==7){
			a[0]=0x37;System.out.println("a[0]=0x37:"+a[0]+","+0x37);
		}else if(b==8){
			a[0]=0x38;System.out.println("a[0]=0x38:"+a[0]+","+0x38);
		}else if(b==9){
			a[0]=0x39;System.out.println("a[0]=0x39:"+a[0]+","+0x39);
		}else{
			System.out.println("输入的数值不对：");
		}
		
		if(c==0){
			a[1]=0x30;System.out.println("a[1]=0x30:"+a[1]+","+0x30);
		}else if(c==1){
			a[1]=0x31;System.out.println("a[1]=0x30:"+a[1]+","+0x30);
		}else if(c==2){
			a[1]=0x32;System.out.println("a[1]=0x30:"+a[1]+","+0x30);
		}else if(c==3){
			a[1]=0x33;System.out.println("a[1]=0x30:"+a[1]+","+0x30);
		}else if(c==4){
			a[1]=0x34;System.out.println("a[1]=0x30:"+a[1]+","+0x30);
		}else if(c==5){
			a[1]=0x35;System.out.println("a[1]=0x30:"+a[1]+","+0x30);
		}else if(c==6){
			a[1]=0x36;System.out.println("a[1]=0x30:"+a[1]+","+0x30);
		}else if(c==7){
			a[1]=0x37;System.out.println("a[1]=0x30:"+a[1]+","+0x30);
		}else if(c==8){
			a[1]=0x38;System.out.println("a[1]=0x30:"+a[1]+","+0x30);
		}else if(c==9){
			a[1]=0x39;System.out.println("a[1]=0x30:"+a[1]+","+0x30);
		}else{
			System.out.println("输入的数值不对：");
		}
		System.out.println("a数组："+a+","+a[0]+","+a[1]);
		return a;
	}
	
	
	public byte[] exchanges(int x){
		int d,c,e,f;
		c=x/1000;
		d=x/100-c*10;
		e=x/10-c*100-d*10;
		f=x%10;
		switch(c){
		case 0:b[0]=0x30;break;
		case 1:b[0]=0x31;break;
		case 2:b[0]=0x32;break;
		case 3:b[0]=0x33;break;
		case 4:b[0]=0x34;break;
		case 5:b[0]=0x35;break;
		case 6:b[0]=0x36;break;
		case 7:b[0]=0x37;break;
		case 8:b[0]=0x38;break;
		case 9:b[0]=0x39;break;
		default:break;
		}
		switch(d){
		case 0:b[1]=0x30;break;
		case 1:b[1]=0x31;break;
		case 2:b[1]=0x32;break;
		case 3:b[1]=0x33;break;
		case 4:b[1]=0x34;break;
		case 5:b[1]=0x35;break;
		case 6:b[1]=0x36;break;
		case 7:b[1]=0x37;break;
		case 8:b[1]=0x38;break;
		case 9:b[1]=0x39;break;
		default:break;
		}
		switch(e){
		case 0:b[2]=0x30;break;
		case 1:b[2]=0x31;break;
		case 2:b[2]=0x32;break;
		case 3:b[2]=0x33;break;
		case 4:b[2]=0x34;break;
		case 5:b[2]=0x35;break;
		case 6:b[2]=0x36;break;
		case 7:b[2]=0x37;break;
		case 8:b[2]=0x38;break;
		case 9:b[2]=0x39;break;
		default:break;
		}
		switch(f){
		case 0:b[3]=0x30;break;
		case 1:b[3]=0x31;break;
		case 2:b[3]=0x32;break;
		case 3:b[3]=0x33;break;
		case 4:b[3]=0x34;break;
		case 5:b[3]=0x35;break;
		case 6:b[3]=0x36;break;
		case 7:b[3]=0x37;break;
		case 8:b[3]=0x38;break;
		case 9:b[3]=0x39;break;
		default:break;
		}
		System.out.println("b数组："+b+","+b[0]+","+b[1]+","+b[2]+","+b[3]);
		return b;
		}	
}
