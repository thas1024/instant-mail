package cc.thas.mail.utils;

import lombok.Data;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class HttpUtil {
    public HttpUtil() {
    }

    public static HttpResult convert(HttpResponse response) throws IOException {
        if (response == null) {
            return null;
        } else {
            HttpResult httpResult = new HttpResult();
            httpResult.setStatusCode(response.getStatusLine().getStatusCode());
            httpResult.setHttpBody(EntityUtils.toString(response.getEntity()));
            Header[] allHeaders = response.getAllHeaders();
            Map<String, String> headers = Arrays.stream(allHeaders)
                    .collect(Collectors.toMap(Header::getName, Header::getValue));
            httpResult.setHeaders(headers);
            EntityUtils.consume(response.getEntity());
            return httpResult;
        }
    }

    public static HttpEntity constructHttpEntity(Map<String, String> params) throws UnsupportedEncodingException {
        if (params != null && params.size() != 0) {
            List<NameValuePair> pairs = params.entrySet().stream()
                    .map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
            return new UrlEncodedFormEntity(pairs);
        } else {
            return null;
        }
    }

    public static Header[] convertHeaders(Map<String, String> headers) {
        if (headers != null && headers.size() != 0) {
            List<Header> headerList = headers.entrySet().stream().map(entry -> new BasicHeader(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
            return headerList.toArray(new Header[0]);
        } else {
            return null;
        }
    }

    public static String constructQueryUrl(String url, Map<String, String> params) {
        if (params != null && params.size() != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Entry<String, String> entry : params.entrySet()) {
                stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            return stringBuilder.substring(0, stringBuilder.length() - 1);
        } else {
            return url;
        }
    }

    @Data
    public static class HttpResult {
        private Integer statusCode;
        private String httpBody;
        private Map<String, String> headers;
    }
}
