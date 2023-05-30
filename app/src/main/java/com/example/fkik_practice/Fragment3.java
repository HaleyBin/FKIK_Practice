package com.example.fkik_practice;


import android.bluetooth.BluetoothDevice;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Set;

public class Fragment3 extends Fragment implements View.OnClickListener{

    private Button bt1, bt2, bt3, mBtnSendData;
    TextView mTvSendData, mTvReceiveData;
    MainActivity mainact = (MainActivity)MainActivity.mContext;
    public Fragment3(){


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment3, container, false);

        bt1 = (Button) v.findViewById(R.id.btnBluetoothOn);
        bt2 = (Button) v.findViewById(R.id.btnBluetoothOff);
        bt3 = (Button) v.findViewById(R.id.btnConnect);
        mBtnSendData = (Button) v.findViewById(R.id.btnSendData);
        mTvSendData = (EditText) v.findViewById(R.id.tvSendData);
        mTvReceiveData = (TextView) v.findViewById(R.id.tvReceiveData);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        mBtnSendData.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBluetoothOn:
                mainact.bluetoothOn();
            case R.id.btnBluetoothOff:
                mainact.bluetoothOff();
            case R.id.btnConnect:
                mainact.listPairedDevices();
            case R.id.btnSendData:
                if (mainact.mThreadConnectedBluetooth != null) {
                    mainact.mThreadConnectedBluetooth.write(mTvSendData.getText().toString());
                    mTvReceiveData.setText("");
                }
        }
    }
}
