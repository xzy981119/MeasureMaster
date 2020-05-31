package com.tea.Traverse_1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tea.Traverse.Station;
import com.tea.TraverseAdjustment.Adapter.TraverseAdapter2;
import com.tea.measuremaster.R;
import com.tea.tool.Geopro;

import java.util.List;

public class TraverseAdapter2_1 extends RecyclerView.Adapter<TraverseAdapter2_1.LinearViewHolder>{
    private Double dd,mm,ss,dms;
    private String sec;
    private Geopro mGeopro;
    private List<Station> stationList;
    //传入外部的数据
    public TraverseAdapter2_1(List<Station> stationList){
        this.stationList = stationList;
    }
    @Override
    public TraverseAdapter2_1.LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TraverseAdapter2_1.LinearViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_station_item_2,parent,false));
    }


    @Override//设置显示内容
    public void onBindViewHolder(TraverseAdapter2_1.LinearViewHolder holder, int position) {
        mGeopro=new Geopro();
        Station station = stationList.get(position);
        dms=Geopro.Rad2Dms(station.getGuanCeJ());
        dd=Math.floor(dms);
        mm=(Math.floor((dms - dd) * 100.0));
        ss=(dms - dd - mm / 100.0) * 10000;
        sec=mGeopro.div2(ss);
        holder.ceZhan.setText("测站:"+station.getCeZhan());
        holder.dis.setText("水平距离："+station.getDis() + "m");
        holder.guanCeJ.setText("观测角："+dd.toString()+"°"+mm.toString()+"′"+sec+"″");
    }

    @Override//返回长度
    public int getItemCount() {//返回的是现实item的个数
        return stationList.size();
    }
    //找到控件
    class LinearViewHolder extends RecyclerView.ViewHolder {
        private TextView ceZhan;
        private TextView guanCeJ;
        private TextView dis;

        public LinearViewHolder (View itemView){
            super(itemView);
            ceZhan = itemView.findViewById(R.id.station_ceZhan_1);
            guanCeJ = itemView.findViewById(R.id.station_guanCeJ_1);
            dis = itemView.findViewById(R.id.station_dis_1);
        }
    }
}
