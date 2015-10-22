package bai4_rn.praktikum_01.mail;

import java.util.ArrayList;
import java.util.List;
import static com.google.common.base.Preconditions.*;

/**
 * Created by abp615 on 22.10.2015.
 */
class MailImpl implements Mail{

    private String mailFrom;
    private String mailTo;
    private String subject;
    private List<String> message;
    private String fileAttachment;
    private String contentType;

    public MailImpl(String mailFrom, String mailTo, String subject, List<String> message, String fileAttachment, String contentType) {

        checkNotNull(mailFrom);
        checkNotNull(mailTo);
        checkNotNull(subject);
        checkNotNull(message);
        checkNotNull(fileAttachment);
        checkNotNull(contentType);

        this.mailFrom = mailFrom;
        this.mailTo = mailTo;
        this.subject = subject;
        this.message = message;
        this.fileAttachment = fileAttachment;
        this.contentType = contentType;
    }

    private MailImpl(String mailFrom, String mailTo, String subject, String fileAttachment, String contentType){
        this(mailFrom,mailTo,subject, new ArrayList(), fileAttachment, contentType);
    }

    public static Mail create(String mailFrom, String mailTo, String subject, String fileAttachment, String contentType) {
        return new MailImpl(mailFrom, mailTo, subject, fileAttachment, contentType);
    }

    @Override
    public String getAttachmentFile() {
        return this.fileAttachment;
    }

    @Override
    public String getAttachmentContentType() {
        return contentType;
    }

    @Override
    public String getSubject() {
        return this.subject;
    }

    @Override
    public List<String> getMessage() {
        return this.message;
    }

    @Override
    public String getMailFromAdress() {
        return this.mailFrom;
    }

    @Override
    public String getMailToAdress() {
        return this.mailTo;
    }
}
