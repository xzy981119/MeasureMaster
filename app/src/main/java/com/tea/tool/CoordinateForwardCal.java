package com.tea.tool;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tea.measuremaster.R;
import com.tea.widget.CustomeEdittext;

import java.io.BufferedReader;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

public class CoordinateForwardCal extends AppCompatActivity implements CustomeEdittext.OnSuccessListener {
    private Toolbar toolbar;
    private Double x,y,length;
    private Double x2,y2;
    private Double dd,mm,ss,dms,rad;
    private EditText mEditText3,mEditText4,mEditText5;
    private ExtendedEditText mEditText6,mEditText1,mEditText2;
    private TextView mTextView;
    private Button btn1;
    private Geopro mGeopro;
    private CoordinateCal mCoordinateCal;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinate1);
        //region 标题栏
        toolbar = (Toolbar)findViewById(R.id.toolbar_coordinate1);
        toolbar.setTitle("坐标正算");//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //endregion


        mEditText1=findViewById(R.id.coordinate1_edit1);
        mEditText2=findViewById(R.id.coordinate1_edit2);
        mEditText3=findViewById(R.id.coordinate1_edit3);
        mEditText4=findViewById(R.id.coordinate1_edit4);
        mEditText5=findViewById(R.id.coordinate1_edit5);
        mEditText6=findViewById(R.id.coordinate1_edit6);
        mTextView=findViewById(R.id.coordinate1_tview1);
        btn1=findViewById(R.id.coordinate1_btn1);
        btn1.setEnabled(false);
        mGeopro=new Geopro();
        mCoordinateCal=new CoordinateCal();
        initView();
    }

    private void initView() {
        mEditText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                  dd=Double.valueOf(s.toString());
            }
        });
        mEditText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                  mm=Double.valueOf(s.toString());
            }
        });
        mEditText5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               ss=Double.valueOf(s.toString());
            }
        });
        mEditText6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                 length=Double.valueOf(s.toString());
                if(mEditText6.getText().toString()!=""){
                    btn1.setEnabled(true);
                    btn1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            x=Double.valueOf(mEditText1.getText().toString());
                            y=Double.valueOf(mEditText2.getText().toString());
                            dms=dd+mm/100.0+ss/10000.0;
                            rad=mGeopro.Dms2Rad(dms);
                            mCoordinateCal.x1=x;
                            mCoordinateCal.y1=y;
                            mCoordinateCal.rad=rad;
                            mCoordinateCal.length=length;
                            mCoordinateCal.Forward();
                            x2=mCoordinateCal.x2;
                            y2=mCoordinateCal.y2;
                            String x3=mCoordinateCal.div2(x2);
                            String y3=mCoordinateCal.div2(y2);
                            mTextView.setText("终点坐标:x="+x3+"  y="+y3);
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onSuccess(String phone) {

    }
}
