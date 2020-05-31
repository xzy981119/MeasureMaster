package com.tea.measuremaster.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tea.measuremaster.R;

import java.util.List;

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.ViewHolder>{

    List<MenuItemBean> menuItemBeanList;
    Context mainMenuContext;

    public MainMenuAdapter(List<MenuItemBean> menuItemBeanList, Context mainMenuContext) {
        this.menuItemBeanList = menuItemBeanList;
        this.mainMenuContext = mainMenuContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_menu_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv_title.setText(menuItemBeanList.get(position).getTitle());
        holder.tv_brief.setText(menuItemBeanList.get(position).getBrief());

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainMenuContext, menuItemBeanList.get(position).getToPage());
                mainMenuContext.startActivity(intent);
            }
        };

        //添加点击事件
        holder.tv_title.setOnClickListener(onClickListener);
        holder.tv_brief.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return menuItemBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title, tv_brief;
        ImageView btn_inter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.main_menu_item_title);
            tv_brief = itemView.findViewById(R.id.main_menu_item_brief);
        }
    }
}
