package com.gfk.common.constant;

/**
 * 基础常量
 */
public class BaseConstants {
    /**
     * 用户类型
     */
    //系统用户
    public static final String SYSTEM_USER_TYPE = "system";
    //用户
    public static final String STAFF_USER_TYPE = "staff";

    /**
     * 系统管理员账号
     */
    public static final String SYS_ADMIN = "admin";

    /*是否已删除*/
    //未删除
    public static final int LOGICDELETED_NO = 0;
    //已删除
    public static final int LOGICDELETED_YES = 1;

    /*开启关闭状态（启用/停用）*/
    //启用
    public static final int STATUS_NOMAL = 0;
    //停用
    public static final int STATUS_BLOCKED = -1;

    /*执行结果（成功/失败）*/
    //启用
    public static final int RUN_STATUS_SUCCESS = 0;
    //停用
    public static final int RUN_STATUS_FAILED = 1;

    /*权限类型（0：一级菜单；1：子菜单 ；2：按钮权限）*/
    //一级菜单
    public static final String PERMISSION_MENU_TYPE_TOP_MENU = "0";
    //子菜单
    public static final String PERMISSION_MENU_TYPE_SUB_MENU = "1";
    //按钮权限
    public static final String PERMISSION_MENU_TYPE_BUTTON = "2";


    /**
     * 用户账号
     */
    /*状态（启用/停用）*/
    //启用
    public static final int STAFF_USER_STATUS_NORMAL = 1;
    //停用
    public static final int STAFF_USER_STATUS_BLOCKED = 2;

    public static final String ADMIN_KEY = "e9135d17-5c33-4510-bfb2-5f5dbfe41433";
}
