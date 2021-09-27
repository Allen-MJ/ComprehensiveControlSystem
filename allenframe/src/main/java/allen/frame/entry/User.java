package allen.frame.entry;

import java.io.Serializable;

import allen.frame.tools.StringUtils;

public class User implements Serializable {
	private String UID;
	private String UCode;
	private String Pwd;
	private String UName;
	private String RID;
	private String UserHead;
	private String UserHeads;
	private int UClass;
	private String AddID;
	private String AddDates;
	private String LastDates;
	private int State;
	private int Mmxg;
	private String GXQM;
	private String bmbj;
	private String QQKEY;
	private String WXKEY;
	private String Jpushqz;
	private String SchoolMenu;
	public String getUID() {
		return UID;
	}
	public void setUID(String uID) {
		UID = uID;
	}
	public String getUCode() {
		return UCode;
	}
	public void setUCode(String uCode) {
		UCode = uCode;
	}
	public String getPwd() {
		return Pwd;
	}
	public void setPwd(String pwd) {
		Pwd = pwd;
	}
	public String getUName() {
		return UName;
	}
	public void setUName(String uName) {
		UName = uName;
	}
	public String getRID() {
		return RID;
	}
	public void setRID(String rID) {
		RID = rID;
	}
	public String getUserHead() {
		return UserHead;
	}
	public void setUserHead(String userHead) {
		UserHead = userHead;
	}
	public String getUserHeads() {
		return UserHeads;
	}
	public void setUserHeads(String userHeads) {
		UserHeads = userHeads;
	}
	public int getUClass() {
		return UClass;
	}
	public void setUClass(int uClass) {
		UClass = uClass;
	}
	public String getAddID() {
		return AddID;
	}
	public void setAddID(String addID) {
		AddID = addID;
	}
	
	public String getAddDates() {
		return AddDates;
	}
	public void setAddDates(String addDates) {
		AddDates = addDates;
	}
	public String getLastDates() {
		return LastDates;
	}
	public void setLastDates(String lastDates) {
		LastDates = lastDates;
	}
	public int getState() {
		return State;
	}
	public void setState(int state) {
		State = state;
	}
	public int getMmxg() {
		return Mmxg;
	}
	public void setMmxg(int mmxg) {
		Mmxg = mmxg;
	}
	public String getGXQM() {
		return StringUtils.null2Empty(GXQM);
	}
	public void setGXQM(String gXQM) {
		GXQM = gXQM;
	}
	public String getBmbj() {
		return bmbj;
	}
	public void setBmbj(String bmbj) {
		this.bmbj = bmbj;
	}
	public String getQQKEY() {
		return QQKEY;
	}
	public void setQQKEY(String qQKEY) {
		QQKEY = qQKEY;
	}
	public String getWXKEY() {
		return WXKEY;
	}
	public void setWXKEY(String wXKEY) {
		WXKEY = wXKEY;
	}
	public String getJpushqz() {
		return Jpushqz;
	}
	public void setJpushqz(String jpushqz) {
		Jpushqz = jpushqz;
	}
	public String getSchoolMenu() {
		return SchoolMenu;
	}
	public void setSchoolMenu(String schoolMenu) {
		SchoolMenu = schoolMenu;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "User [UID=" + UID + ", UCode=" + UCode + ", Pwd=" + Pwd
				+ ", UName=" + UName + ", RID=" + RID + ", UserHead="
				+ UserHead + ", UClass=" + UClass + ", AddID=" + AddID
				+ ", AddDates=" + AddDates + ", LastDates=" + LastDates
				+ ", State=" + State + ", Mmxg=" + Mmxg + "]";
	}
	
}
