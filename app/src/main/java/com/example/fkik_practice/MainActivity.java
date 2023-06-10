package com.example.fkik_practice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static Context mContext;
    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;
    private final int FRAGMENT3 = 3;

    private Button bt_tab1, bt_tab2, bt_tab3;
    private ImageButton bt_stop;
    BluetoothAdapter mBluetoothAdapter;
    Set<BluetoothDevice> mPairedDevices;
    List<String> mListPairedDevices;

    Handler mBluetoothHandler;
    ConnectedBluetoothThread mThreadConnectedBluetooth;
    BluetoothDevice mBluetoothDevice;
    BluetoothSocket mBluetoothSocket;

    final static int BT_REQUEST_ENABLE = 1;
    final static int BT_MESSAGE_READ = 2;
    final static int BT_CONNECTING_STATUS = 3;
    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_tab1 = (Button)findViewById(R.id.bt_tab1);
        bt_tab2 = (Button)findViewById(R.id.bt_tab2);
        bt_tab3 = (Button)findViewById(R.id.bt_tab3);
        bt_stop = (ImageButton)findViewById(R.id.stop_button);

        bt_tab1.setOnClickListener(this);
        bt_tab2.setOnClickListener(this);
        bt_tab3.setOnClickListener(this);
        bt_stop.setOnClickListener(this);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        callFragment(FRAGMENT1);
        mContext = this;
        //permissionCheck();
    }

    private void callFragment(int fragment_no) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (fragment_no){
            case 1:
                Fragment1 fragment1 = new Fragment1();
                transaction.replace(R.id.frame, fragment1);
                transaction.commit();
                break;
            case 2:
                Fragment2 fragment2 = new Fragment2();
                transaction.replace(R.id.frame, fragment2);
                transaction.commit();
                break;
            case 3:
                Fragment3 fragment3 = new Fragment3();
                transaction.replace(R.id.frame, fragment3);
                transaction.commit();
                break;
        }
    }

    public void bluetoothOn() {
        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "블루투스를 지원하지 않는 기기입니다.", Toast.LENGTH_LONG).show();
        } else {
            if (mBluetoothAdapter.isEnabled()) {
                Toast.makeText(getApplicationContext(), "블루투스가 이미 활성화 되어 있습니다.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "블루투스가 활성화 되어 있지 않습니다.", Toast.LENGTH_LONG).show();
                Intent intentBluetoothEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivityForResult(intentBluetoothEnable, BT_REQUEST_ENABLE);
            }
        }
    }

    public void bluetoothOff() {
        if (mBluetoothAdapter.isEnabled()) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mBluetoothAdapter.disable();
            Toast.makeText(getApplicationContext(), "블루투스가 비활성화 되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "블루투스가 이미 비활성화 되어 있습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == BT_REQUEST_ENABLE) {
            if (resultCode == RESULT_OK) { // 블루투스 활성화를 확인을 클릭하였다면
                Toast.makeText(getApplicationContext(), "블루투스 활성화", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) { // 블루투스 활성화를 취소를 클릭하였다면
                Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void listPairedDevices() {
        if (mBluetoothAdapter.isEnabled()) {

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mPairedDevices = mBluetoothAdapter.getBondedDevices();

            if (mPairedDevices.size() > 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("장치 선택");

                mListPairedDevices = new ArrayList<String>();
                for (BluetoothDevice device : mPairedDevices) {

                    mListPairedDevices.add(device.getName());
                    //mListPairedDevices.add(device.getName() + "\n" + device.getAddress());
                }
                final CharSequence[] items = mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);
                mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);

                builder.setItems(items, (dialog, item) -> connectSelectedDevice(items[item].toString()));
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                Toast.makeText(getApplicationContext(), "페어링된 장치가 없습니다.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "블루투스가 비활성화 되어 있습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void connectSelectedDevice(String selectedDeviceName) {
        for (BluetoothDevice tempDevice : mPairedDevices) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            if (selectedDeviceName.equals(tempDevice.getName())) {
                mBluetoothDevice = tempDevice;
                break;
            }
        }
        try {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Toast.makeText(getApplicationContext(), "먼지 모름 빠짐", Toast.LENGTH_SHORT).show();
                return;
            }
            mBluetoothSocket = createBluetoothSocket(mBluetoothDevice);
            mBluetoothSocket.connect();
            mThreadConnectedBluetooth = new ConnectedBluetoothThread(mBluetoothSocket);
            mThreadConnectedBluetooth.start();
            mBluetoothHandler.obtainMessage(BT_CONNECTING_STATUS, 1, -1).sendToTarget();
        } catch (IOException e) {
            //Toast.makeText(getApplicationContext(), "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            connectSelectedDevice(selectedDeviceName);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_tab1:
                callFragment(FRAGMENT1);
                break;
            case R.id.bt_tab2:
                callFragment(FRAGMENT2);
                break;
            case R.id.bt_tab3:
                callFragment(FRAGMENT3);
                break;
            case R.id.stop_button:
                //긴급 정지 패킷 발송 부분
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public class ConnectedBluetoothThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedBluetoothThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "소켓 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = mmInStream.available();
                    if (bytes != 0) {
                        SystemClock.sleep(100);
                        bytes = mmInStream.available();
                        bytes = mmInStream.read(buffer, 0, bytes);
                        mBluetoothHandler.obtainMessage(BT_MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                    }
                } catch (IOException e) {
                    break;
                }
            }
        }

        public void write(String str) {
            byte[] bytes = str.getBytes();
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "데이터 전송 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "소켓 해제 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
        }

    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device)
            throws IOException {
        if (Build.VERSION.SDK_INT >= 10) {
            try {
                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[]{UUID.class});
                return (BluetoothSocket) m.invoke(device, BT_UUID);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Could not create Insecure RFComm Connection", Toast.LENGTH_LONG).show();
            }
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        return device.createRfcommSocketToServiceRecord(BT_UUID);
    }
}