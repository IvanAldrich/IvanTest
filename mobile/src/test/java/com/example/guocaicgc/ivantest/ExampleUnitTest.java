package com.example.guocaicgc.ivantest;

import android.os.Trace;
import android.text.format.Time;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private final static double SUN_POSITION = -0.8333;
    private final static double Degs = 57.2957795130823;
    private final static double Rads = 1.74532925199433E-02;

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

        List<Integer> items = new ArrayList<>();
        items.add(1);
        items.add(1);
        items.add(3);
        items.add(4);
        Iterator it = items.iterator();
        while (it.hasNext()) {
            Integer item = (Integer)it.next();
            if (item == 1) {
                items.remove(item);
            }
        }
        for (Integer item : items) {
            if (item == 1) {
                items.remove(item);
            }
        }
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) == 1) {
                items.remove(i);
            }
        }
        assertTrue(items.contains(1));
    }

    @Test
    public void testForRemove() throws Exception {
        List<String> items = new ArrayList<>();
        items.add("1");
        items.add("2");
        items.add("3");
        items.add("4");
        items.add("5");

        items.remove(null);
        boolean isFound = false;
        for (int i = 0; i < items.size(); i++) {
            String item = items.get(i);
            if (item.equals("3")) {
                isFound = true;
            }
            if (isFound) {
                items.remove(i--);
            }
            int count = items.size();

        }
    }

    @Test
    public void testCoumputeSunUpAndDown() throws Exception {
        Trace.beginSection("computeTime");
        coumputeSunUpAndDown(2016, 1, 2, 116.47932797670366, 39.994550577167885, false);
        Trace.endSection();
    }

    private static double coumputeSunUpAndDown(int wYear, int wMonth, int wDay,
                                               double dX, double dY, boolean bIsSunRise) {
        // 累加本月份前的总天数
        int iTotalDays = 0;
        for (int iLoop = 1; iLoop < wMonth; iLoop++) {
            int iDaysOfMonth = 0;
            switch (iLoop) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12: {
                    iDaysOfMonth = 31;
                }
                break;
                case 4:
                case 6:
                case 9:
                case 11: {
                    iDaysOfMonth = 30;
                }
                break;
                case 2: {
                    boolean isLongYear = false;
                    if ((wYear % 4) != 0) {
                        isLongYear = false;
                    } else if ((wYear % 4) == 0 && (wYear % 100) != 0) {
                        isLongYear = true;
                    } else if ((wYear % 100) == 0 && (wYear % 400) == 0) {
                        isLongYear = true;
                    }

                    if (isLongYear) {
                        iDaysOfMonth = 29;
                    } else {
                        iDaysOfMonth = 28;
                    }
                }
                break;
                default:
                    break;
            }
            iTotalDays += iDaysOfMonth;
        }
        // 累加本月份的天数至总天数
        iTotalDays += wDay;

        int iSign;
        if (bIsSunRise) {
            iSign = 1;
        } else {
            iSign = -1;
        }

        // 计算日升或日落的时间
        double UT0 = 180;
        double UT = 180;
        do {
            UT0 = UT;
            double L = 280.460 + 360.0077 * iTotalDays / 365.25;
            double G = 357.528 + 359.9905 * iTotalDays / 365.25;
            double Lh = L + 1.915 * Math.sin(G * Rads) + 0.020
                    * Math.sin(2 * G * Rads);
            double earthjiaodu = 23.4393 - 0.013 * iTotalDays / 36525;
            double suncha = Math.asin(Math.sin(earthjiaodu * Rads)
                    * Math.sin(Lh * Rads))
                    * Degs;
            double suntime = UT0 - 180 - 1.915 * Math.sin(G * Rads) - 0.020
                    * Math.sin(2 * G * Rads) + 2.466 * Math.sin(2 * Lh * Rads)
                    - 0.053 * Math.sin(4 * Lh * Rads);
            // double xiuzheng = Math.acos((Math.sin(SUN_POSITION * Rads) - Math
            // .sin(dY * Rads) * Math.sin(suncha * Rads))
            // / (Math.cos(dY * Rads) * Math.cos(suncha * Rads)))
            // * Degs;

            double xiuzheng = Math.acos((Math.sin(SUN_POSITION * Rads) - Math
                    .sin(dY * Rads) * Math.sin(suncha * Rads))
                    / (Math.cos(dY * Rads) * Math.cos(suncha * Rads)))
                    * Degs;
            UT = UT0 - (suntime + dX + iSign * xiuzheng);
        } while (Math.abs(UT - UT0) > 0.1);

        UT = UT / 15 + 8;
        return UT;
    }

    private static double coumputeSunUpAndDownMine(int iTotalDays,
                                                   double dX, double dY, boolean bIsSunRise) {
        int iSign;
        if (bIsSunRise) {
            iSign = 1;
        } else {
            iSign = -1;
        }

        // 计算日升或日落的时间
        double UT0 = 180;
        double UT = 180;
        do {
            UT0 = UT;
            double L = 280.460 + 360.0077 * iTotalDays / 365.25;
            double G = 357.528 + 359.9905 * iTotalDays / 365.25;
            double Lh = L + 1.915 * Math.sin(G * Rads) + 0.020
                    * Math.sin(2 * G * Rads);
            double earthjiaodu = 23.4393 - 0.013 * iTotalDays / 36525;
            double suncha = Math.asin(Math.sin(earthjiaodu * Rads)
                    * Math.sin(Lh * Rads))
                    * Degs;
            double suntime = UT0 - 180 - 1.915 * Math.sin(G * Rads) - 0.020
                    * Math.sin(2 * G * Rads) + 2.466 * Math.sin(2 * Lh * Rads)
                    - 0.053 * Math.sin(4 * Lh * Rads);
            // double xiuzheng = Math.acos((Math.sin(SUN_POSITION * Rads) - Math
            // .sin(dY * Rads) * Math.sin(suncha * Rads))
            // / (Math.cos(dY * Rads) * Math.cos(suncha * Rads)))
            // * Degs;

            double xiuzheng = Math.acos((Math.sin(SUN_POSITION * Rads) - Math
                    .sin(dY * Rads) * Math.sin(suncha * Rads))
                    / (Math.cos(dY * Rads) * Math.cos(suncha * Rads)))
                    * Degs;
            UT = UT0 - (suntime + dX + iSign * xiuzheng);
        } while (Math.abs(UT - UT0) > 0.1);

        UT = UT / 15 + 8;
        return UT;
    }

    public static boolean isDay(boolean isValidGps, double dX, double dY,
                                long timeMills) {
        int wYear = 0, wMonth = 0, wDay = 0, wHour = 0, wMinute = 0;

        if (isValidGps && timeMills > 0) {// GPS是否有效,取得GPS时间
            Time time = new Time();
            time.set(timeMills);
            wYear = time.year;
            wMonth = time.month + 1;
            wDay = time.monthDay;
            wHour = time.hour;
            wMinute = time.minute;
        } else {
            Time t = new Time();
            t.setToNow();
            wYear = t.year;
            wMonth = t.month + 1;
            wDay = t.monthDay;
            wHour = t.hour;
            wMinute = t.minute;
        }

        double sunup = coumputeSunUpAndDown(wYear, wMonth, wDay, dX, dY, true); // 计算当天的太阳升起时间
        double sundown = coumputeSunUpAndDown(wYear, wMonth, wDay, dX, dY,
                false); // 计算当天的太阳降落时间
        double curtime = wHour + (double) wMinute / 60;
        if (curtime >= sunup && curtime < sundown) // 比较当前时间是否在白天范围内
        {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isDayMine(boolean isValidGps, double dX, double dY,
                                    long timeMills) {
        int wHour = 0, wMinute = 0;

        Calendar calendar = Calendar.getInstance();
        if (isValidGps && timeMills > 0) {// GPS是否有效,取得GPS时间
            calendar.setTimeInMillis(timeMills);
        }
        wHour = calendar.get(Calendar.HOUR_OF_DAY);
        wMinute = calendar.get(Calendar.MINUTE);

        double sunup = 0;//coumputeSunUpAndDownMine(wYear, wMonth, wDay, dX, dY, true); // 计算当天的太阳升起时间
        double sundown = 0;//coumputeSunUpAndDown(wYear, wMonth, wDay, dX, dY,
//                false); // 计算当天的太阳降落时间
        double curtime = wHour + (double) wMinute / 60;
        if (curtime >= sunup && curtime < sundown) // 比较当前时间是否在白天范围内
        {
            return true;
        } else {
            return false;
        }
    }
}