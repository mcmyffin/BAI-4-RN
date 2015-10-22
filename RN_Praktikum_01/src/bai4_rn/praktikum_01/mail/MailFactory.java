package bai4_rn.praktikum_01.mail;

/**
 * Created by Andreas on 22.10.2015.
 */
public class MailFactory {
    private MailFactory() {
    }

    public static Mail createMail(String mailFrom, String mailTo, String subject, String fileAttachment, String contentType) {
        return MailImpl.create(mailFrom, mailTo, subject, fileAttachment, contentType);
    }
}
