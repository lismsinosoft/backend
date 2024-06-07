package com.gfk.framework.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.gfk.common.context.Context;
import com.gfk.common.context.ContextHolder;
import com.gfk.common.util.TimeUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自动填充 创建时间/更新时间/创建者/更新者
 * https://mybatis.plus/guide/auto-fill-metainfo.html
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@Component
public class AuditorMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Date nowDate = TimeUtils.nowDate();
        this.strictInsertFill(metaObject, "createTime", Date.class, nowDate);
        this.strictInsertFill(metaObject, "createBy", String.class, ContextHolder.currentContext().get(Context.PreferenceName.userName));
        this.strictInsertFill(metaObject, "updateTime", Date.class, nowDate);
        this.strictInsertFill(metaObject, "updateBy", String.class, ContextHolder.currentContext().get(Context.PreferenceName.userName));
        this.strictInsertFill(metaObject, "isDeleted", Boolean.class, false);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", TimeUtils.nowDate(), metaObject);
        this.setFieldValByName("updateBy", ContextHolder.currentContext().get(Context.PreferenceName.userName), metaObject);
    }
}
