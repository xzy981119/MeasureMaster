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

public class SubRadToDms extends AppCompatActivity {
    private Toolbar toolbar;
    private Double mRad,mDms,ss;
    private int dd,mm;
    private String deg,minu,sec;
    private Button mButton;
    private TextView mTextView;
    private Geopro mGeopro;
    private EditText mEditText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radtodms);
        //region 标题栏
        toolbar = (Toolbar)findViewById(R.id.toolbar_radtodms);
        toolbar.setTitle("弧度转化为角度");//设置Toolbar标题
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
        mButton=findViewById(R.id.radtodms_btn1);
        mTextView=findViewById(R.id.dmstorad_tview1);
        mEditText=findViewById(R.id.dmstorad_edt1);
        mButton.setEnabled(false);
        mGeopro=new Geopro();
        initView();
    }

    private void initView() {
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mButton.setEnabled(true);
                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRad=Double.valueOf(mEditText.getText().toString());
                        mDms=mGeopro.Rad2Dms(mRad);
                        String s0=mGeopro.div2(mDms);
                        Double s1=Double.valueOf(s0);
                        dd=(int)(Math.floor(s1));
                        deg=String.valueOf(dd);
                        mm=(int)(Math.floor((s1 - dd) * 100.0));
                        minu=String.valueOf(mm);
                        ss=(s1 - dd - mm / 100.0) * 10000;
                        sec=Double.valueOf(ss).toString();
                        sec=mGeopro.div1(Double.valueOf(sec));
                        mTextView.setText(deg+"°"+minu+"′"+sec+"″");
                    }
                });
            }
        });
    }
}
