package classes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

final class SimplePdfGenerator {
  private SimplePdfGenerator() {}

  static byte[] generate(String text) throws IOException {
    List<String> lines = wrapLines(text, 90);
    StringBuilder stream = new StringBuilder();
    stream.append("BT\n");
    stream.append("/F1 10 Tf\n");
    stream.append("40 760 Td\n");
    for (int i = 0; i < lines.size(); i++) {
      if (i > 0) {
        stream.append("0 -12 Td\n");
      }
      stream.append("(").append(escape(lines.get(i))).append(") Tj\n");
    }
    stream.append("ET");
    String streamContent = stream.toString();

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    List<Integer> offsets = new ArrayList<>();
    write(out, "%PDF-1.4\n");

    offsets.add(out.size());
    write(
        out,
        "1 0 obj\n<< /Type /Catalog /Pages 2 0 R >>\nendobj\n");

    offsets.add(out.size());
    write(
        out,
        "2 0 obj\n<< /Type /Pages /Kids [3 0 R] /Count 1 >>\nendobj\n");

    offsets.add(out.size());
    write(
        out,
        "3 0 obj\n<< /Type /Page /Parent 2 0 R /MediaBox [0 0 612 792] /Contents 4 0 R /Resources << /Font << /F1 5 0 R >> >> >>\nendobj\n");

    offsets.add(out.size());
    write(
        out,
        "4 0 obj\n<< /Length "
            + streamContent.getBytes(StandardCharsets.US_ASCII).length
            + " >>\nstream\n"
            + streamContent
            + "\nendstream\nendobj\n");

    offsets.add(out.size());
    write(
        out,
        "5 0 obj\n<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica >>\nendobj\n");

    int xrefOffset = out.size();
    write(out, "xref\n0 6\n");
    write(out, "0000000000 65535 f \n");
    for (int offset : offsets) {
      write(out, String.format("%010d 00000 n \n", offset));
    }
    write(out, "trailer\n<< /Size 6 /Root 1 0 R >>\n");
    write(out, "startxref\n" + xrefOffset + "\n");
    write(out, "%%EOF");
    return out.toByteArray();
  }

  private static List<String> wrapLines(String text, int maxChars) {
    List<String> wrapped = new ArrayList<>();
    for (String rawLine : text.split("\n", -1)) {
      if (rawLine.length() <= maxChars) {
        wrapped.add(rawLine);
        continue;
      }
      int start = 0;
      while (start < rawLine.length()) {
        int end = Math.min(start + maxChars, rawLine.length());
        wrapped.add(rawLine.substring(start, end));
        start = end;
      }
    }
    return wrapped;
  }

  private static String escape(String value) {
    StringBuilder escaped = new StringBuilder();
    for (int i = 0; i < value.length(); i++) {
      char ch = value.charAt(i);
      if (ch == '\\' || ch == '(' || ch == ')') {
        escaped.append('\\');
      }
      escaped.append(ch);
    }
    return escaped.toString();
  }

  private static void write(ByteArrayOutputStream out, String value) throws IOException {
    out.write(value.getBytes(StandardCharsets.US_ASCII));
  }
}
