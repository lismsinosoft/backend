package com.gfk.common.util;

import cn.hutool.core.util.StrUtil;
import com.gfk.framework.properties.MailProperties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 邮件
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
public class Mail {
	static String smtp = MailProperties.getSmtp();
	static String account = MailProperties.getAccount();
	static String passw =MailProperties.getPassw();
	static String port = MailProperties.getPort();
	static boolean useSSL = MailProperties.isUseSSL();
	static boolean useTLS = MailProperties.isUseTLS();
	static boolean useAuth = MailProperties.isUseAuth();
	static String debugMail = MailProperties.getDebugMail();

    public static void sendMail(String title,String content, String emailAddr) throws Exception{
        Properties prop = new Properties();
        //启用用户认证
        if(useAuth){
            prop.put("mail.smtp.auth", "true");
        }
        //启用TLS加密
        if(useTLS){
            prop.put("mail.smtp.starttls.enable", "true");
        }
        //启用SSL加密
        if(useSSL){
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        }
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.host", smtp);
        prop.setProperty("mail.smtp.port", port);

        Session session = Session.getDefaultInstance(prop);
        session.setDebug(true);

        Message message = createSimpleMail(session,title,content,emailAddr);

        Transport transport = session.getTransport();
        if (useAuth){
            transport.connect(account, passw);
        }
        else{
            transport.connect();
        }
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    public static MimeMessage createSimpleMail(Session session,String title,String content,String emailAddr) throws Exception {
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress(account));
        //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        //message.setRecipient(Message.RecipientType.TO, new InternetAddress("839630724@qq.com"));
        if(StrUtil.isNotBlank(debugMail)){
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(debugMail));
        }
        else{
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailAddr));
        }

        //邮件的标题
        // message.setSubject("仅测试一下");
        message.setSubject(title);
        //邮件的文本内容
        // String content = "<h1>网上商城官方激活邮件!点下面链接完成激活操作!</h1><h3><a href='http://61.132.85.19:9811/login.jsp'>http://61.132.85.19:9811/login.jsp</a></h3><h3><a href='http://61.132.85.19:9811/login.jsp'>点击这里</a></h3>";
        message.setContent(content, "text/html;charset=UTF-8");
        //返回创建好的邮件对象
        return message;
    }

}
