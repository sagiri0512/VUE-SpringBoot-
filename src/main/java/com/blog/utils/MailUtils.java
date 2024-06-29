package com.blog.utils;

import com.blog.entity.EmailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class MailUtils {
    public static boolean sendMail(EmailProperties emailProperties, String to, String title, String content) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailProperties.getHost());
        mailSender.setPort(587);
        mailSender.setUsername(emailProperties.getUser());
        mailSender.setPassword(emailProperties.getCode());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailProperties.getUser());
            message.setTo(to);
            message.setSubject(title);
            message.setText(content);

            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}