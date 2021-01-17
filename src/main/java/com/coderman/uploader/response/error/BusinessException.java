package com.coderman.uploader.response.error;

/**
 * @Author zhangyukang
 * @Date 2021/1/16 14:45
 * @Version 1.0
 **/
public class BusinessException extends Exception {

    private BaseErrorCode errorCode;

    public BusinessException(BaseErrorCode errorCode){
        super(errorCode.getErrorMsg());
        this.errorCode=errorCode;
    }

    public BusinessException(BaseErrorCode errorCode,String customizedErrorMsg){
        super(customizedErrorMsg);
        this.errorCode=errorCode;
        errorCode.setErrorMsg(customizedErrorMsg);
    }

    public static void main(String[] args) throws BusinessException {
        throw new BusinessException(BusinessErrorCode.USER_NOT_LOGIN);
    }

    public BaseErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(BaseErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
