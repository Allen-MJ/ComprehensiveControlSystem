package allen.frame.tools.countdowntime;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public abstract class BaseCountdownTimeManager {
	protected ArrayList<CountdownTime> timeQueue;
	protected Timer timeTimer;
	protected TimerTask timeTask;

	public void removeTime(CountdownTime time) {
		if (timeQueue != null) {
			for (int i = 0; i < timeQueue.size(); i++) {
				if (TextUtils.equals(time.getId(), timeQueue.get(i).getId())) {
					timeQueue.remove(i);
				}
			}
		}
	}

	abstract void countdownTimeQueue();

	abstract void initCountdownTimeQueueManager();

	public abstract CountdownTime addTime(int time, String id, CountdownTime.OnCountdownTimeListener listener);
}
