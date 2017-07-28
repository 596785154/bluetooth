package com.example.bluetooth_text;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button button;
    private BluetoothSocket clientSocket;
    String inMsg;
    InputStream inputStream;
    DataInputStream data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button=(Button)findViewById(R.id.button1);	
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 获得BluetoothAdapter对象，该API是android 2.0开始支持的
				BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
				// adapter不等于null，说明本机有蓝牙设备
				if (adapter != null) {
					System.out.println("本机有蓝牙设备！");
					// 如果蓝牙设备未开启
					if (!adapter.isEnabled()) {
						Intent intent = new Intent(
								BluetoothAdapter.ACTION_REQUEST_ENABLE);
						// 请求开启蓝牙设备
						startActivity(intent);
					}
					// 获得已配对的远程蓝牙设备的集合
					Set<BluetoothDevice> devices = adapter.getBondedDevices();
					if (devices.size() > 0) {
						for (Iterator<BluetoothDevice> it = devices.iterator(); it
								.hasNext();) {
							BluetoothDevice device = (BluetoothDevice) it
									.next();
							// 打印出远程蓝牙设备的物理地址
							System.out.println("aaaaaaa");
							System.out.println(device.getAddress());

							boolean bool_one = (device.getName()
									.equals("Galaxy Nexus na2"));//判断要连接的远程主机的名字
							System.out.println("bbbbbbbbbbb");
							System.out.println("远程主机的名字："+device.getName());
							if (bool_one) {
								try {
									System.out.println("cccccccccc");
									clientSocket = device
											.createRfcommSocketToServiceRecord(UUID
													.fromString("00001101-0000-1000-8000-00805F9B34FB"));
									System.out.println("dddddddddd");
									System.out.println("zzzzzzz"+clientSocket);
									clientSocket.connect();
									System.out.println("eeeeeeeee");
								} catch (IOException e) {
									System.out.println("fffffffffff");
								}
								try {
									System.out.println("ggggggggggg");
									inputStream = clientSocket.getInputStream();
									System.out.println("hhhhhhhhhh");
									data=new DataInputStream(inputStream);
									System.out.println("jjjjjjjjj");
									if(data!=null){
										System.out.println("asdsdsgfdgdrghrt"+data);
									inMsg=data.readUTF();
									if(inMsg!=null){
										
										System.out.println("输入流为："+inMsg);
									}else{
										System.out.println("输入流为：null");
									}
									}else{
										System.out.println("输入流data：null");
									}
								} catch (IOException e) {
									e.printStackTrace();
									System.out.println("kkkkkkkkkk");
								}
							}
						}

					} else {
						System.out.println("还没有已配对的远程蓝牙设备！");
					}
				} else {
					System.out.println("本机没有蓝牙设备！");
				}
			}
		});	
	}
}
