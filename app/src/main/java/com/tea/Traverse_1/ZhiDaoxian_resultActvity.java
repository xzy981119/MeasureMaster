package com.tea.Traverse_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tea.Traverse.Daoxian_resultActivity;
import com.tea.Traverse.Traben_data;
import com.tea.measuremaster.R;
import com.tea.tool.Geopro;

import java.util.UUID;

import database.traverseDbSheme.TraverseLibBaseHelper;
import database.traverseDbSheme.TraverseLibDbSheme;
import database.traverse_1Dbsheme.Traverse_1LibBaseHelper;

public class ZhiDaoxian_resultActvity extends AppCompatActivity {
    private SQLiteDatabase mDatabase;
    private Geopro mGeopro;
    private Context mContext;
    private Toolbar toolbar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templete_1_result);
        //region 标题栏加返回箭头
        toolbar = (Toolbar)findViewById(R.id.toolbar_daoxian_1_result);
        toolbar.setTitle("支导线近似平差计算");//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);
        mGeopro=new Geopro();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //endregion
        textView = (TextView)findViewById(R.id.daoxian_1_result_text);

        try {
            Traben_data_1.jisuan();
            String str;
            str = "---------------导线基本信息-----------------\n";
            str += "测站数：" + Traben_data_1.guanCeJList.size() + "\n";
            str += "导线全长" + Geopro.Round(Traben_data_1.sum_dis,5) + "\n";
            str += "---------------测站点坐标-------------------\n";
            str += Geopro.formatStr("测站",10) + Geopro.formatStr("X坐标",20) + Geopro.formatStr("Y坐标",20) + "\n";
            for (int i = 0; i < Traben_data_1.resultList.size(); i++)
            {
                str += Geopro.formatStr(String.valueOf(Traben_data_1.ceZhan.get(i)),10) + "    ";
                str += Geopro.formatStr(String.valueOf(Geopro.Round(Traben_data_1.resultList.get(i).getX(),4)),20) + "    ";
                str += Geopro.formatStr(String.valueOf(Geopro.Round(Traben_data_1.resultList.get(i).getY(),4)),20) + "\n";
            }
            str += "\n---------------角度数据---------------------\n";
            str += String.format("%-20s", "    ") + String.format("%-20s", "     ") + String.format("%-20s", "方位角") + "\n";
            str += String.format("%-10s", "测站名") + String.format("%-20s", "观测角") + "\n";
            for (int i = 0; i < Traben_data_1.fangweijiao.size(); i++)
            {
                str +=String.format("%-20s", "    ") + String.format("%-20s", "     ") + Geopro.hudutoDMS(Traben_data_1.fangweijiao.get(i)) + "\n";
                if (i != Traben_data_1.fangweijiao.size() - 1)
                {
                    str += String.format("%-15s", Traben_data_1.ceZhan.get(i));
                    str += String.format("%-20s", mGeopro.div2(Geopro.Rad2Dms(Traben_data_1.guanCeJList.get(i) ))) + "\n";
                    //输出的是改正后的观测角
                }
            }
            //endregion
            for(int i=0;i<Traben_data_1.guanCeJList.size();i++)
            {
                Database(i);
            }
            textView.setText(str);
        }
        catch (Exception e){
            Toast.makeText(ZhiDaoxian_resultActvity.this, "请正确填写数据再进行计算", Toast.LENGTH_SHORT).show();
            return;
        }
    }
    private void  Database(int i){
        UUID uuid=UUID.randomUUID();
        mDatabase=new Traverse_1LibBaseHelper(ZhiDaoxian_resultActvity.this).getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(TraverseLibDbSheme.TraverseLibTable.Cols.UUID,uuid.toString());
        values.put(TraverseLibDbSheme.TraverseLibTable.Cols.NAME,Traben_data_1.ceZhan.get(i));
        values.put(TraverseLibDbSheme.TraverseLibTable.Cols.ANGLE,Geopro.Rad2Dms(Traben_data_1.fangweijiao.get(i)));
        values.put(TraverseLibDbSheme.TraverseLibTable.Cols.AZIMUTH,Geopro.Rad2Dms(Traben_data_1.guanCeJList.get(i)));
        values.put(TraverseLibDbSheme.TraverseLibTable.Cols.X,Geopro.formatStr(String.valueOf(Geopro.Round(Traben_data.resultList.get(i).getX(),3)),20));
        values.put(TraverseLibDbSheme.TraverseLibTable.Cols.Y,Geopro.formatStr(String.valueOf(Geopro.Round(Traben_data.resultList.get(i).getY(),3)),20));
        mDatabase.insert(TraverseLibDbSheme.TraverseLibTable.NAME,null,values);
    }
}
