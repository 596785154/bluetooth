package com.example.bluetooth_text1;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.UUID;
import android.os.Bundle;
import android.os.Message;
import android.provider.SyncStateContract.Constants;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	BluetoothAdapter Adapter = BluetoothAdapter.getDefaultAdapter();
	private BluetoothServerSocket serverSocket;
	private Button button;
	OutputStream output;
	DataOutputStream dataoutput;
	InputStream inputStream;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				UUID MY_UUID = null;
				String NAME = "Galaxy Nexus na2";
				System.out.println("333333");
				BluetoothServerSocket tmp = null;
				System.out.println("4444444444");
				try {
					System.out.println("555555555");
					tmp = Adapter.listenUsingRfcommWithServiceRecord(
							NAME,
							MY_UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
					System.out.println("6666666666");
				} catch (Exception e) {
				}
				serverSocket = tmp;
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO �Զ����ɵķ������
						BluetoothSocket socket = null;
						System.out.println("77777777");
						try {
							socket = serverSocket.accept();
						} catch (IOException e1) {
							// TODO �Զ����ɵ� catch ��
							e1.printStackTrace();
						}
						try {
							System.out.println("ggggggggggg");
							inputStream = socket.getInputStream();
							System.out.println("��ȡ��������ֵ��");
							readSocketInputStream(inputStream);

						} catch (Exception e) {
							System.out.println("err");
						}
					}
				});
				thread.start();
			}
		});
	}

	private void readSocketInputStream(InputStream is) {
		byte[] bits = new byte[1];
		int oldlength = 0;
		boolean boo = true;
		try {
			while (boo) {
				int length = is.read(bits);// ���ȶ�1���ֽڵ�������
				int newLength = is.available();// ��ȡʣ��û�ж���ĳ���
				byte[] bit = new byte[newLength];// �½����鱣��δ�����ֽ�
				byte[] total = new byte[length + newLength];// �½����鱣�������ֽ�
				is.read(bit);// ��û�ж�������ݱ��浽�½������鵱��
				System.arraycopy(bits, 0, total, 0, bits.length);// ����
				System.arraycopy(bit, 0, total, bits.length, bit.length);
				String s = new String(total);
				System.out.println("���ݳ����ǣ�" + (length + newLength) + ";�����ǣ�"
						+ s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
