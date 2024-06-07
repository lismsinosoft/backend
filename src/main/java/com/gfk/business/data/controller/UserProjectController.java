package com.gfk.business.data.controller;


import com.gfk.business.user.model.vo.UserProjectMappingVO;
import com.gfk.business.user.service.UserProjectMappingService;
import com.gfk.common.context.ContextHolder;
import com.gfk.common.exception.BusinessException;
import com.gfk.common.model.Result;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户项目管理
 *
 * @author wzl
 * @version 1.0 2024/3/6
 */
@Api(tags = "用户关联项目查询")
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserProjectController {


    private final UserProjectMappingService userProjectMappingService;

    @ApiOperation("用户项目查询")
    @ApiOperationSupport(order = 1)
    @PostMapping("/user-project/{number}")
    public Result<List<UserProjectMappingVO>> queryUserListBySpecId(@PathVariable("number") String number) {
        String userIdStr = ContextHolder.currentContext().getUserId();
        Long userId = null;
        try {
            userId = Long.valueOf(number);
        } catch (Exception e) {
            throw new BusinessException("用户ID解析失败");
        }
        return Result.success(userProjectMappingService.getUserProjectList(userId));
    }

    @ApiOperation("用户项目查询")
    @ApiOperationSupport(order = 2)
    @PostMapping("/user-project")
    public Result<List<UserProjectMappingVO>> queryUserList() {
        String userIdStr = ContextHolder.currentContext().getUserId();
        Long userId = null;
        try {
            userId = Long.valueOf(userIdStr);
        } catch (Exception e) {
            throw new BusinessException("用户ID解析失败");
        }
        return Result.success(userProjectMappingService.getUserProjectList(userId));
    }
}
