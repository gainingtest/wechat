package com.js.support.dto;


import java.io.Serializable;

public class UserInfoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6734534622612518410L;

	private String userId;
	private String userName;
	private String userSource;
	private String nickName;
	private String gender;
	private String birthday;
	private String mobile;
	private String province;
	private String city;
	private String area;
	private String address;
	private String email;
	private String lastLoginTime;
	private String workPhone;

	private String userState;

	private String isValidation;

	private String validationDate;

	private String openid;
	private String idType;
	private String idNo;
	private String marritalStatus;
	private String occupation;

	private String homePhone;
	private Integer userLevel;
	private String imgUrl;// 微信头像
	private String userDegree;// 用户完善度
	private String authenState;// 认证状态

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getUserDegree() {
		return userDegree;
	}

	public void setUserDegree(String userDegree) {
		this.userDegree = userDegree;
	}

	public String getAuthenState() {
		return authenState;
	}

	public void setAuthenState(String authenState) {
		this.authenState = authenState;
	}

	public Integer getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSource() {
		return userSource;
	}

	public void setUserSource(String userSource) {
		this.userSource = userSource;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user_info.MOBILE
	 *
	 * @return the value of user_info.MOBILE
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user_info.MOBILE
	 *
	 * @param mobile
	 *            the value for user_info.MOBILE
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user_info.PROVINCE
	 *
	 * @return the value of user_info.PROVINCE
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user_info.PROVINCE
	 *
	 * @param province
	 *            the value for user_info.PROVINCE
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user_info.CITY
	 *
	 * @return the value of user_info.CITY
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public String getCity() {
		return city;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user_info.CITY
	 *
	 * @param city
	 *            the value for user_info.CITY
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user_info.AREA
	 *
	 * @return the value of user_info.AREA
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public String getArea() {
		return area;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user_info.AREA
	 *
	 * @param area
	 *            the value for user_info.AREA
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user_info.ADDRESS
	 *
	 * @return the value of user_info.ADDRESS
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user_info.ADDRESS
	 *
	 * @param address
	 *            the value for user_info.ADDRESS
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user_info.EMAIL
	 *
	 * @return the value of user_info.EMAIL
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user_info.EMAIL
	 *
	 * @param email
	 *            the value for user_info.EMAIL
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user_info.LAST_LOGIN_TIME
	 *
	 * @return the value of user_info.LAST_LOGIN_TIME
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public String getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user_info.LAST_LOGIN_TIME
	 *
	 * @param lastLoginTime
	 *            the value for user_info.LAST_LOGIN_TIME
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user_info.ID_TYPE
	 *
	 * @return the value of user_info.ID_TYPE
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public String getIdType() {
		return idType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user_info.ID_TYPE
	 *
	 * @param idType
	 *            the value for user_info.ID_TYPE
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public void setIdType(String idType) {
		this.idType = idType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user_info.ID_NO
	 *
	 * @return the value of user_info.ID_NO
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public String getIdNo() {
		return idNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user_info.ID_NO
	 *
	 * @param idNo
	 *            the value for user_info.ID_NO
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user_info.MARRITAL_STATUS
	 *
	 * @return the value of user_info.MARRITAL_STATUS
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public String getMarritalStatus() {
		return marritalStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user_info.MARRITAL_STATUS
	 *
	 * @param marritalStatus
	 *            the value for user_info.MARRITAL_STATUS
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public void setMarritalStatus(String marritalStatus) {
		this.marritalStatus = marritalStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user_info.OCCUPATION
	 *
	 * @return the value of user_info.OCCUPATION
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public String getOccupation() {
		return occupation;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user_info.OCCUPATION
	 *
	 * @param occupation
	 *            the value for user_info.OCCUPATION
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user_info.HOME_PHONE
	 *
	 * @return the value of user_info.HOME_PHONE
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public String getHomePhone() {
		return homePhone;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user_info.HOME_PHONE
	 *
	 * @param homePhone
	 *            the value for user_info.HOME_PHONE
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user_info.WORK_PHONE
	 *
	 * @return the value of user_info.WORK_PHONE
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public String getWorkPhone() {
		return workPhone;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user_info.WORK_PHONE
	 *
	 * @param workPhone
	 *            the value for user_info.WORK_PHONE
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user_info.IS_VALIDATION
	 *
	 * @return the value of user_info.IS_VALIDATION
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public String getIsValidation() {
		return isValidation;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user_info.IS_VALIDATION
	 *
	 * @param isValidation
	 *            the value for user_info.IS_VALIDATION
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public void setIsValidation(String isValidation) {
		this.isValidation = isValidation;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user_info.VALIDATION_DATE
	 *
	 * @return the value of user_info.VALIDATION_DATE
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public String getValidationDate() {
		return validationDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user_info.VALIDATION_DATE
	 *
	 * @param validationDate
	 *            the value for user_info.VALIDATION_DATE
	 *
	 * @mbggenerated Mon May 30 15:23:20 CST 2016
	 */
	public void setValidationDate(String validationDate) {
		this.validationDate = validationDate;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

}
