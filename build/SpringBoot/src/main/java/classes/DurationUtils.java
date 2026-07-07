package classes;

import d3e.core.DateFormatExt;
import d3e.core.DoubleExt;
import d3e.core.DurationExt;
import d3e.core.IntegerExt;
import d3e.core.ListExt;
import d3e.core.StringExt;
import d3e.core.TimeExt;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class DurationUtils {
  public DurationUtils() {}

  public static String durationToString(Duration d) {
    if (d == null) {
      return "";
    }
    long minutes = DurationExt.inMinutes(d) % 60l;
    long hours = DoubleExt.toInt((DurationExt.inMinutes(d) / 60l));
    return IntegerExt.toString(hours) + "h " + IntegerExt.toString(minutes) + "m";
  }

  public static String makeTwoDigit(long num) {
    if (num < 10l) {
      return "0" + IntegerExt.toString(num);
    } else {
      return IntegerExt.toString(num);
    }
  }

  public static String formattedDuration(Duration value, String format, boolean showZero) {
    if (value == null || Objects.equals(value, Duration.ZERO)) {
      return showZero ? "0h 00m" : "";
    }
    if (format == null || Objects.equals(format, "")) {
      return value.toString();
    }
    long seconds = DurationExt.inSeconds(value);
    if (seconds < 0l) {
      seconds *= -1l;
    }
    long hours = DoubleExt.truncate((seconds / 3600l));
    long minutes = DoubleExt.truncate(((seconds % 3600l) / 60l));
    switch (format) {
      case "HHh MMm":
        {
          return DurationUtils.makeTwoDigit(hours)
              + "h "
              + DurationUtils.makeTwoDigit(minutes)
              + "m";
        }
      case "HH:mm":
        {
          return DurationUtils.makeTwoDigit(hours) + "." + DurationUtils.makeTwoDigit(minutes);
        }
      case "HH.HHh":
        {
          return IntegerExt.toString(hours) + "." + IntegerExt.toString(minutes) + "h";
        }
      case "HH.HH":
        {
          return IntegerExt.toString(hours) + "." + IntegerExt.toString(minutes);
        }
      default:
        {
        }
    }
    return value.toString();
  }

  public static String formattedDurationWithColumn(
      Duration value, String format, boolean showZero) {
    if (value == null || Objects.equals(value, Duration.ZERO)) {
      return showZero ? "0h 00m" : "";
    }
    if (format == null || Objects.equals(format, "")) {
      return value.toString();
    }
    long seconds = DurationExt.inSeconds(value);
    if (seconds < 0l) {
      seconds *= -1l;
    }
    long hours = DoubleExt.truncate((seconds / 3600l));
    long minutes = DoubleExt.truncate(((seconds % 3600l) / 60l));
    switch (format) {
      case "HHh MMm":
        {
          return DurationUtils.makeTwoDigit(hours)
              + "h "
              + DurationUtils.makeTwoDigit(minutes)
              + "m";
        }
      case "HH MM":
        {
          return DurationUtils.makeTwoDigit(hours) + ":" + DurationUtils.makeTwoDigit(minutes);
        }
      case "HH.HHh":
        {
          return IntegerExt.toString(hours) + ":" + IntegerExt.toString(minutes) + "h";
        }
      case "HH.HH":
        {
          return IntegerExt.toString(hours) + ":" + IntegerExt.toString(minutes);
        }
      default:
        {
        }
    }
    return value.toString();
  }

  public static String returnDuration(String durationStr) {
    //  check durationStr length equals to 2 then add .
    if (StringExt.length(durationStr) == 2l && !durationStr.endsWith(":")) {
      durationStr = durationStr + ":";
    }
    //  Split the duration string into hours and minutes
    List<String> durationList = StringExt.split(durationStr, ":");
    if (ListExt.length(durationList) != 2l) {
      //  If the duration string is not in the correct format, return -1 that indicates an error
      return "-1";
    }
    long hours = IntegerExt.tryParse(ListExt.get(durationList, 0l), 10l);
    long minutes = IntegerExt.tryParse(ListExt.get(durationList, 1l), 10l);
    //  Check if the hours and minutes are in the correct range
    if (hours < 0l || hours > 23l || minutes < 0l || minutes > 59l) {
      //  If the hours or minutes are not in the correct range, return a zero duration
      return "-1";
    }
    //  If the duration is in the correct format and range, return it in the same format
    return durationStr;
  }

  public static Duration stringToDuration(String durationStr) {
    List<String> durationList = StringExt.split(durationStr, ":");
    long hours = IntegerExt.tryParse(ListExt.get(durationList, 0l), 10l);
    long minutes = IntegerExt.tryParse(ListExt.get(durationList, 1l), 10l);
    return DurationExt.Duration(0l, hours, 0l, 0l, minutes, 0l);
  }

  public static Duration getFormattedDuration(String durationStr) {
    //  check durationStr length equals to 1
    if (StringExt.length(durationStr) == 1l) {
      long hours = IntegerExt.tryParse(durationStr, 10l);
      if (hours > 0l || hours <= 23l) {
        return DurationExt.Duration(0l, hours, 0l, 0l, 0l, 0l);
      }
    } else if (StringExt.length(durationStr) == 2l && !durationStr.endsWith(":")) {
      long hours = IntegerExt.tryParse(durationStr, 10l);
      if (hours > 0l || hours <= 23l) {
        return DurationExt.Duration(0l, hours, 0l, 0l, 0l, 0l);
      }
    }
    return null;
  }

  public static String formatTime(String timeString, String format) {
    String dateString = "1970-01-01T";
    List<String> timeInArray = StringExt.split(timeString, ":");
    String hour = ListExt.get(timeInArray, 0l);
    String minutes = ListExt.get(timeInArray, 1l);
    if (StringExt.length(hour) == 1l) {
      hour = "0" + hour;
    }
    if (StringExt.length(minutes) == 1l) {
      minutes = "0" + minutes;
    }
    String timeString1 = hour + ":" + minutes;
    LocalDateTime time = LocalDateTime.parse(dateString + timeString1);
    DateTimeFormatter formatter = DateFormatExt.DateFormat(format, null);
    return formatter.format(time);
  }

  public static String getFormattedTime(LocalTime time, boolean militryTime) {
    if (time == null) {
      return "";
    }
    String timeString =
        IntegerExt.toString(TimeExt.getHour(time))
            + ":"
            + IntegerExt.toString(TimeExt.getMinutes(time));
    if (militryTime) {
      return DurationUtils.formatTime(timeString, "HH:mm");
    } else {
      return DurationUtils.formatTime(timeString, "hh:mm a");
    }
  }
}
