package com.gfk.business.user.controller;

import com.gfk.business.user.model.form.UserLoginForm;
import com.gfk.business.user.model.vo.UserLoginResultVO;
import com.gfk.business.user.service.UserService;
import com.gfk.common.cache.CacheRepository;
import com.gfk.common.model.Result;
import com.gfk.framework.jwt.JWTConstants;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 用户登录接口
 * <a href="http://www.ityouknow.com/springboot/2017/06/26/spring-boot-shiro.html">参考</a>
 *
 * @author : wzl
 * @version 1.0 2023/02/26
 **/
@Api(tags = "999 - 用户登录管理")
@RestController
@RequestMapping("/api/user_login")
@RequiredArgsConstructor
@Slf4j
public class UserLoginController {

    private final UserService userService;

    /***
     * 注入redis模版
     */
    private final CacheRepository cacheRepository;

    /**
     * 用户登录
     */
    @ApiOperation("用户登录")
    @ApiOperationSupport(order = 1)
    @PostMapping("/login")
    public Result<UserLoginResultVO> login(@Valid @RequestBody UserLoginForm form) {
        return Result.success(userService.login(form));
    }

//    /**
//     *  修改密码
//     */
//    @ApiOperation("修改密码")
//    @ApiOperationSupport(order = 7,ignoreParameters = {"createTime","updateTime","createBy","updateBy"})
//    @PostMapping("/changePwd")
//    public SimpleResponseForm<String> changePwd(@Valid @RequestBody UserChangePwdForm form) {
//        userService.changePwd(form);
//        return success();
//    }

    /**
     * 用户token校验
     */
    @ApiOperation("用户token校验")
    @ApiOperationSupport(order = 99)
    @GetMapping("/token_check")
    public Result<Boolean> tokenCheck(ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(JWTConstants.X_ACCESS_TOKEN);
        return Result.success(userService.tokenCheck(token));
    }
}
