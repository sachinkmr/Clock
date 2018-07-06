package assignment.clocks.digital.roman;

import assignment.clocks.digital.DigitalClock;
import assignment.utils.RomanUtils;

import java.time.LocalTime;
import java.time.ZoneId;

public class RomanDigitalClock extends DigitalClock {
    @Override
    public String getHourString() {
        int hour = LocalTime.now(ZoneId.systemDefault()).getHour();
        return pad(4, hour == 0 ? 12 : hour > 12 ? hour - 12 : hour);
    }

    @Override
    public String getMinuteString() {
        return pad(7, LocalTime.now(ZoneId.systemDefault()).getMinute());
    }

    @Override
    public String getSecondString() {
        return pad(7, LocalTime.now(ZoneId.systemDefault()).getSecond());
    }


    @Override
    public String pad(int paddingCount, int number) {
        StringBuilder sb = new StringBuilder();
        String str = number == 0 ? "0" : RomanUtils.toRoman(number);
        for (int i = str.length(); i < 7; i++) {
            sb.append(' ');
        }
        sb.append(str);
        return sb.toString();
    }
}
