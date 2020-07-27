package com.asyncmail.demo.service.impl;

import com.asyncmail.demo.service.MailService;
import com.asyncmail.demo.util.MailUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.HashMap;

@Service
public class MailServiceImpl  implements MailService {

    @Resource
    private MailUtil mailUtil;

    //异步发送html格式的邮件
    @Async
    @Override
    public void sendHtmlMail() {
        String from = "demouser@163.com";
        String[] to = {"371125307@qq.com"};
        String subject = "恭喜您成功注册老刘代码库网站";
        //String content = "邮件已发送成功";
        HashMap<String,String> content= new HashMap<String,String>();
        content.put("username","laoliu");
        content.put("nickname","老刘");
        content.put("id","0000001");
        String templateName= "mail/regmail.html";
        try {
            mailUtil.sendHtmlMail(from, to, null, null, subject, templateName, content);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("邮件发送出错");
        }
    }
}
