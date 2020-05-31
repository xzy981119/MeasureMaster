package com.tea.tool;

import android.app.Activity;

import com.tea.Traverse.TraBean;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Geopro extends Activity {
    /// <summary>
    /// rad格式转dms的string
    /// </summary>
    /// <param name="rad">rad</param>
    /// <returns>str</returns>
    public static String Rad2Str(double rad)
    {
        String str = "";
        double d = rad / Math.PI * 180;
        String sign = "";
        if (d < 0)
        {
            sign = "-";
        }
        d = Math.abs(d);
        double dd, mm, ss;
        dd = Math.floor(d);
        mm = Math.floor((d - dd) * 60.0);
        ss = (d - dd - mm / 60.0) * 3600.0;//string.Format("{0:00}", mm)
        return str;
    }
    //endregion
    //region 度、分、秒数据化为弧度参与计算,导线计算时用到
    public static double DMStohudu(double du, double fen, double miao){
        return (du + fen / 60 + miao / 3600) * Math.PI /180;
    }
    //region 方位角计算,返回值为0-2*pi的弧度值
    public static double fangweijiaojisuan(double x1, double y1, double x2, double y2) {//根据坐标计算方位角
        double a = 180 - 90 * Math.abs(y2 - y1 + Math.pow(10, -10)) / (y2 - y1 + Math.pow(10, -10)) - Math.atan((x2 - x1) / (y2 - y1 + Math.pow(10, -10))) * 180 / Math.PI;
        //加上10^-10可以保证y2 = y1时不会报错
        return (a * Math.PI / 180);
    }

    //endregion
    public static String hudutoDMS(double hudu) {
        double du, d, m, s, result;
        if (hudu > 2 * Math.PI) {
            hudu -= 2 * Math.PI;
        }
        if(hudu < 0) {
            hudu += 2 * Math.PI;
        }
        du = hudu * 180 / Math.PI;//转化为度，再进行度.分秒的转化
        d = Math.floor(du);
        m = Math.floor(60 * (du - d));
        s = 60 * (60 * (du - d) - m);
        result = d + m / 100 + s / 10000;
        if ((60 - s) < 0.01){//实现分秒的60进制
            m = m + 1;
            s = 0;
            if (60 - m == 0){
                result = d + 1;
                m = 0;
            }
        }
        return (int)d + "°" + (int)m + "′" + Round(s,1) + "″";
    }
    public static double Round(double d, int i)//标准的四舍六入五留双函数
    {
        int k = 1;
        if (d <0){
            d = Math.abs(d);
            k = -1;
        }
        double ratio = Math.pow(10, i);
        double num = d * ratio;
        double mod = num % 1;
        BigDecimal bg = new BigDecimal(mod);
        mod = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        double integer = Math.floor(num);
        double returnNum;
        if(mod > 0.5){
            returnNum=(integer + 1) / ratio;
        }
        else if(mod < 0.5){
            returnNum=integer / ratio;
        }
        else{
            returnNum=(integer % 2 == 0 ? integer : integer + 1) / ratio;
        }
        return returnNum * k;
    }
    //endregion
    //region 弧度化秒，导线计算时用到
    public static double hudutos(double hudu) {
        double d, m, s;
        double du = hudu * 180 / Math.PI;
        d = Math.floor(du);
        m = Math.floor((du - d) * 60);
        s = Round(((du - d) * 60 - m) * 60, 1);
        return d * 3600 + m * 60 + s;//保留到0.1秒
    }
    /// <summary>
    /// Dms2Rad
    /// </summary>
    /// <param name="dms">dms</param>
    /// <returns>rad</returns>
    public static double Dms2Rad(double dms)
    {
        int sign = 1;
        double rad = 0, sec = 0;
        int deg = 0, minu = 0;
        if (dms < 0)
        {
            sign = -1;
            dms = -dms;
        }
        //deg = (int)(dms + 0.0001);
        //minu = (int)((dms - deg) * 100 + 0.0001);
        deg = (int)(Math.floor(dms));
        minu = (int)(Math.floor((dms - deg) * 100.0));

        sec = (dms - deg - minu / 100.0) * 10000;
        rad = deg + minu / 60.0 + sec / 3600.0;
        rad = rad / 180.0 * Math.PI;
        rad = rad * sign;
        return rad;
    }
    /// <summary>
    /// 控制输出为小数点后三位并四舍五入
    /// </summary>
    /// <param name="a"></param>
    /// <param name="f"></param>
    public String div1(double d){
        String str =String.valueOf(d);
        BigDecimal bigDecimal=new BigDecimal(str);
        return String.valueOf(bigDecimal.setScale(3, RoundingMode.HALF_UP));
    }
    public String div2(double d){
        String str =String.valueOf(d);
        BigDecimal bigDecimal=new BigDecimal(str);
        return String.valueOf(bigDecimal.setScale(4, RoundingMode.HALF_UP));
    }
    public static double Radtodms_no(double Rad) {
        double du, d, m, s, result;
        int i = 1;
        if (Rad < 0)
        {
            i = -1;
            Rad = Math.abs(Rad);
        }
        du = Rad * 180 / Math.PI;//转化为度，再进行度.分秒的转化
        d = Math.floor(du);
        m = Math.floor(60 * (du - d));
        s = 60 * (60 * (du - d) - m);
        result = d + m / 100 + s / 10000;
        if ((60 - s) < 0.01){//秒保留两位小数，实现分秒的60进制
            m = m + 1;
            if (60 - m == 0){
                result = d + 1;
            }
        }
        return Round(i * result,6);//保留了6位小数，以保证精度
    }
    /// <summary>
    /// Rad2Dms
    /// </summary>
    /// <param name="rad">Rad</param>
    /// <returns>dms</returns>
    public static double Rad2Dms(double rad)
    {
        double dms = 0, sec = 0;
        int deg = 0, minu = 0;
        int sign = 1;
        if (rad < 0)
        {
            sign = -1;
            rad = -rad;
        }
        dms = rad / Math.PI * 180;

        //deg = (int)(dms + 0.0001);
        //minu = (int)((dms - deg) * 60 + 0.0001);
        deg = (int)(Math.floor(dms));
        minu = (int)(Math.floor((dms - deg) * 60.0));

        sec = (dms - deg - minu / 60.0) * 3600.0;
        dms = deg + minu / 100.0 + sec / 10000.0;
        dms = sign * dms;
        return dms;
    }
    //region 生成指定长度字符串，不足位右补空格
    public static String formatStr(String str, int length) {
        int strLen;
        if (str == null) {
            strLen = 0;
        }else{
            strLen= str.length();
        }

        if (strLen == length) {
            return str;
        }
        else if (strLen < length) {
            int temp = length - strLen;
            String tem = "";
            for (int i = 0; i < temp; i++) {
                tem = tem + " ";
            }
            return str + tem;
        }
        else{
            return str;
        }
    }
}
