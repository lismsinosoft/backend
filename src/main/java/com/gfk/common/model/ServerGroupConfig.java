package com.gfk.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("服务器群配置")
@Data
public class ServerGroupConfig {

    /**  serverCurrentHost */
    @ApiModelProperty("serverCurrentHost")
    private String serverCurrentHost;

    /**  fileBathPath */
    @ApiModelProperty("fileBathPath")
    private String fileBathPath;

    /**  account */
    @ApiModelProperty("account")
    private String account;

    /**  password */
    @ApiModelProperty("password")
    private String password;

    public ServerGroupConfig(String serverCurrentHost,String fileBathPath,String account,String password){
        this.serverCurrentHost=serverCurrentHost;
        this.fileBathPath=fileBathPath;
        this.account=account;
        this.password=password;
    }
}
