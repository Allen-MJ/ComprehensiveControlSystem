package allen.frame.entry;

import java.io.Serializable;

public class Response implements Serializable {
	/**
	 * 数据
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String message;
	private String data;
	public Response() {
		super();
	}
	@Override
	public String toString() {
		return "Response [code=" + code + ", message=" + message + ", data="
				+ data + "]";
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public boolean isSuccess(String code){
		return code.equals(getCode());
	}
}
