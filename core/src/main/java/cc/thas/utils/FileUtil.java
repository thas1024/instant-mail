package cc.thas.utils;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2019/12/28 22:27
 */
public class FileUtil {

    public static final String DOT = ".";

    public static String getFileNameSuffix(String fileName) {
        if (fileName == null) {
            return null;
        }
        int i = fileName.lastIndexOf(DOT);
        if (i > -1 && i < fileName.length() - 1) {
            return fileName.substring(i + 1);
        }
        return null;
    }

}
