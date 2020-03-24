package com.coocaa.union.manager.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 编号规则： 1 - 01 - 01 - 1:系统错误，2：业务错误 - 01：业务系统 - 01：编号
 * Created by neo on 14-4-30.
 */
public enum ErrorCodes {
    /**
     * 系统错误
     */
    SYSTEM_ERROR(1001,"系统错误"),
    /**
     * 参数错误
     */
    INVALID_INPUT_PARAMS(1002,"参数错误：%s"),
    /**
     * 没有找到实体对象
     */
    NO_SUCH_ENTITY(1003, "没有找到对象"),
    /**
     * 旧密码错误
     */
    INVALID_OLD_PWD(9001, "旧密码错误"),
    /**
     * appKey已经存在
     */
    INVALID_APP_KEY(2001, "appKey已经存在。"),

    /**
     * AppKey 不存在
     */
    NO_CLIENTID(2002, "AppKey 不存在"),
    CLIENT_ID_INVALID(2002, "接入系统已被禁用，请联系管理员。%s"),
    LADP_ACCOUNT_NOT_EDIT(2003, "域账号不允许编辑"),
    ;

    Integer code;
    private String msg;
    ErrorCodes(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg(){
        return this.msg;
    }

    public static ErrorCodes fromString(String code){
        return KEYS_MAP.get(Integer.valueOf(code));
    }

    private static Map<Integer, ErrorCodes> KEYS_MAP =new HashMap<>();
    static{
        for(ErrorCodes e: ErrorCodes.values()){
            KEYS_MAP.put(e.code, e);
        }
    }
}
