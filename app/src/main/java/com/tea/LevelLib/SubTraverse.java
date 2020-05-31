package com.tea.LevelLib;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tea.measuremaster.R;
import com.tea.tool.Geopro;

public class SubTraverse extends AppCompatActivity {
    //region 定义控件变量
    private Toolbar toolbar;
    private Button button;

    private EditText hou_black_shang,hou_black_zhong,hou_black_xia,hou_red_zhong,
            qian_black_shang,qian_black_xia,qian_black_zhong,qian_red_zhong;
    private EditText editText_station,editText_K_hou,editText_K_qian;

    private TextView lastStation,leijicha_text;
    //endregion

    //region 定义计算变量
    private String cezhan;
    private double k1,k2;//后尺，前尺
    private double hhs,hhz,hhx;//后黑上，中，下
    private double qhs,qhz,qhx;//前黑上，中，下
    private double hhongz,qhongz;//后红中，前红中
    private String k01,k02;
    private String hhs1,hhz1,hhx1;
    private String qhs1,qhz1,qhx1;
    private String hhongz1,qhongz1;

    private double qianshiju,houshiju,shicha,leijicha;//视距
    private double hou_black_red,qian_black_red,houjianqian,heijianhong;//高差之差
    private double gaocha_black,gaocha_red,gaocha_avg;//高差
    //endregion

    protected void onResume() {
        super.onResume();
        Log.i("Activity生命周期","水准的OnResume方法调用");

        lastStation.setText("上一测站:    "+ LevelLib_station.cezhan);
        leijicha_text.setText("后前视距累积差:   "+ LevelLib_station.leijishiju);
        if (LevelLib_station.isnext){
            //region 清空控件
            hou_black_shang.setText("");
            hou_black_zhong.setText("");
            hou_black_xia.setText("");
            hou_black_shang.setText("");
            qian_black_zhong.setText("");
            qian_black_xia.setText("");
            qian_black_shang.setText("");
            hou_red_zhong.setText("");
            qian_red_zhong.setText("");
            editText_station.setText("");
            editText_K_qian.setText("");
            editText_K_hou.setText("");
            //endregion
        }

        if (LevelLib_known.dengji == 2){
            editText_K_qian.setText("301.550");
            editText_K_hou.setText("301.550");
            editText_K_qian.setFocusable(false);//设置结果不可编辑，但是能响应点击事件
            editText_K_hou.setFocusable(false);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levellib);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//不让自动弹出软键盘

        //region 找到控件
        hou_black_shang = (EditText)findViewById(R.id.shuizhun_hou_black_shang);
        hou_black_zhong = (EditText)findViewById(R.id.shuizhun_hou_black_zhong);
        hou_black_xia = (EditText)findViewById(R.id.shuizhun_hou_black_xia);
        hou_red_zhong = (EditText)findViewById(R.id.shuizhun_hou_red_zhong);
        qian_black_shang = (EditText)findViewById(R.id.shuizhun_qian_black_shang);
        qian_black_xia = (EditText)findViewById(R.id.shuizhun_qian_black_xia);
        qian_black_zhong = (EditText)findViewById(R.id.shuizhun_qian_black_zhong);
        qian_red_zhong = (EditText)findViewById(R.id.shuizhun_qian_red_zhong);
        editText_station = (EditText)findViewById(R.id.shuizhun_station);
        editText_K_hou = (EditText)findViewById(R.id.shuizhun_K_hou);
        editText_K_qian = (EditText)findViewById(R.id.shuizhun_K_qian);
        lastStation = (TextView)findViewById(R.id.shuizhun_lastStation);
        leijicha_text = (TextView)findViewById(R.id.shuizhun_leijicha);
        //endregion

        //region 标题栏
        toolbar = (Toolbar)findViewById(R.id.toolbar_shuizhun);
        toolbar.setTitle("水准路线计算");//设置Toolbar标题
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

        if (LevelLib_known.dengji == 2){
            editText_K_qian.setText("301.550");
            editText_K_hou.setText("301.550");
            editText_K_qian.setFocusable(false);//设置结果不可编辑，但是能响应点击事件
            editText_K_hou.setFocusable(false);
        }

        button = (Button) findViewById(R.id.shuizhun_jisuan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //region 传入数据
                try{
                    cezhan = editText_station.getText().toString();
                    k1 = Double.parseDouble(editText_K_hou.getText().toString());
                    k01=String.valueOf(k1);
                    k2 = Double.parseDouble(editText_K_qian.getText().toString());
                    k02=String.valueOf(k02);
                    hhs = Double.parseDouble(hou_black_shang.getText().toString());
                    hhs1=String.valueOf(hhs);
                    hhx = Double.parseDouble(hou_black_xia.getText().toString());
                    hhx1=String.valueOf(hhx);
                    hhz = Double.parseDouble(hou_black_zhong.getText().toString());
                    hhz1=String.valueOf(hhz);
                    qhz = Double.parseDouble(qian_black_zhong.getText().toString());
                    qhz1=String.valueOf(qhz);
                    qhs = Double.parseDouble(qian_black_shang.getText().toString());
                    qhs1=String.valueOf(qhs);
                    qhx = Double.parseDouble(qian_black_xia.getText().toString());
                    qhx1=String.valueOf(qhx);
                    qhongz = Double.parseDouble(qian_red_zhong.getText().toString());
                    qhongz1=String.valueOf(qhongz);
                    hhongz = Double.parseDouble(hou_red_zhong.getText().toString());
                    hhongz1=String.valueOf(hhongz);
                }
                catch (Exception e){
                    Toast.makeText(SubTraverse.this, "请正确填写全部数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                //endregion
                //region 测站检核计算
                //region 二等
                if (LevelLib_known.dengji == 2) {
                    houshiju = Geopro.Round(hhs - hhx,2);//计算出来的视距需要乘以100再化成米
                    qianshiju = Geopro.Round(qhs - qhx,2);
                    shicha = Geopro.Round(houshiju - qianshiju,2);
                    leijicha = shicha + LevelLib_station.leijishiju;

                    hou_black_red = Geopro.Round((hhz + k1 - hhongz) * 10,2);//后尺差以mm为单位
                    qian_black_red = Geopro.Round((qhz + k2 - qhongz) * 10,2);
                    houjianqian = Geopro.Round(hou_black_red - qian_black_red,2);

                    gaocha_black = Geopro.Round(hhz - qhz,3);//高程以cm为单位
                    gaocha_red = Geopro.Round(hhongz - qhongz,3);
                    heijianhong = Geopro.Round((gaocha_black - gaocha_red) * 10,2);

                    gaocha_avg = Geopro.Round((gaocha_black + gaocha_red) / 2,4);
                }
                //endregion
                //region 四等
                if (LevelLib_known.dengji == 4){
                    houshiju = Geopro.Round((hhs - hhx) / 10,1);//计算出来的视距需要乘以100再化成米
                    qianshiju = Geopro.Round((qhs - qhx) / 10,1);
                    shicha = Geopro.Round(houshiju - qianshiju,1);
                    leijicha = shicha + LevelLib_station.leijishiju;

                    hou_black_red = Geopro.Round((hhz + k1 - hhongz),0);
                    qian_black_red = Geopro.Round((qhz + k2 - qhongz),0);
                    houjianqian = Geopro.Round(hou_black_red - qian_black_red,0);

                    gaocha_black = Geopro.Round((hhz - qhz) / 1000,3);//高程以m为单位
                    gaocha_red = Geopro.Round((hhongz - qhongz) / 1000,3);

                    // 由于后视尺和前视线尺的常数不同，差值应为0.1m
                    if(Math.abs(gaocha_red) > Math.abs(gaocha_black)){
                        if(gaocha_red > 0)
                            heijianhong = gaocha_black - (gaocha_red - 0.1);
                        else
                            heijianhong = gaocha_black - (gaocha_red + 0.1);
                    }
                    else{
                        if(gaocha_red > 0)
                            heijianhong = gaocha_black - (gaocha_red + 0.1);
                        else
                            heijianhong = gaocha_black - (gaocha_red - 0.1);
                    }
                    heijianhong = Geopro.Round(heijianhong * 1000,0);
                    gaocha_avg = Geopro.Round((gaocha_black - houjianqian / 1000 / 2),4);
                }
                //endregion
                //endregion

                //region 数据存储
                LevelLib_result.juli.add(Geopro.Round(houshiju + qianshiju,1));
                LevelLib_result.gaocha.add(gaocha_avg);
                LevelLib_result.cezhan.add(cezhan);
                //endregion
                Intent intent = new Intent(SubTraverse.this,LevelLib_station.class);
                intent.putExtra("测站",cezhan);

                intent.putExtra("前视距",qianshiju);
                intent.putExtra("后视距",houshiju);
                intent.putExtra("当前视差",shicha);
                intent.putExtra("累积视差",leijicha);

                intent.putExtra("前黑红读差",qian_black_red);
                intent.putExtra("后黑红读差",hou_black_red);
                intent.putExtra("后减前",houjianqian);

                intent.putExtra("黑面高差",gaocha_black);
                intent.putExtra("红面高差",gaocha_red);
                intent.putExtra("黑减红",heijianhong);

                intent.putExtra("平均高差",gaocha_avg);

                //用于数据合格后把数据传入数据库
                intent.putExtra("K1",k01);
                intent.putExtra("K2",k02);
                intent.putExtra("hhs",hhs1);
                intent.putExtra("hhz",hhz1);
                intent.putExtra("hhx",hhx1);
                intent.putExtra("qhs",qhs1);
                intent.putExtra("qhz",qhz1);
                intent.putExtra("qhx",qhx1);
                intent.putExtra("qhongz",qhongz1);
                intent.putExtra("hhongz",hhongz1);

                startActivity(intent);//每次都会触发station的oncreate事件
            }
        });
    }
}
