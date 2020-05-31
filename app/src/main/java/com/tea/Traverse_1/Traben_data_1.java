package com.tea.Traverse_1;

import com.tea.Traverse.Traben_data;
import com.tea.tool.Geopro;

import java.util.ArrayList;

public class Traben_data_1 {
    //记录转向信息和闭合差
    public static int k;
    //记录是否超限
    public static double bhc;
    public static double xbhc;
    public static double ybhc;
    public static double xybhc;
    public static double sum_jiao,sum_X,sum_Y,sum_dis;
    //分别为起始边AB和结束边CD的方位角
    public static double fangwei_first;
    //分别为起始点和结束点的坐标
    public static Traben_data_1.ZuoBiao B;

    //存储点号信息
    public static ArrayList<String> ceZhan = new ArrayList<>();
    //表示各站观测角,存储为弧度
    public static ArrayList<Double> guanCeJList = new ArrayList<>();
    //表示各站观测距离
    public static ArrayList<Double> disList = new ArrayList<>();

    //存储方位角
    public static ArrayList<Double> fangweijiao = new ArrayList<>();
    //存储坐标增量
    public static ArrayList<Double> Xzengliang = new ArrayList<>();
    public static ArrayList<Double> Yzengliang = new ArrayList<>();
    //平差结果,即各点坐标
    public static ArrayList<Traben_data_1.ZuoBiao> resultList = new ArrayList<>();

    //存储坐标
    public static class ZuoBiao{
        double x;
        double y;

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public ZuoBiao(double x, double y){
            this.x=x;
            this.y=y;
        }
    }

    //region 附和导线平差的计算部分
    public static void jisuan(){
        fangweijiao.clear();
        Xzengliang.clear();
        Yzengliang.clear();
        resultList.clear();

        //region 方位角计算
        double n = 0;//存储加减360的信息
        fangweijiao.add(fangwei_first);
        for (int i = 0; i < guanCeJList.size(); i++)
        {
            double fangwei = fangweijiao.get(i) + k * guanCeJList.get(i) - k * Math.PI;
            if (fangwei > Math.PI * 2)
            {
                fangwei = fangwei - Math.PI * 2;
                n = n - Math.PI * 2;
            }
            else if (fangwei < 0)
            {
                fangwei = fangwei + Math.PI * 2;
                n = n + Math.PI * 2;
            }
            fangweijiao.add(fangwei);
        }
        //endregion
        //region 角度近似平差
        sum_jiao = 0;
        for (int i = 0; i < guanCeJList.size(); i++) {
            sum_jiao = guanCeJList.get(i) + sum_jiao;
        }
        {
            fangweijiao.clear();//清空未经平差的方位角
            fangweijiao.add(fangwei_first);
            for (int i = 0; i < guanCeJList.size(); i++)
            {
                double fangwei;
                fangwei = fangweijiao.get(i) + k * (guanCeJList.get(i)) - k * Math.PI;
                if (fangwei > Math.PI * 2)
                {
                    fangwei = fangwei - Math.PI * 2;
                }
                else if (fangwei < 0)
                {
                    fangwei = fangwei + Math.PI * 2;
                }
                fangweijiao.add(fangwei);
            }
        }
        //endregion
        //region 计算坐标增量

        for (int i = 0; i < disList.size(); i++)
        {
            Xzengliang.add(disList.get(i) * Math.cos(fangweijiao.get(i + 1)));
            Yzengliang.add(disList.get(i) * Math.sin(fangweijiao.get(i + 1)));
        }
        sum_X = 0;
        for (int i = 0; i < Xzengliang.size(); i++) {
            sum_X = Xzengliang.get(i) + sum_X;
        }
        sum_Y = 0;
        for (int i = 0; i < Yzengliang.size(); i++) {
            sum_Y = Yzengliang.get(i) + sum_Y;
        }
        sum_dis = 0;
        for (int i = 0; i < disList.size(); i++) {
            sum_dis = disList.get(i) + sum_dis;
        }
        //endregion
        //region 计算坐标
        resultList.add(B);

        for (int i = 0; i < disList.size(); i++)
        {
            resultList.add(new Traben_data_1.ZuoBiao(resultList.get(i).getX() + Xzengliang.get(i),
                    resultList.get(i).getY() + Yzengliang.get(i)));
        }
        //endregion
    }
}
