package Server;

public class MyMath {

    public static String findSum(String str1, String str2) {
        if (str1.length() > str2.length()) {
            String t = str1;
            str1 = str2;
            str2 = t;
        }
        String str = "";
        int n1 = str1.length(), n2 = str2.length();
        str1 = new StringBuilder(str1).reverse().toString();
        str2 = new StringBuilder(str2).reverse().toString();
        int carry = 0;
        for (int i = 0; i < n1; i++) {
            int sum = ((int) (str1.charAt(i) - '0') +
                    (int) (str2.charAt(i) - '0') + carry);
            str += (char) (sum % 10 + '0');
            carry = sum / 10;
        }
        for (int i = n1; i < n2; i++) {
            int sum = ((int) (str2.charAt(i) - '0') + carry);
            str += (char) (sum % 10 + '0');
            carry = sum / 10;
        }
        if (carry > 0)
            str += (char) (carry + '0');
        str = new StringBuilder(str).reverse().toString();
        return str;
    }

    public static String findDiff(String str1, String str2) {
        String sign = "+";
        if (isSmaller(str1, str2)) {
            sign = "-";
            String t = str1;
            str1 = str2;
            str2 = t;
        }
        String str = "";
        int n1 = str1.length(), n2 = str2.length();
        str1 = new StringBuilder(str1).reverse().toString();
        str2 = new StringBuilder(str2).reverse().toString();
        int carry = 0;
        for (int i = 0; i < n2; i++) {
            int sub
                    = ((int) (str1.charAt(i) - '0')
                    - (int) (str2.charAt(i) - '0') - carry);
            if (sub < 0) {
                sub = sub + 10;
                carry = 1;
            } else
                carry = 0;

            str += (char) (sub + '0');
        }
        for (int i = n2; i < n1; i++) {
            int sub = ((int) (str1.charAt(i) - '0') - carry);
            if (sub < 0) {
                sub = sub + 10;
                carry = 1;
            } else
                carry = 0;
            str += (char) (sub + '0');
        }
        if (sign == "-") str += sign;
        return new StringBuilder(str).reverse().toString();
    }

    private static boolean isSmaller(String str1, String str2) {
        int n1 = str1.length(), n2 = str2.length();
        if (n1 < n2)
            return true;
        if (n2 < n1)
            return false;
        for (int i = 0; i < n1; i++)
            if (str1.charAt(i) < str2.charAt(i))
                return true;
            else if (str1.charAt(i) > str2.charAt(i))
                return false;
        return false;
    }

    public static String findDivision(String number, int divisor) {
        StringBuilder result = new StringBuilder();
        char[] dividend = number.toCharArray();
        int carry = 0;
        for (int i = 0; i < dividend.length; i++) {
            int x = carry * 10 + Character.getNumericValue(dividend[i]);
            result.append(x / divisor);
            carry = x % divisor;
        }
        for (int i = 0; i < result.length(); i++) {
            if (result.charAt(i) != '0') {
                return result.substring(i);
            }
        }
        return "";
    }

    static String findMultiply(String num1, String num2) {
        int len1 = num1.length();
        int len2 = num2.length();
        if (len1 == 0 || len2 == 0) return "0";
        int result[] = new int[len1 + len2];
        int i_n1 = 0;
        int i_n2 = 0;
        for (int i = len1 - 1; i >= 0; i--) {
            int carry = 0;
            int n1 = num1.charAt(i) - '0';
            i_n2 = 0;
            for (int j = len2 - 1; j >= 0; j--) {
                int n2 = num2.charAt(j) - '0';
                int sum = n1 * n2 + result[i_n1 + i_n2] + carry;
                carry = sum / 10;
                result[i_n1 + i_n2] = sum % 10;
                i_n2++;
            }
            if (carry > 0) result[i_n1 + i_n2] += carry;
            i_n1++;
        }
        int i = result.length - 1;
        while (i >= 0 && result[i] == 0) i--;
        if (i == -1) return "0";
        String s = "";
        while (i >= 0) s += (result[i--]);
        return s;
    }

}
