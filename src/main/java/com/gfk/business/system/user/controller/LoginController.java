package com.gfk.business.system.user.controller;

import com.gfk.business.common.captcha.service.CaptchaService;
import com.gfk.business.system.user.service.SysUserService;
import com.gfk.common.cache.CacheRepository;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理员登录接口
 * 参考：http://www.ityouknow.com/springboot/2017/06/26/spring-boot-shiro.html
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@Api(tags = "管理员登录管理")
@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    /**
     * 登录失败重试次数
     */
    private static final int LOGIN_RETRY_COUNT = 5;
    private final SysUserService sysUserService;
    private final CacheRepository cacheRepository;
    private final CaptchaService captchaService;

//    /**
//     * 管理员登录
//     */
//    @ApiOperation("管理员登录")
//    @PostMapping("/login")
//    public Result<SysUser> login(@Valid @RequestBody LoginForm form) {
//        String username = form.getUsername();
//        String password = form.getPassword();
//
//        // 图片校验码校验
////        CaptchaCheckForm captchaCheckForm=new CaptchaCheckForm();
////        captchaCheckForm.setType(2);
////        captchaCheckForm.setCaptcha(form.getCaptcha());
////        captchaCheckForm.setCheckKey(form.getCheckKey());
////        captchaService.checkCaptcha(captchaCheckForm);
//
//        SysUser sysUser = sysUserService.findByUsername(username);
//        if (null == sysUser) {
//            throw new BusinessException("未找到该管理员，请重新再试");
//        }
//        if (BaseConstants.STATUS_BLOCKED == sysUser.getStatus()) {
//            log.info("{}管理员已被锁定", sysUser.getUsername());
//            throw new BusinessException("管理员已被禁用，请联系超级管理员");
//        }
//        String passwd = EncryptUtils.encryptPassword(username, password, sysUser.getSalt());
//        if (!passwd.equals(sysUser.getPassword())) {
//            // 账号登录密码多次错误锁定管理员
//            Integer failedCount = (Integer) cacheRepository.get(CacheConstants.SYS_USER_LOGIN_FAILED, String.valueOf(sysUser.getId()));
//            failedCount = (null == failedCount ? 1 : (++failedCount));
//            cacheRepository.set(CacheConstants.SYS_USER_LOGIN_FAILED, String.valueOf(sysUser.getId()), failedCount, 15, TimeUnit.MINUTES);
//            if (failedCount >= LOGIN_RETRY_COUNT) {
//                // 禁用用户
//                sysUserService.frozen((new UserFrozenForm(String.valueOf(sysUser.getId()), BaseConstants.STATUS_BLOCKED)));
//                log.info("{}管理员已被锁定", sysUser.getUsername());
//                throw new BusinessException("管理员已被禁用，请联系超级管理员");
//            }
//            String errorMsg = StrUtil.format("密码错误，您还有{}次机会输入，超出次数后管理员将被禁用", LOGIN_RETRY_COUNT - failedCount);
//            throw new BusinessException(errorMsg);
//        }
//
//        // 生成token
//        String sign = JwtUtil.sign(BaseConstants.SYSTEM_USER_TYPE, username, passwd, String.valueOf(sysUser.getId()));
//        cacheRepository.set(CacheConstants.SYS_USER_TOKEN, sign, sign, JwtUtil.EXPIRE_TIME * 2, TimeUnit.MILLISECONDS);
//
//        sysUser.setToken(sign);
//
//        return Result.success(sysUser);
//
//        // Shiro安全验证
///*        Subject subject = SecurityUtils.getSubject();
//        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//        String msg = "";
//        try {
//            // 验证用户名密码
//            subject.login(token);
//            userInfo.getPassword()(ShiroUtils.getUserInfo());
//        }
//        catch (IncorrectCredentialsException e) {
//            msg = "登录密码错误";
//        } catch (ExcessiveAttemptsException e) {
//            msg = "登录失败次数过多";
//        } catch (LockedAccountException e) {
//            msg = "帐号已被锁定";
//        } catch (DisabledAccountException e) {
//            msg = "帐号已被禁用";
//        } catch (ExpiredCredentialsException e) {
//            msg = "帐号已过期";
//        } catch (UnknownAccountException e) {
//            msg = "帐号不存在";
//        } catch (UnauthorizedException e) {
//            msg = "您没有得到相应的授权！";
//        } catch (Exception e) {
//            logger.info("系统异常：{}", e.getMessage());
//        }
//        logger.info(msg+" username:{}", username);
//        return error(msg);*/
//    }

    /**
     * 用户注册
     * shiro中的密码是如何验证是否匹配的
     * https://blog.csdn.net/chenyidong521/article/details/80245362
     * @return
     * @throws Exception
     */
//    @ApiOperation("用户注册")
//    @PostMapping("/register")
//    public SimpleResponseForm<SysUser> register(@Valid @RequestBody SysUser sysUser) throws Exception {
//        SysUser user = sysUserService.add(sysUser);
//        return success(user);
//    }
}
