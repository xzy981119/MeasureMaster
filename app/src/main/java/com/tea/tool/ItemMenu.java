package com.tea.tool;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tea.LevelLib.SubTraverse;
import com.tea.TraverseAdjustment.Adapter.SubTemplete;
import com.tea.coortrans.SubCoortrans;
import com.tea.measuremaster.R;
import com.tea.measuremaster.adapter.MainMenuAdapter;
import com.tea.measuremaster.adapter.MenuItemBean;

import java.util.ArrayList;
import java.util.List;

public class ItemMenu extends AppCompatActivity {
    private Toolbar toolbar;
    RecyclerView recyclerView;
    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.item_menu);
        //region 标题栏
        toolbar =findViewById(R.id.toolbar_menu2);
        toolbar.setTitle("测量小工具");//设置Toolbar标题
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
        recyclerView = findViewById(R.id.item_menu_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        /**
         * 初始化recyclerView需要的数据
         */
        List<MenuItemBean> menuItemBeanList = new ArrayList<>();

        MenuItemBean item = new MenuItemBean("坐标转换",
                "大地坐标转换为空间直角坐标/空间直角坐标转化为大地坐标",
                SubCoortrans.class);
        menuItemBeanList.add(item);
        MenuItemBean item2=new MenuItemBean("坐标正算","已知一点坐标及到另一点的坐标方位角及水平距离，求另一点的坐标", CoordinateForwardCal.class);
        menuItemBeanList.add(item2);
        MenuItemBean itemBean=new MenuItemBean("坐标反算","已知两点坐标，求两点所夹坐标方位角及水平距离",CoordinateInverseCal.class);
        menuItemBeanList.add(itemBean);
        MenuItemBean item1=new MenuItemBean("角弧度转换","角度转换为弧度", SubDmsToRad.class);
        menuItemBeanList.add(item1);
        MenuItemBean item3=new MenuItemBean("弧角度转换","弧度转换为角度",SubRadToDms.class);
        menuItemBeanList.add(item3);

        MainMenuAdapter mainMenuAdapter = new MainMenuAdapter(menuItemBeanList,mContext);

        recyclerView.setAdapter(mainMenuAdapter);
   }
}
