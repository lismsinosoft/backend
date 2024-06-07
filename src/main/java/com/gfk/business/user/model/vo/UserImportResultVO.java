package com.gfk.business.user.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户导入结果VO
 *
 * @author whk
 * @date 2021/05/212
 **/
@ApiModel("用户导入结果VO")
@Data
public class UserImportResultVO {

    /**
     * 成功数量
     */
    @ApiModelProperty("成功数量")
    private Integer success = 0;

    /**
     * 失败数量
     */
    @ApiModelProperty("失败数量")
    private Integer fail = 0;

    /**
     * 失败详情
     */
    @ApiModelProperty("失败详情")
    private List<UserImportResultFailInfoVO> failInfoList = new ArrayList<>();
}
