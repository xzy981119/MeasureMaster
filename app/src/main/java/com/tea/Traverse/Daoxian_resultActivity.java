package com.tea.Traverse;

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

import com.tea.measuremaster.R;
import com.tea.tool.Geopro;

import java.util.UUID;

import database.traverseDbSheme.TraverseLibBaseHelper;
import database.traverseDbSheme.TraverseLibDbSheme;

public class Daoxian_resultActivity extends AppCompatActivity {
    private SQLiteDatabase mDatabase;
    private Geopro mGeopro;
    private Context mContext;
    private Toolbar toolbar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templete_result);
        //region 标题栏加返回箭头
        toolbar = (Toolbar)findViewById(R.id.toolbar_daoxian_result);
        toolbar.setTitle("附和导线近似平差计算");//设置Toolbar标题
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
        textView = (TextView)findViewById(R.id.daoxian_result_text);

     try {
       Traben_data.jisuan();

       if (Traben_data.warn_jiao){
           Toast.makeText(Daoxian_resultActivity.this, "角度闭合差超限！！！", Toast.LENGTH_LONG).show();
       }
       if (Traben_data.warn_bian){
           Toast.makeText(Daoxian_resultActivity.this, "导线全长闭合差超限！！！", Toast.LENGTH_LONG).show();
       }

       String str;

       str = "---------------限差要求--------------------\n";
       str += "角度闭合差限差：" + Geopro.Round(24 * Math.sqrt(Traben_data.guanCeJList.size()), 3) + "\n";
       str += "导线全长相对闭合差限差:" + String.format("%1.4f",1 / 5000d) + "\n\n";
       str += "---------------导线基本信息-----------------\n";
       str += "测站数：" + Traben_data.guanCeJList.size() + "\n";
       str += "导线全长" + Geopro.Round(Traben_data.sum_dis,5) + "\n";
       str += "角度闭合差：" +mGeopro.div2(Geopro.hudutos(Traben_data.bhc))+ "″\n";
       str += "各站角度改正值：" +mGeopro.div2(Geopro.hudutos(Traben_data.bhc / Traben_data.guanCeJList.size()))+ "″\n";
       str += "X坐标闭合差：" + Geopro.Round(Traben_data.xbhc * 1000, 1) + "mm" + "\n";
       str += "Y坐标闭合差：" + Geopro.Round(Traben_data.ybhc * 1000, 1)  + "mm" + "\n";
       str += "导线全长闭合差：" + String.format("%f",Geopro.Round(Traben_data.xybhc / Traben_data.sum_dis, 8)) + "\n\n";
       str += "---------------测站点坐标-------------------\n";
       str += Geopro.formatStr("测站",10) + Geopro.formatStr("X坐标",20) + Geopro.formatStr("Y坐标",20) + "\n";
       for (int i = 0; i < Traben_data.resultList.size(); i++)
       {
           str += Geopro.formatStr(String.valueOf(Traben_data.ceZhan.get(i)),10) + "    ";
           str += Geopro.formatStr(String.valueOf(Geopro.Round(Traben_data.resultList.get(i).getX(),4)),20) + "    ";
           str += Geopro.formatStr(String.valueOf(Geopro.Round(Traben_data.resultList.get(i).getY(),4)),20) + "\n";
       }
       str += "\n---------------角度数据---------------------\n";
       str += String.format("%-20s", "    ") + String.format("%-20s", "     ") + String.format("%-20s", "方位角") + "\n";
       str += String.format("%-10s", "测站名") + String.format("%-20s", "观测角") + "\n";
       for (int i = 0; i < Traben_data.fangweijiao.size(); i++)
       {
           str +=String.format("%-20s", "    ") + String.format("%-20s", "     ") + Geopro.hudutoDMS(Traben_data.fangweijiao.get(i)) + "\n";
           if (i != Traben_data.fangweijiao.size() - 1)
           {
               str += String.format("%-15s", Traben_data.ceZhan.get(i));
               str += String.format("%-20s", mGeopro.div2(Geopro.Rad2Dms(Traben_data.guanCeJList.get(i) - Traben_data.k * Traben_data.bhc / Traben_data.guanCeJList.size()))) + "\n";
               //输出的是改正后的观测角
           }
       }
       for(int i=0;i<Traben_data.guanCeJList.size();i++)
       {
           Database(i);
       }
       //endregion
       textView.setText(str);
      }
        catch (Exception e){
            Toast.makeText(Daoxian_resultActivity.this, "请正确填写数据再进行计算", Toast.LENGTH_SHORT).show();
            return;
        }
    }
    /**Database数据库绑定
     * 用于存储相关数据
     */
    private void  Database(int i){
        UUID uuid=UUID.randomUUID();
        mDatabase=new TraverseLibBaseHelper(Daoxian_resultActivity.this).getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(TraverseLibDbSheme.TraverseLibTable.Cols.UUID,uuid.toString());
        values.put(TraverseLibDbSheme.TraverseLibTable.Cols.NAME,Traben_data.ceZhan.get(i));
        values.put(TraverseLibDbSheme.TraverseLibTable.Cols.ANGLE,Geopro.Rad2Dms(Traben_data.fangweijiao.get(i)));
        values.put(TraverseLibDbSheme.TraverseLibTable.Cols.AZIMUTH,Geopro.Rad2Dms(Traben_data.guanCeJList.get(i) - Traben_data.k * Traben_data.bhc / Traben_data.guanCeJList.size()));
        values.put(TraverseLibDbSheme.TraverseLibTable.Cols.X,Geopro.formatStr(String.valueOf(Geopro.Round(Traben_data.resultList.get(i).getX(),3)),20));
        values.put(TraverseLibDbSheme.TraverseLibTable.Cols.Y,Geopro.formatStr(String.valueOf(Geopro.Round(Traben_data.resultList.get(i).getY(),3)),20));
        mDatabase.insert(TraverseLibDbSheme.TraverseLibTable.NAME,null,values);
    }
}
