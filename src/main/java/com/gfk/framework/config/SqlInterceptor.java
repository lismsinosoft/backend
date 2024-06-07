package com.gfk.framework.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

/**
 * 格式化SQL拦截器
 *
 * @author wzl
 * @version 1.0 2023/5/3
 */
@Component
@Profile({"dev", "test"})
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})}
)
@ConditionalOnExpression("${db-config.sql-log-enable:false}")
public class SqlInterceptor implements Interceptor {
    private static final ThreadLocal<SimpleDateFormat> dateTimeFormatter = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public static String beautifySql(String sql) {
        sql = sql.replaceAll("[\\s\n ]+", " ");
        return sql;
    }

    @Override
    public Object intercept(Invocation invocation) throws Exception {
        Object result = null;
        //捕获掉异常，不要影响业务
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        String sqlId = mappedStatement.getId();
        Logger log = LoggerFactory.getLogger(sqlId);
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Configuration configuration = mappedStatement.getConfiguration();
        long startTime = System.currentTimeMillis();
        String sql = this.getSql(configuration, boundSql);
        try {
            this.formatInput(log, sql);
            result = invocation.proceed();
            long endTime = System.currentTimeMillis();
            long sqlCostTime = endTime - startTime;
            this.formatSqlLog(log, sqlCostTime, result);
        } catch (Exception e) {
            this.formatException(log, e);
            throw e;
        }
        return result;
    }


    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    /**
     * 获取完整的sql语句
     *
     * @param configuration configuration
     * @param boundSql      boundSql
     * @return 完整SQL
     */
    private String getSql(Configuration configuration, BoundSql boundSql) {
        // 输入sql字符串空判断
        String sql = boundSql.getSql();
        if (StringUtils.isEmpty(sql)) {
            return "";
        }
        return formatSql(sql, configuration, boundSql);
    }

    /**
     * 将占位符替换成参数值
     *
     * @param sql           SQL语句
     * @param configuration configuration
     * @param boundSql      boundSql
     * @return 占位符替换后的SQL
     */
    private String formatSql(String sql, Configuration configuration, BoundSql boundSql) {
        //美化sql
        sql = beautifySql(sql);
        //填充占位符, 目前基本不用mybatis存储过程调用,故此处不做考虑
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        List<String> parameters = new ArrayList<>();
        if (parameterMappings != null) {
            MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
            for (ParameterMapping parameterMapping : parameterMappings) {
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    // 参数值
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    // 获取参数名称
                    if (boundSql.hasAdditionalParameter(propertyName)) {
                        // 获取参数值
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        // 如果是单个值则直接赋值
                        value = parameterObject;
                    } else {
                        value = metaObject == null ? null : metaObject.getValue(propertyName);
                    }
                    if (value instanceof Number) {
                        parameters.add(String.valueOf(value));
                    } else {
                        StringBuilder builder = new StringBuilder();
                        if (null == value) {
                            builder.append("NULL");
                        } else if (value instanceof Boolean) {
                            builder.append(value);
                        } else {
                            builder.append("'");
                            if (value instanceof Date) {
                                builder.append((dateTimeFormatter.get()).format((Date) value));
                            } else if (value instanceof String) {
                                builder.append(value);
                            }
                            builder.append("'");
                        }
                        parameters.add(builder.toString());
                    }
                }
            }
        }
        for (String value : parameters) {
            //直接使用value替换的话，如果内容包含了$符号，会报java.lang.IllegalArgumentException: Illegal group reference异常
            sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(value));
        }
        return sql;
    }


    /**
     * 格式化所执行SQL
     *
     * @param log 日志对象
     * @param sql SQL语句
     */
    private void formatInput(Logger log, String sql) {
        String sqlLog = "==> " + sql;
        log.info(sqlLog);
    }

    /**
     * 捕获异常及输出
     *
     * @param log 日志对象
     * @param e   执行异常
     */
    private void formatException(Logger log, Exception e) {

        log.info("<== Exception: " + e.toString() + "  Cause: " + e.getCause());
    }

    /**
     * 格式化sql日志
     *
     * @param log      日志对象
     * @param costTime 消耗时间
     * @param obj      结果对象
     */
    private void formatSqlLog(Logger log, long costTime, Object obj) {
        StringBuilder result = new StringBuilder();
        if (obj instanceof List) {
            List list = (List) obj;
            int count = list.size();
            result.append("<== Total: ").append(count);
        } else if (obj instanceof Integer) {
            result.append("<== Total: ").append(obj);
        }
        result.append(" Spend Time ==> ").append(costTime).append(" ms");
        log.info(result.toString());
    }

}
