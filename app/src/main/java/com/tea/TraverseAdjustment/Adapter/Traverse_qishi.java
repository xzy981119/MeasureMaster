package com.tea.TraverseAdjustment.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tea.Traverse.Station;
import com.tea.Traverse.Traben_data;
import com.tea.measuremaster.R;
import com.tea.tool.Geopro;

import java.util.ArrayList;
import java.util.List;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

public class Traverse_qishi extends AppCompatActivity {
    private Toolbar toolbar;
    private Button button;
    private EditText du_first,fen_first,miao_first;
    private EditText du_end,fen_end,miao_end;
    private ExtendedEditText BX,BY,CX,CY;
    private RadioButton left,right;



    //endregion
    protected void onResume() {
        super.onResume();
        //region 清空列表数据，防止返回首页再进入依旧保存有数据
        Log.i("Activity生命周期","OnResume方法调用");
        Traben_data.ceZhan.clear();
        Traben_data.disList.clear();
        Traben_data.guanCeJList.clear();
        //endregion
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traverse_1);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//不让自动弹出软键盘

        //region 标题栏
        toolbar = (Toolbar)findViewById(R.id.toolbar_daoxian);
        toolbar.setTitle("附和导线近似平差计算");//设置Toolbar标题
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

        //region 找到控件
        left = (RadioButton)findViewById(R.id.daoxian_left) ;
        right = (RadioButton)findViewById(R.id.daoxian_right) ;

        du_first=findViewById(R.id.traverse_1_edt5_ID);
        fen_first=findViewById(R.id.traverse_1_edt6_ID);
        miao_first=findViewById(R.id.traverse_1_edt7_ID);
        du_end=findViewById(R.id.traverse_1_edt8_ID);
        fen_end=findViewById(R.id.traverse_1_edt9_ID);
        miao_end=findViewById(R.id.traverse_1_edt10_ID);

        BX=findViewById(R.id.traverse_1_edt1_ID);
        BY=findViewById(R.id.traverse_1_edt2_ID);
        CX=findViewById(R.id.traverse_1_edt3_ID);
        CY=findViewById(R.id.traverse_1_edt4_ID);

        button =(Button)findViewById(R.id.traverse_btn1_ID);
        //endregion

        //region 清空列表数据，防止退出再进入依旧保存有数据
        Traben_data.ceZhan.clear();
        Traben_data.disList.clear();
        Traben_data.guanCeJList.clear();
        //endregion
        //button的点击事件
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //region 转向选择
                if (left.isChecked()){
                    Traben_data.k = 1;
                }
                if (right.isChecked()){
                    Traben_data.k = -1;
                }
                //endregion
                //region 数据导入
                try {
                    Traben_data.fangwei_first = Geopro.DMStohudu(Integer.parseInt(du_first.getText().toString())
                            ,Integer.parseInt(fen_first.getText().toString()),Double.parseDouble(miao_first.getText().toString()));
                    Traben_data.fangwei_end =Geopro.DMStohudu(Integer.parseInt(du_end.getText().toString())
                            ,Integer.parseInt(fen_end.getText().toString()),Double.parseDouble(miao_end.getText().toString()));
                    Traben_data.B = new Traben_data.ZuoBiao(Double.parseDouble(BX.getText().toString())
                            ,Double.parseDouble(BY.getText().toString()));
                    Traben_data.C = new Traben_data.ZuoBiao(Double.parseDouble(CX.getText().toString())
                            ,Double.parseDouble(CY.getText().toString()));
                }
                catch (Exception e) {
                    Toast.makeText(Traverse_qishi.this, "请正确填写全部数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Log.d("Activity生命周期", String.valueOf(Caculate.hudutodms(Daoxian_data.fangwei_first)));
                //endregion
                Intent intent = new Intent(Traverse_qishi.this,SubTemplete.class);
                startActivity(intent);
            }
        });
        //endregion
    }
}
