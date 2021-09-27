package allen.frame.entry;

import java.io.Serializable;

public class DownLoadInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String url;
	private long size;
	private long lastPosition;
	public final static long TYPE_ERRO = -1;
	public DownLoadInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public long getLastPosition() {
		return lastPosition;
	}
	public void setLastPosition(long lastPosition) {
		this.lastPosition = lastPosition;
	}
}
