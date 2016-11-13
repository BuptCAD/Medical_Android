package cn.wydewy.medicalapp.model;

import org.litepal.crud.DataSupport;

/**
 * Created by wangyuan on 2016/11/11.
 */

public class ReleaseNum extends DataSupport {
    private String releaseId;
    private String outpatientId;
    private String doctorId;
    private String price;
    private String date;
    private String remark;
    private String isSuccesss;

    public String getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
    }

    public String getOutpatientId() {
        return outpatientId;
    }

    public void setOutpatientId(String outpatientId) {
        this.outpatientId = outpatientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsSuccesss() {
        return isSuccesss;
    }

    public void setIsSuccesss(String isSuccesss) {
        this.isSuccesss = isSuccesss;
    }

    public String getIsFamilyNum() {
        return isFamilyNum;
    }

    public void setIsFamilyNum(String isFamilyNum) {
        this.isFamilyNum = isFamilyNum;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getAmpm() {
        return ampm;
    }

    public void setAmpm(String ampm) {
        this.ampm = ampm;
    }

    private String isFamilyNum;
    private String week;
    private String ampm;

}
