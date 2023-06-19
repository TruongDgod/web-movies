package com.bepay.application.utils.mail;

import com.bepay.application.models.mail.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author DanTran
 * @see MailService
 */

@Service
public class MailService {

    @Autowired
    JavaMailSender mailSender;

    public Mail contentEmail(String mailFrom, String[] arrMailTo, String subject, String content) {
        Mail mail = new Mail();
        mail.setMailFrom(mailFrom);
        mail.setArrMailTo(arrMailTo);
        mail.setMailSubject(subject);
        mail.setMailContent(content);
        return mail;
    }

    public void sendEmail(Mail mail) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom()));
            mimeMessageHelper.setTo(mail.getArrMailTo());
            mimeMessageHelper.setText(mail.getMailContent(), true);

            mailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
