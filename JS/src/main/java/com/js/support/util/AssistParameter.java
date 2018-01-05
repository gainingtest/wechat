package com.js.support.util;


/**
 * 验证以及辅助参数
 *
 */
public class AssistParameter {

    private String sessionCode;//session中获取的图片验证码
    private String code;//图片验证码
    private String smsCode;//短信验证码
    private String mobileBusiType;//业务类型
    public String getSessionCode() {
        return sessionCode;
    }
    public void setSessionCode(String sessionCode) {
        this.sessionCode = sessionCode;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getSmsCode() {
        return smsCode;
    }
    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
    public String getMobileBusiType() {
        return mobileBusiType;
    }
    public void setMobileBusiType(String mobileBusiType) {
        this.mobileBusiType = mobileBusiType;
    }
    
}
