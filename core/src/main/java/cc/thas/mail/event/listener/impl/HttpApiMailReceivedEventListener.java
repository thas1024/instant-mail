package cc.thas.mail.event.listener.impl;

import cc.thas.mail.event.impl.MailReceivedEvent;
import cc.thas.mail.event.listener.AbstractMailReceivedEventListener;
import cc.thas.mail.logger.MailReceivedLog;
import cc.thas.mail.message.MailMessage;
import cc.thas.mail.utils.HttpClientUtil;
import cc.thas.mail.utils.HttpUtil;
import cc.thas.mail.utils.VelocityTemplateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/5 18:48
 */
public class HttpApiMailReceivedEventListener extends AbstractMailReceivedEventListener {

    private final boolean usePost;
    private final String url;
    private final Map<String, String> params;
    private final Map<String, String> headers;

    public HttpApiMailReceivedEventListener(boolean usePost, String url, Map<String, String> params,
                                            Map<String, String> headers) {
        this.usePost = usePost;
        this.url = url;
        this.params = params;
        this.headers = headers;
    }

    @Override
    protected void onMailReceived(MailReceivedEvent event) {
        MailMessage message = event.getPayload().getMessage();
        Map<String, String> context = null;
        if (message != null) {
            context = ((JSONObject) JSON.toJSON(message)).entrySet().stream().collect(
                    Collectors.toMap(entry -> String.valueOf(entry.getKey()),
                            entry -> String.valueOf(entry.getValue())));
        }
        Map<String, String> params = render(this.params, context);
        Map<String, String> headers = render(this.headers, context);
        HttpUtil.HttpResult httpResult;
        if (usePost) {
            httpResult = HttpClientUtil.post(url, params, headers);
        } else {
            httpResult = HttpClientUtil.get(url, params, headers);
        }
        onHttpResult(httpResult);
    }

    public void onHttpResult(HttpUtil.HttpResult httpResult) {
        if (httpResult.getStatusCode() != 200) {
            MailReceivedLog.error("Http invoked error when mail received.Msg: %s", httpResult.getHttpBody());
        }
    }


    private Map<String, String> render(Map<String, String> params, final Map<String, String> context) {
        if (params != null && params.size() > 0) {
            return params.entrySet().stream().collect(Collectors.toMap(
                    entry -> VelocityTemplateUtil.render(entry.getKey(), context),
                    entry -> VelocityTemplateUtil.render(entry.getValue(), context)));
        }
        return params;
    }
}
