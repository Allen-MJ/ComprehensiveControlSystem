package allen.frame.entry;

import java.io.Serializable;
import java.util.ArrayList;

public class Type implements Serializable {
	private String id;
	private String name;
	private String pid;
	private String pname;
	private String type;
	private String remark;
	private int xjnum;//下一级数量
	private int resId;
	private String url;
	private ArrayList<Type> list;
	private int isShow = 0;
	private String num;
	private String selectText;

	/**
	 * Wg : string
	 * Wgid : string
	 */

	private String Wg;
	private String Wgid;

	public Type() {
		super();
	}
	public Type(String id,String name){
		this.id = id;
		this.name = name;
	}
	public Type(String id,String name,String remark){
		this.id = id;
		this.name = name;
		this.remark = remark;
	}
	public Type(String id,String name,int resId){
		this.id = id;
		this.name = name;
		this.resId = resId;
	}

	public Type(String id,String name,int resId,String remark){
		this.id = id;
		this.name = name;
		this.resId = resId;
		this.remark = remark;
	}
	public Type(String id,String name,String url,String remark){
		this.id = id;
		this.name = name;
		this.url = url;
		this.remark = remark;
	}

	public String getSelectText() {
		return selectText;
	}

	public void setSelectText(String selectText) {
		this.selectText = selectText;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public int getXjnum() {
		return xjnum;
	}
	public void setXjnum(int xjnum) {
		this.xjnum = xjnum;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getResId() {
		return resId;
	}
	public void setResId(int resId) {
		this.resId = resId;
	}

	public ArrayList<Type> getList() {
		return list;
	}

	public void setList(ArrayList<Type> list) {
		this.list = list;
	}

	public int getListSize(){
		int len = list==null?0:list.size();
		return len;
	}

	public void addType(Type item){
		int len = list==null?0:list.size();
		if(item==null){
			return;
		}
		if(len==0){
			this.list = new ArrayList<>();
		}
		this.list.add(item);
	}
	public void addList(ArrayList<Type> item){
		int len = list==null?0:list.size();
		int ilen = item==null?0:item.size();
		if(ilen==0){
			return;
		}
		if(len==0){
			this.list = new ArrayList<>();
		}
		this.list.addAll(item);
	}

	public int isShow() {
		return isShow;
	}

	public void setShow(int show) {
		isShow = show;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Type{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", pid='" + pid + '\'' +
				", pname='" + pname + '\'' +
				", type='" + type + '\'' +
				", remark='" + remark + '\'' +
				", xjnum=" + xjnum +
				", resId=" + resId +
				'}';
	}

	public String getWg() {
		return Wg;
	}

	public void setWg(String Wg) {
		this.Wg = Wg;
	}

	public String getWgid() {
		return Wgid;
	}

	public void setWgid(String Wgid) {
		this.Wgid = Wgid;
	}
}
