package com.tea.tool;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tea.TraverseAdjustment.Adapter.Traverse_qishi;
import com.tea.measuremaster.R;
import com.tea.widget.CustomeEdittext;

public class CoordinateInverseCal extends AppCompatActivity implements CustomeEdittext.OnSuccessListener {
    private Toolbar toolbar;
    private String x,y,x2,y2,length;
    private String deg,minu,sec;
    private Double dms,rad,ss;
    private int dd,mm;
    CustomeEdittext mEditText1,mEditText2,mEditText3,mEditText4;
    private TextView mTextView;
    private Button btn1;
    private Geopro mGeopro;
    private CoordinateCal mCoordinateCal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinate2);
        //region 标题栏
        toolbar = (Toolbar)findViewById(R.id.toolbar_coordinate2);
        toolbar.setTitle("坐标反算");//设置Toolbar标题
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
        mEditText1=findViewById(R.id.coordinate2_edit1);
        mEditText1.setOnSuccessListener(CoordinateInverseCal.this);
        mEditText2=findViewById(R.id.coordinate2_edit2);
        mEditText2.setOnSuccessListener(this);
        mEditText3=findViewById(R.id.coordinate2_edit3);
        mEditText3.setOnSuccessListener(this);
        mEditText4=findViewById(R.id.coordinate2_edit4);
        mEditText4.setOnSuccessListener(this);
        btn1=findViewById(R.id.coordinate2_btn1);
        mTextView=findViewById(R.id.coordinate2_tview1);
        mGeopro=new Geopro();
        mCoordinateCal=new CoordinateCal();
        try {
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    x=mEditText1.getContent();
                    y=mEditText2.getContent();
                    x2=mEditText3.getContent();
                    y2=mEditText4.getContent();
                    if(!TextUtils.isEmpty(x) && !TextUtils.isEmpty(y)&&!TextUtils.isEmpty(x2) && !TextUtils.isEmpty(y2)){
                        mCoordinateCal.x1=Double.valueOf(x);
                        mCoordinateCal.y1=Double.valueOf(y);
                        mCoordinateCal.x2=Double.valueOf(x2);
                        mCoordinateCal.y2=Double.valueOf(y2);
                        mCoordinateCal.Inverse();
                        mCoordinateCal.getLength();
                        length=mGeopro.div2(Double.valueOf(mCoordinateCal.length));
                        rad=mCoordinateCal.rad;
                        dms=mGeopro.Rad2Dms(rad);
                        dd=(int)(Math.floor(dms));
                        deg=String.valueOf(dd);
                        mm=(int)(Math.floor((dms - dd) * 100.0));
                        minu=String.valueOf(mm);
                        ss=(dms - dd - mm / 100.0) * 10000;
                        sec=Double.valueOf(ss).toString();
                        sec=mGeopro.div1(Double.valueOf(sec));
                        mTextView.setText("水平距离为："+length+"  方位角为："+deg+"°"+minu+"′"+sec+"″");
                    }
                   else {
                        Toast.makeText(CoordinateInverseCal.this, "请正确填写全部数据", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (Exception e){

            return;
        }
    }


    @Override
    public void onSuccess(String phone) {

    }
}
