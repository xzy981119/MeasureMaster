package com.tea.Traverse;

import java.util.List;
import java.util.UUID;

public class TraBean {
    private UUID mUUID;
    public  String Name;
    public  String Angle;
    public  String Azimuth;
    public  String Dist;
    private String X;
    private String Y;
    private String Deltax;
    private String Deltay;
    public List<Pointinfo> KnownPnts;
    public List<StationInfo> Stations;
    public List<Double> Azimuths;
    public double DistSum;
    public double AngleClosure;
    public double XClosure;
    public double YClosure;
    public double _K;


    public String getName() {
        return Name;
    }
    public String getAngle(){
        return Angle;
    }
    public String getAzimuth(){
        return  Azimuth;
    }
    public String getDist(){
        return Dist;
    }
    public String getX(){return  X;}
    public String getY(){return Y;}
    public String getDeltax(){return  Deltax;}
    public String getDeltay(){return  Deltay;}
    public TraBean(){
        this(UUID.randomUUID());
    }
    public UUID getID(){return mUUID;}

    public TraBean(UUID uuid){
        mUUID=uuid;
    }

    public void setName(String name){
        this.Name=name;
    }
    public void setAngle(String angle){
        this.Angle=angle;
    }
    public void setAzimuth(String azimuth){
        this.Azimuth=azimuth;
    }
    public void setDist(String dist) {
        this.Dist = dist;
    }
    public void setX(String x){this.X=x;}
    public void setY(String y){this.Y=y;}
    public void setDeltax(String deltax){this.Deltax=deltax;}
    public void setDeltay(String deltay){this.Deltay=deltay;}
    public class Pointinfo
    {
        public String Name;
        public double X;
        public double Y;
        public Boolean IsKnown;
    }
    public class StationInfo
    {
        public Pointinfo Site;
        public double ForeAzimuth;
        public double BackAzimuth;
        public double Angle;
        public double BackDist;
        public double DeltaX;
        public double DeltaY;
        public StationInfo()
        {
            Site=new Pointinfo();
        }
    }
}
