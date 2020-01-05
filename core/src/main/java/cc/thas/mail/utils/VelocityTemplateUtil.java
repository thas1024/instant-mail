package cc.thas.mail.utils;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.Map;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/5 20:37
 */
public class VelocityTemplateUtil {

    public static final String LOG_TAG = "VelocityTemplateUtil";
    private static final VelocityEngine VELOCITY_ENGINE;

    static {
        VELOCITY_ENGINE = new VelocityEngine();
        VELOCITY_ENGINE.init();
    }

    public static String render(String template, Map<String, String> map) {
        if (map == null) {
            return template;
        }
        VelocityContext context = new VelocityContext(map);
        StringWriter writer = new StringWriter();
        VELOCITY_ENGINE.evaluate(context, writer, LOG_TAG, template);
        return writer.toString();
    }

}
