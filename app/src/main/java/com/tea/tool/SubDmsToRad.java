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

import java.time.temporal.Temporal;
import java.util.function.DoubleBinaryOperator;

public class SubDmsToRad extends AppCompatActivity {
    private Toolbar toolbar;
    private Double dd,mm,ss,dms,Rad;
    private EditText dd_edit;
    private EditText mm_edit;
    private EditText ss_edit;
    private Button compute;
    private Geopro mGeopro;
    private TextView sum_edit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmstorad);
        //region 标题栏
        toolbar = (Toolbar)findViewById(R.id.toolbar_dmstorad);
        toolbar.setTitle("角度转化为弧度");//设置Toolbar标题
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
        compute=findViewById(R.id.dmstorad_btn1);
        dd_edit=findViewById(R.id.radtodms_edt1);
        mm_edit=findViewById(R.id.radtodms_edt2);
        ss_edit=findViewById(R.id.radtodms_edt3);
        sum_edit=findViewById(R.id.radtodms_tview1);
        mGeopro=new Geopro();
        compute.setEnabled(false);

        initView();
    }

    private void initView() {
        dd_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                  dd=Double.valueOf(dd_edit.getText().toString());
            }
        });
        mm_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               mm=Double.valueOf(mm_edit.getText().toString());
            }
        });
        ss_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                compute.setEnabled(true);
                compute.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ss= Double.valueOf(ss_edit.getText().toString());
                        dms=dd+mm/100.0+ss/10000.0;
                        Rad=mGeopro.Dms2Rad(dms);
                        String s0=mGeopro.div1(Rad);
                        sum_edit.setText(s0+"rad");
                    }
                });
            }
        });
    }
}
