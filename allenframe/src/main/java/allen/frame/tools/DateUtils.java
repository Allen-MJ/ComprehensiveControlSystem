package allen.frame.tools;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

    /**
     * 获取当前日期对应的星期
     */
    public static String[] WEEK = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    public static final int WEEKDAYS = 7;
    private static Calendar calendar = Calendar.getInstance();

    public static String DateToWeek(Date date) {
        calendar.setTime(date);
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayIndex < 1 || dayIndex > WEEKDAYS) {
            return null;
        }

        return WEEK[dayIndex - 1];
    }

    public static String getDate() {
        //获取系统的日期
//年
        int year = calendar.get(Calendar.YEAR);
//月
        int month = calendar.get(Calendar.MONTH) + 1;
//日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "年" + month + "月" + day + "日 ";
    }

    public static String getTime() {
//获取系统时间
//小时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//分钟
        int minute = calendar.get(Calendar.MINUTE);
//秒
        int second = calendar.get(Calendar.SECOND);
        return hour + "：" + minute;
    }

    /**
     * yyyy-MM-dd HH:mm:ss 转换成 个性时间
     *
     * @param date
     * @return
     */
    public static String getChDate(String date) {
        int y, m, d;
        String hh, mm;
        if (StringUtils.notEmpty(date)) {
            if (date.contains(" ")) {
                String[] das = date.split(" ");
                String[] da1s = das[0].split("-");
                String[] da2s = das[1].split(":");
                y = Integer.parseInt(da1s[0]);
                m = Integer.parseInt(da1s[1]);
                d = Integer.parseInt(da1s[2]);
                hh = da2s[0];
                mm = da2s[1];
                Time time = new Time();
                time.setToNow();
                if (y == time.year) {
                    return m + "月" + d + "日 " + hh + ":" + mm;
                } else {
                    return da1s[0] + "年" + m + "月" + d + "日 " + hh + ":" + mm;
                }
            }
        }
        return StringUtils.null2Empty(date);
    }

    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年
    /**
     * 返回文字描述的日期
     *
     * @param dateString
     * @return
     */
    public static String getTimeFormatText(String dateString) {
        if (StringUtils.empty(dateString)) {
            return null;
        }
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            return r + "个月前";
        }
        if (diff > day) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "个小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }

    public static void doTimeDateSetting(final Context context, Handler handler) {
        // textView.setOnClickListener(new View.OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // doSetTimeDialog(context, textView);
        // doSetDateDialog(context, textView);
        // }
        // });

        doSetTimeDialog(context, handler);
        doSetDateDialog(context, handler);
    }

    public static void doTimeDateSetting(final Context context, final TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSetTimeDialog(context, textView);
                doSetDateDialog(context, textView);
            }
        });

        // doSetTimeDialog(context, textView);
        // doSetDateDialog(context, textView);
    }

    /**
     * 设置其一个日期的
     *
     * @param context
     * @param handler
     */
    @SuppressLint("NewApi")
    public static void doSetDateDialog(Context context, final Handler handler) {
        final DatePicker mDatePicker = new DatePicker(context);
        AlertDialog.Builder timeDialog = new AlertDialog.Builder(context);
        timeDialog.setTitle("选择日期");
        timeDialog.setIcon(null);
        timeDialog.setCancelable(false);
        mDatePicker.setCalendarViewShown(false);
        timeDialog.setView(mDatePicker); // 只需将您时间控件的view引入
        timeDialog.setPositiveButton("确 定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 取出TimePicker设定的时间就行了
                int year = mDatePicker.getYear();
                int monthOfYear = mDatePicker.getMonth();
                int dayOfMonth = mDatePicker.getDayOfMonth();
                Message msg = new Message();
                if (monthOfYear < 9 && dayOfMonth < 10) {
                    msg.obj = year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth;
                    // textView.setText(year + "-0" + (monthOfYear + 1) + "-0" +
                    // dayOfMonth);
                } else if (dayOfMonth < 10) {
                    msg.obj = year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth;
                } else if (monthOfYear < 9) {
                    msg.obj = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                } else {
                    msg.obj = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                }
                msg.what = 100;
                handler.sendMessage(msg);
            }
        }).create();
        timeDialog.show();
    }

    // 显示其 时间 dialog
    public static void doSetTimeDialog(Context context, final Handler handler) {
        final TimePicker timePicker = new TimePicker(context);
        AlertDialog.Builder timeDialog = new AlertDialog.Builder(context);
        timeDialog.setTitle("选择时间");
        timeDialog.setIcon(null);
        timeDialog.setCancelable(false);
        timeDialog.setView(timePicker); // 只需将您时间控件的view引入
        timeDialog.setPositiveButton("确 定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 取出TimePicker设定的时间就行了
                int hhs = timePicker.getCurrentHour();
                int mms = timePicker.getCurrentMinute();
                Message msg = new Message();
                msg.obj = " " + hhs + ":" + mms + ":00";
                msg.what = 101;
                handler.sendMessage(msg);
                // textView.append(" " + hhs + ":" + mms + ":00");
            }
        }).create();
        timeDialog.show();

    }

    /**
     * 设置其一个日期的
     *
     * @param context
     * @param textView
     */
    @SuppressLint("NewApi")
    public static void doSetDateDialog(Context context, final TextView textView) {
        final DatePicker mDatePicker = new DatePicker(context);
        AlertDialog.Builder timeDialog = new AlertDialog.Builder(context);
        timeDialog.setTitle("选择日期");
        timeDialog.setIcon(null);
        timeDialog.setCancelable(false);
        mDatePicker.setCalendarViewShown(false);
        timeDialog.setView(mDatePicker); // 只需将您时间控件的view引入
        timeDialog.setPositiveButton("确 定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 取出TimePicker设定的时间就行了
                int year = mDatePicker.getYear();
                int monthOfYear = mDatePicker.getMonth();
                int dayOfMonth = mDatePicker.getDayOfMonth();
//				Message msg = new Message();
                if (monthOfYear < 9 && dayOfMonth < 10) {
//					msg.obj = year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth;
                    textView.setText(year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth);
                } else if (dayOfMonth < 10) {
//					msg.obj = year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth;
                    textView.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
                } else if (monthOfYear < 9) {
//					msg.obj = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                    textView.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
                } else {
//					msg.obj = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    textView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                }
            }
        }).create();
        timeDialog.show();
    }

    // 显示其 时间 dialog
    public static void doSetTimeDialog(Context context, final TextView textView) {
        final TimePicker timePicker = new TimePicker(context);
        AlertDialog.Builder timeDialog = new AlertDialog.Builder(context);
        timeDialog.setTitle("选择时间");
        timeDialog.setIcon(null);
        timeDialog.setCancelable(false);
        timeDialog.setView(timePicker); // 只需将您时间控件的view引入
        timeDialog.setPositiveButton("确 定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 取出TimePicker设定的时间就行了
                int hhs = timePicker.getCurrentHour();
                int mms = timePicker.getCurrentMinute();
                // Message msg = new Message();
                // msg.obj = " " + hhs + ":" + mms + ":00";
                // msg.what = 101;
                textView.setText(hhs + ":" + (mms < 10 ? "0" + mms : mms));
            }
        }).create();
        timeDialog.show();

    }

    /**
     * 两个日期间相差天数
     * @param startDate
     * @param endDate
     * @return
     */
    public static double days(String startDate, String endDate) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");// 输入日期的格式
        Date date1 = null;
        try {
            date1 = simpleDateFormat.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date2 = null;
        try {
            date2 = simpleDateFormat.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        GregorianCalendar cal1 = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();
        cal1.setTime(date1);
        cal2.setTime(date2);
        double dayCount = (cal2.getTimeInMillis() - cal1.getTimeInMillis()) / (1000 * 3600 * 24);// 从间隔毫秒变成间隔天数
        return dayCount;
    }

    /**
     * 某个日期距离当前时间的天数
     * @param endDate
     * @return
     */
    public static double days( String endDate) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");// 输入日期的格式
        Date date1 = null;
        try {
            date1 = simpleDateFormat.parse(String.valueOf(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date2 = null;
        try {
            date2 = simpleDateFormat.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        GregorianCalendar cal1 = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();
        cal1.setTime(date1);
        cal2.setTime(date2);
        double dayCount = (cal2.getTimeInMillis() - cal1.getTimeInMillis()) / (1000 * 3600 * 24);// 从间隔毫秒变成间隔天数
        return dayCount;
    }
}
