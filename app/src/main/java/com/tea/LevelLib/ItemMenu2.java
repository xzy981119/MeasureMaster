package com.tea.LevelLib;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tea.TraverseAdjustment.Adapter.Traverse_qishi;
import com.tea.Traverse_1.Traverse_1_qishi;
import com.tea.Traverse_2.Traverse_2_qishi;
import com.tea.ZhLevelLib.ZhLevelLib_known;
import com.tea.measuremaster.R;
import com.tea.measuremaster.adapter.MainMenuAdapter;
import com.tea.measuremaster.adapter.MenuItemBean;

import java.util.ArrayList;
import java.util.List;

public class ItemMenu2 extends AppCompatActivity {
    private Toolbar toolbar;
    RecyclerView recyclerView;
    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.item_menu_shuizhun);
        //region 标题栏
        toolbar =findViewById(R.id.toolbar_menu4);
        toolbar.setTitle("水准测量");//设置Toolbar标题
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
        recyclerView = findViewById(R.id.item_menu1_recyclerView2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        /**
         * 初始化recyclerView需要的数据
         */
        List<MenuItemBean> menuItemBeanList = new ArrayList<>();

        MenuItemBean item = new MenuItemBean("闭合水准",
                "由一个已知点出发，闭合到该已知点",
                LevelLib_known.class);
        menuItemBeanList.add(item);
        MenuItemBean item1=new MenuItemBean("附和水准","由一个已知点出发，附和到另一个已知点", LevelLib_known.class);
        menuItemBeanList.add(item1);
        MenuItemBean item2=new MenuItemBean("支水准","从一个已知点出发，不附和、闭合到任何已知点", ZhLevelLib_known.class);
        menuItemBeanList.add(item2);
        MainMenuAdapter mainMenuAdapter = new MainMenuAdapter(menuItemBeanList,mContext);

        recyclerView.setAdapter(mainMenuAdapter);
    }
}
