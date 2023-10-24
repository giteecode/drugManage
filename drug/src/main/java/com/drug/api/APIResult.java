package com.drug.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 接口返回
 *
 * @param <T>
 */
@ApiModel(description="API返回公共组件")
public class APIResult<T> extends ResultSupport {
	
	private static final long serialVersionUID = -1444725629728008153L;
	
	@ApiModelProperty(value="返回数据对象")
	protected T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 接口调用失败,有错误字符串码和描述,有返回对象
     * @param code
     * @param message
     * @param data
     * @param <U>
     * @return
     */
    public static <U> APIResult<U> newFailResult(int code, String message, U data) {
        APIResult<U> apiResult = new APIResult<U>();
        apiResult.setCode(code);
        apiResult.setMessage(message);
        apiResult.setData(data);
        return apiResult;
    }

    /**
     * 接口调用失败,有错误字符串码和描述,没有返回对象
     * @param code
     * @param message
     * @param <U>
     * @return
     */
    public static <U> APIResult<U> newFailResult(int code, String message) {
        APIResult<U> apiResult = new APIResult<U>();
        apiResult.setCode(code);
        apiResult.setMessage(message);
        return apiResult;
    }

    /**
     * 接口调用失败,有默认字符串码和描述,没有返回对象
     * @param message
     * @param <U>
     * @return
     */
    public static <U> APIResult<U> newFailResult(String message) {
    	return newFailResult(ResultCode.ERROR_CODE, message);
    }
    
    /**
     * 接口调用失败,有默认字符串码，没有描述,没有返回对象
     * @param <U>
     * @return
     */
    public static <U> APIResult<U> newFailResult() {
    	return newFailResult(null);
    }

    /**
     * 接口调用成功返回数据 包含对象属性及CODE
     * @param data
     * @return
     */
    public static <U> APIResult<U> newSuccessResult(int code, U data){
        APIResult<U> apiResult = new APIResult<U>();
        apiResult.setCode(code);
        apiResult.setData(data);
        return apiResult;
    }
    
    /**
     * 接口调用成功返回数据 包含对象属性
     * @param data
     * @return
     */
    public static <U> APIResult<U> newSuccessResult(U data){
        return newSuccessResult(ResultCode.SUCCESS_CODE, data);
    }

    /**
     * 接口调用成功返回数据 不包含对象属性
     * @return
     * @return
     */
    public static APIResult<Object> newSuccessResult(){
    	return newSuccessResult(null);
    }
}
