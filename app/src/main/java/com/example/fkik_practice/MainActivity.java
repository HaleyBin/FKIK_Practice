package com.example.fkik_practice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;
    private final int FRAGMENT3 = 3;

    private Button bt_tab1, bt_tab2, bt_tab3;
    private ImageButton bt_stop;

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

        callFragment(FRAGMENT1);

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
}