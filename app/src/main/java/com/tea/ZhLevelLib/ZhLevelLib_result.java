package com.tea.ZhLevelLib;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tea.LevelLib.LevelLib_known;
import com.tea.measuremaster.R;
import com.tea.tool.Geopro;

import java.util.ArrayList;

public class ZhLevelLib_result extends AppCompatActivity {
    private SQLiteDatabase mDatabase;
    private Toolbar toolbar;
    private TextView t1,t2;
    private double bhc,sum_juli,sum_juli_1,sum_gaocha,sum_gaocha_1;
    private ArrayList<Double> gaizhengshu = new ArrayList<>();
    private ArrayList<Double> gaihougaocha = new ArrayList<>();
    private ArrayList<Double> gaocheng = new ArrayList<>();
    private ArrayList<String> dianhao = new ArrayList<>();
    public static ArrayList<String> cezhan = new ArrayList<>();
    public static ArrayList<String>cezhan1=new ArrayList<>();
    //存储距离
    public static ArrayList<Double> juli = new ArrayList<>();
    public static ArrayList<Double>juli_1=new ArrayList<>();
    //存储高差
    public static ArrayList<Double> gaocha = new ArrayList<>();
    public static ArrayList<Double>gaocha_1=new ArrayList<>();
    public static ArrayList<Double>gaocha_2=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhlevellib_result);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        //region 标题栏加返回箭头
        toolbar = (Toolbar)findViewById(R.id.toolbar_shuizhun_result_1);
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

        t1 = (TextView)findViewById(R.id.shuizhun_1_result_t1);
        t2 = (TextView)findViewById(R.id.shuizhun_1_result_t2);

        gaizhengshu.clear();
        gaihougaocha.clear();
        gaocheng.clear();

        //region 水准平差
        sum_juli = 0;
        sum_juli_1=0;
        for (int i = 0; i < juli.size(); i++) {
            sum_juli = juli.get(i) + sum_juli;
        }
        for(int i=0;i<juli_1.size();i++){
            sum_juli_1=juli_1.get(i)+sum_juli_1;
        }
        sum_gaocha = 0;
        sum_gaocha_1=0;
        for (int i = 0; i < gaocha.size(); i++) {
            sum_gaocha = gaocha.get(i) + sum_gaocha;
        }
        for(int i=0;i<gaocha_1.size();i++){
            sum_gaocha_1=gaocha_1.get(i)+sum_gaocha_1;
        }
        if (LevelLib_known.dengji == 2) {
            bhc = sum_gaocha+sum_gaocha_1;//观测值减去真实值
        }
        if (LevelLib_known.dengji == 4) {
            bhc = sum_gaocha+sum_gaocha_1;//观测值减去真实值
        }
        dianhao.add(0,"Start");
        for (int i = 0; i < juli.size() - 1; i++) {
            dianhao.add(String.valueOf(i + 1));
        }
        dianhao.add("End");
        gaocheng.add(ZhLevelLib_known.gaocheng_start);
        //闭合差改正数都是m
        for (int i = 0; i < gaocha.size(); i++)//改正分配
        {
            if (LevelLib_known.dengji == 2) {
                if(gaocha.get(i)>=0){
                    gaocheng.add(Geopro.Round(Math.abs(gaocha.get(i)) + Math.abs(gaocha_1.get(gaocha.size()-i-1))+gaocheng.get(i),6));
                    gaocha_2.add(Math.abs(gaocha.get(i)) + Math.abs(gaocha_1.get(gaocha.size()-i-1)));
                }
                if(gaocha.get(i)<0){
                    gaocheng.add(-1*(Geopro.Round(Math.abs(gaocha.get(i)) + Math.abs(gaocha_1.get(gaocha.size()-i-1))/100+gaocheng.get(i),6)));
                    gaocha_2.add(-1*(Math.abs(gaocha.get(i)) + Math.abs(gaocha_1.get(gaocha.size()-i-1))));
                }
//                gaizhengshu.add(Geopro.Round(-bhc * juli.get(i) / sum_juli,6));
//                gaihougaocha.add(Geopro.Round(gaocha.get(i) / 100 + gaizhengshu.get(i), 6));

            }
            if (LevelLib_known.dengji == 4) {
                if(gaocha.get(i)>=0){
                    gaocheng.add(Geopro.Round(Math.abs(gaocha.get(i)) + Math.abs(gaocha_1.get(gaocha.size()-i-1))+gaocheng.get(i),6));
                }
                if(gaocha.get(i)<0){
                    gaocheng.add(-1*(Geopro.Round(Math.abs(gaocha.get(i)) + Math.abs(gaocha_1.get(gaocha.size()-i-1))+gaocheng.get(i),6)));
                }
//                gaizhengshu.add(Geopro.Round(-bhc * juli.get(i) / sum_juli,4));
//                gaihougaocha.add(Geopro.Round(gaocha.get(i) + gaizhengshu.get(i), 4));
//                gaocheng.add(Geopro.Round(gaihougaocha.get(i) + gaocheng.get(i),4));
            }
        }
        //gaocheng.add(Shuizhun_setupActivity.gaocheng_end);
        //endregion

        //region 计算报告
        String str;
        str = "\t已知点数据\n";
        str += "-------------------------------------------------------------------\n";
        str += "起点高程: " + ZhLevelLib_known.gaocheng_start +" m\n";
        str += "测站数：" + gaocha.size() + "\n";
        str += "总距离：" + Geopro.Round(sum_juli,1) + " m\n";
        if (ZhLevelLib_known.dengji == 2) {
            str += "高程闭合差：" + Geopro.Round(bhc * 1000, 2) + " mm\n";
            if ((sum_juli+sum_juli_1)/2 <= 1000){
                str += "高程闭合差允许值：" + " 4 mm\n";
            }
            else{
                str += "高程闭合差允许值：" + 4 * Geopro.Round(Math.sqrt((sum_juli+sum_juli_1)/2/1000),1) + " mm\n";
            }
        }
        if (ZhLevelLib_known.dengji == 4) {
            str += "高程闭合差：" + Geopro.Round(bhc * 1000, 1) + " mm\n";
            if (sum_juli <= 1000){
                str += "高程闭合差允许值：" + " 20 mm\n";
            }
            else {
                str += "高程闭合差允许值：" + 20 * Geopro.Round(Math.sqrt((sum_juli+sum_juli_1)/2/ 1000), 1) + " mm\n";
            }
        }

        String str1;
        str1 = "\t高程配赋数据\n";
        str1 += "----------------------------------------------------------------------------------------------------------------------------------------------------\n";
        if (ZhLevelLib_known.dengji == 4) {
            str1 += Geopro.formatStr("点名", 10) + Geopro.formatStr("测站", 15) + Geopro.formatStr("平距(m)", 15) + Geopro.formatStr("高差(m)", 15)
                     + Geopro.formatStr("高程(m)", 15) + "\n";
            for (int i = 0; i < gaocheng.size(); i++) {
                str1 += Geopro.formatStr(String.valueOf(dianhao.get(i)), 25) + String.format("%-60s", "     ") + String.format("%-60s", "     ") + String.format("%-15s", Geopro.Round(gaocheng.get(i),4)) + "\n";
                if (i != gaocheng.size() - 1) {
                    str1 += String.format("%-18s", "     ") + String.format("%-20s", cezhan.get(i)) + String.format("%-20s", (juli.get(i)+juli_1.get(gaocheng.size()-1-i))/2) + String.format("%-20s", gaocha_2.get(i));
                    str1 += String.format("%-20s", Geopro.Round(gaocheng.get(i),4)) + "\n";
                }
            }
        }
        if (ZhLevelLib_known.dengji == 2) {
            str1 += Geopro.formatStr("点名", 10) + Geopro.formatStr("测站", 15) + Geopro.formatStr("平距(m)", 15) + Geopro.formatStr("高差(cm)", 15)
                   + Geopro.formatStr("高程(m)", 15) + "\n";
            for (int i = 0; i < gaocheng.size(); i++) {
                str1 += Geopro.formatStr(String.valueOf(dianhao.get(i)), 25) + String.format("%-60s", "     ") + String.format("%-60s", "     ") + String.format("%-15s", Geopro.Round(gaocheng.get(i),6)) + "\n";
                if (i != gaocheng.size() - 1) {
                    str1 += String.format("%-18s", "     ") + String.format("%-20s", cezhan.get(i)) + String.format("%-20s", (juli.get(i)+juli_1.get(gaocheng.size()-1-i))/2) + String.format("%-20s", gaocha_2.get(i));
                    str1 += String.format("%-20s", Geopro.Round(gaocheng.get(i) * 100,4)) + "\n";
                }
            }

        }
        t1.setText(str);
        t2.setText(str1);
        //endregion
    }
}
