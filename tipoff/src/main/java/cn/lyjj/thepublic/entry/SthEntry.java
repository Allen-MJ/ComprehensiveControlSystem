package cn.lyjj.thepublic.entry;

import java.io.Serializable;
import java.util.List;

import allen.frame.entry.Units;
import allen.frame.entry.UploadFile;
import allen.frame.tools.StringUtils;

public class SthEntry implements Serializable {
    private long appealId;
    private String serialNumber;
    private Units org;
    private String name;
    private String phone;
    private String idNumber;
    private String sex;
    private String point;
    private String address;
    private String gid;
    private String gridName;
    private String content;
    private String state;
    private String stateName;
    private String addTime;
    private String seizedUser;
    private String seizedTime;
    private String appealSource;
    private String createTime;
    private List<UploadFile> files;

    public long getAppealId() {
        return appealId;
    }

    public void setAppealId(long appealId) {
        this.appealId = appealId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Units getOrg() {
        return org;
    }

    public void setOrg(Units org) {
        this.org = org;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSexName() {
        if(StringUtils.empty(sex)){
            return "";
        }
        String name = "";
        switch (sex){
            case "1":
                name = "男";
                break;
            case "2":
                name = "女";
                break;
        }
        return name;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getSeizedUser() {
        return seizedUser;
    }

    public void setSeizedUser(String seizedUser) {
        this.seizedUser = seizedUser;
    }

    public String getSeizedTime() {
        return seizedTime;
    }

    public void setSeizedTime(String seizedTime) {
        this.seizedTime = seizedTime;
    }

    public String getAppealSource() {
        return appealSource;
    }

    public void setAppealSource(String appealSource) {
        this.appealSource = appealSource;
    }

    public List<UploadFile> getFiles() {
        return files;
    }

    public void setFiles(List<UploadFile> files) {
        this.files = files;
    }

    public String getStateName() {
        String mStateName = "";
        switch (state){
            case "1":
                mStateName = "待受理";
                break;
            case "2":
                mStateName = "已受理";
                break;
            case "3":
                mStateName = "已办结";
                break;
        }
        return StringUtils.empty(mStateName)?stateName:mStateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGridName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }
}
