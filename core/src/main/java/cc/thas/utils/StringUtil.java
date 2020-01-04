package cc.thas.utils;

import cc.thas.mail.logger.SystemErrorLog;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class StringUtil {

    private static final String UNDER_LINE = "_";

    public static String format(String str, Object... args) {
        if (ArrayUtils.isEmpty(args)) {
            return str;
        }
        try {
            return String.format(str, args);
        } catch (Exception e) {
            String argsStr = StringUtils.join(Arrays.asList(args), ",");
            String errMsg = String.format("format str failed, params={str:%s, args:[%s]}", str, argsStr);
            SystemErrorLog.error(e, errMsg);
            return str + ", args:[" + argsStr + "]";
        }
    }

    /**
     * 将二进制数据转换成十六进制
     *
     * @param bytes 二进制数据
     * @return 十六进制数据
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String valueOf(Object o) {
        if (o == null) {
            return null;
        }
        return String.valueOf(o);
    }

}
