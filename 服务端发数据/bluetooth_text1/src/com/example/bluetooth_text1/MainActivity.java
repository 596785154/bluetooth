package com.example.bluetooth_text1;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.UUID;
import android.os.Bundle;
import android.os.Message;
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
	BluetoothAdapter Adapter= BluetoothAdapter.getDefaultAdapter();
	private BluetoothServerSocket serverSocket;
	private Button button;
	OutputStream output;
	DataOutputStream dataoutput;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button=(Button)findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				UUID MY_UUID = null; 		
		 		String NAME = "Galaxy Nexus na2";
		 		  System.out.println("333333");
		          BluetoothServerSocket tmp = null; 
		          System.out.println("4444444444");
		          try {  
		         	 System.out.println("555555555");
		             tmp =Adapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
		             System.out.println("6666666666");
		          } catch (Exception e) { }
		           serverSocket = tmp; 
				Thread thread=new Thread(new Runnable(){				
					@Override
					public void run() {
						// TODO 自动生成的方法存根
						 BluetoothSocket socket; 
				          while (true) { 
				              try {  
				            	  System.out.println("77777777");
				                  socket = serverSocket.accept();  
				                  System.out.println("888888888"); 
				                  output = socket.getOutputStream();
				                  System.out.println("121212121212");
				  				  dataoutput = new DataOutputStream(output);
				  				System.out.println("获得Dataoutputstream："+dataoutput);
				  				  String outgoingMsg ="1111";
				  				System.out.println("获得outgoingMsg"+outgoingMsg);
				  				  dataoutput.writeUTF(outgoingMsg);
				  				System.out.println("向流里写数据：");
				  				  dataoutput.flush();
				  				System.out.println("刷新数据：");
				  				  socket.close();
				              } catch (Exception e) { 
				                  break;  
				             } 
				             if (socket != null) {  
				                  // Do work to manage the connection (in a separate thread)
				            	 System.out.println("9999999999");
				             	try {
				 					serverSocket.close();
				 				} catch (Exception e) {
				 					
				 					e.printStackTrace();
				 				}  
				                  break;  
				              }  else{
				            	  System.out.println("101-10");
				              }
				          }  
					}	
				});
				thread.start();
			}
		});
	}
}
