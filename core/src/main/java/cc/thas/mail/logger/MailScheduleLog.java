package cc.thas.mail.logger;

import cc.thas.mail.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/4 17:06
 */
public class MailScheduleLog {

    private static final Logger LOG = LoggerFactory.getLogger(MailScheduleLog.class);

    public static void error(String format, Object... args) {
        LOG.error(StringUtil.format(format, args));
    }

    public static void error(Throwable t, String format, Object... args) {
        LOG.error(StringUtil.format(format, args), t);
    }

    public static void warn(String format, Object... args) {
        LOG.error(StringUtil.format(format, args));
    }

    public static void warn(Throwable t, String format, Object... args) {
        LOG.error(StringUtil.format(format, args), t);
    }

    public static void info(String format, Object... args) {
        LOG.info(StringUtil.format(format, args));
    }

}
