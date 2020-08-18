package utils;

public class MathUtils {
    public static boolean isNumericInt(String s) {
        if (s == null) {
            return false;
        }
        try {
            double d = Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isNumericDouble(String s) {
        if (s == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
