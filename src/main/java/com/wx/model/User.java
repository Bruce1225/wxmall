package com.wx.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class User implements Serializable {
    private Integer uid;

    private String wxid;

    private String realName;

    private String nickName;

    private Date birthday;

    private Integer age;

    private String address;

    private Boolean gender;

    private String wxName;

    private String loginName;

    private String password;

    private Integer phone1;

    private Integer phone2;

    private Integer phone3;

    private String idCardNo;

    private String headPic;

    private BigDecimal account;

    private Integer score;

    private Date lastLoginTime;

    private Byte source;

    private Byte userType;

    private Boolean isdel;

    private static final long serialVersionUID = 1L;

    public User(Integer uid, String wxid, String realName, String nickName, Date birthday, Integer age, String address, Boolean gender, String wxName, String loginName, String password, Integer phone1, Integer phone2, Integer phone3, String idCardNo, String headPic, BigDecimal account, Integer score, Date lastLoginTime, Byte source, Byte userType, Boolean isdel) {
        this.uid = uid;
        this.wxid = wxid;
        this.realName = realName;
        this.nickName = nickName;
        this.birthday = birthday;
        this.age = age;
        this.address = address;
        this.gender = gender;
        this.wxName = wxName;
        this.loginName = loginName;
        this.password = password;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.phone3 = phone3;
        this.idCardNo = idCardNo;
        this.headPic = headPic;
        this.account = account;
        this.score = score;
        this.lastLoginTime = lastLoginTime;
        this.source = source;
        this.userType = userType;
        this.isdel = isdel;
    }

    public User() {
        super();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid == null ? null : wxid.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName == null ? null : wxName.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getPhone1() {
        return phone1;
    }

    public void setPhone1(Integer phone1) {
        this.phone1 = phone1;
    }

    public Integer getPhone2() {
        return phone2;
    }

    public void setPhone2(Integer phone2) {
        this.phone2 = phone2;
    }

    public Integer getPhone3() {
        return phone3;
    }

    public void setPhone3(Integer phone3) {
        this.phone3 = phone3;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo == null ? null : idCardNo.trim();
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic == null ? null : headPic.trim();
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
    }

    public Byte getUserType() {
        return userType;
    }

    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    public Boolean getIsdel() {
        return isdel;
    }

    public void setIsdel(Boolean isdel) {
        this.isdel = isdel;
    }
}