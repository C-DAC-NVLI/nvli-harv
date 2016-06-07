/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author vootla
 */
public class DatesRelatedUtil {

    public static Date convertDateToGranularityFormat(String granularityFormat, String date) {
        Date d = null;
        try {
            SimpleDateFormat sdf = null;
            if ("YYYY-MM-DDThh:mm:ssZ".equals(granularityFormat)) {
                sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

            } else if ("YYYY-MM-DD".equals(granularityFormat)) {
                sdf = new SimpleDateFormat("yyyy-MM-dd");
            }
            if (sdf != null) {
                d = sdf.parse(date);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d;
    }

    public static Date getDateInUTCFormat(Date inputDate) throws ParseException {
        final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        final String TIME_ZONE = "UTC";

        TimeZone tz = TimeZone.getTimeZone(TIME_ZONE);
        DateFormat df1 = new SimpleDateFormat(DATE_FORMAT);
        df1.setTimeZone(tz);
        String date = df1.format(inputDate);

        DateFormat df2 = new SimpleDateFormat(DATE_FORMAT);
        return df2.parse(date);
    }

    public static String getISOFormat(Date inputDate) throws ParseException {
        final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        DateFormat df1 = new SimpleDateFormat(DATE_FORMAT);
        return df1.format(inputDate);

    }

    public static Date getCurrentDateTimeInUTCFormat() throws ParseException {
        final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        final String TIME_ZONE = "UTC";

        TimeZone tz = TimeZone.getTimeZone(TIME_ZONE);
        DateFormat df1 = new SimpleDateFormat(DATE_FORMAT);
        df1.setTimeZone(tz);
        String date = df1.format(new Date());

        DateFormat df2 = new SimpleDateFormat(DATE_FORMAT);
        return df2.parse(date);
    }
}
