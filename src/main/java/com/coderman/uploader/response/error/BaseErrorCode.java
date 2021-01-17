package com.coderman.uploader.response.error;

/**
 * @Author zhangyukang
 * @Date 2021/1/16 14:40
 * @Version 1.0
 **/
public interface BaseErrorCode {
    /**
     * 获取错误码
     * @return
     */
    int getErrorCode();

    /**
     * 获取错误信息
     * @return
     */
    String getErrorMsg();

    /**
     * 设置错误信息
     * @param errorMsg
     */
    void setErrorMsg(String errorMsg);
}
