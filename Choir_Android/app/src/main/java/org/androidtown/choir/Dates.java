package org.androidtown.choir;

import java.util.ArrayList;
import java.util.*;
import java.text.*;

/**
 * Created by astears on 11/27/17.
 */

public class Dates {
    public static ArrayList<Date> getDates() {
        Date now = new Date();
        ArrayList<Date> datesInRange = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd E");

        Date nextDate = getNextDate(now);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(nextDate);

        while (calendar.compareTo(endCalendar) <= 0) {
            Date result = calendar.getTime();

            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                datesInRange.add(result);
            }
            calendar.add(Calendar.DATE, 1);
        }

        /*for (Date d : datesInRange) {
            dateFormat = new SimpleDateFormat("E");
            System.out.print(dateFormat.format(d));

            dateFormat = new SimpleDateFormat("MM");
            System.out.print(" " + dateFormat.format(d));

            dateFormat = new SimpleDateFormat("dd");
            System.out.print(" " + dateFormat.format(d));
            System.out.println();
        }*/

        return datesInRange;
    }

    private static Date getNextDate(Date now) {

        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DATE, c.getMaximum(Calendar.DATE));

        Date lastDayOfNextMonth = c.getTime();

        return lastDayOfNextMonth;
    }
}

