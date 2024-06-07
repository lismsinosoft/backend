package com.gfk.framework.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 邮件账号
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@Component
@ConfigurationProperties(prefix = "email")
public class MailProperties {

    private static String smtp;

    private static String port;

    private static String account;

    private static String passw;

    private static String toMail;

    private static boolean useSSL;

    private static boolean useTLS;

    private static boolean useAuth;

    private static String debugMail;

    private static String warningMail;

    public static String getSmtp() {
        return smtp;
    }

    public static String getWarningMail() {
        return warningMail;
    }

    public void setWarningMail(String warningMail) {
        MailProperties.warningMail = warningMail;
    }

    public void setSmtp(String smtp) {
        MailProperties.smtp = smtp;
    }

    public static String getPort() {
        return port;
    }

    public void setPort(String port) {
        MailProperties.port = port;
    }

    public static String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        MailProperties.account = account;
    }

    public static String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        MailProperties.passw = passw;
    }

    public static String getToMail() {
        return toMail;
    }

    public void setToMail(String toMail) {
        MailProperties.toMail = toMail;
    }

    public static boolean isUseSSL() {
        return useSSL;
    }

    public void setUseSSL(boolean useSSL) {
        MailProperties.useSSL = useSSL;
    }

    public static boolean isUseTLS() {
        return useTLS;
    }

    public void setUseTLS(boolean useTLS) {
        MailProperties.useTLS = useTLS;
    }

    public static boolean isUseAuth() {
        return useAuth;
    }

    public void setUseAuth(boolean useAuth) {
        MailProperties.useAuth = useAuth;
    }

    public static String getDebugMail() {
        return debugMail;
    }

    public void setDebugMail(String debugMail) {
        MailProperties.debugMail = debugMail;
    }
}
