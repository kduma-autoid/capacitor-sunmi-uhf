package dev.duma.capacitor.sunmiuhf;

public class StrTools {
    public static byte[] hexStrToByteArray(String str) {
        if (str == null) {
            return null;
        }

        str = str.replaceAll("\\s", "");

        if (str.length() == 0) {
            return new byte[0];
        }

        if (str.length() % 2 != 0 || !str.matches("[0-9A-Fa-f]+")) {
            return null;
        }

        int len = str.length() / 2;
        byte[] buffer = new byte[len];
        for(int i = 0; i < len; ++i) {
            buffer[i] = (byte)Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
        }
        return buffer;
    }

    public static String byteArrayToHexStr(byte[] bytes) {
        return byteArrayToHexStr(bytes, false);
    }

    public static String byteArrayToHexStr(byte[] bytes, boolean spaced) {
        if (bytes == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
            if (spaced) {
                sb.append(" ");
            }
        }

        if (spaced && sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    public static String normalizeHexStr(String str) {
        return normalizeHexStr(str, false);
    }

    public static String normalizeHexStr(String str, boolean spaced) {
        return byteArrayToHexStr(hexStrToByteArray(str), spaced);
    }

    public static String normalizeHexStr(byte[] str) {
        return normalizeHexStr(str, false);
    }

    public static String normalizeHexStr(byte[] str, boolean spaced) {
        return byteArrayToHexStr(str, spaced);
    }
}
