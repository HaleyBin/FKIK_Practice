package com.example.fkik_practice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment2 extends Fragment implements View.OnClickListener{
    Button j_c;
    Button bt_1x_pre, bt_1x_next;
    Button bt_2y_pre, bt_2y_next;
    Button bt_3z_pre, bt_3z_next;
    Button bt_4r_pre, bt_4r_next;
    Button bt_5p_pre, bt_5p_next;
    Button bt_6y_pre, bt_6y_next;
    Button bt_Move;

    TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_6;

    EditText et_1, et_2, et_3, et_4, et_5, et_6;

    private boolean JC_State = false;

    public Fragment2(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment2, container, false);

        j_c = (Button) v.findViewById(R.id.Remote_ToggleButton_JointCartesian);

        bt_1x_next = (Button) v.findViewById(R.id.Remote_Button_Joint1X_Next);
        bt_1x_pre = (Button) v.findViewById(R.id.Remote_Button_Joint1X_Pre);
        bt_2y_next = (Button) v.findViewById(R.id.Remote_Button_Joint2y_Next);
        bt_2y_pre = (Button) v.findViewById(R.id.Remote_Button_Joint2y_Pre);
        bt_3z_next = (Button) v.findViewById(R.id.Remote_Button_Joint3z_Next);
        bt_3z_pre = (Button) v.findViewById(R.id.Remote_Button_Joint3z_Pre);
        bt_4r_next = (Button) v.findViewById(R.id.Remote_Button_Joint4Roll_Next);
        bt_4r_pre = (Button) v.findViewById(R.id.Remote_Button_Joint4Roll_Pre);
        bt_5p_next = (Button) v.findViewById(R.id.Remote_Button_Joint5Pitch_Next);
        bt_5p_pre = (Button) v.findViewById(R.id.Remote_Button_Joint5Pitch_Pre);
        bt_6y_next = (Button) v.findViewById(R.id.Remote_Button_Joint6Yaw_Next);
        bt_6y_pre = (Button) v.findViewById(R.id.Remote_Button_Joint6Yaw_Pre);

        bt_Move = (Button) v.findViewById(R.id.Remote_Button_Move);

        tv_1 = (TextView) v.findViewById(R.id.Remote_TextView_Joint1X);
        tv_2 = (TextView) v.findViewById(R.id.Remote_TextView_Joint2Y);
        tv_3 = (TextView) v.findViewById(R.id.Remote_TextView_Joint3z);
        tv_4 = (TextView) v.findViewById(R.id.Remote_TextView_Joint4Roll);
        tv_5 = (TextView) v.findViewById(R.id.Remote_TextView_Joint5Pitch);
        tv_6 = (TextView) v.findViewById(R.id.Remote_TextView_Joint6Yaw);

        et_1 = (EditText) v.findViewById(R.id.Remote_EditText_Joint1X);
        et_2 = (EditText) v.findViewById(R.id.Remote_EditText_Joint2y);
        et_3 = (EditText) v.findViewById(R.id.Remote_EditText_Joint3z);
        et_4 = (EditText) v.findViewById(R.id.Remote_EditText_Joint4Roll);
        et_5 = (EditText) v.findViewById(R.id.Remote_EditText_Joint5Pitch);
        et_6 = (EditText) v.findViewById(R.id.Remote_EditText_Joint6Yaw);

        et_1.setText(String.valueOf(0));
        et_2.setText(String.valueOf(0));
        et_3.setText(String.valueOf(0));
        et_4.setText(String.valueOf(0));
        et_5.setText(String.valueOf(0));
        et_6.setText(String.valueOf(0));

        j_c.setOnClickListener(this);

        bt_1x_next.setOnClickListener(this);
        bt_1x_pre.setOnClickListener(this);
        bt_2y_next.setOnClickListener(this);
        bt_2y_pre.setOnClickListener(this);
        bt_3z_next.setOnClickListener(this);
        bt_3z_pre.setOnClickListener(this);
        bt_4r_next.setOnClickListener(this);
        bt_4r_pre.setOnClickListener(this);
        bt_5p_next.setOnClickListener(this);
        bt_5p_pre.setOnClickListener(this);
        bt_6y_next.setOnClickListener(this);
        bt_6y_pre.setOnClickListener(this);

        bt_Move.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity().getApplicationContext(), String.valueOf(v.getId()), Toast.LENGTH_SHORT).show();
        switch (v.getId()){
            case R.id.Remote_ToggleButton_JointCartesian:
                if(JC_State){
                    JC_State = true;

                    tv_1.setText(R.string.Remote_x);
                    tv_2.setText(R.string.Remote_Y);
                    tv_3.setText(R.string.Remote_Z);
                    tv_4.setText(R.string.Remote_Roll);
                    tv_5.setText(R.string.Remote_Pitch);
                    tv_6.setText(R.string.Remote_Yaw);
                    
                    //수치 받아온걸로 바꾸기
                    
                }else{
                    JC_State = false;
                    tv_1.setText(R.string.Remote_J1);
                    tv_2.setText(R.string.Remote_J2);
                    tv_3.setText(R.string.Remote_J3);
                    tv_4.setText(R.string.Remote_J4);
                    tv_5.setText(R.string.Remote_J5);
                    tv_6.setText(R.string.Remote_J6);
                    
                    //수치 받아온걸로 바꾸기
                    

                }
            case R.id.Remote_Button_Joint1X_Next:
                et_1.setText(String.valueOf(Integer.parseInt(String.valueOf(et_1.getText())) + 1));
            case R.id.Remote_Button_Joint1X_Pre:
                et_1.setText(String.valueOf(Integer.parseInt(String.valueOf(et_1.getText())) - 1));
            case R.id.Remote_Button_Joint2y_Next:
                et_2.setText(String.valueOf(Integer.parseInt(String.valueOf(et_2.getText())) + 1));
            case R.id.Remote_Button_Joint2y_Pre:
                et_2.setText(String.valueOf(Integer.parseInt(String.valueOf(et_2.getText())) - 1));
            case R.id.Remote_Button_Joint3z_Next:
                et_3.setText(String.valueOf(Integer.parseInt(String.valueOf(et_3.getText())) + 1));
            case R.id.Remote_Button_Joint3z_Pre:
                et_3.setText(String.valueOf(Integer.parseInt(String.valueOf(et_3.getText())) - 1));
            case R.id.Remote_Button_Joint4Roll_Next:
                et_4.setText(String.valueOf(Integer.parseInt(String.valueOf(et_4.getText())) + 1));
            case R.id.Remote_Button_Joint4Roll_Pre:
                et_4.setText(String.valueOf(Integer.parseInt(String.valueOf(et_4.getText())) - 1));
            case R.id.Remote_Button_Joint5Pitch_Next:
                et_5.setText(String.valueOf(Integer.parseInt(String.valueOf(et_5.getText())) + 1));
            case R.id.Remote_Button_Joint5Pitch_Pre:
                et_5.setText(String.valueOf(Integer.parseInt(String.valueOf(et_5.getText())) - 1));
            case R.id.Remote_Button_Joint6Yaw_Next:
                et_6.setText(String.valueOf(Integer.parseInt(String.valueOf(et_6.getText())) + 1));
            case R.id.Remote_Button_Joint6Yaw_Pre:
                et_6.setText(String.valueOf(Integer.parseInt(String.valueOf(et_6.getText())) - 1));
            case R.id.Remote_Button_Move:
                //명령 보내기
                if (JC_State){//Joint

                }else{//Cartesian

                }

        }
    }
}
