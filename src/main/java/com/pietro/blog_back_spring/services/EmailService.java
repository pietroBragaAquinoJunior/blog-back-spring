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

    public void sendResetEmail(String to, String token) throws MessagingException  {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(to);
        helper.setFrom("nao-responder@myblog.com");
        helper.setSubject("Redefinir a senha");
        helper.setText(
            String.format(
                """
                    Use este token: %s para redefinir a senha da sua conta.
                    Por favor acesse: %s/reset-password-second para redefinir a sua senha.
                """, token, frontendBaseUrl)
            ,
            String.format(
                 """
                    <div>
                    <p>Use este token: %s para redefinir a senha da sua conta.</p>
                    <p>Entre no link abaixo:</p>
                    <p><a href=%s/reset-password-second>Redefinir Senha</a></p>
                    </div>
                """
                , token, frontendBaseUrl)
            );
        mailSender.send(mimeMessage);
    }
}
