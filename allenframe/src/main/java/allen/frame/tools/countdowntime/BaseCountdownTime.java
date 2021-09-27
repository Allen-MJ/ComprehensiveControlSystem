package allen.frame.tools.countdowntime;

public abstract class BaseCountdownTime {
	protected int day;
	protected int hour;
	protected int minute;
	protected int second;

	abstract String getTimeText();
}
