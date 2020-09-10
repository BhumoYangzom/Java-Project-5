import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for Statistics class
 * 
 * @author apotsangyangzom
 * @version 2018-10-25
 */
public class StatisticsTest
{
    // ZoneId zone = null;
    protected final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss z";
    GregorianCalendar utcDateTime = new GregorianCalendar(2018, 8, 30, 17, 45, 30);
    // ZonedDateTime zdtDateTime = new ZonedDateTime(2018, 8, 30, 17, 45, 30, 00,
    // zone);
    ZonedDateTime zdtDateTime;
    String DateTimeStr = "2018-08-30T:00:00 UTC";
    String dateTime = "2011-12-03T10:15:30+01:00[Europe/Paris]";// "2016-08-30T02:00:00. 145Z";
    String dateTime1 = "2019-08-30T02:00:00. 145Z";
    StatsType statstype;
    Statistics s = new Statistics(22.5, "JAYX", zdtDateTime, 10, statstype);
    Statistics s1 = new Statistics(22.5, "JAYX", zdtDateTime, 10, statstype);
    Statistics stat = new Statistics(22.5, "JAYX", utcDateTime, 10, statstype);

    /**
     * Test for toString method
     */
    @Test
    public void testToString()
    {
        String expected = null;
        String actual = stat.toString();
        assertEquals(expected, actual);
    }

    /**
     * test for CreateDateFromString
     * 
     * @throws ParseException
     */
    @Test
    public void testCreateDateFromString() throws ParseException
    {

        SimpleDateFormat f = new SimpleDateFormat(s.DATE_TIME_FORMAT);
        Date d = f.parse("2018-08-30T17:45:30 UTC");
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(d);
        GregorianCalendar expected = s.createDateFromString("2018-08-30T17:45:30 UTC");
        assertEquals(expected, (GregorianCalendar) f.getCalendar());

    }

    /**
     * Test for createZDateFromString()
     */

    @Test
    public void testCreateZDateFromString()
    {
        ZonedDateTime date = s.createZDateFromString("2016-07-24T18:47:36Z");
        ZonedDateTime expected = ZonedDateTime.parse("2016-07-24T18:47:36Z");
        Assert.assertEquals(expected, date);
    }

    /**
     * Test for createStringFromDate with GregorianCalendar
     */

    @Test
    public void testCreateStringFromDateGregorianCalendar()
    {
        Assert.assertEquals("2018-08-30T:00:00 UTC", DateTimeStr);
    }

    /**
     * Test for createStringFromDate with ZonedDateTime()
     */

    @Test
    public void testCreateStringFromDateZonedDateTime()
    {
        Assert.assertEquals("2018-08-30T:00:00 UTC", DateTimeStr);
    }

    /**
     * Test for getNumberOfReportingStations
     */

    @Test
    public void testGetNumberOfReportingStations()
    {
        int expected = 10;
        int actual = s.getNumberOfReportingStations();
        assertEquals("Error", expected, actual);

    }

    /**
     * Test for getUTCDateTimeString
     */
    @Test
    public void testGetUTCDateTimeString()
    {
        String expected = "gregory";

        assertEquals("Faiure", expected, stat.getUTCDateTimeString());

    }

    /**
     * Test for newerThanGregorianCalendar()
     */
    @Test
    public void testNewerThanGregorianCalendar()
    {
        GregorianCalendar newer = new GregorianCalendar(2017, 8, 30, 17, 45, 30);

        boolean expected = true;
        boolean actual = newer.before(utcDateTime);
        Assert.assertEquals(expected, actual);

    }

    /**
     * Test for olderThanGregorianCalendar()
     */

    @Test
    public void testOlderThanGregorianCalendar()
    {
        GregorianCalendar newer = new GregorianCalendar(2017, 8, 30, 17, 45, 30);

        boolean expected = false;
        boolean actual = newer.after(utcDateTime);
        Assert.assertEquals(expected, actual);
    }

    /**
     * Test for sameAsGregorianCalendar()
     */

    @Test
    public void testSameAsGregorianCalendar()
    {
        GregorianCalendar sameDate = new GregorianCalendar(2018, 8, 30, 17, 45, 30);

        boolean expected = true;
        boolean actual = sameDate.equals(utcDateTime);
        Assert.assertEquals(expected, actual);

    }

    /**
     * Test for newerThanZonedDateTime()
     */

    @Test
    public void testNewerThanZonedDateTime()
    {

        ZonedDateTime newer = s.createZDateFromString("2016-07-24T18:47:36Z");
        ZonedDateTime older = s1.createZDateFromString("2015-08-24T18:47:36Z");
        Statistics obj = new Statistics(31.3, "TAIR", older, 1, StatsType.AVERAGE);

        boolean expected = true;
        boolean actual = obj.newerThan(newer);
        Assert.assertEquals(expected, actual);

    }

    /**
     * Test for olderThan with ZonedDateTime
     */

    @Test
    public void testOlderThanZonedDateTime()
    {
        // ZonedDateTime newer = s.createZDateFromString("2016-07-24T18:47:36Z");
        ZonedDateTime older = s1.createZDateFromString("2015-08-24T18:47:36Z");
        Statistics obj = new Statistics(31.3, "TAIR", older, 1, StatsType.AVERAGE);

        boolean expected = false;
        boolean actual = obj.olderThan(older);
        Assert.assertEquals(expected, actual);

    }

    /**
     * Test for sameAs comparing ZonedDateTime.
     */
    @Test
    public void testSameAsZonedDateTime()
    {
        ZonedDateTime newer = s.createZDateFromString("2015-08-24T18:47:36Z");
        ZonedDateTime older = s1.createZDateFromString("2015-08-24T18:47:36Z");
        Statistics obj = new Statistics(31.3, "TAIR", older, 1, StatsType.AVERAGE);

        boolean expected = true;
        boolean actual = obj.sameAs(newer);
        Assert.assertEquals(expected, actual);

    }

}
