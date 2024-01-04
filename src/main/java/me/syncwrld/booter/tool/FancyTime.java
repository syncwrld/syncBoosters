package me.syncwrld.booter.tool;

import lombok.Getter;

public class FancyTime {

  @Getter private final int days;
  @Getter private final int hours;
  @Getter private final int minutes;
  @Getter private final int seconds;

  public FancyTime(int days, int hours, int minutes, int seconds) {
    this.days = days;
    this.hours = hours;
    this.minutes = minutes;
    this.seconds = seconds;
  }

  public FancyTime(String string) {
    this.days = fromString(string).getDays();
    this.hours = fromString(string).getHours();
    this.minutes = fromString(string).getMinutes();
    this.seconds = fromString(string).getSeconds();
  }

  private FancyTime fromString(String s) {
    if (s == null || s.trim().isEmpty()) {
      throw new IllegalArgumentException("Input string cannot be null or empty.");
    }

    String[] inputArgs = s.split(" ");

    int days = 0, hours = 0, minutes = 0, seconds = 0;

    for (String current : inputArgs) {
      String lowerCaseCurrent = current.toLowerCase();

        int parsedInt = Integer.parseInt(lowerCaseCurrent.substring(0, lowerCaseCurrent.length() - 1));
        switch (lowerCaseCurrent.charAt(lowerCaseCurrent.length() - 1)) {
        case 's':
          seconds += parsedInt;
          break;
        case 'm':
          minutes += parsedInt;
          break;
        case 'h':
          hours += parsedInt;
          break;
        case 'd':
          days += parsedInt;
      }
    }

    return new FancyTime(days, hours, minutes, seconds);
  }

  public long toTicks() {
    int dayTicks = days * 60 * 60 * 24 * 20;
    int hourTicks = hours * 60 * 60 * 20;
    int minuteTicks = minutes * 60 * 20;
    int secondsTicks = seconds * 20;

    return (long) dayTicks + hourTicks + minuteTicks + secondsTicks;
  }

}
