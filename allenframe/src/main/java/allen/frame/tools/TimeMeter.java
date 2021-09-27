package allen.frame.tools;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimeMeter {
	private long start = 0;
	private static TimeMeter meter;
	private long maxTime = 0;//最大时间（单位s）
	private Timer timer;
	private TimerTask task;
	private boolean isWorking = false;
	public TimeMeter(){
	};
	public static TimeMeter getInstance(){
		if(meter==null){
			meter = new TimeMeter();
		}
		return meter;
	}
	
	public TimeMeter setMaxTime(long maxTime){
		this.maxTime = maxTime;
		return meter;
	}
	
	public static String getCountdown(long time){
		String countdown = "00分00秒";
		StringBuffer sb = new StringBuffer();
		if(time>0){
			long h = time/3600;
			long m = (time%3600)/60;
			long s = (time%3600)%60;
			if(h>0){
				sb.append(getTwoNumber(h));
				sb.append("时");
			}
			if(m>0){
				sb.append(getTwoNumber(m));
			}else{
				sb.append("00");
			}
			sb.append("分");
			sb.append(getTwoNumber(s));
			sb.append("秒");
			return sb.toString();
		}
		return countdown;
	}
	
	private static String getTwoNumber(long time){
		if(time<10){
			return "0"+time;
		}
		return String.valueOf(time);
	}
	
	public TimeMeter start(){
		start = System.currentTimeMillis();
		if(timer==null){
			timer = new Timer();
		}
		if(task!=null){
			task.cancel();
		}
		startTask();
		if(task!=null&&timer!=null){
			timer.schedule(task, 1000,1000);
		}
		handler.sendEmptyMessage(0);
		return meter;
	};
	
	public TimeMeter start(String time){
		Logger.e("debug", "time:"+time);
		if(StringUtils.empty(time)){
			start = System.currentTimeMillis();
		}else{
			start = getTime2Long(time);
		}
		if(timer==null){
			timer = new Timer();
		}
		if(task!=null){
			task.cancel();
		}
		startTask();
		if(task!=null&&timer!=null){
			timer.schedule(task, 1000,1000);
		}
		handler.sendEmptyMessage(0);
		return meter;
	};
	
	public TimeMeter stop(){
		if(timer!=null){
			timer.cancel();
		}
		if(task!=null){
			task.cancel();
		}
		timer = null;
		start = 0;
		handler.sendEmptyMessage(2);
		return meter;
	}
	/**
	 * 将字符串转为时间戳
	 * @param time
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static long getTime2Long(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try{
            date = sdf.parse(time);
            return date.getTime();
        } catch(ParseException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis();
	}
	
	public void setTimerLisener(OnTimerLisener lisener) {
		this.lisener = lisener;
	}
	
	private void startTask(){
		task = new TimerTask() {
			
			@Override
			public void run() {
				long cu = System.currentTimeMillis();
				long offtime = (cu-start)/1000;
				if(maxTime<=0){
					Message msg = new Message();
					msg.what = 1;
					msg.obj = offtime;
					handler.sendMessage(msg);
				}else{
					if(offtime<maxTime){
						Message msg = new Message();
						msg.what = 1;
						msg.obj = maxTime-offtime;
						handler.sendMessage(msg);
					}else{
						stop();
					}
				}
			}
		};
	}
	
	public boolean isWorking() {
		return isWorking;
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0://开始
				isWorking = true;
				if(lisener!=null){
					lisener.onStart();
				}
				break;
			case 1://计时
				isWorking = true;
				long inTime = (Long) msg.obj;
				if(lisener!=null){
					lisener.onInTime(inTime);
				}
				break;
			case 2://结束(走满)
				isWorking = false;
				if(lisener!=null){
					lisener.onEnd();
				}
				break;
			default:
				break;
			}
		};
	};
	private OnTimerLisener lisener;
	public interface OnTimerLisener{
		public void onStart();
		public void onInTime(long inTime);
		public void onEnd();
	}
}
