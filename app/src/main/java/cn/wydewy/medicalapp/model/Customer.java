package cn.wydewy.medicalapp.model;

import org.litepal.crud.DataSupport;

/**
 * Created by wangyuan on 2016/11/2.
 */

public class Customer extends DataSupport {
    String customerId;//:主键
    String picHead;//:头像路径
    String customerName;//客户名字
    int age;//:年龄
    String nickName;//:昵称
    int sex;//:性别
    String idCard;//:身份证
    String phone;//:电话号码
    String customerAdrr;//:客户地址
    String password;//:密码
    String remarks;//:备注
    int isMarried;//:是否结婚
    String job;//:工作
    String nation;//：籍贯
    String birthPlace;//：生日
    String residence;//:居住地
    String workplace;//:工作地点

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPicHead() {
        return picHead;
    }

    public void setPicHead(String picHead) {
        this.picHead = picHead;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCustomerAdrr() {
        return customerAdrr;
    }

    public void setCustomerAdrr(String customerAdrr) {
        this.customerAdrr = customerAdrr;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getIsMarried() {
        return isMarried;
    }

    public void setIsMarried(int isMarried) {
        this.isMarried = isMarried;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }
}
