package com.coderman.uploader.response.error;

/**
 * @Author zhangyukang
 * @Date 2021/1/16 14:49
 * @Version 1.0
 **/
public enum BusinessErrorCode implements BaseErrorCode{

    USER_NOT_LOGIN(10001, "用户未登入"),
    INVALID_PARAMETER(10002,"参数错误");

    /** 错误消息 */
    private String errorMsg;
    /** 错误码 */
    private Integer errorCode;

    BusinessErrorCode(Integer errorCode, String errorMsg) {
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMsg() {
        return this.errorMsg;
    }

    @Override
    public void setErrorMsg(String errorMsg) {
        this.errorMsg=errorMsg;
    }

}
