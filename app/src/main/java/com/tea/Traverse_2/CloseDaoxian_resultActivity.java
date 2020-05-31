package com.tea.Traverse_2;

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
import com.tea.Traverse.TraBean;
import com.tea.Traverse.Traben_data;
import com.tea.measuremaster.R;
import com.tea.tool.Geopro;

public class CloseDaoxian_resultActivity extends AppCompatActivity {
    private SQLiteDatabase mDatabase;
    private Geopro mGeopro;
    private Context mContext;
    private Toolbar toolbar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templete_2_result);
        //region 标题栏加返回箭头
        toolbar = (Toolbar)findViewById(R.id.toolbar_daoxian_2_result);
        toolbar.setTitle("闭合导线近似平差计算");//设置Toolbar标题
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
        textView = (TextView)findViewById(R.id.daoxian_2_result_text);

//        try {
            TraBean_data_2.jisuan();

            if (TraBean_data_2.warn_jiao){
                Toast.makeText(CloseDaoxian_resultActivity.this, "角度闭合差超限！！！", Toast.LENGTH_LONG).show();
            }
            if (TraBean_data_2.warn_bian){
                Toast.makeText(CloseDaoxian_resultActivity.this, "导线全长闭合差超限！！！", Toast.LENGTH_LONG).show();
            }

            String str;

            str = "---------------限差要求--------------------\n";
            str += "角度闭合差限差：" + Geopro.Round(24 * Math.sqrt(TraBean_data_2.guanCeJList.size()), 3) + "\n";
            str += "导线全长相对闭合差限差:" + String.format("%1.4f",1 / 5000d) + "\n\n";
            str += "---------------导线基本信息-----------------\n";
            str += "测站数：" + TraBean_data_2.guanCeJList.size() + "\n";
            str += "导线全长" + Geopro.Round(TraBean_data_2.sum_dis,5) + "\n";
            str += "角度闭合差：" +mGeopro.div2(Geopro.hudutos(TraBean_data_2.bhc))+ "″\n";
            str += "各站角度改正值：" +mGeopro.div2(Geopro.hudutos(TraBean_data_2.bhc / TraBean_data_2.guanCeJList.size()))+ "″\n";
            str += "X坐标闭合差：" + Geopro.Round(TraBean_data_2.xbhc * 1000, 1) + "mm" + "\n";
            str += "Y坐标闭合差：" + Geopro.Round(TraBean_data_2.ybhc * 1000, 1)  + "mm" + "\n";
            str += "导线全长闭合差：" + String.format("%f",Geopro.Round(TraBean_data_2.xybhc / TraBean_data_2.sum_dis, 8)) + "\n\n";
            str += "---------------测站点坐标-------------------\n";
            str += Geopro.formatStr("测站",10) + Geopro.formatStr("X坐标",20) + Geopro.formatStr("Y坐标",20) + "\n";
            for (int i = 0; i < TraBean_data_2.resultList.size(); i++)
            {
                str += Geopro.formatStr(String.valueOf(TraBean_data_2.ceZhan.get(i)),10) + "    ";
                str += Geopro.formatStr(String.valueOf(Geopro.Round(TraBean_data_2.resultList.get(i).getX(),4)),20) + "    ";
                str += Geopro.formatStr(String.valueOf(Geopro.Round(TraBean_data_2.resultList.get(i).getY(),4)),20) + "\n";
            }
            str += "\n---------------角度数据---------------------\n";
            str += String.format("%-20s", "    ") + String.format("%-20s", "     ") + String.format("%-20s", "方位角") + "\n";
            str += String.format("%-10s", "测站名") + String.format("%-20s", "观测角") + "\n";
            for (int i = 0; i < TraBean_data_2.fangweijiao.size(); i++)
            {
                str +=String.format("%-20s", "    ") + String.format("%-20s", "     ") + Geopro.hudutoDMS(TraBean_data_2.fangweijiao.get(i)) + "\n";
                if (i != TraBean_data_2.fangweijiao.size() - 1)
                {
                    str += String.format("%-15s", TraBean_data_2.ceZhan.get(i));
                    str += String.format("%-20s", mGeopro.div2(Geopro.Rad2Dms(TraBean_data_2.guanCeJList.get(i) - TraBean_data_2.k * TraBean_data_2.bhc / TraBean_data_2.guanCeJList.size()))) + "\n";
                    //输出的是改正后的观测角
                }
            }
            //endregion
            textView.setText(str);
//        }
//        catch (Exception e){
//            Toast.makeText(CloseDaoxian_resultActivity.this, "请正确填写数据再进行计算", Toast.LENGTH_SHORT).show();
//            return;
//        }
    }
}
