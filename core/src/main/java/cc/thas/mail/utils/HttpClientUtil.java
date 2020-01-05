package cc.thas.mail.utils;

import cc.thas.mail.logger.SystemErrorLog;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Map;

public class HttpClientUtil {
    private static final int DEFAULT_TIME_OUT = 120000;
    private static final PoolingHttpClientConnectionManager CONNECTION_MANAGER =
            new PoolingHttpClientConnectionManager();
    private static HttpClient DEFAULT_HTTP_CLIENT;

    static {
        CONNECTION_MANAGER.setMaxTotal(1000);
        CONNECTION_MANAGER.setDefaultMaxPerRoute(500);
        RequestConfig requestConfig =
                RequestConfig.custom().setRedirectsEnabled(false).setConnectionRequestTimeout(120000)
                        .setConnectTimeout(120000).setSocketTimeout(120000).build();
        DEFAULT_HTTP_CLIENT =
                HttpClients.custom().setDefaultRequestConfig(requestConfig).setConnectionManager(CONNECTION_MANAGER)
                        .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                        .build();
    }

    public HttpClientUtil() {
    }

    public static HttpResponse doExecute(HttpRequestBase request) throws IOException {
        return DEFAULT_HTTP_CLIENT.execute(request);


    }

    public static HttpUtil.HttpResult execute(HttpRequestBase request) {
        try {
            return HttpUtil.convert(doExecute(request));
        } catch (IOException e) {
            SystemErrorLog.error(e, "Http execute error.");
            return null;
        }
    }

    public static HttpUtil.HttpResult post(String url, Map<String, String> params, Map<String, String> headers) {
        if (url != null) {
            HttpEntity httpEntity = null;
            try {
                httpEntity = HttpUtil.constructHttpEntity(params);
            } catch (UnsupportedEncodingException e) {
                SystemErrorLog.error(e, "Construct HttpEntity error.");
                return null;
            }
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(httpEntity);
            httpPost.setHeaders(HttpUtil.convertHeaders(headers));
            return execute(httpPost);
        } else {
            return null;
        }
    }

    public static HttpUtil.HttpResult post(String url, Map<String, String> params) {
        return post(url, params, Collections.emptyMap());
    }

    public static HttpUtil.HttpResult get(String url, Map<String, String> params, Map<String, String> headers) {
        if (url != null) {
            HttpGet httpGet = new HttpGet(HttpUtil.constructQueryUrl(url, params));
            httpGet.setHeaders(HttpUtil.convertHeaders(headers));
            return execute(httpGet);
        } else {
            return null;
        }
    }

    public static HttpUtil.HttpResult get(String url) {
        return get(url, Collections.emptyMap(), Collections.emptyMap());
    }
}
