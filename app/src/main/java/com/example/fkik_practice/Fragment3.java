package com.example.fkik_practice;


import android.bluetooth.BluetoothDevice;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Set;

public class Fragment3 extends Fragment implements View.OnClickListener{

    private Button bt1, bt2, bt3;
    // 클래스 선언
    private PermissionSupport permission;
    private BluetoothSerialClient client = MainActivity.client;
    private BluetoothSerialClient.BluetoothStreamingHandler handler;



    public Fragment3(){

        //BT Check
        if(client == null){
            Toast.makeText(getActivity().getApplicationContext(), "블루투스를 사용할 수 없는 기기입니다.", Toast.LENGTH_LONG).show();
        }


        //BT Enable
        if(!client.isEnabled()){
            client.enableBluetooth(getActivity().getApplicationContext(), new BluetoothSerialClient.OnBluetoothEnabledListener() {
                @Override
                public void onBluetoothEnabled(boolean success) {
                    //sucess == false >> Bluetooth Disable
                }
            });
        }





        //데이터 전송
        //handler.write("qwer".getBytes());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment3, container, false);

        bt1 = (Button) v.findViewById(R.id.Settings_Button_Bluetooth_Connect);
        bt2 = (Button) v.findViewById(R.id.Settings_Button_Bluetooth_Disconnect);
        bt3 = (Button) v.findViewById(R.id.Settings_Button_Bluetooth_test);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Settings_Button_Bluetooth_Connect:
                //디바이스 스캔
                final ArrayList<BluetoothDevice> devicesList = new ArrayList<BluetoothDevice>();

                Set<BluetoothDevice> pairedDvices = client.getPairedDevices(getActivity().getApplicationContext());
                devicesList.addAll(pairedDvices);

                client.scanDevices(getActivity().getApplicationContext(), new BluetoothSerialClient.OnScanListener() {
                    @Override
                    public void onStart() {
                        // 스캔 시작
                    }

                    @Override
                    public void onFoundDevice(BluetoothDevice bluetoothDevice) {
                        if(devicesList.contains(bluetoothDevice)){
                            devicesList.remove(bluetoothDevice);
                        }
                        devicesList.add(bluetoothDevice);
                    }

                    @Override
                    public void onFinish() {
                        //스캔 종료
                    }
                });
            case R.id.Settings_Button_Bluetooth_Disconnect:
                handler = new BluetoothSerialClient.BluetoothStreamingHandler() {
                    @Override
                    public void onError(Exception e) {
                        //에러, 연결 종료 됨.
                    }

                    @Override
                    public void onConnected() {
                        //연결 이벤트
                    }

                    @Override
                    public void onDisconnected() {
                        //연결 종료 이벤트
                    }

                    @Override
                    public void onData(byte[] buffer, int length) {
                        //블루투스로부터 데이터 이벤트가 발생 (read)
                    }
                };

            case R.id.Settings_Button_Bluetooth_test:
                handler.write("qwer".getBytes());
        }
    }
}
