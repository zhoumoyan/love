package cn.LoveUO.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户数据的实体类
 */
public class User extends BaseEntity implements Serializable {


	private static final long serialVersionUID = -1372829784096461887L;
	private Integer uid;
	private String username;
	private String password;
	private String salt;
	private String email;
	private Integer gender;
	private String avatar;
	private Integer pay;
	private String truename;
	private String lover;
	private String words;
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date meettime;
	private String validataCode;
	private Long outDate;


	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getPay() {
		return pay;
	}

	public void setPay(Integer pay) {
		this.pay = pay;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getLover() {
		return lover;
	}

	public void setLover(String lover) {
		this.lover = lover;
	}

	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words;
	}

	public Date getMeettime() {
		return meettime;
	}

	public void setMeettime(Date meettime) {
		this.meettime = meettime;
	}

	public String getValidataCode() {
		return validataCode;
	}

	public void setValidataCode(String validataCode) {
		this.validataCode = validataCode;
	}
	public Long getOutDate() {
		return outDate;
	}

	public void setOutDate(Long outDate) {
		this.outDate = outDate;
	}

	@Override
	public String toString() {
		return "User{" +
				"uid=" + uid +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", salt='" + salt + '\'' +
				", email='" + email + '\'' +
				", gender=" + gender +
				", avatar='" + avatar + '\'' +
				", pay=" + pay +
				", truename='" + truename + '\'' +
				", lover='" + lover + '\'' +
				", words='" + words + '\'' +
				", meettime=" + meettime +
				", validataCode='" + validataCode + '\'' +
				", outDate=" + outDate +
				'}';
	}
}
