package com.gfk.common.constant;

import com.gfk.common.model.ServerGroupConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务常量
 */
public class BusinessConstants {

    /** 示例*/
    public static final String BUSINESS_DEMO = "demo";

    /* 用户-用户类型(0-系统用户) */
    /** 系统用户 */
    public static final int USER_TYPE_SYSTEM = 0;



    /** 服务器集群 */
    public static final List<ServerGroupConfig> SERVER_GROUP = new ArrayList<ServerGroupConfig>(
/*            Arrays.asList(
                    new ServerGroupConfig("10.151.183.38", "","ec2-adm","P@ssword123"),
                    new ServerGroupConfig("10.151.183.123","","ec2-adm","P@ssword123"))*/
    );
}
