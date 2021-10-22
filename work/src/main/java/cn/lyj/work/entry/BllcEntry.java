package cn.lyj.work.entry;

import java.io.Serializable;

public class BllcEntry implements Serializable {
    private String dwname;//受理单位
    private String person;//工作人员
    private String phone;//工作人员联系方式
    private String result;//办理结果
    private String sx;//时限
    private String sldate;//受理时间
    private String ys;//用时
    private String regsate;//办理状态
    private String TcType;//
    private String IsBoverId;//
    private String IsXbatre;//是否协办
    private String IsMxvoer;//是否转办

    public BllcEntry() {
    }

    public String getDwname() {
        return dwname;
    }

    public void setDwname(String dwname) {
        this.dwname = dwname;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSx() {
        return sx;
    }

    public void setSx(String sx) {
        this.sx = sx;
    }

    public String getSldate() {
        return sldate;
    }

    public void setSldate(String sldate) {
        this.sldate = sldate;
    }

    public String getYs() {
        return ys;
    }

    public void setYs(String ys) {
        this.ys = ys;
    }

    public String getRegsate() {
        return regsate;
    }

    public void setRegsate(String regsate) {
        this.regsate = regsate;
    }

    public String getTcType() {
        return TcType;
    }

    public void setTcType(String tcType) {
        TcType = tcType;
    }

    public String getIsBoverId() {
        return IsBoverId;
    }

    public void setIsBoverId(String isBoverId) {
        IsBoverId = isBoverId;
    }

    public String getIsXbatre() {
        return IsXbatre;
    }

    public void setIsXbatre(String isXbatre) {
        IsXbatre = isXbatre;
    }

    public String getIsMxvoer() {
        return IsMxvoer;
    }

    public void setIsMxvoer(String isMxvoer) {
        IsMxvoer = isMxvoer;
    }

    @Override
    public String toString() {
        return "BllcEntry{" +
                "dwname='" + dwname + '\'' +
                ", person='" + person + '\'' +
                ", phone='" + phone + '\'' +
                ", result='" + result + '\'' +
                ", sx='" + sx + '\'' +
                ", sldate='" + sldate + '\'' +
                ", ys='" + ys + '\'' +
                ", regsate='" + regsate + '\'' +
                '}';
    }
}
