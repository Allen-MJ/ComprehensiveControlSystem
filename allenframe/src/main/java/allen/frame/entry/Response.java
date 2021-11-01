package allen.frame.entry;

import java.io.Serializable;

public class Response implements Serializable {
	/**
	 * 数据
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String msg;
	private Object obj;
	public Response() {
		super();
	}

	public Response(String code, String msg, String obj) {
		this.code = code;
		this.msg = msg;
		this.obj = obj;
	}

	@Override
	public String toString() {
		return "Response [code=" + code + ", msg=" + msg + ", obj="
				+ obj + "]";
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public boolean xequals(String ... codes){
		boolean isok = false;
		if(codes==null){
			throw new NullPointerException("code is empty");
		}else{
			for(String c:codes){
				isok = isok || c.equals(code);
			}
		}
		return isok;
	}

	public boolean codeEquals(String ... codes){
		boolean isok = false;
		if(codes==null){
			throw new NullPointerException("code is empty");
		}else{
			for(String c:codes){
				isok = isok || c.equals(code);
			}
		}
		return isok;
	}
}
