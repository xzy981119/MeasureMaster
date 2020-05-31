package com.tea.ZhLevelLib;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tea.LevelLib.LevelLib_known;
import com.tea.LevelLib.LevelLib_result;
import com.tea.LevelLib.LevelLib_station;
import com.tea.LevelLib.SubTraverse;
import com.tea.measuremaster.R;

public class ZhLevelLib_known extends AppCompatActivity {
    private Toolbar toolbar;
    private Button button;
    private RadioButton rd_2,rd_4;
    private EditText start;

    //存储等级信息
    public static int dengji;
    //存储起终点高程信息
    public static double gaocheng_start,gaocheng_end;

    //endregion
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Activity生命周期","setup 的 OnResume方法调用");

        ZhLevelLib_result.juli.clear();
        ZhLevelLib_result.gaocha.clear();
        ZhLevelLib_result.cezhan.clear();

        LevelLib_station_1.leijishiju = 0;
        LevelLib_station_1.cezhan = "";
        LevelLib_station_1.isnext = false;
        LevelLib_station_2.leijishiju=0;
        LevelLib_station_2.cezhan="";
        LevelLib_station_2.isnext=false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhlevellib_known);

        //region 标题栏
        toolbar = (Toolbar)findViewById(R.id.toolbar_shuizhun_setup_1);
        toolbar.setTitle("支水准路线计算");//设置Toolbar标题
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

        ZhLevelLib_result.juli.clear();
        ZhLevelLib_result.gaocha.clear();
        ZhLevelLib_result.cezhan.clear();
        LevelLib_station_1.leijishiju = 0;
        LevelLib_station_1.cezhan = "";
        LevelLib_station_1.isnext = false;
        LevelLib_station_2.leijishiju=0;
        LevelLib_station_2.cezhan="";
        LevelLib_station_2.isnext=false;

        button = (Button) findViewById(R.id.shuizhun_start_1);
        rd_2 = (RadioButton)findViewById(R.id.shuizhun_2deng_1);
        rd_4 = (RadioButton)findViewById(R.id.shuizhun_4deng_1);
        start = (EditText) findViewById(R.id.shuizhun_gaocheng_start_1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if (rd_2.isChecked()){
                        dengji = 2;
                    }
                    if (rd_4.isChecked()){
                        dengji = 4;
                    }
                    gaocheng_start = Double.parseDouble(start.getText().toString());
                }
                catch (Exception e){
                    Toast.makeText(ZhLevelLib_known.this, "请正确填写全部数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(ZhLevelLib_known.this, SubTraverse_1.class);
                startActivity(intent);
            }
        });
    }
}
