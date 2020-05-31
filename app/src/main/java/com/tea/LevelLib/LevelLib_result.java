package com.tea.LevelLib;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.tea.Traverse.Daoxian_resultActivity;
import com.tea.measuremaster.R;
import com.tea.tool.Geopro;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import database.LevelLibResultDbSheme.LibResultBaseHelper;
import database.LevelLibResultDbSheme.LibResultDbSheme;
import database.traverseDbSheme.TraverseLibBaseHelper;

public class LevelLib_result extends AppCompatActivity {
    private SQLiteDatabase mDatabase;
    private Toolbar toolbar;
    private TextView t1,t2;
    private double bhc,sum_juli,sum_gaocha;
    private ArrayList<Double> gaizhengshu = new ArrayList<>();
    private ArrayList<Double> gaihougaocha = new ArrayList<>();
    private ArrayList<Double> gaocheng = new ArrayList<>();
    private ArrayList<String> dianhao = new ArrayList<>();
    public static ArrayList<String> cezhan = new ArrayList<>();
    //存储距离
    public static ArrayList<Double> juli = new ArrayList<>();
    //存储高差
    public static ArrayList<Double> gaocha = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levellib_result);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        //region 标题栏加返回箭头
        toolbar = (Toolbar)findViewById(R.id.toolbar_shuizhun_result);
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

        t1 = (TextView)findViewById(R.id.shuizhun_result_t1);
        t2 = (TextView)findViewById(R.id.shuizhun_result_t2);

        gaizhengshu.clear();
        gaihougaocha.clear();
        gaocheng.clear();

        //region 水准平差
        sum_juli = 0;
        for (int i = 0; i < juli.size(); i++) {
            sum_juli = juli.get(i) + sum_juli;
        }
        sum_gaocha = 0;
        for (int i = 0; i < gaocha.size(); i++) {
            sum_gaocha = gaocha.get(i) + sum_gaocha;
        }
        if (LevelLib_known.dengji == 2) {
            bhc = sum_gaocha / 100 - (LevelLib_known.gaocheng_end - LevelLib_known.gaocheng_start);//观测值减去真实值
        }
        if (LevelLib_known.dengji == 4) {
            bhc = sum_gaocha - (LevelLib_known.gaocheng_end - LevelLib_known.gaocheng_start);//观测值减去真实值
        }
        dianhao.add(0,"Start");
        for (int i = 0; i < juli.size() - 1; i++) {
            dianhao.add(String.valueOf(i + 1));
        }
        dianhao.add("End");
        gaocheng.add(LevelLib_known.gaocheng_start);
        //闭合差改正数都是m
        for (int i = 0; i < gaocha.size(); i++)//改正分配
        {
            if (LevelLib_known.dengji == 2) {
                gaizhengshu.add(Geopro.Round(-bhc * juli.get(i) / sum_juli,6));
                gaihougaocha.add(Geopro.Round(gaocha.get(i) / 100 + gaizhengshu.get(i), 6));
                gaocheng.add(Geopro.Round(gaihougaocha.get(i) + gaocheng.get(i),6));
            }
            if (LevelLib_known.dengji == 4) {
                gaizhengshu.add(Geopro.Round(-bhc * juli.get(i) / sum_juli,4));
                gaihougaocha.add(Geopro.Round(gaocha.get(i) + gaizhengshu.get(i), 4));
                gaocheng.add(Geopro.Round(gaihougaocha.get(i) + gaocheng.get(i),4));
            }
        }
        //gaocheng.add(Shuizhun_setupActivity.gaocheng_end);
        //endregion

        //region 计算报告
        String str;
        str = "\t已知点数据\n";
        str += "-------------------------------------------------------------------\n";
        str += "起点高程: " + LevelLib_known.gaocheng_start + " m      " + "  终点高程: " + LevelLib_known.gaocheng_end + " m\n";
        str += "测站数：" + gaocha.size() + "\n";
        str += "总距离：" + Geopro.Round(sum_juli,1) + " m\n";
        if (LevelLib_known.dengji == 2) {
            str += "高程闭合差：" + Geopro.Round(bhc * 1000, 2) + " mm\n";
            if (sum_juli <= 1000){
                str += "高程闭合差允许值：" + " 4 mm\n";
            }
            else{
                str += "高程闭合差允许值：" + 4 * Geopro.Round(Math.sqrt(sum_juli / 1000),1) + " mm\n";
            }
        }
        if (LevelLib_known.dengji == 4) {
            str += "高程闭合差：" + Geopro.Round(bhc * 1000, 1) + " mm\n";
            if (sum_juli <= 1000){
                str += "高程闭合差允许值：" + " 20 mm\n";
            }
            else {
                str += "高程闭合差允许值：" + 20 * Geopro.Round(Math.sqrt(sum_juli / 1000), 1) + " mm\n";
            }
        }

        String str1;
        str1 = "\t高程配赋数据\n";
        str1 += "----------------------------------------------------------------------------------------------------------------------------------------------------\n";
        if (LevelLib_known.dengji == 4) {
            str1 += Geopro.formatStr("点名", 10) + Geopro.formatStr("测站", 15) + Geopro.formatStr("平距(m)", 15) + Geopro.formatStr("高差(m)", 15)
                    + Geopro.formatStr("改正数(mm)", 15) + Geopro.formatStr("改后高差(m)", 15) + Geopro.formatStr("高程(m)", 15) + "\n";
            for (int i = 0; i < gaocheng.size(); i++) {
                str1 += Geopro.formatStr(String.valueOf(dianhao.get(i)), 25) + String.format("%-60s", "     ") + String.format("%-60s", "     ") + String.format("%-15s", Geopro.Round(gaocheng.get(i),4)) + "\n";
                if (i != gaocheng.size() - 1) {
                    str1 += String.format("%-18s", "     ") + String.format("%-20s", cezhan.get(i)) + String.format("%-20s", juli.get(i)) + String.format("%-20s", gaocha.get(i));
                    str1 += String.format("%-25s", Geopro.Round(gaizhengshu.get(i) * 1000,1)) + String.format("%-20s", Geopro.Round(gaihougaocha.get(i),4)) + "\n";
                    Database(i);
                }
            }
        }
        if (LevelLib_known.dengji == 2) {
            str1 += Geopro.formatStr("点名", 10) + Geopro.formatStr("测站", 15) + Geopro.formatStr("平距(m)", 15) + Geopro.formatStr("高差(cm)", 15)
                    + Geopro.formatStr("改正数(mm)", 15) + Geopro.formatStr("改后高差(cm)", 15) + Geopro.formatStr("高程(m)", 15) + "\n";
            for (int i = 0; i < gaocheng.size(); i++) {
                str1 += Geopro.formatStr(String.valueOf(dianhao.get(i)), 25) + String.format("%-60s", "     ") + String.format("%-60s", "     ") + String.format("%-15s", Geopro.Round(gaocheng.get(i),6)) + "\n";
                if (i != gaocheng.size() - 1) {
                    str1 += String.format("%-18s", "     ") + String.format("%-20s", cezhan.get(i)) + String.format("%-20s", juli.get(i)) + String.format("%-20s", gaocha.get(i));
                    str1 += String.format("%-25s", Geopro.Round(gaizhengshu.get(i) * 1000,3)) + String.format("%-20s", Geopro.Round(gaihougaocha.get(i) * 100,4)) + "\n";
                    Database(i);
                }
            }

        }
        t1.setText(str);
        t2.setText(str1);
        //endregion
    }
    private  void Database(int i){
        UUID uuid=UUID.randomUUID();
        mDatabase=new LibResultBaseHelper(LevelLib_result.this).getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(LibResultDbSheme.LibResultTable.Cols.UUID,uuid.toString());
        values.put(LibResultDbSheme.LibResultTable.Cols.CEZHAN,cezhan.get(i));
        values.put(LibResultDbSheme.LibResultTable.Cols.JULI,juli.get(i).toString());
        values.put(LibResultDbSheme.LibResultTable.Cols.GAOCHA,gaocha.get(i).toString());
        values.put(LibResultDbSheme.LibResultTable.Cols.GAIZHENGSHU,gaizhengshu.get(i).toString());
        values.put(LibResultDbSheme.LibResultTable.Cols.GAIHOUGAOCHA,gaihougaocha.get(i).toString());
        mDatabase.insert(LibResultDbSheme.LibResultTable.NAME,null,values);
    }
}
