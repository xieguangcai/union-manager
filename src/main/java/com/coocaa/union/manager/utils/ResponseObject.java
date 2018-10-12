package com.coocaa.union.manager.utils;

/**
 * Created by xie.gc on 14-9-9.
 */
public class ResponseObject<T> {
    private boolean success;
    private String code;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private T data;
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 将处理结果以json格式返回
     * @param data 处理后返回的数据
     * @param message 处理错误消息
     * @return
     */
    private static ResponseObject toResponse(boolean isSuccess, Object data,String errorCode, String message){
        ResponseObject obj = new ResponseObject();
        obj.setSuccess(isSuccess);
        obj.setData(data);
        obj.setCode(errorCode);
        obj.setMessage(message);
        return obj;
    }

    /**
     * 构建成功结果
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseObject<T> success(T data){
        ResponseObject<T> result = new ResponseObject<T>();
        result.setData(data);
        result.setSuccess(true);
        return result;
    }

    public static <T> ResponseObject<T> error(String errorCode, String error){
        ResponseObject<T> result = new ResponseObject<T>();
        result.setSuccess(false);
//        result.setData(error);
        result.setMessage(error);
        return  result;
    }

    /**
     * 设置成功消息，并不返回任何值
     * @param <T>
     * @return
     */
    public static <T> ResponseObject<T> success(){
        ResponseObject<T> result = new ResponseObject<T>();
        result.setSuccess(true);
        return result;
    }
}