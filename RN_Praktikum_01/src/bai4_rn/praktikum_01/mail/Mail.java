package bai4_rn.praktikum_01.mail;

import java.util.List;

/**
 * Created by Andreas on 22.10.2015.
 */
public interface Mail {


    public String getMailFromAdress();
    public String getMailToAdress();

    public String getSubject();
    public List<String> getMessage();

    public String getAttachmentFile();
    public String getAttachmentContentType();

}
