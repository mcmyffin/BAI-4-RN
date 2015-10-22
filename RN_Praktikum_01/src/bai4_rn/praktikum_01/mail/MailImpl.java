package bai4_rn.praktikum_01.mail;

import java.util.ArrayList;
import java.util.List;
import static com.google.common.base.Preconditions.*;

/**
 * Created by abp615 on 22.10.2015.
 */
public class MailImpl implements Mail{

    private String mailFrom;
    private String mailTo;
    private String subject;
    private List<String> message;
    private String fileAttachment;

    public MailImpl(String mailFrom, String mailTo, String subject, List<String> message, String fileAttachment) {

        checkNotNull(mailFrom);
        checkNotNull(mailTo);
        checkNotNull(subject);
        checkNotNull(message);
        checkNotNull(fileAttachment);

        this.mailFrom = mailFrom;
        this.mailTo = mailTo;
        this.subject = subject;
        this.message = message;
        this.fileAttachment = fileAttachment;
    }

    public MailImpl(String mailFrom, String mailTo, String subject, String fileAttachment){
        this(mailFrom,mailTo,subject, new ArrayList(),fileAttachment);
    }

    @Override
    public String getAttachmentFile() {
        return this.fileAttachment;
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