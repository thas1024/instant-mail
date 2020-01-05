package cc.thas.mail.message;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/4 13:09
 */
@Data
public class MailMessage implements Serializable {

    private String id;
    private boolean seen;
    private String subject;
    private String from;
    private String to;
    private Date sentDate;
    private String content;

}
