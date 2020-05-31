package com.tea.TraverseAdjustment.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tea.Traverse.Daoxian_resultActivity;
import com.tea.Traverse.Station;
import com.tea.Traverse.Traben_data;
import com.tea.measuremaster.R;
import com.tea.tool.Geopro;
import com.tea.widget.CustomeEdittext;

import java.util.ArrayList;
import java.util.List;

public class SubTemplete extends AppCompatActivity implements CustomeEdittext.OnSuccessListener{
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private EditText du,fen,miao;
    private EditText dis;
    CustomeEdittext cezhan;
    private Button button_next,button_back,button_pingcha,button_draw;
    private TextView disT,dism;
    private TraverseAdapter2 adapter;
    private List<Station> stationList=new ArrayList<>();
    private Station station;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traverse);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//将自动弹出软键盘隐藏

        //region Toolbar标题栏
        toolbar =findViewById(R.id.toolbar_daoxian_jisuan);
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
        cezhan=findViewById(R.id.daoxian_cezhan);
        cezhan.setOnSuccessListener(this);
        du=findViewById(R.id.daoxian_du);
        fen=findViewById(R.id.daoxian_fen);
        miao=findViewById(R.id.daoxian_miao);
        dis=findViewById(R.id.daoxian_dis);
        button_back=findViewById(R.id.daoxian_button_back);
        button_next=findViewById(R.id.daoxian_button_next);
        button_pingcha=findViewById(R.id.daoxian_button_pingcha);
        disT=findViewById(R.id.daoxian_dis_text);
        dism =findViewById(R.id.daoxian_m_text);
        //endregion

        //因为第一站不用观测距离，将dis,disT两个控件设为隐藏
        //这里将dis的内容设为0，是为了避免改编辑框为空，会造成输入不完整的提示
        dis.setText("0");
        dis.setVisibility(View.INVISIBLE);
        disT.setVisibility(View.INVISIBLE);
        dism.setVisibility(View.INVISIBLE);

        recyclerView =findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(SubTemplete.this));//设置组织方式
        adapter=new TraverseAdapter2(stationList);
        recyclerView.setAdapter(adapter);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    stationList.remove(stationList.size() - 1);
                    adapter.notifyDataSetChanged();//刷新adapter
                    Traben_data.guanCeJList.remove(Traben_data.guanCeJList.size() - 1);
                    if(Traben_data.guanCeJList.size()!=0) {
                        Traben_data.disList.remove(Traben_data.disList.size() - 1);
                    }
                    if(Traben_data.guanCeJList.size()==0){
                        dis.setText("0");
                        dis.setVisibility(View.INVISIBLE);
                        disT.setVisibility(View.INVISIBLE);
                        dism.setVisibility(View.INVISIBLE);
                    }
                }
                catch (Exception e) {
                    Toast.makeText(SubTemplete.this, "上一站无数据", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addToList()==true){
                    dis.setVisibility(View.VISIBLE);
                    disT.setVisibility(View.VISIBLE);
                    dism.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();//刷新adapter
                    Toast.makeText(SubTemplete.this,"该站数据已加入数据库",Toast.LENGTH_SHORT).show();
                }
            }
        });
        button_pingcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SubTemplete.this,Daoxian_resultActivity.class);
                startActivity(intent);
            }
        });
    }
    private boolean addToList() {

        //把station放在前面，这样输入无效数据能及时抛出异常，不会在列表中添加错误数据
            //将数据放入recyclerview，用于显示
           try {
               station = new Station(cezhan.getContent(),
                       Double.parseDouble(dis.getText().toString()), Geopro.DMStohudu(Integer.parseInt(du.getText().toString())
                       ,Double.parseDouble(fen.getText().toString()),Double.parseDouble(miao.getText().toString())));

               stationList.add(station);

               //因为第一站不观测距离，所以第一站不用往disList中加观测值
               if(Traben_data.guanCeJList.size()!=0){
                   Traben_data.disList.add(Double.parseDouble(dis.getText().toString()));
               }
               Traben_data.ceZhan.add(cezhan.getContent());
               //将各观测角放入数据类中，便于计算平差
               Traben_data.guanCeJList.add(Geopro.DMStohudu(Integer.parseInt(du.getText().toString())
                       ,Double.parseDouble(fen.getText().toString()),Double.parseDouble(miao.getText().toString())));
           }
        catch (Exception e){
            Toast.makeText(SubTemplete.this, "请正确填写全部数据", Toast.LENGTH_SHORT).show();
            return false;
        }

        cezhan.setContent("");
        du.setText("");
        fen.setText("");
        miao.setText("");
        dis.setText("");

        return true;
    }

    @Override
    public void onSuccess(String phone) {

    }
}
