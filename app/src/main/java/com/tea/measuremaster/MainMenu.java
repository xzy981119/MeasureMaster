package com.tea.measuremaster;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tea.Geodesy.GeoAcivity;
import com.tea.LevelLib.ItemMenu2;
import com.tea.LevelLib.LevelLib_known;
import com.tea.LevelLib.SubTraverse;
import com.tea.Traverse.ItemMenu1;
import com.tea.TraverseAdjustment.Adapter.SubTemplete;
import com.tea.TraverseAdjustment.Adapter.Traverse_qishi;
import com.tea.coortrans.SubCoortrans;
import com.tea.measuremaster.adapter.MainMenuAdapter;
import com.tea.measuremaster.adapter.MenuItemBean;
import com.tea.quxian.QuxianActivity;
import com.tea.tool.ItemMenu;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends AppCompatActivity {
    private Toolbar toolbar;
    RecyclerView recyclerView;
    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.main_menu);
        //region 标题栏
        toolbar = (Toolbar)findViewById(R.id.toolbar_menu1);
        toolbar.setTitle("测量精灵");//设置Toolbar标题
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
        mContext = this;
        initView();
    }

    private void initView(){
        recyclerView = findViewById(R.id.main_menu_recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);

        /**
         * 初始化recyclerView需要的数据
         */
        List<MenuItemBean> menuItemBeanList = new ArrayList<>();

        MenuItemBean item = new MenuItemBean("水准路线测量",
                "闭合水准路线/附和水准路线/支水准路线",
                ItemMenu2.class);
        menuItemBeanList.add(item);
        MenuItemBean item2=new MenuItemBean("导线测量","附和导线/支导线/闭合导线", ItemMenu1.class);
        menuItemBeanList.add(item2);
        MenuItemBean item3=new MenuItemBean("大地主题解算","大地正算/大地反算", GeoAcivity.class);
        menuItemBeanList.add(item3);
//        MenuItemBean item4=new MenuItemBean("曲线放样","道路曲线计算", QuxianActivity.class);
//        menuItemBeanList.add(item4);
        MenuItemBean item1=new MenuItemBean("测量小工具","坐标转换/方位角正反算/角弧度转换", ItemMenu.class);
        menuItemBeanList.add(item1);

        MainMenuAdapter mainMenuAdapter = new MainMenuAdapter(menuItemBeanList,mContext);

        recyclerView.setAdapter(mainMenuAdapter);

    }


}
