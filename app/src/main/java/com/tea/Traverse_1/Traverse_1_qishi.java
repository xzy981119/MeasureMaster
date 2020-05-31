package com.tea.Traverse_1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tea.Traverse.Traben_data;
import com.tea.TraverseAdjustment.Adapter.SubTemplete;
import com.tea.TraverseAdjustment.Adapter.Traverse_qishi;
import com.tea.measuremaster.R;
import com.tea.tool.Geopro;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

public class Traverse_1_qishi extends AppCompatActivity {
    private Toolbar toolbar;
    private Button button;
    private EditText du_first,fen_first,miao_first;
    private ExtendedEditText BX,BY;
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
        setContentView(R.layout.activity_traverse_2_qishi);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//不让自动弹出软键盘

        //region 标题栏
        toolbar = (Toolbar)findViewById(R.id.toolbar_daoxian_1);
        toolbar.setTitle("支导线近似平差计算");//设置Toolbar标题
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
        left = (RadioButton)findViewById(R.id.daoxian_left_1) ;
        right = (RadioButton)findViewById(R.id.daoxian_right_1) ;

        du_first=findViewById(R.id.traverse_2_edt3_ID);
        fen_first=findViewById(R.id.traverse_2_edt4_ID);
        miao_first=findViewById(R.id.traverse_2_edt5_ID);

        BX=findViewById(R.id.traverse_2_edt1_ID);
        BY=findViewById(R.id.traverse_2_edt2_ID);

        button =(Button)findViewById(R.id.traverse_2_btn1_ID);
        //endregion

        //region 清空列表数据，防止退出再进入依旧保存有数据
        Traben_data_1.ceZhan.clear();
        Traben_data_1.disList.clear();
        Traben_data_1.guanCeJList.clear();
        //endregion
        //button的点击事件
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //region 转向选择
                if (left.isChecked()){
                    Traben_data_1.k = 1;
                }
                if (right.isChecked()){
                    Traben_data_1.k = -1;
                }
                //endregion
                //region 数据导入
                try {
                    Traben_data_1.fangwei_first = Geopro.DMStohudu(Integer.parseInt(du_first.getText().toString())
                            ,Integer.parseInt(fen_first.getText().toString()),Double.parseDouble(miao_first.getText().toString()));
                    Traben_data_1.B = new Traben_data_1.ZuoBiao(Double.parseDouble(BX.getText().toString())
                            ,Double.parseDouble(BY.getText().toString()));
                }
                catch (Exception e) {
                    Toast.makeText(Traverse_1_qishi.this, "请正确填写全部数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Log.d("Activity生命周期", String.valueOf(Caculate.hudutodms(Daoxian_data.fangwei_first)));
                //endregion
                Intent intent = new Intent(Traverse_1_qishi.this, SubTemplete_1.class);
                startActivity(intent);
            }
        });
        //endregion
    }
}
