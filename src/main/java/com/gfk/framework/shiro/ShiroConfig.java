package com.gfk.framework.shiro;

import com.gfk.framework.jwt.JwtFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        logger.info("initial shirFilter...");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/unlogin");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        //拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/ws**", "anon");
        // 第三方 获取活动报表 信息
        filterChainDefinitionMap.put("/doc.html", "anon");
        // swagger放行 （https://blog.csdn.net/weixin_42036952/article/details/90298357）
        filterChainDefinitionMap.put("/swagger/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/image/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");

        filterChainDefinitionMap.put("/token_check", "anon");
        filterChainDefinitionMap.put("/gen/**", "anon");

        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/test/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/te_user/**", "anon");
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/captcha/**", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/403", "anon");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/user_login/login", "anon");//用户端-登录
        filterChainDefinitionMap.put("/user_login/saml", "anon");//用户端-SSO登录
        filterChainDefinitionMap.put("/user_login/register", "anon");//用户端-注册
        filterChainDefinitionMap.put("/user_login/register_check", "anon");//用户端-注册检查
        filterChainDefinitionMap.put("/user_login/initPwd", "anon");//用户端-初始化密码
        filterChainDefinitionMap.put("/user_login/user", "anon");//无验证，同步数据
        filterChainDefinitionMap.put("/user_login/postCode", "anon");//无验证，发送验证码
        filterChainDefinitionMap.put("/user_login/forgetPwd", "anon");//无验证，忘记密码发送验证码
        filterChainDefinitionMap.put("/user_login/forget_check", "anon");//无验证，忘记密码检查
        filterChainDefinitionMap.put("/user_login/changePwd", "anon");//无验证，修改密码
        filterChainDefinitionMap.put("/user_login/token_login", "anon");//无验证，sso token验证
        filterChainDefinitionMap.put("/user_login/sso_reg", "anon");//无验证，sso 初始化密码
        filterChainDefinitionMap.put("/system/config/sys_config/get_config_by_key", "anon");//根据key获取配置
        filterChainDefinitionMap.put("/lego/**", "anon");//LEGO同步记录
        filterChainDefinitionMap.put("/saml2/**", "anon");//自定义授权地址
        filterChainDefinitionMap.put("/wechat/**", "anon");//微信相关模块
        filterChainDefinitionMap.put("/subscribe/**", "anon");//订阅相关模块
        filterChainDefinitionMap.put("/unavailable/**", "anon");//停机时间查询

        filterChainDefinitionMap.put("/project/**", "anon");//项目配置，使用其他方式鉴权
        filterChainDefinitionMap.put("/sys/data/**", "anon");//导入配置，使用其他方式鉴权
        filterChainDefinitionMap.put("/user/**", "anon");//用户配置，使用其他方式鉴权

        // 监控配置
        //filterChainDefinitionMap.put("/actuator/**", "jwt,roles[admin]");

        // 健康检查
        filterChainDefinitionMap.put("/actuator/**", "anon");

        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
//        filterChainDefinitionMap.put("/**", "authc");

        /* JWT 结合 Shiro*/
        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new LinkedHashMap<>(1);
        filterMap.put("jwt", new JwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        // 所有请求通过我们自己的JWT Filter
        filterChainDefinitionMap.put("/**", "jwt");

        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * ）
     *
     * @return
     */
/*    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("sha256");//散列算法
        hashedCredentialsMatcher.setHashIterations(1);//散列的次数，比如散列两次，相当于 md5(md5(""))；需要与注册时加密的方式一致
        return hashedCredentialsMatcher;
    }*/
    @Bean
    public SecurityManager securityManager(MyShiroRealm myShiroRealm, RedisCacheManager redisCacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm);

        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        //自定义缓存实现,使用redis
        securityManager.setCacheManager(redisCacheManager);
        return securityManager;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * cacheManager 缓存 redis实现
     * shiro结合redis实现缓存用户的权限信息，避免每次查询数据库
     * （使用的是shiro-redis开源插件）
     *
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager(RedisManager redisManager) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager);
        //redis中针对不同用户缓存(此处的id需要对应user实体中的id字段,用于唯一标识)
        redisCacheManager.setPrincipalIdFieldName("id");
        //用户权限信息缓存时间（秒：s）
        redisCacheManager.setExpire(50 * 3600);
        return redisCacheManager;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisManager redisManager(RedisProperties redisProperties) {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisProperties.getHost() + ":" + redisProperties.getPort());
        redisManager.setDatabase(redisProperties.getDatabase());
        redisManager.setTimeout((int) redisProperties.getTimeout().toMillis());
        if (!StringUtils.isEmpty(redisProperties.getPassword())) {
            redisManager.setPassword(redisProperties.getPassword());
        }
        logger.info("redis cache init finish.");
        return redisManager;
    }
}
