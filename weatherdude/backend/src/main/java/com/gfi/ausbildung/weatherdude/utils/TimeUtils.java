package com.gfi.ausbildung.weatherdude.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.ToIntFunction;

public class TimeUtils
{
  public static int getSecond(LocalDateTime date)
  {
    return date.getSecond();
  }

  public static int getMinute(LocalDateTime date)
  {
    return date.getMinute();
  }

  public static int getHour(LocalDateTime date)
  {
    return date.getHour();
  }

  public static int getDay(LocalDateTime date)
  {
    return date.getDayOfMonth();
  }

  public static int getMonth(LocalDateTime date)
  {
    return date.getMonthValue();
  }

  public static int getYear(LocalDateTime date)
  {
    return date.getYear();
  }

  public static int calculateDifference(LocalDateTime a, LocalDateTime b, ChronoUnit unit)
  {
    switch (unit)
    {
      case SECONDS:
        return getDiff(a, b, TimeUtils::getSecond);
      case MINUTES:
        return getDiff(a, b, TimeUtils::getMinute);
      case HOURS:
        return getDiff(a, b, TimeUtils::getHour);
      case DAYS:
        return getDiff(a, b, TimeUtils::getDay);
      case MONTHS:
        return getDiff(a, b, TimeUtils::getMonth);
      case YEARS:
        return getDiff(a, b, TimeUtils::getYear);
      default:
        break;
    }

    throw new IllegalArgumentException("Invalid time unit!");
  }

  private static int getDiff(LocalDateTime a, LocalDateTime b, ToIntFunction<LocalDateTime> function)
  {
    int aVal = function.applyAsInt(a);
    int bVal = function.applyAsInt(b);
    return Math.abs(bVal - aVal);
  }

  public static int calculateSecondDifference(LocalDateTime a, LocalDateTime b)
  {
    int minuteDiff = calculateMinuteDifference(a, b);
    int minuteInSecondDiff = minuteDiff * 60;

    int secondDiff = calculateDifference(a, b, ChronoUnit.SECONDS);

    return minuteInSecondDiff + secondDiff;
  }

  public static int calculateMinuteDifference(LocalDateTime a, LocalDateTime b)
  {
    int hourDiff = calculateHourDifference(a, b);
    int hourInMinutesDiff = hourDiff * 60;

    int minuteDiff = calculateDifference(a, b, ChronoUnit.MINUTES);

    return hourInMinutesDiff + minuteDiff;
  }

  public static int calculateHourDifference(LocalDateTime a, LocalDateTime b)
  {
    int dayDiff = calculateDayDifference(a, b);
    int dayInHourDiff = dayDiff * 24;

    int hourDiff = calculateDifference(a, b, ChronoUnit.HOURS);

    return dayInHourDiff + hourDiff;
  }

  public static int calculateDayDifference(LocalDateTime a, LocalDateTime b)
  {
    return calculateDifference(a, b, ChronoUnit.DAYS);
  }

  public static int calculateMonthDifference(LocalDateTime a, LocalDateTime b)
  {
    return calculateDifference(a, b, ChronoUnit.MONTHS);
  }

  public static int calculateYearDifference(LocalDateTime a, LocalDateTime b)
  {
    return calculateDifference(a, b, ChronoUnit.YEARS);
  }

}
