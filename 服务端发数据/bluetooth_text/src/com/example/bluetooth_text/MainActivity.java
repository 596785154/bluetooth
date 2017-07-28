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
				// ���BluetoothAdapter���󣬸�API��android 2.0��ʼ֧�ֵ�
				BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
				// adapter������null��˵�������������豸
				if (adapter != null) {
					System.out.println("�����������豸��");
					// ��������豸δ����
					if (!adapter.isEnabled()) {
						Intent intent = new Intent(
								BluetoothAdapter.ACTION_REQUEST_ENABLE);
						// �����������豸
						startActivity(intent);
					}
					// �������Ե�Զ�������豸�ļ���
					Set<BluetoothDevice> devices = adapter.getBondedDevices();
					if (devices.size() > 0) {
						for (Iterator<BluetoothDevice> it = devices.iterator(); it
								.hasNext();) {
							BluetoothDevice device = (BluetoothDevice) it
									.next();
							// ��ӡ��Զ�������豸�������ַ
							System.out.println("aaaaaaa");
							System.out.println(device.getAddress());

							boolean bool_one = (device.getName()
									.equals("Galaxy Nexus na2"));//�ж�Ҫ���ӵ�Զ������������
							System.out.println("bbbbbbbbbbb");
							System.out.println("Զ�����������֣�"+device.getName());
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
										
										System.out.println("������Ϊ��"+inMsg);
									}else{
										System.out.println("������Ϊ��null");
									}
									}else{
										System.out.println("������data��null");
									}
								} catch (IOException e) {
									e.printStackTrace();
									System.out.println("kkkkkkkkkk");
								}
							}
						}

					} else {
						System.out.println("��û������Ե�Զ�������豸��");
					}
				} else {
					System.out.println("����û�������豸��");
				}
			}
		});	
	}
}
