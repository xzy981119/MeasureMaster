package com.tea.Traverse;

public class Station {
    private String ceZhan;
    private double dis;
    private double guanCeJ;

    //存储测站的数据
    public Station(String ceZhan,double dis,double guanCeJ){
        this.ceZhan=ceZhan;
        this.dis=dis;
        this.guanCeJ = guanCeJ;
    }
    public String getCeZhan() {return ceZhan;}

    public double getDis() {
        return dis;
    }

    public double getGuanCeJ() {
        return guanCeJ;
    }
}
