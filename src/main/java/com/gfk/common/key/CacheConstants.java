package com.gfk.common.key;

/**
 * 系统相关redisKey
 *
 * @author : wzl
 * @version 1.0 : 2023/02/26
 **/
public class CacheConstants {

    /**
     * 登录用户Token令牌缓存KEY前缀
     */
    public static final String SYS_USER_TOKEN = "sys:user:token";

    /**
     * 登录用户Token令牌缓存KEY前缀
     */
    public static final String STAFF_USER_TOKEN = "staff:user:token";

    /**
     * 用户临时Token令牌缓存KEY前缀
     */
    public static final String STAFF_USER_TEMPORARY_TOKEN = "staff:user:temporary:token";

    /**
     * 用户名缓存用户信息
     */
    public static final String SYS_USER_NAME = "sys:user:name:";

    /**
     * 用户ID缓存用户信息
     */
    public static final String SYS_USER_ID = "sys:user:id:";

    /**
     * 用户是否具有自定义的额外权限
     */
    public static final String STAFF_USER_FLAG_FOR_USER = "staff:user:flag:for_user";

    /**
     * 系统基础字典信息缓存
     */
    /* 地区*/
    public static final String SYS_BASE_DICT_PROVINCIAL_CODE = "sys:base:dict:provincial:code:";
    /* 地区列表*/
    public static final String SYS_BASE_DICT_PROVINCIAL_CODESTR = "sys:base:dict:provincial:codestr:";
    /* 字典数据*/
    public static final String SYS_BASE_DICT_TYPE_DATA_LIST = "sys:base:dict:type:data:list:";
    /* tms historical数据更新时间*/
    public static final String SYS_BASE_DATA_MAX_DATE = "sys:base:data:max:data:";


    /** 其他缓存 */

    /**
     * 管理端管理员登录校验码
     */
    public static final String SYS_USER_LOGIN_CAPTCHA = "captcha:sys:user:login:";
    /**
     * 用户端用户登录校验码
     */
    public static final String STAFF_LOGIN_CAPTCHA = "captcha:staff:user:login:";
    /**
     * 短信发送校验码
     */
    public static final String SMS_CAPTCHA = "captcha:sms:";


    /**
     * 用户密码错误校验缓存
     */
    public static final String SYS_USER_LOGIN_FAILED = "sys:user:login:failed:";

    /**
     * 用户密码错误校验缓存
     */
    public static final String STAFF_USER_LOGIN_FAILED = "staff:user:login:failed:";

    /**
     * 手机验证码
     */
    public static final String USER_REG_CODE = "user:reg:code:";

    /**
     * 忘记密码验证码
     */
    public static final String USER_CODE_FORGETPWD = "user:code:forgetpwd:";

    /**
     * 定时任务激活服务器
     */
    public static final String SCHEDULE_TASK_ACTIVE_SERVER = "schedule:task:active:server";

    public static final String SIMULATOR_FILTER_TYPE_HIERARCHY = "type_hierarchy:";
}
