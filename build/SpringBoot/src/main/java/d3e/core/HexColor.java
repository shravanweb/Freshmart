package d3e.core;
import pdf.classes.PdfColor;

public class HexColor {
	public static PdfColor fromHexStr(String hexString) {
        String hexCode = (hexString != null && hexString.length() > 0) ? hexString : "00000000";
        StringBuilder buffer = new StringBuilder();
        if (hexCode.length() == 6 || hexCode.length() == 7) {
            buffer.append("ff");
        }
        buffer.append(hexCode.replace("#", ""));
        try {
            return new PdfColor((int) Long.parseLong(buffer.toString(), 16), 0, 0, 0);
        } catch (NumberFormatException e) {
            return new PdfColor(255, 255, 255, 0);
        }
    }

    public static PdfColor fromHexInt(long hex1) {
    	int hex = (int) hex1;
    	int alpha = (hex >> 24) & 0xFF;
        int red = (hex >> 16) & 0xFF;
        int green = (hex >> 8) & 0xFF;
        int blue = hex & 0xFF;
        return new PdfColor(red, green, blue, alpha);
    }

    public static String toHexStr(PdfColor color, boolean leadingHashSign) {
        try {
            return (leadingHashSign ? "#" : "") +
                    String.format("%02X", color.getAlpha()) +
                    String.format("%02X", color.getRed()) +
                    String.format("%02X", color.getGreen()) +
                    String.format("%02X", color.getBlue());
        } catch (Exception e) {
            return "";
        }
    }

    public static int toHexInt(PdfColor color) {
        return (int) IntegerExt.parse(color.toHex());
    }

}