package com.gfk.framework.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 应用（系统）信息
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@Component
@ConfigurationProperties(prefix = "app.info")
public class ApplicationInfoProperties {

    /** 系统访问地址*/
    private static String host;
    /** 名称*/
    private static String name;
    /** 版本*/
    private static String version;
    /** 版本年份*/
    private static String copyrightYear;
    /** 作者*/
    private static String author;
    /** 联系方式*/
    private static String contact;
    /** 描述*/
    private static String description;
    /** 接口文档标题*/
    private static String apiTitle;
    /** 应用程序访问地址（获取图片等资源）*/
    private static String appUrl;
    /** 当前服务器标识*/
    private static String serverCurrentHost;


    public static String getHost() {
        return host;
    }

    public static String getName() {
        return name;
    }

    public static String getVersion() {
        return version;
    }

    public static String getCopyrightYear() {
        return copyrightYear;
    }

    public static String getAuthor() {
        return author;
    }

    public static String getContact() {
        return contact;
    }

    public static String getDescription() {
        return description;
    }

    public static String getApiTitle() {
        return apiTitle;
    }

    public static String getAppUrl() {
        return appUrl;
    }

    public void setHost(String host) {
        ApplicationInfoProperties.host = host;
    }

    public void setName(String name) {
        ApplicationInfoProperties.name = name;
    }

    public void setVersion(String version) {
        ApplicationInfoProperties.version = version;
    }

    public void setCopyrightYear(String copyrightYear) {
        ApplicationInfoProperties.copyrightYear = copyrightYear;
    }

    public void setAuthor(String author) {
        ApplicationInfoProperties.author = author;
    }

    public void setContact(String contact) {
        ApplicationInfoProperties.contact = contact;
    }

    public void setDescription(String description) {
        ApplicationInfoProperties.description = description;
    }

    public void setApiTitle(String apiTitle) {
        ApplicationInfoProperties.apiTitle = apiTitle;
    }

    public void setAppUrl(String appUrl) {
        ApplicationInfoProperties.appUrl = appUrl;
    }

    public static String getServerCurrentHost() {
        return serverCurrentHost;
    }

    public void setServerCurrentHost(String serverCurrentHost) {
        ApplicationInfoProperties.serverCurrentHost = serverCurrentHost;
    }
}
