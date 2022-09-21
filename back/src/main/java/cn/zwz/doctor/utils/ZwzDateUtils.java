package cn.zwz.doctor.utils;

import cn.zwz.data.utils.ZwzNullUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ZwzDateUtils {


    /**
     * 返回当月月份
     * @return 2021-11
     */
    public static String getZwzNowMouth() {
        Date date = new Date();
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        return year + "-" + (month < 10 ? "0" + month : month);
    }

    /**
     * 返回上月月份
     * @return 2021-10
     */
    public static String getZwzLastMouth() {
        Date date = new Date();
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        if(month > 1) {
            month --;
        } else {
            month = 12;
            year --;
        }
        return year + "-" + (month < 10 ? "0" + month : month);
    }

    /**
     * 返回已过天数
     */
    public static int getZwzInTimeDaySize(String date) {
        if(ZwzNullUtils.isNull(date)) {
            return -1;
        }
        String[] split = date.split("-");
        if(split.length != 3) {
            return -2;
        }
        try {
            int i = Integer.parseInt(split[2]);
            return i;
        }catch (Exception e) {
            return -3;
        }
    }

    /**
     * 生成档案日期流水号
     * @return
     */
    public static String makeArchivesSerialNumber(long size){
        Date date = new Date();
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int date1 = date.getDate();
        return year + "" + (month < 10 ? "0" + month : month) + (date1 < 10 ? "0" + date1 : date1) + String.format("%03d", size + 1);
    }

    public static String miaoToStr(int miao){
        int hour = miao / 3600;
        int minu = (miao % 3600) / 60;
        miao %= 60;
        if(hour > 0){
            return hour + "时" + minu + "分" + miao + "秒";
        }if(minu > 0){
            return minu + "分" + miao + "秒";
        }
        return miao + "秒";
    }

    /**
     * 根据距离零点的时间戳,计算具体时间
     * @param time
     * @return
     */
    public static String getTimeBySJC(Integer time){
        int hour = time / 3600;
        int minu = (time % 3600) / 60;
        int miao = (time % 60);
        return hour + ":" + (minu < 10 ? "0" + minu : minu) + ":" + (miao < 10 ? "0" + miao : miao);
    }

    /**
     * 获取一个月有几天
     * @param year
     * @param mouth
     * @return
     */
    public static int getDaysByMouth(String year,String mouth){
        int y = Integer.parseInt(year);
        int m = Integer.parseInt(mouth);
        if(m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12 ){
            return 31;
        }else if(m == 4 || m == 6 || m == 9 || m == 11 ){
            return 30;
        }
        if(isRunNian(y)){
            return 29;
        }
        return 28;
    }

    public static int getDaysByMouth(){
        Date date = new Date();
        int y = date.getYear() + 1900;
        int m = date.getMonth() + 1;
        if(m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12 ){
            return 31;
        }else if(m == 4 || m == 6 || m == 9 || m == 11 ){
            return 30;
        }
        if(isRunNian(y)){
            return 29;
        }
        return 28;
    }

    public static int getDaysByLastMouth(){
        Date date = new Date();
        int y = date.getYear() + 1900;
        int m = date.getMonth();
        if(m == 0) {
            m = 12;
            y --;
        }
        if(m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12 ){
            return 31;
        }else if(m == 4 || m == 6 || m == 9 || m == 11 ){
            return 30;
        }
        if(isRunNian(y)){
            return 29;
        }
        return 28;
    }

    /**
     * 获取指定日期的星期
     * int nowStar = getNowStar("2021-1-1");
     * @param date
     * @return
     */
    public static int getNowStar(String date) {
        try{
            SimpleDateFormat simFormat = new SimpleDateFormat("yyyy-M-d");
            Date date1 = simFormat.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (w < 0)  {
                w = 0;
            }
            return w;
        }catch (Exception e){
            System.out.println("日期格式错误");
            return -1;
        }
    }

    public static String getNowDate(){
        Date date = new Date();
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int date1 = date.getDate();
        return year + "-" + month + "-" + date1;
    }

    public static String getCompleteNowDate(){
        Date date = new Date();
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int date1 = date.getDate();
        return year + "-" + (month < 10 ? "0" + month : month) + "-" + (date1 < 10 ? "0" + date1 : date1);
    }

    public static String getCompleteNowDate1(){
        Date date = new Date();
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int date1 = date.getDate();
        int hours = date.getHours();
        String s = UUID.randomUUID().toString();
        s = s.substring(s.length() - 4,s.length());
        return "" + year + (month < 10 ? "0" + month : month) + (date1 < 10 ? "0" + date1 : date1) + (hours < 10 ? "0" + hours : hours) + s;
    }

    public static String getCompleteNowDate2(){
        Date date = new Date();
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int date1 = date.getDate();
        int hours = date.getHours();
        String s = UUID.randomUUID().toString();
        s = s.substring(s.length() - 3,s.length());
        return "" + year + (month < 10 ? "0" + month : month) + (date1 < 10 ? "0" + date1 : date1) + (hours < 10 ? "0" + hours : hours) + s + "A";
    }


    public static String getCompleteNextDate(){
        Date date = new Date(System.currentTimeMillis() + 86400000);
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int date1 = date.getDate();
        return year + "-" + (month < 10 ? "0" + month : month) + "-" + (date1 < 10 ? "0" + date1 : date1);
    }


    /**
     * 传入年月,求计算打卡日期到几号
     * @return
     */
    public static int getNowOnlyDate(String aYear,String aMouth){
        Date date = new Date();
        int aaYear = Integer.parseInt(aYear);
        int aaMouth = Integer.parseInt(aMouth);
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int date1 = date.getDate();
        if(aaYear < year || ( aaYear == year && aaMouth < month)){
            return getDaysByMouth(aYear,aMouth);
        }
        return date1;
    }

    public static String getNowMouth(){
        Date date = new Date();
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        return year + "-" + month;
    }

    public static String getNowOnlyMouth(){
        Date date = new Date();
        int month = date.getMonth() + 1;
        return "" + month;
    }

    public static String getNowYear(){
        Date date = new Date();
        int year = date.getYear() + 1900;
        return year + "";
    }

    public static String getNowTime(){
        Date date = new Date();
        int hour = date.getHours();
        int minutes = date.getMinutes();
        int seconds = date.getSeconds();
        return hour + "时" + minutes + "分" + seconds + "秒";
    }

    public static String getNextYearDate(String date){
        String[] split = date.split("-");
        int year = Integer.parseInt(split[0]) + 1;
        return year + "-" + split[1] + "-" + split[2];
    }

    public static boolean isRunNian(int year){
        if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
            return true;
        return false;
    }
    public static boolean isRunNian(String yearStr){
        int year = Integer.parseInt(yearStr);
        if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
            return true;
        return false;
    }

    public static String getBirthday(String idcard){
        if(idcard == null || ZwzNullUtils.isNull(idcard) || idcard.length() < 17)
            return null;
        return idcard.substring(6,10) + "-" + idcard.substring(10,12) + "-" + idcard.substring(12,14);
    }

    public static int dateDiff(String startDate,String endDate)
    {
        // int year1, int month1, int day1, int year2, int month2, int day2
        String[] start = startDate.split("-");
        String[] end = endDate.split("-");

        int year1 = Integer.parseInt(start[0]);
        int month1 = Integer.parseInt(start[1]);
        int day1 = Integer.parseInt(start[2]);

        int year2 = Integer.parseInt(end[0]);
        int month2 = Integer.parseInt(end[1]);
        int day2 = Integer.parseInt(end[2]);

        int y2, m2, d2, y1, m1, d1;
        m1 = (month1 + 9) % 12;
        y1 = year1 - m1 / 10;
        d1 = 365 * y1 + y1 / 4 - y1 / 100 + y1 / 400 + (m1 * 306 + 5) / 10 + (day1 - 1);

        m2 = (month2 + 9) % 12;
        y2 = year2 - m2 / 10;
        d2 = 365 * y2 + y2 / 4 - y2 / 100 + y2 / 400 + (m2 * 306 + 5) / 10 + (day2 - 1);
        return (d2 - d1);
    }

    public static int dateDiff(String startDate)
    {
        // int year1, int month1, int day1, int year2, int month2, int day2
        String[] end = startDate.split("-");
        String[] start = getNowDate().split("-");

        int year1 = Integer.parseInt(start[0]);
        int month1 = Integer.parseInt(start[1]);
        int day1 = Integer.parseInt(start[2]);

        int year2 = Integer.parseInt(end[0]);
        int month2 = Integer.parseInt(end[1]);
        int day2 = Integer.parseInt(end[2]);

        int y2, m2, d2, y1, m1, d1;
        m1 = (month1 + 9) % 12;
        y1 = year1 - m1 / 10;
        d1 = 365 * y1 + y1 / 4 - y1 / 100 + y1 / 400 + (m1 * 306 + 5) / 10 + (day1 - 1);

        m2 = (month2 + 9) % 12;
        y2 = year2 - m2 / 10;
        d2 = 365 * y2 + y2 / 4 - y2 / 100 + y2 / 400 + (m2 * 306 + 5) / 10 + (day2 - 1);
        return (d2 - d1);
    }


    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
        String res = "";
        if (!"".equals(s)) {
            try {
                res = String.valueOf(sdf.parse(s).getTime()/1000);
            } catch (Exception e) {
                System.out.println("传入了null值");
            }
        }else {
            long time = System.currentTimeMillis();
            res = String.valueOf(time/1000);
        }

        return res;
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp0(String s){
        s += " 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
        String res = "";
        if (!"".equals(s)) {
            try {
                res = String.valueOf(sdf.parse(s).getTime()/1000);
            } catch (Exception e) {
                System.out.println("传入了null值");
            }
        }else {
            long time = System.currentTimeMillis();
            res = String.valueOf(time/1000);
        }

        return res;
    }

    public static List<Long> getMouthStamp0(String mouth){
        String start = mouth + "-01 00:00:00";
        String[] split = mouth.split("-");
        int daysByMouth = getDaysByMouth(split[0], split[1]);
        String end = mouth + "-" + daysByMouth + " 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long res1 = 0l;
        long res2 = 0l;
        List<Long> ansList = new ArrayList<>();
        try {
            res1 = sdf.parse(start).getTime()/1000;
            res2 = sdf.parse(end).getTime()/1000;
        } catch (Exception e) {
            System.out.println("传入了null值");
        }
        ansList.add(res1);
        ansList.add(res2);
        return ansList;
    }

    /*
     * 将时间转换为时间戳
     */
    public static long dateToStamp2(String s){
        if(s.endsWith(".0")) {
            try{
                String[] split = s.split(".0");
                s = split[0];
            }catch (Exception e) {}
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
        long res = 0;
        if (!"".equals(s)) {
            try {
                res = sdf.parse(s).getTime();
            } catch (Exception e) {
                System.out.println("传入了null值");
            }
        }else {
            long time = System.currentTimeMillis();
            res = time;
        }
        return res;
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp00(String s){
        s += " 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String res = "";
        if (!"".equals(s)) {
            try {
                res = String.valueOf(sdf.parse(s).getTime()/1000);
            } catch (Exception e) {
                System.out.println("传入了null值");
            }
        }else {
            long time = System.currentTimeMillis();
            res = String.valueOf(time/1000);
        }
        return res;
    }

    public static long dateToStampLong(String s){
        s += " 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long res = 0;
        if (!"".equals(s)) {
            try {
                res = sdf.parse(s).getTime()/1000;
            } catch (Exception e) {
                System.out.println("传入了null值");
            }
        }else {
            long time = System.currentTimeMillis();
            res = time/1000;
        }
        return res;
    }

    /**
     * 获取当前时间戳
     * @return
     */
    public static long getZwzNowCuo() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获取当前完整日期时间
     * @return
     */
    public static String getZwzNowTime() {
        Date date = new Date();
        int hour = date.getHours();
        int minutes = date.getMinutes();
        int seconds = date.getSeconds();
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int date1 = date.getDate();
        return "" + year + "-" + (month < 10 ? "0" + month : month) + "-" + (date1 < 10 ? "0" + date1 : date1)
                + " " + hour + ":" + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
    }

    /**
     * 查询当日零点时间戳
     * @return
     */
    public static long zwzDateToCuo(String s){
        s += " 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(s).getTime()/1000;
        } catch (Exception e) {
            return System.currentTimeMillis()/1000;
        }
    }


    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(int time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
        String times = format.format(new Date(time * 1000L));
        return times;
    }

    public static String stampToOnlyDate(String timeStr){
        int time = Integer.parseInt(timeStr);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d");
        String times = format.format(new Date(time * 1000L));
        return times;
    }

    public static String stampToOnlyDate0(long timeStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String times = format.format(new Date(timeStr * 1000L));
        return times;
    }

    public static String starToChinese(int star){
        if(star == 0) {
            return "星期日";
        }
        if(star == 1) {
            return "星期一";
        }
        if(star == 2) {
            return "星期二";
        }
        if(star == 3) {
            return "星期三";
        }
        if(star == 4) {
            return "星期四";
        }
        if(star == 5) {
            return "星期五";
        }
        return "星期六";
    }

    public static String getNowChineseStar(){
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar=Calendar.getInstance();
        return weekDays[calendar.get(Calendar.DAY_OF_WEEK)-1];
    }

    public static String getNowChineseStar(String dateStr) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//设置时间格式
        Calendar cal = Calendar.getInstance();
        Date time= null;
        try {
            time = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(time);
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        return weekDays[cal.get(Calendar.DAY_OF_WEEK)-1];
    }

    public static String getNowNumberStar(String dateStr) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//设置时间格式
        Calendar cal = Calendar.getInstance();
        Date time= null;
        try {
            time = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(time);
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        return weekDays[cal.get(Calendar.DAY_OF_WEEK)-1];
    }

    /**
     * 返回当日周一0点的时间戳
     * @return
     */
    public static long getStar1Cuo() {
        String date = getCompleteNowDate();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//设置时间格式
        Calendar cal = Calendar.getInstance();
        Date time= null;
        try {
            time = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(time);
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        long l = dateToStampLong(date);
        int i = cal.get(Calendar.DAY_OF_WEEK);
        if(i == 1) {
            l -= 7 * 86400;
        }else {
            l-= (i - 2) * 86400;
        }
        return l;
    }


    /**
     * 根据上下班时间戳 获取班次
     * @return
     */
    public static String getBanCiByCuo(int shangBan, int xiaBan) {
        int sHour = shangBan / 3600;
        int sMin = shangBan % 3600 / 60;
        int sMiao = shangBan % 60;
        int eHour = xiaBan / 3600;
        int eMin = xiaBan % 3600 / 60;
        int eMiao = xiaBan % 60;
        String sStr = "早" + sHour;
        String eStr = "晚" + eHour;
        if(sMin != 0) {
            sStr += "点" + sMin;
        }
        if(sMiao != 0) {
            sStr += "分" + sMiao + "秒";
        }
        if(eMin != 0) {
            eStr += "点" + eMin;
        }
        if(eMiao != 0) {
            eStr += "分" + eMiao + "秒";
        }
        return sStr + eStr;
    }

    /**
     * 根据时间戳获取时间
     * @return
     */
    public static String getChineseTimeByCuo(int startTime,int endTime) {
        int sHour = startTime / 3600;
        int sMin = startTime % 3600 / 60;
        int sMiao = startTime % 60;
        int eHour = endTime / 3600;
        int eMin = endTime % 3600 / 60;
        int eMiao = endTime % 60;
        String sStr = "" + sHour + "点";
        String eStr = "-" + eHour + "点";
        if(sMin != 0) {
            sStr += "" + sMin;
        }
        if(sMiao != 0) {
            sStr += "分" + sMiao + "秒";
        }
        if(eMin != 0) {
            eStr += "" + eMin;
        }
        if(eMiao != 0) {
            eStr += "分" + eMiao + "秒";
        }
        return sStr + eStr;
    }

    /**
     * 根据秒计算时间长度
     * @return 中文
     */
    public static String getTimeLengthByMiao(int miao) {
        int sHour = miao / 3600;
        int sMin = miao % 3600 / 60;
        int sMiao = miao % 60;
        String sStr = "";
        if(sHour != 0) {
            sStr = sHour + "时";
        }
        if(sMin != 0) {
            sStr += sMin + "分";
        }
        if(sMiao != 0) {
            sStr += sMiao + "秒";
        }
        return sStr;
    }
}
