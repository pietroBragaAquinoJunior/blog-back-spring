package com.pietro.blog_back_spring.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Value("${frontend.base-url}")
    private String frontendBaseUrl;

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendResetEmail(String to, String token) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(to);
        helper.setFrom("no-reply@myblog.com");
        helper.setSubject("Reset your password");
        helper.setText(
            String.format(
                """
                    Use this token: %s to reset your account.
                    Please acces: %s/reset-password-second to reset password.
                """, token, frontendBaseUrl)
            ,
            String.format(
                 """
                    <div>
                    <p>Use this token: %s to reset your account.</p>
                    <p>Open the link below to reset your password:</p>
                    <p><a href=%s/reset-password-second>Reset password</a></p>
                    </div>
                """
                , token, frontendBaseUrl)
            );
        mailSender.send(mimeMessage);
    }
}
