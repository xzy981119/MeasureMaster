package com.tea.tool;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CoordinateCal {
    public Double x1,y1,x2,y2;
    public Double  dx,dy;
    public Double length;
    public Double rad;
    /// <summary>
    /// 控制输出为小数点后三位并四舍五入
    /// </summary>
    /// <param name="a"></param>
    /// <param name="f"></param>
    public String div2(double d){
        String str =String.valueOf(d);
        BigDecimal bigDecimal=new BigDecimal(str);
        return String.valueOf(bigDecimal.setScale(4, RoundingMode.HALF_UP));
    }
    /// <summary>
    /// 坐标正算
    /// </summary>
    /// <param name="rad">rad</param>
    public  void Forward(){
        dx=length*Math.cos(rad);
        dy=length*Math.sin(rad);
        x2=x1+dx;
        y2=y1+dy;
    }
    public void getLength(){
        length=Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
    }
    /// <summary>
    /// 坐标反算
    /// </summary>
    /// <param name="rad">rad</param>
    public  void Inverse(){
       dx=x2-x1;
       dy=y2-y1;
       if(dx==0&&dy>0)
       {
           rad=Math.PI/2;
       }
       else if(dx==0&&dy<0){
           rad=Math.PI/2*3;
       }
       else {
           rad=Math.atan(Math.abs(dy)/Math.abs(dx));
           if(dx>0&&dy>=0){
               rad=rad;
           }
           else if(dx<0&&dy>=0){
               rad=Math.PI-rad;
           }
           else if (dx<0&&dy<0){
               rad=Math.PI+rad;
           }
           else {
               rad=Math.PI*2-rad;
           }
       }

    }
}
