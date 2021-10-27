package cn.lyj.work.entry;

import java.io.Serializable;

public class SthEntry implements Serializable {
    private String id;//事项id
    private String no;//流水号
    private String sldate;//受理时间
    private String sldw;//受理单位
    private String dqdw;//当前主处理单位
    private String slr;//受理人
    private String fyr;//反映人姓名
    private String sfz;//反映人身份证号码
    private String phone;//反映人联系电话
    private String sex;//反映人性别
    private String fyqd;//反映渠道
    private String rklx;//人口类型
    private String jtzz;//家庭住址
    private String jjcd;//紧急程度
    private String sxlxId;//事项类型ID
    private String sxlx;//事项类型
//    private String sxly;//事项来源
    private String sjlyId;//涉及领域ID
    private String sjly;//涉及领域
    private String sfdb;//是否督办
    private String dbld;//督办领导姓名
    private String fysx;//反映事项内容
    private String dtzb;//地图坐标
    private String light;//亮灯情况
    private String state;//办理状态
    private String blresult;//办理结果

    public SthEntry() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getSldate() {
        return sldate;
    }

    public void setSldate(String sldate) {
        this.sldate = sldate;
    }

    public String getSldw() {
        return sldw;
    }

    public void setSldw(String sldw) {
        this.sldw = sldw;
    }

    public String getDqdw() {
        return dqdw;
    }

    public void setDqdw(String dqdw) {
        this.dqdw = dqdw;
    }

    public String getSlr() {
        return slr;
    }

    public void setSlr(String slr) {
        this.slr = slr;
    }

    public String getFyr() {
        return fyr;
    }

    public void setFyr(String fyr) {
        this.fyr = fyr;
    }

    public String getSfz() {
        return sfz;
    }

    public void setSfz(String sfz) {
        this.sfz = sfz;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFyqd() {
        return fyqd;
    }

    public void setFyqd(String fyqd) {
        this.fyqd = fyqd;
    }

    public String getRklx() {
        return rklx;
    }

    public void setRklx(String rklx) {
        this.rklx = rklx;
    }

    public String getJtzz() {
        return jtzz;
    }

    public void setJtzz(String jtzz) {
        this.jtzz = jtzz;
    }

    public String getJjcd() {
        return jjcd;
    }

    public void setJjcd(String jjcd) {
        this.jjcd = jjcd;
    }

    public String getSxlx() {
        return sxlx;
    }

    public void setSxlx(String sxlx) {
        this.sxlx = sxlx;
    }

    public String getSxlxId() {
        return sxlxId;
    }

    public void setSxlxId(String sxlxId) {
        this.sxlxId = sxlxId;
    }

    public String getSjlyId() {
        return sjlyId;
    }

    public void setSjlyId(String sjlyId) {
        this.sjlyId = sjlyId;
    }
/*public String getSxly() {
        return sxly;
    }

    public void setSxly(String sxly) {
        this.sxly = sxly;
    }*/

    public String getSjly() {
        return sjly;
    }

    public void setSjly(String sjly) {
        this.sjly = sjly;
    }

    public String getSfdb() {
        return sfdb;
    }

    public void setSfdb(String sfdb) {
        this.sfdb = sfdb;
    }

    public String getDbld() {
        return dbld;
    }

    public void setDbld(String dbld) {
        this.dbld = dbld;
    }

    public String getFysx() {
        return fysx;
    }

    public void setFysx(String fysx) {
        this.fysx = fysx;
    }

    public String getDtzb() {
        return dtzb;
    }

    public void setDtzb(String dtzb) {
        this.dtzb = dtzb;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBlresult() {
        return blresult;
    }

    public void setBlresult(String blresult) {
        this.blresult = blresult;
    }
}
