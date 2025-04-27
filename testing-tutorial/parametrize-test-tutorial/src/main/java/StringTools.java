import org.apache.commons.lang3.StringUtils;

public class StringTools {

    public static boolean isAlphanumeric(String s){
        return StringUtils.isAlphanumeric(s);
    }

    public static boolean isBlank(String s){
        return StringUtils.isBlank(s);
    }

    public static boolean isEmpty(String s){
        return StringUtils.isEmpty(s);
    }
}