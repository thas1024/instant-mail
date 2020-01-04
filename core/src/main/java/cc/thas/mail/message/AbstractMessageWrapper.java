package cc.thas.mail.message;

import org.apache.commons.lang3.time.DateFormatUtils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2019/12/28 23:02
 */
public abstract class AbstractMessageWrapper implements MessageGetter, Initable {

    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd hh:mm:ss";

    private volatile boolean initialed;

    @Override
    public String getFrom() throws MessagingException, UnsupportedEncodingException {
        return internetAddressToString(getFromAddress());
    }

    @Override
    public List<InternetAddress> getToAddressList() throws MessagingException {
        return getReceiveAddressList(Message.RecipientType.TO);
    }

    @Override
    public String getTo() throws MessagingException, UnsupportedEncodingException {
        return internetAddressListToString(getToAddressList());
    }

    @Override
    public String getFormattedSentDate() throws MessagingException {
        Date sentDate = getSentDate();
        if (sentDate != null) {
            return DateFormatUtils.format(sentDate, DEFAULT_DATETIME_FORMAT);
        }
        return null;
    }

    @Override
    public String stringify() throws MessagingException, IOException {
        return "id: " + getId() + "\nsubject: " + getSubject() + "\nfrom: " + getFrom() + "\nsent_date: "
            + getFormattedSentDate() + "\nto: " + getTo() + "\ncontent: " + getContent() + "\n";
    }

    @Override
    public void checkInitialed() throws MessagingException, IOException {
        if (!initialed) {
            init();
            initialed = true;
        }
    }

    protected String internetAddressToString(InternetAddress address) throws UnsupportedEncodingException {
        String personal = address.getPersonal();
        return personal != null ? MimeUtility.decodeText(personal) + "<" + address.getAddress() + ">" :
            address.getAddress();
    }

    protected String internetAddressListToString(List<InternetAddress> addressList) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder();

        for (InternetAddress internetAddress : addressList) {
            stringBuilder.append(internetAddressToString(internetAddress)).append(";");
        }

        return stringBuilder.length() > 0 ? stringBuilder.substring(0, stringBuilder.length() - 1) : null;
    }
}
