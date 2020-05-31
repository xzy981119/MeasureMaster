package com.tea.Traverse_2;

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

public class TraverseAdapter2_2 extends RecyclerView.Adapter<TraverseAdapter2_2.LinearViewHolder>{
    private Double dd,mm,ss,dms;
    private String sec;
    private Geopro mGeopro;
    private List<Station> stationList2;
    //传入外部的数据
    public TraverseAdapter2_2(List<Station> stationList){
        this.stationList2 = stationList;
    }
    @Override
    public TraverseAdapter2_2.LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TraverseAdapter2_2.LinearViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_station_item_3,parent,false));
    }

    @Override//设置显示内容
    public void onBindViewHolder(TraverseAdapter2_2.LinearViewHolder holder, int position) {
        mGeopro=new Geopro();
        Station station = stationList2.get(position);
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
        return stationList2.size();
    }
    //找到控件
    class LinearViewHolder extends RecyclerView.ViewHolder {
        private TextView ceZhan;
        private TextView guanCeJ;
        private TextView dis;

        public LinearViewHolder (View itemView){
            super(itemView);
            ceZhan = itemView.findViewById(R.id.station_ceZhan_2);
            guanCeJ = itemView.findViewById(R.id.station_guanCeJ_2);
            dis = itemView.findViewById(R.id.station_dis_2);
        }
    }
}
