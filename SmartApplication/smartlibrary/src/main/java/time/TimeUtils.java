package time;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static final String TIME_TYPE_01 = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_TYPE_02 = "yyyy-MM-dd";

    /**
     * 时间戳转换成字符窜
     */
    public static String getDateToString(long time, String type) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat(type);
        return sf.format(d);
    }

    /**
     * 将字符串转为时间戳
     */
    public static long getStringToDate(String time) {
        return getStringToDate(time,TIME_TYPE_01);
    }

    public static long getStringToDate(String time , String type) {
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }



    //获取时间间隔
    public static String getTimeInterval(String timestamp) {
        return getInterval(getDate(timestamp));
    }

    // 时间间隔计算
    public static String getInterval(String createtime) { // 传入的时间格式必须类似于2012-8-21  17:53:20这样的格式

        // 将时间戳转换为一下格式

        String interval = null;

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date d1 = (Date) sd.parse(createtime, pos);

        // 用现在距离1970年的时间间隔new
        // Date().getTime()减去以前的时间距离1970年的时间间隔d1.getTime()得出的就是以前的时间与现在时间的时间间隔
        Date d3 = new Date();
        long time = (d3.getTime() - d1.getTime()) / 1000;// 得出的时间间隔是毫秒

        if (time < 60 && time > 0) {
            interval = time + "秒前";

        } else if (time > 60 && time < 3600) {
            interval = time / 60 + "分钟前";

        } else if (time >= 3600 && time < 3600 * 1) {
            interval = time / 3600 + "小时前";

        } else if (3600 * 1 < time && time < 3600 * 24) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ParsePosition pos2 = new ParsePosition(0);
            Date d2 = (Date) sdf.parse(createtime, pos2);
            interval = sdf.format(d2).substring(11, 16);
        } else if (time >= 3600 * 24 && time < 3600 * 48) {
            interval = "昨天";

        } else if (time >= 3600 * 48 && time < 3600 * 72) {
            interval = "前天";
        } else if (time >= 3600 * 72) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            ParsePosition pos2 = new ParsePosition(0);
            Date d2 = (Date) sdf.parse(createtime, pos2);
            interval = sdf.format(d2);

        }
        return interval;
    }

    // 从1970年到今天的秒数转为日期
    public static String getDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(Long.parseLong(time)));
    }


//    /**
//     * 判断当输入日期是星期几
//     *
//     * @param pTime 设置的需要判断的时间  //格式如2012-09-08
//     * @return dayForWeek 判断结果
//     * @Exception 发生异常
//     */
//    public static String getWeek(String pTime) {
//        String Week = "";
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Calendar c = Calendar.getInstance();
//        try {
//            c.setTime(format.parse(pTime));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
//            Week += "天";
//        }
//        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
//            Week += "一";
//        }
//        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
//            Week += "二";
//        }
//        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
//            Week += "三";
//        }
//        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
//            Week += "四";
//        }
//        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
//            Week += "五";
//        }
//        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
//            Week += "六";
//        }
//        return Week;
//    }
//
//    /**
//     * 判断当输入日期
//     *
//     * @param pTime 设置的需要判断的时间  //格式如2012-09-08
//     * @return dayForWeek 判断结果
//     * @Exception 发生异常
//     */
//    public static String getDay(String pTime) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Calendar c = Calendar.getInstance();
//        try {
//            c.setTime(format.parse(pTime));
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return c.get(Calendar.DAY_OF_MONTH) + "";
//    }
//
//    /**
//     * 判断当输入日期年月
//     *
//     * @param pTime 设置的需要判断的时间  //格式如2012-09-08
//     * @return dayForWeek 判断结果
//     * @Exception 发生异常
//     */
//    public static String getYearMonth(String pTime) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Calendar c = Calendar.getInstance();
//        try {
//            c.setTime(format.parse(pTime));
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH)+1) + "月";
//    }
//
//    /**
//     * 判断当输入日期的时间
//     *
//     * @param pTime 设置的需要判断的时间  //格式如2012-09-08
//     * @return dayForWeek 判断结果
//     * @Exception 发生异常
//     */
//    public static String getTime(String pTime) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Calendar c = Calendar.getInstance();
//        try {
//            c.setTime(format.parse(pTime));
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        int h = c.get(Calendar.HOUR_OF_DAY);
//        int m = c.get(Calendar.MINUTE);
//
//        return (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m);
//    }
//
//
//    public static String getDate(String pTime) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Calendar c = Calendar.getInstance();
//        try {
//            c.setTime(format.parse(pTime));
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH)+1) + "-"+c.get(Calendar.DAY_OF_MONTH);
//    }
//
//    /**
//     * 获取当前日期
//     *
//     * @return
//     */
//    public static String getCurrentDate() {
//        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String date = sDateFormat.format(new Date());
//        return date;
//    }
//
//    /**
//     * 获取当前时间
//     *
//     * @return
//     */
//    public static String getCurrentTime() {
//        SimpleDateFormat sDateFormat = new SimpleDateFormat("HH:mm");
//        String date = sDateFormat.format(new Date());
//        return date;
//    }
//
//    /**
//     * 获取当前全量日期yyyy-MM-dd HH:mm:ss
//     *
//     * @return
//     */
//    public static String getCurrentD() {
//        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date = sDateFormat.format(new Date());
//        return date;
//    }
//
//
//

//
//    public static String getDateToString(Date date) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        return sdf.format(date);
//    }

}