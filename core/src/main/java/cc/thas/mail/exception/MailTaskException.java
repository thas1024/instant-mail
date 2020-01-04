package cc.thas.mail.exception;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/4 16:02
 */
public class MailTaskException extends RuntimeException {

    public MailTaskException() {
        super();
    }

    public MailTaskException(String message) {
        super(message);
    }

    public MailTaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailTaskException(Throwable cause) {
        super(cause);
    }

    protected MailTaskException(String message, Throwable cause,
                                boolean enableSuppression,
                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
