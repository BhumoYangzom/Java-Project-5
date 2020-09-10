
import java.io.IOException;

import java.util.TreeMap;

import org.junit.Assert;

import org.junit.Test;

/**
 * Test for MapData class
 * 
 * @author apotsangyangzom
 * @version 2018-10-25
 */
public class MapDataTest
{

    MapData map = new MapData(2018, 8, 30, 17, 45, "data");
    TreeMap<String, Integer> paramPositions;
    // TreeMap<String, Statistics> treemap = new TreeMap<String, Statistics>();
    // Statistics maximum;
    // EnumMap<StatsType, TreeMap<String, Statistics>> statistics;
    // GregorianCalendar utcDateTime = new GregorianCalendar(2018, 8, 30, 17, 45,
    // 30);

    /**
     * Test for createFileName()
     */
    @Test
    public void testCreateFileName()
    {
        Assert.assertEquals("File does not exist.", "201808301745.mdf",
                map.createFileName(2018, 8, 30, 17, 45, "data"));
    }

    /**
     * Test for getIndexOf()
     * 
     * @throws NullPointerException
     * @throws IOException
     */
    @Test
    public void testGetIndexOf() throws NullPointerException, IOException
    {
        map.createFileName(2018, 8, 30, 17, 45, "data");
        map.parseFile();
        paramPositions = new TreeMap<String, Integer>();
        // String[] headers = { "STID", "STNM", "TIME", "RELH", "TAIR" };
        paramPositions.put("STID", 0);
        paramPositions.put("STNM", 1);
        paramPositions.put("TIME", 2);
        paramPositions.put("RELH", 3);
        paramPositions.put("TAIR", 4);

        Integer a = map.getIndexOf("STID");
        Integer b = map.getIndexOf("STNM");
        Integer c = map.getIndexOf("TIME");
        Integer d = map.getIndexOf("RELH");
        Integer e = map.getIndexOf("TAIR");

        Assert.assertEquals(a, map.getIndexOf("STID"));
        Assert.assertEquals(b, map.getIndexOf("STNM"));
        Assert.assertEquals(c, map.getIndexOf("TIME"));
        Assert.assertEquals(d, map.getIndexOf("RELH"));
        Assert.assertEquals(e, map.getIndexOf("TAIR"));

    }

    /*
     * @Test public void testGetStatistics() throws IOException {
     * map.createFileName(2018, 8, 30, 17, 45, "data"); map.parseFile(); maximum =
     * new Statistics(36.5, "HOOk", utcDateTime, 10, StatsType.MAXIMUM);
     * treemap.put("TAIR", maximum);
     * 
     * statistics.put(StatsType.MAXIMUM, treemap); double actual =
     * map.getStatistics(StatsType.MAXIMUM, "TAIR").getValue();
     * 
     * Assert.assertEquals("Failed to calculate or an error occured", actual,
     * map.getStatistics(StatsType.MAXIMUM, "TAIR").getValue(), 0.1);
     * 
     * }
     */
    /**
     * Test for toString()
     * 
     * @throws IOException
     */
    @Test
    public void testToString() throws IOException
    {
        map.createFileName(2018, 8, 30, 17, 45, "data");
        map.parseFile();
        String d = map.toString();
        System.out.println(d);

        Assert.assertEquals(d, map.toString());
    }

}
