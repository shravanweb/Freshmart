package classes;

import d3e.core.DateExt;
import d3e.core.DateFormatExt;
import d3e.core.DateTimeExt;
import d3e.core.DurationExt;
import d3e.core.ListExt;
import d3e.core.StringExt;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CalenderUtils {
  public CalenderUtils() {}

  public static long getMonthNumber(String month) {
    switch (month) {
      case "January":
        {
          return 1l;
        }
      case "February":
        {
          return 2l;
        }
      case "March":
        {
          return 3l;
        }
      case "April":
        {
          return 4l;
        }
      case "May":
        {
          return 5l;
        }
      case "June":
        {
          return 6l;
        }
      case "July":
        {
          return 7l;
        }
      case "August":
        {
          return 8l;
        }
      case "September":
        {
          return 9l;
        }
      case "October":
        {
          return 10l;
        }
      case "November":
        {
          return 11l;
        }
      case "December":
        {
          return 12l;
        }
      default:
        {
        }
    }
    return 0l;
  }

  public static String getMonthName(long month) {
    switch (((int) month)) {
      case ((int) 1l):
        {
          return "January";
        }
      case ((int) 2l):
        {
          return "February";
        }
      case ((int) 3l):
        {
          return "March";
        }
      case ((int) 4l):
        {
          return "April";
        }
      case ((int) 5l):
        {
          return "May";
        }
      case ((int) 6l):
        {
          return "June";
        }
      case ((int) 7l):
        {
          return "July";
        }
      case ((int) 8l):
        {
          return "August";
        }
      case ((int) 9l):
        {
          return "September";
        }
      case ((int) 10l):
        {
          return "October";
        }
      case ((int) 11l):
        {
          return "November";
        }
      case ((int) 12l):
        {
          return "December";
        }
      default:
        {
        }
    }
    return "";
  }

  public static List<LocalDate> prepareCalenderData(LocalDate date) {
    LocalDateTime firstDay =
        DateTimeExt.DateTime(DateExt.getYear(date), DateExt.getMonth(date), 1l, 0l, 0l, 0l, 0l, 0l);
    long weekDay = (DateTimeExt.weekday(firstDay)) % 7l;
    LocalDate firstDayDate =
        DateExt.of(firstDay.getYear(), DateTimeExt.month(firstDay), DateTimeExt.day(firstDay));
    return CalenderUtils.getListOfDates(firstDayDate, -weekDay, 42l - weekDay);
  }

  public static List<LocalDate> getListOfDates(LocalDate firstDay, long statsWith, long endsWith) {
    List<LocalDate> list_of_dates = ListExt.asList();
    for (long i = statsWith; i < endsWith; i++) {
      LocalDate day = DateExt.plusDays(firstDay, i);
      list_of_dates.add(day);
    }
    return list_of_dates;
  }

  public static List<Long> getYearsList(long year, boolean forward) {
    List<Long> list_of_years = ListExt.asList();
    list_of_years.add(year);
    if (forward) {
      for (long i = 1l; i < 12l; i++) {
        list_of_years.add(year + i);
      }
    } else {
      for (long i = 1l; i < 12l; i++) {
        ListExt.insert(list_of_years, 0l, (year - i));
      }
    }
    return list_of_years;
  }

  public static String getFormatedDate(LocalDate date, String format) {
    if (date == null) {
      return "";
    }
    LocalDate date1 = date != null ? date : DateExt.now();
    if (format != null && StringExt.getIsNotEmpty(format)) {
      DateTimeFormatter formatter = DateFormatExt.DateFormat(format, null);
      String formatedDate = formatter.format(DateExt.toDateTime(date1, null));
      if (formatedDate != null) {
        return formatedDate;
      } else {
        return date1.toString();
      }
    }
    return date1.toString();
  }

  public static String getFormatedDateFromDateTime(LocalDateTime date, String format) {
    LocalDateTime date1 = date != null ? date : LocalDateTime.now();
    if (format != null && StringExt.getIsNotEmpty(format)) {
      DateTimeFormatter formatter = DateFormatExt.DateFormat(format, null);
      String formatedDate = formatter.format(date1);
      if (formatedDate != null) {
        return formatedDate;
      } else {
        return date1.toString();
      }
    }
    return date1.toString();
  }

  public static String getFormatedTimeFromDateTime(LocalDateTime date, String format) {
    LocalDateTime date1 = date != null ? date : LocalDateTime.now();
    if (format != null && StringExt.getIsNotEmpty(format)) {
      DateTimeFormatter formatter = DateFormatExt.DateFormat(format, null);
      String formatedDate = formatter.format(date1);
      if (formatedDate != null) {
        return formatedDate;
      } else {
        return date1.toString();
      }
    }
    return date1.toString();
  }

  public static List<LocalDateTime> getWeekDayDates(LocalDateTime dateTime) {
    List<LocalDateTime> weekDayDates = ListExt.asList();
    //  Calculate the start of the current week (Monday)
    //  dateTime.weekday gives 1 for Monday, 2 for Tuesday, ... 7 for Sunday
    //  To find the previous Monday, we subtract (dateTime.weekday - 1) days
    LocalDateTime monday =
        DateTimeExt.subtract(
            dateTime, DurationExt.Duration(DateTimeExt.weekday(dateTime) - 1l, 0l, 0l, 0l, 0l, 0l));
    //  Loop to add the dates of the week
    for (long i = 0l; i < 7l; i++) {
      weekDayDates.add(DateTimeExt.add(monday, DurationExt.Duration(i, 0l, 0l, 0l, 0l, 0l)));
    }
    return weekDayDates;
  }
}
