package com.gfk.business.system.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gfk.business.system.user.controller.form.UserFrozenForm;
import com.gfk.business.system.user.mapper.SysUserMapper;
import com.gfk.business.system.user.model.SysUser;
import com.gfk.business.system.user.service.SysUserService;
import com.gfk.common.cache.CacheRepository;
import com.gfk.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 管理员信息 服务实现
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;

    private final CacheRepository cacheRepository;

    /**
     * 根据管理员名查找管理员
     *
     * @param username 管理员名
     * @return
     */
    @Override
    public SysUser findByUsername(String username) {
        return sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
    }


    /**
     * 冻结/解冻
     *
     * @param form
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void frozen(UserFrozenForm form) {
        SysUser sysUser = sysUserMapper.selectById(form.getId());
        if (sysUser == null) {
            throw new BusinessException("未找到该管理员，请检查");
        }
        sysUser.setStatus(form.getStatus());
        sysUserMapper.updateById(sysUser);
    }
}
